package ch.beerpro.data.repositories;

import android.util.Pair;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import androidx.lifecycle.LiveData;
import ch.beerpro.domain.models.Beer;
import ch.beerpro.domain.models.Entity;
import ch.beerpro.domain.models.Fridge;
import ch.beerpro.domain.utils.FirestoreQueryLiveData;
import ch.beerpro.domain.utils.FirestoreQueryLiveDataArray;

import static androidx.lifecycle.Transformations.map;
import static androidx.lifecycle.Transformations.switchMap;
import static ch.beerpro.domain.utils.LiveDataExtensions.combineLatest;

public class FridgeRepository {


    private static LiveData<List<Fridge>> getFridgeByUser(String userId) {
        return new FirestoreQueryLiveDataArray<>(FirebaseFirestore.getInstance().collection(Fridge.COLLECTION)
                .orderBy(Fridge.FIELD_ADDED_AT, Query.Direction.DESCENDING).whereEqualTo(Fridge.FIELD_USER_ID, userId),
                Fridge.class);
    }

    private static LiveData<Fridge> getUserFridgeFor(Pair<String, Beer> input) {
        String userId = input.first;
        Beer beer = input.second;
        DocumentReference document = FirebaseFirestore.getInstance().collection(Fridge.COLLECTION)
                .document(Fridge.generateId(userId, beer.getId()));
        return new FirestoreQueryLiveData<>(document, Fridge.class);
    }

    public Task<Void> toggleUserFridgelistItem(String userId, String itemId) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String fridgeId = Fridge.generateId(userId, itemId);

        DocumentReference fridgeQuery = db.collection(Fridge.COLLECTION).document(fridgeId);

        return fridgeQuery.get().continueWithTask(task -> {
            if (task.isSuccessful() && task.getResult().exists()) {
                return fridgeQuery.delete();
            } else if (task.isSuccessful()) {
                return fridgeQuery.set(new Fridge(userId, itemId, new Date()));
            } else {
                throw task.getException();
            }
        });
    }

    public LiveData<List<Pair<Fridge, Beer>>> getMyFridgeWithBeers(LiveData<String> currentUserId,
                                                                   LiveData<List<Beer>> allBeers) {
        return map(combineLatest(getMyFridge(currentUserId), map(allBeers, Entity::entitiesById)), input -> {
            List<Fridge> fridges = input.first;
            HashMap<String, Beer> beersById = input.second;

            ArrayList<Pair<Fridge, Beer>> result = new ArrayList<>();
            for (Fridge fridge : fridges) {
                Beer beer = beersById.get(fridge.getBeerId());
                result.add(Pair.create(fridge, beer));
            }
            return result;
        });
    }

    public LiveData<List<Fridge>> getMyFridge(LiveData<String> currentUserId) {
        return switchMap(currentUserId, FridgeRepository::getFridgeByUser);
    }


    public LiveData<Fridge> getMyFridgeForBeer(LiveData<String> currentUserId, LiveData<Beer> beer) {


        return switchMap(combineLatest(currentUserId, beer), FridgeRepository::getUserFridgeFor);
    }


}

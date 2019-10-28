package ch.beerpro.data.repositories;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Date;
import java.util.List;

import androidx.lifecycle.LiveData;
import ch.beerpro.domain.models.Price;
import ch.beerpro.domain.utils.FirestoreQueryLiveDataArray;

import static androidx.lifecycle.Transformations.switchMap;

public class PriceRepository {

    private final FirestoreQueryLiveDataArray<Price> allPrices = new FirestoreQueryLiveDataArray<>(
            FirebaseFirestore.getInstance().collection(Price.COLLECTION)
                    .orderBy(Price.FIELD_CREATION_DATE, Query.Direction.DESCENDING), Price.class);

    public static LiveData<List<Price>> getPricesByUser(String userId) {
        return new FirestoreQueryLiveDataArray<>(FirebaseFirestore.getInstance().collection(Price.COLLECTION)
                .orderBy(Price.FIELD_CREATION_DATE, Query.Direction.DESCENDING)
                .whereEqualTo(Price.FIELD_USER_ID, userId), Price.class);
    }

    public static LiveData<List<Price>> getPricesByUserAndBeer(String userId, String beerId) {
        return new FirestoreQueryLiveDataArray<>(FirebaseFirestore.getInstance().collection(Price.COLLECTION)
                .orderBy(Price.FIELD_CREATION_DATE, Query.Direction.DESCENDING)
                .whereEqualTo(Price.FIELD_USER_ID, userId)
                .whereEqualTo(Price.FIELD_BEER_ID, beerId), Price.class);
    }

    public LiveData<List<Price>> getMyPrices(LiveData<String> currentUserId) {
        return switchMap(currentUserId, PriceRepository::getPricesByUser);
    }

    public Task<Void> updatePrice(String userId, String itemId, String price) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String priceID = Price.generateId(userId, itemId);

        DocumentReference PriceEntryQuery = db.collection(Price.COLLECTION).document(priceID);

        return PriceEntryQuery.get().continueWithTask(task -> {
            if (task.isSuccessful() && task.getResult().exists()) {
                return PriceEntryQuery.update(Price.FIELD_PRICE,price);
            } else if (task.isSuccessful()) {
                return PriceEntryQuery.set(new Price(priceID, itemId, userId, price, new Date()));
            } else {
                throw task.getException();
            }
        });
    }

}

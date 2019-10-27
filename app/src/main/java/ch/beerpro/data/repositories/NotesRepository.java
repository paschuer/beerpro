package ch.beerpro.data.repositories;

import android.util.Pair;

import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import ch.beerpro.domain.models.Note;
import ch.beerpro.domain.models.Wish;
import ch.beerpro.domain.utils.FirestoreQueryLiveDataArray;

import static androidx.lifecycle.Transformations.map;
import static androidx.lifecycle.Transformations.switchMap;
import static ch.beerpro.domain.utils.LiveDataExtensions.combineLatest;

public class NotesRepository {

    private final FirestoreQueryLiveDataArray<Note> allNotes = new FirestoreQueryLiveDataArray<>(
            FirebaseFirestore.getInstance().collection(Note.COLLECTION)
                    .orderBy(Note.FIELD_CREATION_DATE, Query.Direction.DESCENDING), Note.class);

    public static LiveData<List<Note>> getNotesByUser(String userId) {
        return new FirestoreQueryLiveDataArray<>(FirebaseFirestore.getInstance().collection(Note.COLLECTION)
                .orderBy(Note.FIELD_CREATION_DATE, Query.Direction.DESCENDING)
                .whereEqualTo(Note.FIELD_USER_ID, userId), Note.class);
    }

    public static LiveData<List<Note>> getNotesByUserAndBeer(String userId, String beerId) {
        return new FirestoreQueryLiveDataArray<>(FirebaseFirestore.getInstance().collection(Note.COLLECTION)
                .orderBy(Note.FIELD_CREATION_DATE, Query.Direction.DESCENDING)
                .whereEqualTo(Note.FIELD_USER_ID, userId)
                .whereEqualTo(Note.FIELD_BEER_ID, beerId), Note.class);
    }

    public LiveData<List<Note>> getMyNotes(LiveData<String> currentUserId) {
        return switchMap(currentUserId, NotesRepository::getNotesByUser);
    }

    public Task<Void> updateNote(String userId, String itemId, String NoteText) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String noteID = Note.generateId(userId, itemId);

        DocumentReference NoteEntryQuery = db.collection(Note.COLLECTION).document(noteID);

        return NoteEntryQuery.get().continueWithTask(task -> {
            if (task.isSuccessful() && task.getResult().exists()) {
                return NoteEntryQuery.update(Note.FIELD_NOTE_TEXT,NoteText);
            } else if (task.isSuccessful()) {
                return NoteEntryQuery.set(new Note(noteID, itemId, userId, NoteText, new Date()));
            } else {
                throw task.getException();
            }
        });
    }

}

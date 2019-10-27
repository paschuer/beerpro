package ch.beerpro.presentation.details.createrating;

import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import ch.beerpro.data.repositories.BeersRepository;
import ch.beerpro.data.repositories.LikesRepository;
import ch.beerpro.data.repositories.RatingsRepository;
import ch.beerpro.data.repositories.WishlistRepository;
import ch.beerpro.domain.models.Beer;
import ch.beerpro.domain.models.Note;
import ch.beerpro.presentation.utils.EntityClassSnapshotParser;
import ch.beerpro.data.repositories.NotesRepository;

import static androidx.lifecycle.Transformations.map;

public class CreateNoteViewModel extends ViewModel {

    private static final String TAG = "CreatNoteViewModel";


    private final MutableLiveData<String> beerId = new MutableLiveData<>();

    private EntityClassSnapshotParser<Note> parser = new EntityClassSnapshotParser<>(Note.class);
    private Beer item;

    public CreateNoteViewModel(){


    }

    public Beer getItem() {
        return item;
    }

    public void setItem(Beer item) {
        this.item = item;
    }

    public LiveData<List<Note>> getNotes() {
        NotesRepository notesRepository = new NotesRepository();
        return notesRepository.getNotesByUserAndBeer(FirebaseAuth.getInstance().getCurrentUser().getUid(),item.getId());
    }

    public Task<Void> saveNote(Beer item, String note) {

        NotesRepository notesRepository = new NotesRepository();
        return notesRepository.updateNote(FirebaseAuth.getInstance().getCurrentUser().getUid(),item.getId(),note);

      /*  FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;

        Note newNote = new Note(null, item.getId(), user.getUid(), note, new Date());
        Log.i(TAG, "Adding new note: " + newNote.toString());


        return FirebaseFirestore.getInstance().collection("notes").add(newNote).continueWithTask(task -> {
            if (task.isSuccessful()) {
                return task.getResult().get();
            } else {
                throw task.getException();
            }
        }).continueWithTask(task -> {

            if (task.isSuccessful()) {
                return Tasks.forResult(parser.parseSnapshot(task.getResult()));
            } else {
                throw task.getException();
            }
        });*/
    }

}
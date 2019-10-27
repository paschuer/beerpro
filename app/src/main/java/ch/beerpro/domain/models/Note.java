package ch.beerpro.domain.models;

import com.google.firebase.firestore.Exclude;

import java.util.Date;
import java.util.Map;

import androidx.annotation.NonNull;

public class Note implements Entity {
    public static final String COLLECTION = "notes";
    public static final String FIELD_BEER_ID = "beerId";
    public static final String FIELD_USER_ID = "userId";
    public static final String FIELD_NOTE_TEXT = "note";
    public static final String FIELD_CREATION_DATE = "creationDate";

    @Exclude
    private String id;
    private String beerId;
    private String userId;
    private String note;

    private Date creationDate;

    public Note(String id, String beerId,String userId, String note, Date creationDate) {
        this.id = id;
        this.beerId = beerId;
        this.userId = userId;
        this.note = note;
        this.creationDate = creationDate;
    }

    public static String generateId(String userId, String beerId) {
        return String.format("%s_%s", userId, beerId);
    }

    public Note() {
    }

    public String getId() {
        return this.id;
    }

    public String getBeerId() {
        return this.beerId;
    }


    public String getUserId() {
        return this.userId;
    }

    public String getNote() {
        return this.note;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBeerId(String beerId) {
        this.beerId = beerId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Note)) return false;
        final Note other = (Note) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$beerId = this.getBeerId();
        final Object other$beerId = other.getBeerId();
        if (this$beerId == null ? other$beerId != null : !this$beerId.equals(other$beerId))
            return false;
        final Object this$userId = this.getUserId();
        final Object other$userId = other.getUserId();
        if (this$userId == null ? other$userId != null : !this$userId.equals(other$userId))
            return false;
        final Object this$note= this.getNote();
        final Object other$note = other.getNote();
        if (this$note == null ? other$note != null : !this$note.equals(other$note))
            return false;
        final Object this$creationDate = this.getCreationDate();
        final Object other$creationDate = other.getCreationDate();
        return this$creationDate == null ? other$creationDate == null : this$creationDate.equals(other$creationDate);
    }

    private boolean canEqual(final Object other) {
        return other instanceof Note;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $beerId = this.getBeerId();
        result = result * PRIME + ($beerId == null ? 43 : $beerId.hashCode());
        final Object $userId = this.getUserId();
        result = result * PRIME + ($userId == null ? 43 : $userId.hashCode());
        final Object $note = this.getNote();
        result = result * PRIME + ($note == null ? 43 : $note.hashCode());
        final Object $creationDate = this.getCreationDate();
        result = result * PRIME + ($creationDate == null ? 43 : $creationDate.hashCode());
        return result;
    }

    @NonNull
    public String toString() {
        return "Note(id=" + this.getId() + ", beerId=" + this.getBeerId() + ", userId=" + this.getUserId() + ", note=" + this.getNote()  + ", creationDate=" + this.getCreationDate() + ")";
    }
}

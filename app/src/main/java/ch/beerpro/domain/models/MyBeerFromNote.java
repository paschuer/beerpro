package ch.beerpro.domain.models;

import java.util.Date;

import androidx.annotation.NonNull;

public class MyBeerFromNote implements MyBeer {
    private Note note;
    private Beer beer;

    public MyBeerFromNote(Note note, Beer beer) {
        this.note = note;
        this.beer = beer;
    }

    @Override
    public String getBeerId() {
        return note.getBeerId();
    }

    @Override
    public Date getDate() {
        return note.getCreationDate();
    }

    public Note getNote() {
        return this.note;
    }

    public Beer getBeer() {
        return this.beer;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public void setBeer(Beer beer) {
        this.beer = beer;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof MyBeerFromNote)) return false;
        final MyBeerFromNote other = (MyBeerFromNote) o;
        if (!other.canEqual(this)) return false;
        final Object this$note = this.getNote();
        final Object other$note = other.getNote();
        if (this$note == null ? other$note != null : !this$note.equals(other$note))
            return false;
        final Object this$beer = this.getBeer();
        final Object other$beer = other.getBeer();
        return this$beer == null ? other$beer == null : this$beer.equals(other$beer);
    }

    private boolean canEqual(final Object other) {
        return other instanceof MyBeerFromNote;
    }

    public int hashCode() {
        final int PRIME = 59;
        int note = 1;
        final Object $note = this.getNote();
        note = note * PRIME + ($note == null ? 43 : $note.hashCode());
        final Object $beer = this.getBeer();
        note = note * PRIME + ($beer == null ? 43 : $beer.hashCode());
        return note;
    }

    @NonNull
    public String toString() {
        return "MyBeerFromNote(note=" + this.getNote() + ", beer=" + this.getBeer() + ")";
    }
}

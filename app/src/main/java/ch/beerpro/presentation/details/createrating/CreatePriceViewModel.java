package ch.beerpro.presentation.details.createrating;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import ch.beerpro.data.repositories.PriceRepository;
import ch.beerpro.domain.models.Beer;
import ch.beerpro.domain.models.Price;
import ch.beerpro.presentation.utils.EntityClassSnapshotParser;

public class CreatePriceViewModel extends ViewModel {

    private static final String TAG = "CreatPriceViewModel";


    private final MutableLiveData<String> beerId = new MutableLiveData<>();

    private EntityClassSnapshotParser<Price> parser = new EntityClassSnapshotParser<>(Price.class);
    private Beer item;

    public CreatePriceViewModel(){


    }

    public Beer getItem() {
        return item;
    }

    public void setItem(Beer item) {
        this.item = item;
    }

    public LiveData<List<Price>> getPrice() {
        PriceRepository priceRepository = new PriceRepository();
        return priceRepository.getPricesByUserAndBeer(FirebaseAuth.getInstance().getCurrentUser().getUid(),item.getId());
    }

    public Task<Void> savePrice(Beer item, String price) {

        PriceRepository priceRepository = new PriceRepository();
        return priceRepository.updatePrice(FirebaseAuth.getInstance().getCurrentUser().getUid(),item.getId(),price);

    }

}
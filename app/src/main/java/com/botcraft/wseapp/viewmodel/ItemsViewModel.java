package com.botcraft.wseapp.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.botcraft.wseapp.model.Electronics;
import com.botcraft.wseapp.repository.ItemsRepository;

import java.util.List;

public class ItemsViewModel extends AndroidViewModel {

    private LiveData<List<Electronics>> listLiveData;

    public ItemsViewModel(Application application) {
        super(application);
        listLiveData = new ItemsRepository(application).populateData();
    }


    public LiveData<List<Electronics>> getItems() {
        return listLiveData;
    }

}

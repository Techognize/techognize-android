package com.example.techognize.ui.marketplace;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MarketplaceViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MarketplaceViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is marketplace fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
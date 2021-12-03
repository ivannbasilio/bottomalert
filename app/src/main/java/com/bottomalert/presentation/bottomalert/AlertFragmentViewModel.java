package com.bottomalert.presentation.bottomalert;

import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AlertFragmentViewModel extends ViewModel {
    public MutableLiveData confirmEvent = new MutableLiveData();
    public MutableLiveData cancelEvent  = new MutableLiveData();

    public ObservableField<String> title = new ObservableField<>();
    public ObservableField<String> message = new ObservableField<>();
    public ObservableField<String> confirmButtonLabel = new ObservableField<>();
    public ObservableField<String> cancelButtonLabel  = new ObservableField<>();

    public AlertFragmentViewModel() { }

    public MutableLiveData watchConfirmClickEvent() {
        return confirmEvent;
    }

    public MutableLiveData watchCancelClickEvent() {
        return cancelEvent;
    }

    public void confirmClickEvent() {
        confirmEvent.postValue(null);
    }

    public void cancelClickEvent() {
        cancelEvent.postValue(null);
    }
}

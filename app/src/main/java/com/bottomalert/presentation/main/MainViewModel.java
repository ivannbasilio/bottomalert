package com.bottomalert.presentation.main;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sun.istack.Nullable;

public class MainViewModel extends ViewModel {
    public MutableLiveData<Nullable> showAlertEventClick = new MutableLiveData();

    public ObservableField<String> title = new ObservableField<>();
    public ObservableField<String> message = new ObservableField<>();
    public ObservableField<String> confirmButtonLabel = new ObservableField<>();
    public ObservableField<String> cancelButtonLabel  = new ObservableField<>();

    public MainViewModel() { }

    public MutableLiveData<Nullable> watchShowAlertClickEvent() {
        return showAlertEventClick;
    }

    public void showAlertClickEvent() {
        showAlertEventClick.postValue(null);
    }
}

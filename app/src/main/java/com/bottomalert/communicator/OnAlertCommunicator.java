package com.bottomalert.communicator;

public interface OnAlertCommunicator {
    void onConfirmClick(int requestCode);
    void onCancelClick(int requestCode);
}

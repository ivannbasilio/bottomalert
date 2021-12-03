package com.bottomalert.presentation.main;

import android.os.Bundle;
import android.widget.Toast;

import com.bottomalert.R;
import com.bottomalert.communicator.OnAlertCommunicator;
import com.bottomalert.databinding.ActivityMainBinding;
import com.bottomalert.presentation.bottomalert.AlertFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity implements OnAlertCommunicator {
    private static final int ALERT_REQUEST_CODE = 3006;

    private MainViewModel mViewModel;
    private AlertFragment mAlertFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_main
        );
        setSupportActionBar(binding.toolbar);

        mViewModel = new ViewModelProvider(this)
                .get(MainViewModel.class);

        watchShowAlertEventClick();
    }

    private void watchShowAlertEventClick() {
        mViewModel.watchShowAlertClickEvent()
                .observe(this, nullable -> {
                    showAlert();
                });
    }

    private void showAlert() {
        String title = mViewModel.title.get();
        String message = mViewModel.message.get();
        String confirmButtonLabel = mViewModel.confirmButtonLabel.get();
        String cancelButtonLabel  = mViewModel.cancelButtonLabel.get();

        if (mAlertFragment != null) {
            mAlertFragment.dismiss();
            mAlertFragment = null;
        }
        if (title == null || message == null) {
            showToast("You must set title or message to alert");
            return;
        }
        mAlertFragment = new AlertFragment.Builder()
                .withTitle(title)
                .withMessage(message)
                .setConfirmButtonLabel(confirmButtonLabel)
                .setCancelButtonLabel(cancelButtonLabel)
                .toRequestCode(ALERT_REQUEST_CODE)
                .build();
        mAlertFragment.addOnAlertCommunicator(this);
        mAlertFragment.show(
                getSupportFragmentManager(),
                null
        );
    }

    public void showToast(String message) {
        Toast.makeText(
                this,
                message,
                Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void onConfirmClick(int requestCode) {
        showToast("CONFIRM button clicked");
    }

    @Override
    public void onCancelClick(int requestCode) {
        showToast("CANCEL button clicked");
    }
}
package com.bottomalert.presentation.bottomalert;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bottomalert.R;
import com.bottomalert.communicator.OnAlertCommunicator;
import com.bottomalert.databinding.FragmentAlertBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AlertFragment extends BottomSheetDialogFragment {
    private static final String ARG_REQUEST_CODE         = "arg_request_code";
    private static final String ARG_TITLE                = "arg_title";
    private static final String ARG_MESSAGE              = "arg_message";
    private static final String ARG_CONFIRM_BUTTON_LABEL = "arg_confirm_button_label";
    private static final String ARG_CANCEL_BUTTON_LABEL  = "arg_cancel_button_label";

    public static class Builder {
        private int requestCode;
        private String title;
        private String message;
        private String confirmButtonLabel;
        private String cancelButtonLabel;

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setConfirmButtonLabel(String confirmButtonLabel) {
            this.confirmButtonLabel = confirmButtonLabel;
            return this;
        }

        public Builder setCancelButtonLabel(String cancelButtonLabel) {
            this.cancelButtonLabel = cancelButtonLabel;
            return this;
        }

        public Builder toRequestCode(int requestCode) {
            this.requestCode = requestCode;
            return this;
        }

        public AlertFragment build() {
            Bundle bundle = new Bundle();
            bundle.putString(ARG_TITLE                  , this.title);
            bundle.putString(ARG_MESSAGE                , this.message);
            bundle.putString(ARG_CONFIRM_BUTTON_LABEL   , this.confirmButtonLabel);
            bundle.putString(ARG_CANCEL_BUTTON_LABEL    , this.cancelButtonLabel);
            bundle.putInt(ARG_REQUEST_CODE              , this.requestCode);

            AlertFragment fragment = new AlertFragment();
                          fragment.setArguments(bundle);
            return fragment;
        }
    }

    private LifecycleOwner mLifecycleOwner;
    private AlertFragmentViewModel mViewModel;
    private FragmentAlertBinding mBinding;
    private OnAlertCommunicator mCommunicator;
    private String mTitle;
    private String mMessage;
    private String mConfirmButtonLabel;
    private String mCancelButtonLabel;
    private int mRequestCode;

    public AlertFragment() {
        // Required empty public constructor
    }

    public void addOnAlertCommunicator(OnAlertCommunicator communicator) {
        mCommunicator = communicator;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRequestCode       = getArguments().getInt(ARG_REQUEST_CODE);
            mTitle             = getArguments().getString(ARG_TITLE);
            mMessage           = getArguments().getString(ARG_MESSAGE);
            mConfirmButtonLabel = getArguments().getString(ARG_CONFIRM_BUTTON_LABEL);
            mCancelButtonLabel = getArguments().getString(ARG_CANCEL_BUTTON_LABEL);
        }
        else {
            dismiss();
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnAlertCommunicator) {
            mCommunicator = (OnAlertCommunicator) context;
        }
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {

        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_alert,
                container, false);

        mLifecycleOwner = getViewLifecycleOwner();

        mViewModel = new ViewModelProvider(this)
                .get(AlertFragmentViewModel.class);

        mBinding.setViewModel(mViewModel);

        setup();
        attachConfirmClick();
        attachCancelClick();

        return mBinding.getRoot();
    }

    private void setup() {
        if (mConfirmButtonLabel != null){
            mViewModel.confirmButtonLabel.set(mConfirmButtonLabel);
        }
        else {
            // Set default confirm label on button
            mBinding.btnAlertConfirm.setText(
                    getContext().getString(R.string.label_done)
            );
        }

        if (mCancelButtonLabel == null){
            mBinding.btnAlertCancel.setVisibility(View.GONE);
        }
        else {
            mViewModel.confirmButtonLabel.set(mConfirmButtonLabel);
        }

        mViewModel.title.set(mTitle);
        mViewModel.message.set(mMessage);
    }

    private void attachConfirmClick() {
        mViewModel.watchConfirmClickEvent().observe(mLifecycleOwner, o -> {
            handleSubmitClick();
        });
    }

    private void attachCancelClick() {
        mViewModel.watchCancelClickEvent().observe(mLifecycleOwner, o -> {
            handleCancelClick();
        });
    }

    private void handleSubmitClick() {
        if (mCommunicator != null) {
            mCommunicator.onConfirmClick(mRequestCode);
        }
        dismiss();
    }

    private void handleCancelClick() {
        if (mCommunicator != null) {
            mCommunicator.onCancelClick(mRequestCode);
        }
        dismiss();
    }

    public String getTitle() {
        return mTitle;
    }

    public String getMessage() {
        return mMessage;
    }
}

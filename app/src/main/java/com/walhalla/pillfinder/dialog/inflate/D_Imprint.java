package com.walhalla.pillfinder.dialog.inflate;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;

import com.walhalla.pillfinder.R;

import com.walhalla.pillfinder.databinding.ImprintDialogBinding;
import com.walhalla.ui.DLog;


public class D_Imprint extends DialogFragment {


    private ImprintDialogBinding mBinding;
    public static final String KEY_INPUT_OUTPUT_DATA = "key_input";

    private String input;

//    public static D_Imprint newInstance(String input) {
//        D_Imprint dialog = new D_Imprint();
//        Bundle bundle = new Bundle();
//        bundle.putSerializable(KEY_INPUT_OUTPUT_DATA, input);
//        dialog.setArguments(bundle);
//        return new D_Imprint();
//    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(KEY_INPUT_OUTPUT_DATA, input);
        super.onSaveInstanceState(outState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.tab_imprint)
                .setIcon(R.drawable.pill_red);
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        mBinding = ImprintDialogBinding.inflate(inflater, null, false);

        if (getArguments() != null) {
            input = getArguments().getString(KEY_INPUT_OUTPUT_DATA);
        }
        if (savedInstanceState != null) {
            input = savedInstanceState.getString(KEY_INPUT_OUTPUT_DATA);
        }

        //Init Gui
        if (input == null || input.isEmpty()) {
            disableImprint(true);
            mBinding.chb1.setChecked(true);
        } else {
            mBinding.etDialogInput.setText(input);
        }

        mBinding.chb1.setOnCheckedChangeListener((buttonView, isChecked) -> disableImprint(isChecked));

        AlertDialog d = builder.setView(mBinding.getRoot())
                // set dialog message
                .setCancelable(false)
                .setPositiveButton(getActivity().getString(android.R.string.ok),
                        (dialog, id) -> {
                            // get user input and set it to result
                            // edit text

                            String response = (mBinding.chb1.isChecked()) ? ""
                                    : mBinding.etDialogInput.getText().toString();
                            Intent intent = new Intent();
                            intent.putExtra(KEY_INPUT_OUTPUT_DATA, response);
                            if (getTargetFragment() != null) {
                                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                            }

                        }).setNegativeButton(getActivity().getString(android.R.string.cancel),
                        (dialog, id) -> dialog.cancel())
                .create();
        //d.getWindow().setBackgroundDrawableResource(R.drawable.pretty_gradient);
        return d;
    }


    private void disableImprint(boolean isChecked) {
        if (isChecked) {
            mBinding.etDialogInput.setText(R.string.empty_no_imprint);
            mBinding.etDialogInput.setEnabled(false);
        } else {
            mBinding.etDialogInput.setEnabled(true);
            mBinding.etDialogInput.setText("");
        }
    }


    @Override
    public void onCreate(Bundle bundle) {
        this.setCancelable(true);
        //setRetainInstance(true);
        super.onCreate(bundle);

        //setRetainInstance(true);
        DLog.d("onCreate: " + bundle);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState == null) {
            //smth
        } else {
            // smthelse THIS IS NEVER REACHED BECAUSE BUNDLE IS ALWAYS NULL
        }
        DLog.d("onViewCreated: " + savedInstanceState);
    }


    @Override
    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance())
            getDialog().setDismissMessage(null);
        super.onDestroyView();

    }
}

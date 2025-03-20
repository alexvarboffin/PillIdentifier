package com.walhalla.pillfinder.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.util.Log;
import android.widget.Toast;

import com.walhalla.Util;
import com.walhalla.pillfinder.R;
import com.walhalla.domen.rest.QueryConstants;
import com.walhalla.ui.DLog;


public class D_Shape extends DialogFragment {


    public static final String KEY_INPUT_OUTPUT_DATA = "key_input";

    private String input;

//    public static D_Shape newInstance(String input) {
//        D_Shape dialog = new D_Shape();
//        Bundle bundle = new Bundle();
//        bundle.putString(KEY_INPUT_OUTPUT_DATA, input);
//        dialog.setArguments(bundle);
//        return dialog;
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(KEY_INPUT_OUTPUT_DATA, input);
        super.onSaveInstanceState(outState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        if (getArguments() != null) {
            input = getArguments().getString(KEY_INPUT_OUTPUT_DATA);
        }
        if (savedInstanceState != null) {
            input = savedInstanceState.getString(KEY_INPUT_OUTPUT_DATA);
        }
        DLog.d("Input data: " + input);

        int checkeditem = Util.findArrayIndex(input, QueryConstants.shapes);


        AlertDialog d = new AlertDialog.Builder(getContext())
                .setTitle(R.string.dialog_shape_title)
                .setIcon(R.drawable.ic_shape)
                .setSingleChoiceItems(QueryConstants.shapes, checkeditem, (dialog, position) -> {
                    if (position == -1) {
                        Toast.makeText(getContext(), "No choice", Toast.LENGTH_LONG).show();
                    } else {
                        String shape = QueryConstants.shapes[position];
                        Intent intent = new Intent();
                        intent.putExtra(KEY_INPUT_OUTPUT_DATA, shape);
                        if (getTargetFragment() != null) {
                            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                        }
                    }
                    dialog.dismiss();
                })
                .setNegativeButton(getString(android.R.string.cancel), (dialog, id) -> {
                    dialog.cancel();
                    //getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, getActivity().getIntent());
                })
                .setCancelable(true)
                .create();
        return d;
    }


}

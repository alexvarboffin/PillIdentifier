package com.walhalla.pillfinder.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.walhalla.Util;
import com.walhalla.domen.rest.QueryConstants;
import com.walhalla.pillfinder.R;
import com.walhalla.ui.DLog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;


public class D_Scoring extends DialogFragment {


    public static final String KEY_INPUT_OUTPUT_DATA = "key_input";
    private String input;

//    public static D_Scoring newInstance(String input) {
//        D_Scoring dialog = new D_Scoring();
//        Bundle bundle = new Bundle();
//        bundle.putString(KEY_INPUT_OUTPUT_DATA, input);
//        dialog.setArguments(bundle);
//        return dialog;
//    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(KEY_INPUT_OUTPUT_DATA, input);
        super.onSaveInstanceState(outState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        if (getArguments() != null) {
            input = getArguments().getString(KEY_INPUT_OUTPUT_DATA);
        }
        if (savedInstanceState != null) {
            input = savedInstanceState.getString(KEY_INPUT_OUTPUT_DATA);
        }
        DLog.d("Input data: " + input);

        int checkeditem = Util.findArrayIndex(input, QueryConstants.scoring);

        AlertDialog d = new AlertDialog.Builder(getContext())
                .setTitle(R.string.dialog_scoring_title)
                .setIcon(R.drawable.ic_scoring).setCancelable(true)

                // add a button to close the dialog
                /*
                 * .setNeutralButton("", new
                 * DialogInterface.OnClickListener() { public void
                 * onClick(DialogInterface dialog, int id) {
                 * dialog.cancel(); } })
                 */

                // add switches
                .setSingleChoiceItems(QueryConstants.scoring, checkeditem, (dialog, item) -> {
                    if (item == -1) {
                        //String s = String.format(getString(R.string.score_selected), );
                        //                    callback.mSnackbar(s);
                        //Toast.makeText(getContext(), "No choice", Toast.LENGTH_LONG).show();
                    } else {
                        String value = QueryConstants.scoring[item];
                        Intent intent = new Intent();
                        intent.putExtra(KEY_INPUT_OUTPUT_DATA, value);
                        if (getTargetFragment() != null) {
                            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                        }
                    }
                    dialog.dismiss();
                })
                .setNegativeButton(getString(android.R.string.cancel), (dialog, id) -> dialog.cancel())
                .setCancelable(true)
                .create();
        return d;
    }
}

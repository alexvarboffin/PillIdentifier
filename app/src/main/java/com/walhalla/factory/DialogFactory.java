package com.walhalla.factory;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.walhalla.domen.PillRequest;
import com.walhalla.pillfinder.Constants;
import com.walhalla.pillfinder.MpcField;
import com.walhalla.pillfinder.dialog.D_Color;
import com.walhalla.pillfinder.dialog.D_Scoring;
import com.walhalla.pillfinder.dialog.D_Shape;
import com.walhalla.pillfinder.dialog.D_Size;
import com.walhalla.pillfinder.dialog.inflate.D_Imprint;


import static com.walhalla.pillfinder.fragment.main.FragmentMain.REQUEST_COLOR_OPTION;
import static com.walhalla.pillfinder.fragment.main.FragmentMain.REQUEST_IMPRINT_OPTION;
import static com.walhalla.pillfinder.fragment.main.FragmentMain.REQUEST_SCORING_OPTION;
import static com.walhalla.pillfinder.fragment.main.FragmentMain.REQUEST_SHAPE_OPTION;
import static com.walhalla.pillfinder.fragment.main.FragmentMain.REQUEST_SIZE_OPTION;

import java.util.Objects;


public class DialogFactory implements Constants {

    public static DialogFragment getDialog(Fragment rootFragment, DialogType type) {
        Bundle bundle = new Bundle();
        DialogFragment dialog = null;

        if (type == DialogType.COLOR) {
            dialog = new D_Color();
            bundle.putString(D_Color.KEY_INPUT_OUTPUT_COLOR, PillRequest.INSTANCE.get(MpcField.COLOR));
            dialog.setArguments(bundle);
            //D_Color.newInstance(PillRequest.INSTANCE.get(MpcField.COLOR));
            dialog.setTargetFragment(rootFragment, REQUEST_COLOR_OPTION);
            return dialog;
        } else if (type == DialogType.IMPRINT) {
            String imprint = PillRequest.INSTANCE.get(MpcField.IMPRINT);
            dialog = new D_Imprint();
            Bundle bundle1 = new Bundle();
            bundle1.putSerializable(D_Imprint.KEY_INPUT_OUTPUT_DATA, (imprint == null) ? "" : imprint);
            dialog.setArguments(bundle1);

            //D_Imprint.newInstance((imprint == null) ? "" : imprint);
            dialog.setTargetFragment(rootFragment, REQUEST_IMPRINT_OPTION);
            return dialog;
        } else if (type == DialogType.SCORE) {
            String scope = PillRequest.INSTANCE.get(MpcField.SCORE);
            dialog = new D_Scoring();
            bundle = new Bundle();
            bundle.putString(D_Scoring.KEY_INPUT_OUTPUT_DATA, (scope == null) ? "" : scope);
            dialog.setArguments(bundle);
            dialog.setTargetFragment(rootFragment, REQUEST_SCORING_OPTION);
            return dialog;
        } else if (type == DialogType.SHAPE) {
            dialog = new D_Shape();
            bundle.putString(D_Scoring.KEY_INPUT_OUTPUT_DATA, PillRequest.INSTANCE.get(MpcField.SHAPE));
            dialog.setArguments(bundle);
            dialog.setTargetFragment(rootFragment, REQUEST_SHAPE_OPTION);
            return dialog;
        } else if (type == DialogType.SIZE) {
            String size = PillRequest.INSTANCE.get(MpcField.SIZE);
            dialog = new D_Size();
            Bundle args = new Bundle();
            args.putInt(D_Size.KEY_INPUT_OUTPUT_DATA, (size == null) ? 0 : Integer.valueOf(size));
            dialog.setArguments(args);
            dialog.setTargetFragment(rootFragment, REQUEST_SIZE_OPTION);
            return dialog;
        }
        return dialog;
    }

}

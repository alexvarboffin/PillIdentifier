package com.walhalla.factory

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.walhalla.domen.PillRequest
import com.walhalla.pillfinder.Constants
import com.walhalla.pillfinder.MpcField
import com.walhalla.pillfinder.dialog.D_Color
import com.walhalla.pillfinder.dialog.D_Scoring
import com.walhalla.pillfinder.dialog.D_Shape
import com.walhalla.pillfinder.dialog.D_Size
import com.walhalla.pillfinder.dialog.inflate.D_Imprint
import com.walhalla.pillfinder.fragment.main.FragmentMain

object DialogFactory : Constants {
    fun getDialog(rootFragment: Fragment?, type: DialogType?): DialogFragment? {
        var bundle = Bundle()
        var dialog: DialogFragment? = null

        if (type == DialogType.COLOR) {
            dialog = D_Color()
            bundle.putString(
                D_Color.KEY_INPUT_OUTPUT_COLOR,
                PillRequest.INSTANCE.get(MpcField.COLOR)
            )
            dialog.setArguments(bundle)
            //D_Color.newInstance(PillRequest.INSTANCE.get(MpcField.COLOR));
            dialog.setTargetFragment(rootFragment, FragmentMain.REQUEST_COLOR_OPTION)
            return dialog
        } else if (type == DialogType.IMPRINT) {
            val imprint = PillRequest.INSTANCE.get(MpcField.IMPRINT)
            dialog = D_Imprint()
            val bundle1 = Bundle()
            bundle1.putSerializable(
                D_Imprint.KEY_INPUT_OUTPUT_DATA,
                if (imprint == null) "" else imprint
            )
            dialog.setArguments(bundle1)

            //D_Imprint.newInstance((imprint == null) ? "" : imprint);
            dialog.setTargetFragment(rootFragment, FragmentMain.REQUEST_IMPRINT_OPTION)
            return dialog
        } else if (type == DialogType.SCORE) {
            val scope = PillRequest.INSTANCE.get(MpcField.SCORE)
            dialog = D_Scoring()
            bundle = Bundle()
            bundle.putString(D_Scoring.KEY_INPUT_OUTPUT_DATA, if (scope == null) "" else scope)
            dialog.setArguments(bundle)
            dialog.setTargetFragment(rootFragment, FragmentMain.REQUEST_SCORING_OPTION)
            return dialog
        } else if (type == DialogType.SHAPE) {
            dialog = D_Shape()
            bundle.putString(
                D_Scoring.KEY_INPUT_OUTPUT_DATA,
                PillRequest.INSTANCE.get(MpcField.SHAPE)
            )
            dialog.setArguments(bundle)
            dialog.setTargetFragment(rootFragment, FragmentMain.REQUEST_SHAPE_OPTION)
            return dialog
        } else if (type == DialogType.SIZE) {
            val size = PillRequest.INSTANCE.get(MpcField.SIZE)
            dialog = D_Size()
            val args = Bundle()
            args.putInt(D_Size.KEY_INPUT_OUTPUT_DATA, if (size == null) 0 else size.toInt())
            dialog.setArguments(args)
            dialog.setTargetFragment(rootFragment, FragmentMain.REQUEST_SIZE_OPTION)
            return dialog
        }
        return dialog
    }
}

package com.walhalla.pillfinder.dialog

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.walhalla.Util
import com.walhalla.domen.rest.QueryConstants
import com.walhalla.pillfinder.R
import com.walhalla.ui.DLog.d

class D_Scoring : DialogFragment() {
    private var input: String? = null

    //    public static D_Scoring newInstance(String input) {
    //        D_Scoring dialog = new D_Scoring();
    //        Bundle bundle = new Bundle();
    //        bundle.putString(KEY_INPUT_OUTPUT_DATA, input);
    //        dialog.setArguments(bundle);
    //        return dialog;
    //    }
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(KEY_INPUT_OUTPUT_DATA, input)
        super.onSaveInstanceState(outState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (getArguments() != null) {
            input = requireArguments().getString(KEY_INPUT_OUTPUT_DATA)
        }
        if (savedInstanceState != null) {
            input = savedInstanceState.getString(KEY_INPUT_OUTPUT_DATA)
        }
        d("Input data: " + input)

        val checkeditem = Util.findArrayIndex(input ?: "", QueryConstants.scoring)

        val d = AlertDialog.Builder(requireContext())
            .setTitle(R.string.dialog_scoring_title)
            .setIcon(R.drawable.ic_scoring).setCancelable(true) // add a button to close the dialog
            /*
                 * .setNeutralButton("", new
                 * DialogInterface.OnClickListener() { public void
                 * onClick(DialogInterface dialog, int id) {
                 * dialog.cancel(); } })
                 */
            // add switches

            .setSingleChoiceItems(
                QueryConstants.scoring,
                checkeditem,
                DialogInterface.OnClickListener { dialog: DialogInterface?, item: Int ->
                    if (item == -1) {
                        //String s = String.format(getString(R.string.score_selected), );
                        //                    callback.mSnackbar(s);
                        //Toast.makeText(getContext(), "No choice", Toast.LENGTH_LONG).show();
                    } else {
                        val value = QueryConstants.scoring[item]
                        val intent = Intent()
                        intent.putExtra(KEY_INPUT_OUTPUT_DATA, value)
                        if (getTargetFragment() != null) {
                            getTargetFragment()!!.onActivityResult(
                                getTargetRequestCode(),
                                Activity.RESULT_OK,
                                intent
                            )
                        }
                    }
                    dialog!!.dismiss()
                })
            .setNegativeButton(
                getString(android.R.string.cancel)
            ) { dialog: DialogInterface?, id: Int -> dialog!!.cancel() }
            .setCancelable(true)
            .create()
        return d
    }

    companion object {
        const val KEY_INPUT_OUTPUT_DATA: String = "key_input"
    }
}

package com.walhalla.pillfinder.dialog

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.walhalla.Util
import com.walhalla.domen.rest.QueryConstants
import com.walhalla.pillfinder.R
import com.walhalla.ui.DLog.d

class D_Shape : DialogFragment() {
    private var input: String? = null

    //    public static D_Shape newInstance(String input) {
    //        D_Shape dialog = new D_Shape();
    //        Bundle bundle = new Bundle();
    //        bundle.putString(KEY_INPUT_OUTPUT_DATA, input);
    //        dialog.setArguments(bundle);
    //        return dialog;
    //    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(KEY_INPUT_OUTPUT_DATA, input)
        super.onSaveInstanceState(outState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (arguments != null) {
            input = requireArguments().getString(KEY_INPUT_OUTPUT_DATA)
        }
        if (savedInstanceState != null) {
            input = savedInstanceState.getString(KEY_INPUT_OUTPUT_DATA)
        }
        d("Input data: " + input)

        val checkeditem = Util.findArrayIndex(input!!, QueryConstants.shapes)


        val d = AlertDialog.Builder(requireContext())
            .setTitle(R.string.dialog_shape_title)
            .setIcon(R.drawable.ic_shape)
            .setSingleChoiceItems(
                QueryConstants.shapes,
                checkeditem
            ) { dialog: DialogInterface?, position: Int ->
                if (position == -1) {
                    Toast.makeText(context, "No choice", Toast.LENGTH_LONG).show()
                } else {
                    val shape = QueryConstants.shapes[position]
                    val intent = Intent()
                    intent.putExtra(KEY_INPUT_OUTPUT_DATA, shape)
                    if (getTargetFragment() != null) {
                        getTargetFragment()!!.onActivityResult(
                            getTargetRequestCode(),
                            Activity.RESULT_OK,
                            intent
                        )
                    }
                }
                dialog!!.dismiss()
            }
            .setNegativeButton(
                getString(android.R.string.cancel)
            ) { dialog: DialogInterface?, id: Int ->
                dialog!!.cancel()
            }
            .setCancelable(true)
            .create()
        return d
    }


    companion object {
        const val KEY_INPUT_OUTPUT_DATA: String = "key_input"
    }
}

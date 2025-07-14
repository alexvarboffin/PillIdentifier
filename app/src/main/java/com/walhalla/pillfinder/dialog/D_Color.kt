package com.walhalla.pillfinder.dialog

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.LinearLayout
import android.widget.ToggleButton
import androidx.fragment.app.DialogFragment
import com.walhalla.pillfinder.R
import com.walhalla.ui.DLog.d
import java.util.Arrays
import androidx.core.view.size

class D_Color : DialogFragment(), CompoundButton.OnCheckedChangeListener {
    private var selected_tags = arrayOfNulls<String>(COUNT_OF_COLOR_MAX)
    private lateinit var view1: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view1 = inflater.inflate(R.layout.dialog_color, container, false)
        val colorMenu1 = view1.findViewById<LinearLayout>(R.id.line1)
        val colorMenu2 = view1.findViewById<LinearLayout>(R.id.line2)
        val colorMenu3 = view1.findViewById<LinearLayout>(R.id.line3)

        restoreSelect(savedInstanceState)
        d("[c]" + selected_tags.contentToString())

        for (i in 0..<colorMenu1.size) {
            var button = colorMenu1.getChildAt(i) as ToggleButton
            isSelectedOrNot0(button)
            button.setOnCheckedChangeListener(this)

            val button0 = colorMenu2.getChildAt(i) as ToggleButton
            isSelectedOrNot0(button0)
            button0.setOnCheckedChangeListener(this)

            button = colorMenu3.getChildAt(i) as ToggleButton
            isSelectedOrNot0(button)
            button.setOnCheckedChangeListener(this)
        }
        (view1.findViewById<View?>(R.id.no)).setOnClickListener { v: View? -> this@D_Color.dismiss() }
        (view1.findViewById<View?>(R.id.yes11))
            .setOnClickListener(View.OnClickListener { v: View? ->
                okRequest()
                this@D_Color.dismiss()
            })
        //getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return view1
    }

    private fun restoreSelect(bundle: Bundle?) {
        var input: String? = null
        if (getArguments() != null) {
            input = requireArguments().getString(KEY_INPUT_OUTPUT_COLOR)
        }
        if (bundle != null) {
            input = bundle.getString(KEY_INPUT_OUTPUT_COLOR)
        }

        if (input != null && !input.isEmpty()) {
            val raw0: Array<String?> =
                input.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (i in selected_tags.indices) {
                if (raw0.size > i) {
                    selected_tags[i] = raw0[i]
                }
            }
        }
    }

    private fun okRequest() {
//        StringBuilder result = new StringBuilder();
//        ToggleButton toggleButton;
//
//        for (int i = 0; i < colorMenu1.getChildCount(); i++) {
//            toggleButton = (ToggleButton) colorMenu1.getChildAt(i);
//            if (toggleButton.isChecked()) {
//                result.append(toggleButton.getTag()).append(",");
//            }
//            toggleButton = (ToggleButton) colorMenu2.getChildAt(i);
//            if (toggleButton.isChecked()) {
//                result.append(toggleButton.getTag()).append(",");
//            }
//            toggleButton = (ToggleButton) colorMenu3.getChildAt(i);
//            if (toggleButton.isChecked()) {
//                result.append(toggleButton.getTag()).append(",");
//            }
//        }
//
//        if (result.length() > 0)
//            result.deleteCharAt(result.length() - 1);

        val intent = Intent()
        intent.putExtra(KEY_INPUT_OUTPUT_COLOR, input())
        if (getTargetFragment() != null) {
            getTargetFragment()!!.onActivityResult(
                getTargetRequestCode(),
                Activity.RESULT_OK,
                intent
            )
        }
    }


    private fun isSelectedOrNot0(button: ToggleButton?) {
        if (button != null) {
            val ch = Arrays.asList<String?>(*selected_tags).contains(button.tag.toString())
            if (ch) {
                d("[c]" + button.tag + " " + ch)
            }
            button.isChecked = ch
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //this.setStyle(STYLE_NO_TITLE, R.style.MyTheme_FloatingDialog);
    }

    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        val tag = buttonView.getTag().toString()
        d("Selected color: " + isChecked + " " + tag)


        if (isChecked) {
            // On Now update the UI to reflect the new chosen paint and set the
            // previous one back to normal:
            // buttonView.setBackgroundResource(R.drawable.paint_pressed);
            for (i in selected_tags.indices) {
                if (selected_tags[i] == null) {
                    selected_tags[i] = tag
                    return
                }
            }

            val removed = selected_tags[selected_tags.size - 1]
            if (removed != null) {
                val aa = view1.findViewWithTag<ToggleButton?>(removed)
                if (aa != null) {
                    aa.setChecked(false)
                }
            }

            selected_tags = ashift(selected_tags)
            selected_tags[0] = tag
        } else {
            //Remove color
            for (i in selected_tags.indices) {
                if (tag == selected_tags[i]) {
                    selected_tags[i] = null
                    break
                }
            }
        }

        d(this.selectedCount.toString() + " [Selected] " + selected_tags.contentToString())
    }

    private fun ashift(input: Array<String?>): Array<String?> {
        val raw = arrayOfNulls<String>(input.size)
        raw[0] = input[input.size - 1] //Set last in first
        System.arraycopy(input, 0, raw, 1, input.size - 1)
        return raw
    }


    val selectedCount: Int
        get() {
            var total = 0
            for (i in selected_tags.indices) {
                if (selected_tags[i] != null) {
                    total++
                }
            }
            return total
        }


    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(KEY_INPUT_OUTPUT_COLOR, input())
        super.onSaveInstanceState(outState)
    }

    private fun input(): String {
        val sb = StringBuilder()
        for (i in selected_tags.indices) {
            if (selected_tags[i] != null) {
                sb.append(selected_tags[i])
                if (i + 1 < selected_tags.size) {
                    sb.append(",")
                }
            }
        }
        return sb.toString()
    } //    public static D_Color newInstance(String s) {
    //        D_Color dialog = new D_Color();
    //        Bundle bundle = new Bundle();
    //        bundle.putString(KEY_INPUT_OUTPUT_DATA, s);
    //        dialog.setArguments(bundle);
    //        return dialog;
    //    }
    //    @NonNull
    //    @Override
    //    public Dialog onCreateDialog(Bundle savedInstanceState) {
    //        //return super.onCreateDialog(savedInstanceState);
    //        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    /*        builder.setTitle(R.string.dialog_color_title)
    * /                .setIcon(R.drawable.ic_color); */
    //
    //        LayoutInflater inflater = LayoutInflater.from(getActivity().getApplicationContext());
    //        View view = inflater.inflate(R.layout.dialog_color, null);
    //
    //        colorMenu1 = view.findViewById(R.id.line1);
    //        colorMenu2 = view.findViewById(R.id.line2);
    //        colorMenu3 = view.findViewById(R.id.line3);
    //
    //        buttons_state = ((mDialogListener.getInput() == null) ? "" : mDialogListener.getInput()).split(",");
    //
    //        for (int i = 0; i < colorMenu1.getChildCount(); i++) {
    //            ToggleButton child = (ToggleButton) colorMenu1.getChildAt(i);
    //            child.setOnCheckedChangeListener(callback);
    //            selected(child);
    //
    //            child = (ToggleButton) colorMenu2.getChildAt(i);
    //            child.setOnCheckedChangeListener(callback);
    //            selected(child);
    //
    //            child = (ToggleButton) colorMenu3.getChildAt(i);
    //            child.setOnCheckedChangeListener(callback);
    //            selected(child);
    //        }
    //        final AlertDialog alertDialog = builder.setView(view)
    //                // set alertDialog message
    /*                .setCancelable(false)
    * /                .setPositiveButton(getActivity().getString(android.R.string.ok),
    * /                        (dialog, id) ->
    {
        * /                            ok();
        * /
    })
    * /                .setNegativeButton(getActivity().getString(android.R.string.cancel),
    * /                        (dialog, id) ->
    {
        * /                            dialog.cancel();
        * /
    }) */
    //                .create();
    //        (view.findViewById(R.id.no)).setOnClickListener(v -> alertDialog.dismiss());
    //        (view.findViewById(R.id.yes11)).setOnClickListener(v -> {
    //            ok();
    //            alertDialog.dismiss();
    //        });
    //        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    //        return alertDialog;
    //    }

    companion object {
        private const val COUNT_OF_COLOR_MAX = 2
        const val KEY_INPUT_OUTPUT_COLOR: String = "key_input_data"
    }
}

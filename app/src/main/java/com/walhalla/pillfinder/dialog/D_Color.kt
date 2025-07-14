package com.walhalla.pillfinder.dialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.walhalla.pillfinder.R;
import com.walhalla.ui.DLog;

import java.util.Arrays;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


public class D_Color extends DialogFragment implements OnCheckedChangeListener {

    private static final int COUNT_OF_COLOR_MAX = 2;
    public static final String KEY_INPUT_OUTPUT_COLOR = "key_input_data";


    private String[] selected_tags = new String[COUNT_OF_COLOR_MAX];
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_color, container, false);
        LinearLayout colorMenu1 = view.findViewById(R.id.line1);
        LinearLayout colorMenu2 = view.findViewById(R.id.line2);
        LinearLayout colorMenu3 = view.findViewById(R.id.line3);

        restoreSelect(savedInstanceState);
        DLog.d("[c]" + Arrays.toString(selected_tags));

        for (int i = 0; i < colorMenu1.getChildCount(); i++) {
            ToggleButton button = (ToggleButton) colorMenu1.getChildAt(i);
            isSelectedOrNot0(button);
            button.setOnCheckedChangeListener(this);

            ToggleButton button0 = (ToggleButton) colorMenu2.getChildAt(i);
            isSelectedOrNot0(button0);
            button0.setOnCheckedChangeListener(this);

            button = (ToggleButton) colorMenu3.getChildAt(i);
            isSelectedOrNot0(button);
            button.setOnCheckedChangeListener(this);

        }
        (view.findViewById(R.id.no)).setOnClickListener(v -> D_Color.this.dismiss());
        (view.findViewById(R.id.yes11))
                .setOnClickListener(v -> {
                    okRequest();
                    D_Color.this.dismiss();
                });
        //getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return view;
    }

    private void restoreSelect(Bundle bundle) {
        String input = null;
        if (getArguments() != null) {
            input = getArguments().getString(KEY_INPUT_OUTPUT_COLOR);
        }
        if (bundle != null) {
            input = bundle.getString(KEY_INPUT_OUTPUT_COLOR);
        }

        if (input != null && !input.isEmpty()) {
            String[] raw0 = input.split(",");
            for (int i = 0; i < selected_tags.length; i++) {
                if (raw0.length > i) {
                    selected_tags[i] = raw0[i];
                }
            }
        }
    }

    private void okRequest() {
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

        Intent intent = new Intent();
        intent.putExtra(KEY_INPUT_OUTPUT_COLOR, input());
        if (getTargetFragment() != null) {
            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
        }
    }


    private void isSelectedOrNot0(ToggleButton button) {
        if (button != null) {
            boolean ch = Arrays.asList(selected_tags).contains(button.getTag().toString());
            if (ch) {
                DLog.d("[c]" + button.getTag() + " " + ch);
            }
            button.setChecked(ch);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.setStyle(STYLE_NO_TITLE, R.style.MyTheme_FloatingDialog);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        String tag = String.valueOf(buttonView.getTag());
        DLog.d("Selected color: " + isChecked + " " + tag);


        if (isChecked) {
            // On Now update the UI to reflect the new chosen paint and set the
            // previous one back to normal:
            // buttonView.setBackgroundResource(R.drawable.paint_pressed);
            for (int i = 0; i < selected_tags.length; i++) {
                if (selected_tags[i] == null) {
                    selected_tags[i] = tag;
                    return;
                }
            }

            String removed = selected_tags[selected_tags.length - 1];
            if (removed != null) {
                ToggleButton aa = view.findViewWithTag(removed);
                if (aa != null) {
                    aa.setChecked(false);
                }
            }

            selected_tags = ashift(selected_tags);
            selected_tags[0] = tag;



        } else {
            //Remove color
            for (int i = 0; i < selected_tags.length; i++) {
                if (tag.equals(selected_tags[i])) {
                    selected_tags[i] = null;
                    break;
                }
            }
        }

        DLog.d(getSelectedCount() + " [Selected] " + Arrays.toString(selected_tags));
    }

    private String[] ashift(String[] input) {
        String[] raw = new String[input.length];
        raw[0] = input[input.length - 1];//Set last in first
        System.arraycopy(input, 0, raw, 1, input.length - 1);
        return raw;
    }


    public int getSelectedCount() {
        int total = 0;
        for (int i = 0; i < selected_tags.length; i++) {
            if (selected_tags[i] != null) {
                total++;
            }
        }
        return total;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(KEY_INPUT_OUTPUT_COLOR, input());
        super.onSaveInstanceState(outState);
    }

    private String input() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < selected_tags.length; i++) {
            if (selected_tags[i] != null) {
                sb.append(selected_tags[i]);
                if (i + 1 < selected_tags.length) {
                    sb.append(",");
                }
            }
        }
        return sb.toString();
    }

//    public static D_Color newInstance(String s) {
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
////        builder.setTitle(R.string.dialog_color_title)
////                .setIcon(R.drawable.ic_color);
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
////                .setCancelable(false)
////                .setPositiveButton(getActivity().getString(android.R.string.ok),
////                        (dialog, id) -> {
////                            ok();
////                        })
////                .setNegativeButton(getActivity().getString(android.R.string.cancel),
////                        (dialog, id) -> {
////                            dialog.cancel();
////                        })
//                .create();
//        (view.findViewById(R.id.no)).setOnClickListener(v -> alertDialog.dismiss());
//        (view.findViewById(R.id.yes11)).setOnClickListener(v -> {
//            ok();
//            alertDialog.dismiss();
//        });
//        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        return alertDialog;
//    }

}

package com.walhalla.pillfinder.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.walhalla.pillfinder.R;
import com.walhalla.ui.DLog;



public class D_Size extends DialogFragment {


    public static final String KEY_INPUT_OUTPUT_DATA = "key_input";
    
    private SeekBar sb;
    private TextView mTextValue;
    private int input;

//    public static D_Size newInstance(int value) {
//        D_Size fragment = new D_Size();
//        Bundle args = new Bundle();
//        args.putInt(KEY_INPUT_OUTPUT_DATA, value);
//        fragment.setArguments(args);
//        return fragment;
//    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(KEY_INPUT_OUTPUT_DATA, input);
        super.onSaveInstanceState(outState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);


        if (getArguments() != null) {
            input = getArguments().getInt(KEY_INPUT_OUTPUT_DATA);
        }
        if (savedInstanceState != null) {
            input = savedInstanceState.getInt(KEY_INPUT_OUTPUT_DATA);
        }

        DLog.d( "onCreateDialog: ");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_size_title)
                .setIcon(R.drawable.ic_size);

        LayoutInflater inflater = LayoutInflater.from(getActivity().getApplicationContext());
        View view = inflater.inflate(R.layout.size_dialog, null);
        sb = view.findViewById(R.id.seekBar1);
        mTextValue = view.findViewById(R.id.textView1);
        initializeGUI();

        return builder.setView(view)
                // set dialog message
                //.setCancelable(false)
                .setPositiveButton(getActivity().getApplicationContext().getString(android.R.string.ok),
                        (dialog, id) -> {
                            // get user input and set it to result
                            // edit text
                            int response;
                            if (sb.getProgress() > 4) {
                                response = sb.getProgress();
                            } else
                                response = 0;

                            Intent intent = new Intent();
                            intent.putExtra(KEY_INPUT_OUTPUT_DATA, response);
                            if (getTargetFragment() != null) {
                                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                            }
//                                Intent returnIntent = new Intent();
//                                returnIntent.putExtra(RESULT_DIALOG_SIZE_CODE, response);
//                                //setResult(Activity.RESULT_OK, returnIntent);
//                                getTargetFragment().onActivityResult(getTargetRequestCode(),
//                                        Activity.RESULT_OK, returnIntent);
                        })
                .setNegativeButton(getActivity().getApplicationContext().getString(android.R.string.cancel),
                        (dialog, id) -> dialog.cancel())
                .create();
        // mAdapter = new MultiAdapter(context, mList);
        // lv = (ListView) dialoglayout.findViewById(R.id.list);
        // lv.setAdapter(mAdapter);
        // lv.setOnItemClickListener(new ItemClick());
    }

    private void initializeGUI() {
        sb.setProgress(input);

        String s = (sb.getProgress() == 0) ? getString(R.string.search_all_size)
                : String.format(getString(R.string.search_size), input);
        mTextValue.setText(s);

        sb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub
                if (progress < 4) {
                    mTextValue.setText(getString(R.string.search_all_size));
                } else
                    mTextValue.setText(
                            String.format(getString(R.string.search_size), progress)
                    );

            }
        });
    }

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
//                             Bundle savedInstanceState) {
//        //return super.onCreateView(inflater, container, savedInstanceState);
//        //LayoutInflater inflater = LayoutInflater.from(container.getContext());
//        return inflater.inflate(R.layout.size_dialog, container);
//    }

//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        //ButterKnife.bind(this, view);
//    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
//        this.context = context;
    }

    @Override
    public void onCreate(Bundle icicle) {
        this.setCancelable(true);
        //setRetainInstance(true);
        super.onCreate(icicle);

//        if (null != getArguments()) {
//            inputData = getArguments().getInt("input");
//        }
//        Log.d(TAG, "onCreate: " + inputData);
    }

    @Override
    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance())
            getDialog().setDismissMessage(null);
        super.onDestroyView();

    }

}

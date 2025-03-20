package com.walhalla.pillfinder.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.walhalla.pillfinder.R;
import com.walhalla.pillfinder.databinding.QrLayoutBinding;

public class QRCodeDialog extends DialogFragment {


    private QrLayoutBinding mBinding;

//    @NonNull
//    @Override
//    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//        AlertDialog d = new AlertDialog.Builder(getActivity())
//                .setTitle("title")
////                .setPositiveButton("OK",
////                        new DialogInterface.OnClickListener() {
////                            public void onClick(DialogInterface dialog, int whichButton) {
////                                // do something...
////                            }
////                        }
////                )
////                .setNegativeButton("Cancel",
////                        new DialogInterface.OnClickListener() {
////                            public void onClick(DialogInterface dialog, int whichButton) {
////                                dialog.dismiss();
////                            }
////                        }
////                )
//                .create();
//        return d;
//    }

    private static final String ARG_CODE = "arg_qr_code_text_" + R.raw.opensansregular;

    private String text;

    public QRCodeDialog() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            this.text = getArguments().getString(ARG_CODE);
        }
    }


    public static QRCodeDialog newInstance(@NonNull String code) {
        QRCodeDialog fragment = new QRCodeDialog();
        Bundle arg = new Bundle();
        arg.putString(ARG_CODE, code);
        fragment.setArguments(arg);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mBinding == null) {
            mBinding = QrLayoutBinding.inflate(inflater, container, false);
        }
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bitmap bitmap = net.glxn.qrgen.android.QRCode.from(text)
                //.withColor(0xFFFF0000, 0xFFFFFFAA)
                .bitmap();

        mBinding.qrCode.setImageBitmap(bitmap);
        mBinding.qrCode.setOnClickListener(v -> this.dismiss());
    }

    @Override
    public void onResume() {
        super.onResume();
        String tmp = "" + (TextUtils.isEmpty(text) ? "" : text);
        mBinding.qrTitle.setText(tmp);
    }
}
package com.walhalla.pillfinder.fragment.main;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.walhalla.pillfinder.R;
import com.walhalla.pillfinder.adapter.obj.ingredient.IngredientString;


public abstract class BaseFragment extends Fragment {

    protected IMainView mainView;
//    protected String TAG = String.format(Locale.CANADA, "%1$s %2$s", getClass().getSimpleName(), hashCode());


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        //DLog.d(TAG);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof IMainView) {
            this.mainView = (IMainView) context;
        } else {
            throw new RuntimeException(context + " must implement IMainView");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mainView = null;
    }

    //
//    @Override
//    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//    }
//
//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        DLog.d(TAG);
//    }
}
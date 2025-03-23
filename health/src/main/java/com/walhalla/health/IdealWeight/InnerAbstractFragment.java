package com.walhalla.health.IdealWeight;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.walhalla.health.R;
import com.walhalla.health.util.PrefData;

public abstract class InnerAbstractFragment extends Fragment {

    protected EditText edHeight;
    protected EditText edWeight;

    protected FInnerCallback mainView;
    protected PrefData pref;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = new PrefData(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(aLayout(), container, false);
    }

    protected abstract int aLayout();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FInnerCallback) {
            this.mainView = (FInnerCallback) context;
        } else {
            throw new RuntimeException(context + " must implement FInnerCallback");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mainView = null;
    }

    public interface FInnerCallback {

        void setTitleNew(int title);

        void replaceFragment(Fragment fragment);

        void snackbar(int msg);
    }

    protected void go(Fragment fragment) {
        if (mainView != null) {
            mainView.replaceFragment(fragment);
        }
    }

    protected void title(int res) {
        if (mainView != null) {
            mainView.setTitleNew(res);
        }
    }

    public void toast(int string) {
        if (mainView != null) {
            mainView.snackbar(string);
        }
    }

    protected void bindHeight(View view) {
        edHeight = view.findViewById(R.id.edHeight);
        int aa = pref.getHeight();
        if (aa > 0) {
            edHeight.setText("" + aa);
        }
    }

    protected void bindWeight(View view) {
        edWeight = view.findViewById(R.id.edWeight);
        int aa = pref.getWeight();
        if (aa > 0) {
            edWeight.setText("" + aa);
        }
    }


}

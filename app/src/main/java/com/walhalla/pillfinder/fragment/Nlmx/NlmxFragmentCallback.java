package com.walhalla.pillfinder.fragment.Nlmx;

import androidx.fragment.app.Fragment;

import com.walhalla.lib.service.QLoader;
import com.walhalla.pillfinder.ui.helper.NotificationCallback;

import gov.nih.nlm.model.NlmRxImage;

public interface NlmxFragmentCallback extends NotificationCallback, QLoader {
    void replaceFragment(Fragment fragment);

    //Control
    void viewProxy(int adapterPosition, NlmRxImage data);

    void shareProxy(int adapterPosition, NlmRxImage data);

    void makeQRCode(int adapterPosition, NlmRxImage data);
}
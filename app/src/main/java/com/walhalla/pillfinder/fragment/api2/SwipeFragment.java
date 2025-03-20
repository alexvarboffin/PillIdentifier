package com.walhalla.pillfinder.fragment.api2;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;


import com.google.gson.JsonObject;
import com.walhalla.pillfinder.R;
import com.walhalla.pillfinder.adapter.ComplexPresenter;
import com.walhalla.pillfinder.adapter.ComplexRecyclerViewAdapter;
import com.walhalla.pillfinder.adapter.obj.VieModel;
import com.walhalla.pillfinder.adapter.obj.ingredient.IngredientString;
import com.walhalla.pillfinder.databinding.CategoryListFragmentBinding;
import com.walhalla.pillfinder.fragment.main.BaseFragment;
import com.walhalla.ui.DLog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


// @@@ <T extends JsonObject>

public abstract class SwipeFragment<T extends VieModel> extends BaseFragment
        implements IBaseView<List<T>>, ComplexPresenter, SwipeRefreshLayout.OnRefreshListener
        // , FirebaseManager.GCMListener
{

    protected List<T> adapterSavedState;
    private int index;
    private CategoryListFragmentBinding mBinding;

//    @Override
//    public void onMessage(String from, Bundle data) {
//        Log.d(TAG, "onMessage: " + from);
//    }

    public final static String SAVED_ADAPTER_ITEMS = "SAVED_ADAPTER_ITEMS";
    public final static String SAVED_ADAPTER_KEYS = "SAVED_ADAPTER_KEYS";


    //private FirebaseManager firebaseManager;

    private boolean onSaveInstanceCalled;
    protected ComplexRecyclerViewAdapter mAdapter;

    //private FirebaseRecyclerViewAdapter<Category, CategoryViewHolder> mAdapter;


//    @InjectPresenter
//    protected BaseListPresenter<T> mScreenCategoryListPresenter;
//
//    @ProvidePresenter
//    ListPresenter providePresenter() {
//        return new ();
//    }


    //==============================================================================================

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            // Create channel to show notifications.
//            String channelId = getString(R.string.default_notification_channel_id);
//            String channelName = getString(R.string.default_notification_channel_name);
//            NotificationManager notificationManager =
//                    getContext().getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
//                    channelName, NotificationManager.IMPORTANCE_LOW));
//        }

        // If a notification message is tapped, any data accompanying the notification
        // message is available in the intent extras. In this sample the launcher
        // intent is fired when the notification is tapped, so any accompanying data would
        // be handled here. If you want a different intent fired, set the click_action
        // field of the notification message to the desired intent. The launcher intent
        // is used when no click_action is specified.
        //
        // Handle possible data accompanying notification message.
        // [START handle_data_extras]

//        if (getIntent().getExtras() != null) {
//            for (String key : getIntent().getExtras().keySet()) {
//                Object value = getIntent().getExtras().get(key);
//                Log.d(TAG, "Key: " + key + " Value: " + value);
//            }
//        }
// [END handle_data_extras]
        //setRetainInstance(true);//saved fragment state
        // Check whether we're recreating a previously destroyed instance
        //Retrieve saved state in onCreate. This method is called even when this fragment is on the back stack

        if (savedInstanceState != null && savedInstanceState.containsKey(SAVED_ADAPTER_ITEMS)
            //&& savedInstanceState.containsKey(SAVED_ADAPTER_KEYS)
        ) {

        } else if (getArguments() != null) {
            // Probably initialize members with default values for a new instance
            //setupViews(getArgs());
            //if(App.getInstance(getContext()).isInternetAvailable()){

            //Данные пришли с предыдущего фрагмента...
//            this.uri = getArguments().getString(KEY_RSS_LINK);
//            this.title = getArguments().getString(KEY_TITLE);
            //}
            //else Toast.makeText(mContext, "not connected...", Toast.LENGTH_SHORT).show();

            //

        } else {
            //throw new InvalidArgumentException("args");
        }
        //Log.e(TAG, "#SI: " + savedInstanceState);
        //Log.e(TAG, "#ARG: " + getArguments());
        //Log.e(TAG, "#VARS: " + mRSSTitle + mAdapterSavedState);


//        firebaseManager = FirebaseManagerImpl.getInstance(getContext());
//        firebaseManager.registerListener(this);
//        firebaseManager.subscribeToTopic(Constants.FIREBASE_TOPIC_NAME);
    }


    //App proc killed
    //backstack
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //===============================================================================================
        //When we go to next fragment and return back here, the mAdapter is already present and populated.
        //Don't create it again in such cases. Hence the null check.
        if (mAdapter == null) {
            mAdapter = new ComplexRecyclerViewAdapter(getContext()/*, presenter*/);//new AlbumsAdapter(getContext());//
            mAdapter.setChildItemClickListener(this);
        }
        //Use the state retrieved in onCreate and set it on your views etc in onCreateView
        //This method is not called if the device is rotated when your fragment is on the back stack.
        //That's OK since the next time the device is rotated, we save the state we had retrieved in onCreate
        //instead of saving current state. See onSaveInstanceState for more details.

        if (adapterSavedState != null) {
            onSaveInstanceCalled = true;
        }
        mBinding = CategoryListFragmentBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    //public Object onRetainNonConfigurationInstance() {
    //    return mAdapterSavedState; //saved...
    //}


    //4
    //Fragment 4
    @Override
    public void onViewCreated(@NonNull final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //getLastNonConfigurationInstance()
        // use this if you want the RecyclerView to look like a vertical list view

//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
//        mRecyclerView.setLayoutManager(mLayoutManager);

        // use this if you want the RecyclerView to look like a grid view
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        mBinding.recyclerView.setLayoutManager(layoutManager);
        //       mBinding.recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, Helpers.dpToPx(getContext(), 2), true));
        mBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mBinding.recyclerView.setAdapter(mAdapter);
    }


    //on view state restore


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mBinding.swiperefresh.setOnRefreshListener(this);
        //        d(TAG, "DO_STUFF_" + adapterSavedState);
        if (/*!onSaveInstanceCalled*/ adapterSavedState == null) {
            onRefresh();
        } else {
            Activity activity = getActivity();
            if (activity != null && isAdded()) {
                updateData(adapterSavedState);
            }
            setData();
        }
        if (mBinding.swiperefresh.isRefreshing()) {
            mBinding.swiperefresh.setRefreshing(false);
        }
    }

    protected abstract void setData();


    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
// Save the current state
//        savedInstanceState.putString(KEY_RSS_LINK, uri);
//        savedInstanceState.putString(KEY_TITLE, title);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);

        if (mAdapter != null) {
            //This case is for when the fragment is at the top of the stack. onCreateView was called and hence there is state to save
            //rssItems = rcAdapter.onSaveInstanceState();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

//            case R.id.action_refresh:
//                onRefresh();
//                return true;
//
//            case R.id.action_filter:
//                // Start the refresh background task.
//                // This method calls setRefreshing(false) when it's finished.
//                actionFilter();
//                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected abstract void actionFilter();


    @Override
    public void updateData(List<T> data) {
        if (data == null) {
            data = new ArrayList<>();
        }
        hideLoading();
        List<VieModel> obj = new ArrayList<>();
        obj.addAll(data);
        adapterSavedState = data;
        mAdapter.onRestoreInstanceState(obj);
    }

    @Override
    public void showError(String error) {
        Snackbar.make(mBinding.coordinatorLayout, error, Snackbar.LENGTH_SHORT).show();
        hideLoading();
    }

    @Override
    public void showSuccess(String success) {
        if (mBinding.swiperefresh.isRefreshing()) {
            mBinding.swiperefresh.setRefreshing(false);
        }
        Snackbar.make(mBinding.coordinatorLayout, success, Snackbar.LENGTH_SHORT).show();
        hideLoading();
//        Toast.makeText(getContext(), success, Toast.LENGTH_SHORT).show();
    }

//    private void shareLink(Category category) {
//        Intent sendIntent = new Intent();
//        sendIntent.setAction(Intent.ACTION_SEND);
//        sendIntent.putExtra(Intent.EXTRA_TEXT, category.getTitle() + " " + category.getUrl());
//        sendIntent.setType("text/plain");
//        startActivity(sendIntent);
//    }

//    @OnClick(R.id.fab_new)
//    void createModel(View v) {
//        mScreenCategoryListPresenter.createNewModel();
//    }


    @Override
    public void showLoading() {
        // Signal SwipeRefreshLayout to start the progress indicator
        //swipeRefreshLayout.setRefreshing(true);
        mBinding.progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        if (mBinding.swiperefresh.isRefreshing()) {
            mBinding.swiperefresh.setRefreshing(false);
        }
        mBinding.progress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onRefresh() {
        if (mBinding.swiperefresh.isRefreshing()) {
            mBinding.swiperefresh.setRefreshing(false);
        }
        //LOAD_DATA_FUNCTION
        DLog.d("ACTION_REFRESH --> " + this.getClass().getSimpleName());

    }
}

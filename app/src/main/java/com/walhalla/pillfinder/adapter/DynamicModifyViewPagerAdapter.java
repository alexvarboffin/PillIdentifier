package com.walhalla.pillfinder.adapter;

import static androidx.viewpager.widget.PagerAdapter.POSITION_NONE;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class DynamicModifyViewPagerAdapter extends FragmentStateAdapter {

    private final List<Long> hash = new ArrayList<>();
    private List<Fragment> mFragmentList = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    public DynamicModifyViewPagerAdapter(@NonNull Fragment fragmentActivity) {
        super(fragmentActivity);
    }

    public DynamicModifyViewPagerAdapter(@NonNull Fragment fragment, List<String> titles, List<Fragment> data) {
        super(fragment);
        this.titles.addAll(titles);
        this.mFragmentList.addAll(data);
    }


    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return getItem(position);
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        titles.add(title);
    }

    @Override
    public int getItemCount() {
        return mFragmentList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void swapOne(Fragment fragment, String title) {
        this.mFragmentList = new ArrayList<>();
        this.titles = new ArrayList<>();
        this.mFragmentList.add(fragment);
        this.titles.add(title);
        notifyDataSetChanged();
    }

    /**
     * Magik
     */
    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode();
    }

    @Override
    public boolean containsItem(long itemId) {
//        DLog.d("[HASH]" + itemId);
//        return super.containsItem(itemId);
        return hash.contains(itemId);
    }


//    public void swapAll(List<String> titles, List<Fragment> data) {
//        this.mFragmentList = new ArrayList<>();
//        this.titles = new ArrayList<>();
//        this.mFragmentList.addAll(data);
//        this.hash.clear();
//        for (int i = 0; i < mFragmentList.size(); i++) {
//            hash.add((long) mFragmentList.get(i).hashCode());
//        }
//        this.titles.addAll(titles);
//        notifyDataSetChanged();
//    }

    public void updateEmployeeListItems(List<Fragment> buffer) {
        final EmployeeDiffCallback diffCallback = new EmployeeDiffCallback(this.mFragmentList, buffer);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        this.mFragmentList.clear();
        this.mFragmentList.addAll(buffer);
        diffResult.dispatchUpdatesTo(this);
    }

    public void updateEmployeeListItems(String s) {
        List<Fragment> none = new ArrayList<>();
        final EmployeeDiffCallback diffCallback = new EmployeeDiffCallback(this.mFragmentList, none);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        this.mFragmentList.clear();
        this.mFragmentList.addAll(none);
        diffResult.dispatchUpdatesTo(this);
    }
}

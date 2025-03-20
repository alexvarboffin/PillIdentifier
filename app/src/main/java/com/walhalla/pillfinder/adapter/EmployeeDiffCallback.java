package com.walhalla.pillfinder.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class EmployeeDiffCallback extends DiffUtil.Callback {

    private final List<Fragment> mOldEmployeeList;
    private final List<Fragment> mNewEmployeeList;

    public EmployeeDiffCallback(List<Fragment> oldEmployeeList, List<Fragment> newEmployeeList) {
        this.mOldEmployeeList = oldEmployeeList;
        this.mNewEmployeeList = newEmployeeList;
    }

    @Override
    public int getOldListSize() {
        return mOldEmployeeList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewEmployeeList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldEmployeeList.get(oldItemPosition).getId() == mNewEmployeeList.get(
                newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final Fragment oldEmployee = mOldEmployeeList.get(oldItemPosition);
        final Fragment newEmployee = mNewEmployeeList.get(newItemPosition);

        return oldEmployee.hashCode() == newEmployee.hashCode();
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
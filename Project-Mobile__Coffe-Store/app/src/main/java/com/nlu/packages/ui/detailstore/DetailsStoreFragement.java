package com.nlu.packages.ui.detailstore;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.nlu.packages.R;
import com.nlu.packages.databinding.FragmentDetailsStoreFragementBinding;

import java.util.ArrayList;
import java.util.List;

public class DetailsStoreFragement extends Fragment {
    private ListView listView;
    private StoreAdapter adapter;
    private List<OurData> dataList;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details_store_fragement, container, false);

        dataList = new ArrayList<>();
        dataList.add(new OurData(R.drawable.coffee, "159 Ngoc Lam", "159, Ngoc Lam,Phuong","096 8603588"));
        dataList.add(new OurData(R.drawable.coffee, "159 Ngoc Lam", "159, Ngoc Lam,Phuong","096 8603588"));
        dataList.add(new OurData(R.drawable.coffee, "159 Ngoc Lam", "159, Ngoc Lam,Phuong","096 8603588"));
        dataList.add(new OurData(R.drawable.coffee, "159 Ngoc Lam", "159, Ngoc Lam,Phuong","096 8603588"));
        dataList.add(new OurData(R.drawable.coffee, "159 Ngoc Lam", "159, Ngoc Lam,Phuong","096 8603588"));

        adapter = new StoreAdapter(getActivity(), dataList);
        listView = view.findViewById(R.id.lvPhim);
        listView.setAdapter(adapter);
        return view;
    }

}
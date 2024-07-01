package com.nlu.packages.inventory.lmao;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.nlu.packages.R;
import com.nlu.packages.dto.response.product.ProductResponseDTO;
import com.nlu.packages.enums.EIngredient;
import com.nlu.packages.enums.EProductSize;
import com.nlu.packages.inventory.AmountType;
import com.nlu.packages.inventory.CategoryAdapter;
import com.nlu.packages.inventory.size.SizeAdapter;
import com.nlu.packages.inventory.size.SizeType;
import com.nlu.packages.inventory.topping.ToppingAdapter;
import com.nlu.packages.inventory.topping.ToppingType;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailOrderCoffeeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailOrderCoffeeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Spinner spnSize;
    private Spinner spnMilk;
    private Spinner spnSweet;
    private SizeAdapter sa;
    private CategoryAdapter sa1;
    private ToppingAdapter sa2;
    private ProductResponseDTO prd;
    int quantity;
    public DetailOrderCoffeeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailOrderCoffeeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailOrderCoffeeFragment newInstance(String param1, String param2) {
        DetailOrderCoffeeFragment fragment = new DetailOrderCoffeeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_order_coffee, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Sử dụng cho spinner chọn Size
        spnSize = getView().findViewById(R.id.spinner_size);
        sa = new SizeAdapter(getContext(), R.layout.item_selected, getListSize());
        spnSize.setAdapter(sa);
        spnSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), sa.getItem(position).stringEPS(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Sử dụng cho spinner chọn Milk
        spnMilk = getView().findViewById(R.id.spinner_milk);
        sa1 = new CategoryAdapter(getContext(), R.layout.item_selected, getListMilk());
        spnMilk.setAdapter(sa1);
        spnMilk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), sa1.getItem(position).getType(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Sử dụng cho spinner chọn topping
        spnSweet = getView().findViewById(R.id.spinner_topping);
        sa2 = new ToppingAdapter(getContext(), R.layout.item_selected, getListTopping());
        spnSweet.setAdapter(sa2);
        spnSweet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), sa2.getItem(position).stringEI(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private List<SizeType> getListSize() {
        List<SizeType> list = new ArrayList<>();
        list.add(new SizeType(EProductSize.SMALL));
        list.add(new SizeType(EProductSize.MEDIUM));
        list.add(new SizeType(EProductSize.LARGE));
        return list;
    }
    private List<AmountType> getListMilk() {
        List<AmountType> list = new ArrayList<>();
        list.add(new AmountType("Có"));
        list.add(new AmountType("Không"));
        return list;
    }
    private List<ToppingType> getListTopping() {
        List<ToppingType> list = new ArrayList<>();
        list.add(new ToppingType(EIngredient.CHOCOLATE));
        list.add(new ToppingType(EIngredient.IT_DUONG));
        list.add(new ToppingType(EIngredient.NHIEU_DUONG));
        list.add(new ToppingType(EIngredient.KEM_TUOI));
        list.add(new ToppingType(EIngredient.SHOT_EXPRESSO));
        list.add(new ToppingType(EIngredient.THACH_DUA));
        list.add(new ToppingType(EIngredient.TRAN_CHAU));
        list.add(new ToppingType(EIngredient.SOT_CARAMEL));
        return list;
    }
}
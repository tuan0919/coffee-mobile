package com.nlu.packages.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nlu.packages.R;
import com.nlu.packages.api.ApiService;
import com.nlu.packages.inventory.AmountType;
import com.nlu.packages.inventory.FakeDetailData;
import com.nlu.packages.inventory.CategoryAdapter;
import com.nlu.packages.model.Product;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private Spinner spnDecaf;
    private CategoryAdapter sa;
    private CategoryAdapter sa1;
    private CategoryAdapter sa2;
    private CategoryAdapter sa3;
    private AppCompatButton minusButtonQuantitty;
    private AppCompatButton plusButtonQuantity;
    private TextView quantityText;
    private Product product;
    ImageView productPicture;

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

        // anh san pham
        productPicture = getView().findViewById(R.id.productPicture);productPicture = getView().findViewById(R.id.productPicture);
        //Sử dụng cho spinner chọn Size
        spnSize = getView().findViewById(R.id.spinner_size);
        sa = new CategoryAdapter(getContext(), R.layout.item_selected, getListSize());
        spnSize.setAdapter(sa);
        spnSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), sa.getItem(position).getType(), Toast.LENGTH_SHORT).show();
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
        //Sử dụng cho spinner chọn Sweet
        spnSweet = getView().findViewById(R.id.spinner_sweet);
        sa2 = new CategoryAdapter(getContext(), R.layout.item_selected, getListSweet());
        spnSweet.setAdapter(sa2);
        spnSweet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), sa2.getItem(position).getType(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Sử dụng cho spinner chọn Sweet
        spnDecaf = getView().findViewById(R.id.spinner_decaf);
        sa3 = new CategoryAdapter(getContext(), R.layout.item_selected, getListDecaf());
        spnDecaf.setAdapter(sa3);
        spnSweet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), sa3.getItem(position).getType(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // xu ly tang giam so luong.
        minusButtonQuantitty = getView().findViewById(R.id.minusButtonQuantitty);
        plusButtonQuantity = getView().findViewById(R.id.plusButtonQuantity);
        quantityText = getView().findViewById(R.id.quantityText);
        minusButtonQuantitty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentQuantity = Integer.parseInt(quantityText.getText().toString());
                if (currentQuantity > 1) {
                    currentQuantity--;
                    quantityText.setText(String.valueOf(currentQuantity));
                }
            }
        });
        plusButtonQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentQuantity = Integer.parseInt(quantityText.getText().toString());
                currentQuantity++;
                quantityText.setText(String.valueOf(currentQuantity ));
            }
        });
        //Lenh goi API
        product = new Product();
        ApiService.api.getProduct(1).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                product = response.body();
                Log.d("Taggggg","FaileddddReponse");
                if(product != null) {
                    Log.d("Taggggg","Success");
                    Glide.with(getContext())
                            .load(product.getAvatar())
                            .into(productPicture);
                }
            }
            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Log.d("Taggggg","Failedddd"+t.getMessage());
            }
        });

    }
    FakeDetailData fdd = new FakeDetailData();
    private List<AmountType> getListSize() {
        List<AmountType> list = new ArrayList<>();
        list.add(new AmountType(fdd.getList().get(0)[0]));
        list.add(new AmountType(fdd.getList().get(0)[1]));
        list.add(new AmountType(fdd.getList().get(0)[2]));
        return list;
    }
    private List<AmountType> getListMilk() {
        List<AmountType> list = new ArrayList<>();
        list.add(new AmountType(fdd.getList().get(1)[0]));
        list.add(new AmountType(fdd.getList().get(1)[1]));
        return list;
    }
    private List<AmountType> getListSweet() {
        List<AmountType> list = new ArrayList<>();
        list.add(new AmountType(fdd.getList().get(2)[0]));
        list.add(new AmountType(fdd.getList().get(2)[1]));
        return list;
    }
    private List<AmountType> getListDecaf() {
        List<AmountType> list = new ArrayList<>();
        list.add(new AmountType(fdd.getList().get(3)[0]));
        list.add(new AmountType(fdd.getList().get(3)[1]));
        return list;
    }
}
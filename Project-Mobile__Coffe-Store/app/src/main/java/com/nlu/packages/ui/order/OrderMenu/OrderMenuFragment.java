package com.nlu.packages.ui.order.OrderMenu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nlu.packages.R;

import java.util.ArrayList;
import java.util.Arrays;

public class OrderMenuFragment extends Fragment {
    OrderMenuAdapter adapter;
    ArrayList<OrderMenuCategoryItem> categoryItems;
    ArrayList<OrderMenuProductItem> productItems;
    RecyclerView rvParent;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_menu, container, false);

        rvParent = view.findViewById(R.id.recycleViewOrderMenu);

        String[] category={"Drinks","Bakeries"};
        String[] drinks={"Cold Coffee", "Hot Coffee", "Cookies", "Chocolate Cake"};
        String[] img={"https://mytastycurry.com/wp-content/uploads/2020/04/Cafe-style-cold-coffee-with-icecream.jpg",
        "https://globalassets.starbucks.com/digitalassets/products/bev/SBX20190617_CinnamonDolceLatte.jpg?impolicy=1by1_tight_288",
        "https://assets.bonappetit.com/photos/5ca534485e96521ff23b382b/1:1/w_2560%2Cc_limit/chocolate-chip-cookie.jpg",
        "https://hips.hearstapps.com/hmg-prod/images/chocolate-cake-index-64b83bce2df26.jpg?crop=0.6668359143606668xw:1xh;center,top&resize=1200:*"};
        categoryItems = new ArrayList<>();
        productItems = new ArrayList<>();
        for (int i=0;i<category.length;i++){
            OrderMenuCategoryItem categoryItem=new OrderMenuCategoryItem(category[i]);
            categoryItems.add(categoryItem);
            if(i < category.length){
                OrderMenuProductItem productItem=new OrderMenuProductItem(img[i],drinks[i]);
                productItems.add(productItem);
            }
        }
        adapter = new OrderMenuAdapter(this.getActivity(), categoryItems, productItems);
        rvParent.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvParent.setAdapter(adapter);

        return view;
    }
}
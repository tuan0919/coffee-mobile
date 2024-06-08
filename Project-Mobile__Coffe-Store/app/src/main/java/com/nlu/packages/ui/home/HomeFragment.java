package com.nlu.packages.ui.home;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nlu.packages.R;
import com.nlu.packages.ui.order.OrderFragment;
import com.squareup.picasso.Picasso;

public class HomeFragment extends Fragment {

    ImageView imageViewHome1, imageViewHome2, imageViewHome3, imageViewHome4;
    ConstraintLayout constraintLayoutHome;
    SearchView searchView;
    androidx.appcompat.widget.AppCompatButton button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //add image with url
        imageViewHome1 = view.findViewById(R.id.imageHomeFragment1);
        imageViewHome2 = view.findViewById(R.id.imageHomeFragment2);
        imageViewHome3 = view.findViewById(R.id.imageHomeFragment3);
        imageViewHome4 = view.findViewById(R.id.imageHomeFragment4);
        Picasso.get().load("https://www.peerlesscoffee.com/wp-content/uploads/2019/04/home-latte.jpg").into(imageViewHome1);
        Picasso.get().load("https://product.hstatic.net/1000075078/product/1675355354_bg-tch-sua-da-no_4fbf208885ed464ab4b5e145336d42a2_grande.jpg").into(imageViewHome2);
        Picasso.get().load("https://bizweb.dktcdn.net/thumb/grande/100/168/994/products/hinh-app-3006021-bac-xiu-600x600.jpg?v=1704953520003").into(imageViewHome3);
        Picasso.get().load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMkrl2cJ_c3eYDYqmwrGZ4ReL3taAU8rinUA&s").into(imageViewHome4);

        //add hide soft keyboard
        constraintLayoutHome = view.findViewById(R.id.constraintLayout3);
        searchView = view.findViewById(R.id.searchViewHome1);
        setUpUI(view);

        //change to OrderFragment
        button=view.findViewById(R.id.order_Button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new OrderFragment());
                BottomNavigationView navView = getActivity().findViewById(R.id.nav_view);
                navView.setSelectedItemId(R.id.navigation_order);
            }
        });


        return view;
    }

    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void setUpUI(View view) {
        if (!(view instanceof SearchView)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    hideKeyboard(view);
                    return false;
                }
            });
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setUpUI(innerView);
            }
        }
    }
    private void loadFragment(Fragment fragment) {
        Fragment orderFragment = new OrderFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
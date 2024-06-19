package com.nlu.packages.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nlu.packages.CartActivity;
import com.nlu.packages.R;
import com.nlu.packages.ui.order.OrderFragment;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements CoffeeForYouRvInterface, TopCoffeeRvInterface {
    ImageView imageViewHome1, imageViewHome2, imageViewHome3, imageViewHome4;
    ConstraintLayout constraintLayoutHome;
    SearchView searchView;
    androidx.appcompat.widget.AppCompatButton button;
    RecyclerView coffeeForYouRv, topPickRv;
    ArrayList<String> coffeeForYouDataSource, topPickDataSource;
    LinearLayoutManager linearLayoutManager;
    CoffeForYouRvAdapter coffeForYouRvAdapter;
    TopPickRvAdapter topPickRvAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //add hide soft keyboard
        constraintLayoutHome = view.findViewById(R.id.constraintLayoutHomeFragment);
        searchView = view.findViewById(R.id.searchViewHome1);
        setUpUI(view);

        //change to OrderFragment
        button = view.findViewById(R.id.order_Button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new OrderFragment());
                BottomNavigationView navView = getActivity().findViewById(R.id.nav_view);
                navView.setSelectedItemId(R.id.navigation_order);
            }
        });

        //setting for coffee for you recycle view
        coffeeForYouRv = view.findViewById(R.id.homeCoffeeForYou);

        //setting the data source
        coffeeForYouDataSource = new ArrayList<>();
        coffeeForYouDataSource.add("Expesso");
        coffeeForYouDataSource.add("Cappuccino");
        coffeeForYouDataSource.add("Latte");
        coffeeForYouDataSource.add("Mocha");

        //setting the coffee for you adapter
        linearLayoutManager = new LinearLayoutManager(HomeFragment.this.getContext(), LinearLayoutManager.HORIZONTAL, false);
        coffeForYouRvAdapter = new CoffeForYouRvAdapter(this.getContext(), coffeeForYouDataSource, this);
        coffeeForYouRv.setLayoutManager(linearLayoutManager);
        coffeeForYouRv.setAdapter(coffeForYouRvAdapter);

        //setting for top pick recycle view
        topPickRv = view.findViewById(R.id.homeTopPick);

        //setting the data source
        topPickDataSource = new ArrayList<>();
        topPickDataSource.add("Cappuccino");
        topPickDataSource.add("Latte");
        topPickDataSource.add("Mocha");
        topPickDataSource.add("Expesso");

        //setting the top pick adapter
        linearLayoutManager = new LinearLayoutManager(HomeFragment.this.getContext(), LinearLayoutManager.HORIZONTAL, false);
        topPickRvAdapter = new TopPickRvAdapter(this.getContext(), topPickDataSource, this);
        topPickRv.setLayoutManager(linearLayoutManager);
        topPickRv.setAdapter(topPickRvAdapter);

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

    private void loadActivity(Class<? extends AppCompatActivity> activity) {
        Intent intent = new Intent(getActivity(), activity);
        startActivity(intent);
    }

    @Override
    public void onItemClickCoffeeForYou(int position) {
        Intent intent = new Intent(HomeFragment.this.getContext(), CartActivity.class);

        intent.putExtra("Name", coffeeForYouDataSource.get(position));

        startActivity(intent);
    }

    @Override
    public void onItemClickTopCoffee(int position) {
        Intent intent = new Intent(HomeFragment.this.getContext(), CartActivity.class);

        intent.putExtra("Name", topPickDataSource.get(position));

        startActivity(intent);
    }
}
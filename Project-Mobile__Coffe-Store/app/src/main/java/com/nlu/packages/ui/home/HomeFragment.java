package com.nlu.packages.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nlu.packages.DetailProductOrderActivity;
import com.nlu.packages.R;
import com.nlu.packages.ui.order.OrderFragment;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class HomeFragment extends Fragment {

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
        coffeForYouRvAdapter = new CoffeForYouRvAdapter(coffeeForYouDataSource);
        coffeeForYouRv.setLayoutManager(linearLayoutManager);
        coffeeForYouRv.setAdapter(coffeForYouRvAdapter);

        //setting for top pick recycle view
        topPickRv = view.findViewById(R.id.homeTopPick);

            //setting the data source
        topPickDataSource=new ArrayList<>();
        topPickDataSource.add("Cappuccino");
        topPickDataSource.add("Latte");
        topPickDataSource.add("Mocha");
        topPickDataSource.add("Expesso");

            //setting the top pick adapter
        linearLayoutManager = new LinearLayoutManager(HomeFragment.this.getContext(), LinearLayoutManager.HORIZONTAL, false);
        topPickRvAdapter = new TopPickRvAdapter(topPickDataSource);
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

    class CoffeForYouRvAdapter extends RecyclerView.Adapter<CoffeForYouRvAdapter.MyHolder> {
        ArrayList<String> data;

        public CoffeForYouRvAdapter(ArrayList<String> data) {
            this.data = data;
        }

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(HomeFragment.this.getContext()).inflate(R.layout.coffee_for_you_rv, null, false);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {
            holder.textView1.setText(data.get(position));
            Picasso.get().load("https://media.istockphoto.com/id/1358132613/photo/refreshing-hot-cup-of-coffee-at-a-cafe.jpg?s=612x612&w=0&k=20&c=ObwIF28Vt3k93Nch9U4QYUdOwMA_eiMwVVCvKbypnNc=").into(holder.imageView1);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class MyHolder extends RecyclerView.ViewHolder {
            TextView textView1;
            ImageView imageView1;

            public MyHolder(@NonNull View itemView) {
                super(itemView);
                textView1 = itemView.findViewById(R.id.homeCoffeeTitleRv);
                imageView1 = itemView.findViewById(R.id.homeCoffeeImageRv);
            }
        }
    }

    class TopPickRvAdapter extends RecyclerView.Adapter<TopPickRvAdapter.MyHolder> {
        ArrayList<String> data;

        public TopPickRvAdapter(ArrayList<String> data) {
            this.data = data;
        }

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(HomeFragment.this.getContext()).inflate(R.layout.top_pick_rv, null, false);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {
            holder.textView2.setText(data.get(position));
            Picasso.get().load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRLxLKJHXvIkiVTP4oRp0WkgYGaTBtisWBc3mXdJFMv_6A5PHRl2AZbVaQpCdPNHyjlrAE&usqp=CAU").into(holder.imageView2);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class MyHolder extends RecyclerView.ViewHolder {
            TextView textView2;
            ImageView imageView2;

            public MyHolder(@NonNull View itemView) {
                super(itemView);
                textView2 = itemView.findViewById(R.id.topPickTitleRv);
                imageView2 = itemView.findViewById(R.id.topPickImageRv);
            }
        }
    }
}
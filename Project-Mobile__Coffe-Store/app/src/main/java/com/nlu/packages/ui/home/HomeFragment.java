package com.nlu.packages.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nlu.packages.DetailProductOrderActivity;
import com.nlu.packages.response_dto.product.ProductResponseDTO;
import com.nlu.packages.ui.cart.CartActivity;
import com.nlu.packages.R;
import com.nlu.packages.service.CoffeeApi;
import com.nlu.packages.service.CoffeeService;
import com.nlu.packages.ui.order.OrderFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.nlu.packages.ui.user.ProfileActivity;
import lombok.var;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements CoffeeForYouRvInterface, TopCoffeeRvInterface {
    private ConstraintLayout constraintLayoutHome;
    private SearchView searchView;
    private androidx.appcompat.widget.AppCompatButton button;
    private RecyclerView coffeeForYouRv, topPickRv;
    private ArrayList<ProductResponseDTO> coffeeForYouDataSource, topPickDataSource = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    CoffeForYouRvAdapter coffeForYouRvAdapter;
    TopPickRvAdapter topPickRvAdapter;
    private CoffeeApi coffeeApi;
    private ImageButton avatarButton;
    private Consumer<ProductResponseDTO> onClickHandler;


    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Event Listener
        onClickHandler = (productDTO) -> {
            CoffeeService.getClient()
                    .getProduct("nuoc-uong", "",
                            Map.of("id", productDTO.getProductId()+""))
                    .enqueue(new Callback<List<ProductResponseDTO>>() {
                        @Override
                        public void onResponse(Call<List<ProductResponseDTO>> call, Response<List<ProductResponseDTO>> response) {
                            var intent = new Intent(getContext(), DetailProductOrderActivity.class);
                            intent.putExtra("productOrder", (ArrayList<ProductResponseDTO>) response.body());
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<List<ProductResponseDTO>> call, Throwable throwable) {

                        }
                    });
        };

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

        avatarButton = view.findViewById(R.id.btn_avatar);
        avatarButton.setOnClickListener((v) -> {
            Intent intent = new Intent(getContext(), ProfileActivity.class);
            startActivity(intent);
        });

        //setting for `coffee for you` and `top pick` data source
        getListCoffee();

        //setting for coffee for you recycle view
        coffeeForYouRv = view.findViewById(R.id.homeCoffeeForYou);

        //setting the `coffee for you` adapter
        linearLayoutManager = new LinearLayoutManager(HomeFragment.this.getContext(), LinearLayoutManager.HORIZONTAL, false);
        coffeForYouRvAdapter = new CoffeForYouRvAdapter(this.getContext(), coffeeForYouDataSource, this, onClickHandler);
        coffeeForYouRv.setLayoutManager(linearLayoutManager);
        coffeeForYouRv.setAdapter(coffeForYouRvAdapter);

        //setting for top pick recycle view
        topPickRv = view.findViewById(R.id.homeTopPick);

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

        intent.putExtra("ProductName", coffeeForYouDataSource.get(position).getProductName());
        intent.putExtra("Avatar", coffeeForYouDataSource.get(position).getAvatar());
        intent.putExtra("BasePrice", coffeeForYouDataSource.get(position).getBasePrice());

        startActivity(intent);
    }

    @Override
    public void onItemClickTopCoffee(int position) {
        Intent intent = new Intent(HomeFragment.this.getContext(), CartActivity.class);

        intent.putExtra("ProductName", topPickDataSource.get(position).getProductName());
        intent.putExtra("Avatar", topPickDataSource.get(position).getAvatar());
        intent.putExtra("BasePrice", topPickDataSource.get(position).getBasePrice());

        startActivity(intent);
    }

    public void getListCoffee() {
        coffeeApi = CoffeeService.getClient();
        Call<List<ProductResponseDTO>> call = coffeeApi.getAllProduct();
        call.enqueue(new Callback<List<ProductResponseDTO>>() {
            @Override
            public void onResponse(Call<List<ProductResponseDTO>> call, Response<List<ProductResponseDTO>> response) {
                if (response.isSuccessful()) {
                    //get response data for `coffee for you`
                    List<ProductResponseDTO> responseDTOS = response.body();
                    coffeeForYouDataSource = (ArrayList<ProductResponseDTO>) responseDTOS;

                    //get response data for `top pick`
                    topPickDataSource = (ArrayList<ProductResponseDTO>) response.body();

                    //update data to adapter
                    coffeForYouRvAdapter.updateData(responseDTOS);

                    //shuffle the data  
                    Collections.shuffle(responseDTOS);

                    //re-update data to adapter
                    topPickRvAdapter.updateData(topPickDataSource);
                } else {
                    System.out.println("lỗi lấy data");
                }
            }

            @Override
            public void onFailure(Call<List<ProductResponseDTO>> call, Throwable throwable) {
                System.out.println(throwable.getMessage());
            }
        });
    }
}
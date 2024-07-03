package com.nlu.packages.ui.order;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.nlu.packages.R;
import com.nlu.packages.response_dto.product.ProductResponseDTO;
import com.nlu.packages.service.CoffeeApi;
import com.nlu.packages.service.CoffeeService;
import com.nlu.packages.ui.order.OrderFavorite.OrderFavoriteFragment;
import com.nlu.packages.ui.order.OrderMenu.OrderMenuFragment;
import com.nlu.packages.ui.order.OrderPopular.OrderPopularFragment;
import com.nlu.packages.ui.order.OrderPrevious.OrderPreviousFragment;
import com.nlu.packages.ui.order.OrderProduct.ProductSearch;
import lombok.var;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment {
    private SearchView searchView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageButton backButton;
    private CoffeeApi coffeeApi=CoffeeService.getClient();
    public OrderFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        tabLayout = view.findViewById(R.id.tabLayoutOrderFragment);
        viewPager = view.findViewById(R.id.viewPagerOrder);
        backButton = view.findViewById(R.id.orderBackButton);
        searchView = view.findViewById(R.id.searchViewOrder1);
        coffeeApi = CoffeeService.getClient();

        OrderViewPageAdapter adapter = new OrderViewPageAdapter(
                getActivity().getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(new OrderMenuFragment(), "Menu");
        adapter.addFragment(new OrderPopularFragment(), "Popular");
        adapter.addFragment(new OrderPreviousFragment(), "Previous");
        adapter.addFragment(new OrderFavoriteFragment(), "Favorites");
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                String title = adapter.getPageTitle(i).toString();
                if (title.equals("Previous")) {
                    OrderPreviousFragment fragment =
                            (OrderPreviousFragment) adapter.getItem(i);
                    fragment.loadPreviousOrderData();
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        //xử lý sự kiện tìm kiếm, tra cứu thông tin
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Call<List<ProductResponseDTO>> call = coffeeApi.searchProduct(query);
                call.enqueue(new Callback<List<ProductResponseDTO>>() {
                    @Override
                    public void onResponse(Call<List<ProductResponseDTO>> call, Response<List<ProductResponseDTO>> response) {
                        List<ProductResponseDTO> responseDTOS = response.body();
                        if(responseDTOS.isEmpty()){
                            Toast.makeText(getContext(),"Không tìm thấy", Toast.LENGTH_SHORT).show();
                        }else{
                            Intent intent = new Intent(OrderFragment.this.getContext(), ProductSearch.class);
                            intent.putExtra("ProductOrder", (ArrayList<ProductResponseDTO>) responseDTOS);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ProductResponseDTO>> call, Throwable throwable) {
                        System.out.println(throwable);
                    }
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        //Phân tab bằng viewpager
        tabLayout.setupWithViewPager(viewPager);

        for (int i=0; i<tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if(tab != null){
                TextView tabTextView = new TextView(this.getContext());
                tabTextView.setText(tab.getText());
                tabTextView.setTextSize(15);
                if (i == 0) {
                    // First tab is selected by default
                    tabTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.Secondary));
                } else {
                    tabTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.Greyscale1));
                }
                tabTextView.setTypeface(tabTextView.getTypeface(), Typeface.BOLD);
                tabTextView.setAllCaps(false);
                tab.setCustomView(tabTextView);
            }
        }

        //xử lý sự kiện khi tab được nhấn vào
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View customView = tab.getCustomView();
                if(customView instanceof TextView){
                    ((TextView) customView).setTextColor(ContextCompat.getColor(requireContext(), R.color.Secondary));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View customView = tab.getCustomView();
                if (customView instanceof TextView) {
                    ((TextView) customView).setTextColor(ContextCompat.getColor(requireContext(), R.color.Greyscale1));
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //xử lý sự kiện cho nút back button
        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        return view;
    }

    //Trở lại activity cũ
    public void onBackPressed(){
        getActivity().getSupportFragmentManager().popBackStack();
    }
}
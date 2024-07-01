package com.nlu.packages.ui.order;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.nlu.packages.R;
import com.nlu.packages.dto.response.product.ProductResponseDTO;
import com.nlu.packages.ui.order.OrderFavorite.OrderFavoriteFragment;
import com.nlu.packages.ui.order.OrderMenu.OrderMenuFragment;
import com.nlu.packages.ui.order.OrderPopular.OrderPopularFragment;
import com.nlu.packages.ui.order.OrderPopular.PopularDrinksRvAdapter;
import com.nlu.packages.ui.order.OrderPopular.TrendThisMonthRvAdapter;
import com.nlu.packages.ui.order.OrderPrevious.OrderPreviousFragment;

import java.util.List;

public class OrderFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageButton backButton;
    public static OrderFragment orderFragment;
    public OrderFragment() {
    }
    public static OrderFragment newInstance() {
        if (orderFragment == null) {
            orderFragment = new OrderFragment();
        }
        return orderFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        tabLayout = view.findViewById(R.id.tabLayoutOrderFragment);
        viewPager = view.findViewById(R.id.viewPagerOrder);
        backButton = view.findViewById(R.id.orderBackButton);

        OrderViewPageAdapter adapter = new OrderViewPageAdapter(
                getActivity().getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(new OrderMenuFragment(), "Menu");
        adapter.addFragment(new OrderPopularFragment(), "Popular");
        adapter.addFragment(new OrderPreviousFragment(), "Previous");
        adapter.addFragment(new OrderFavoriteFragment(), "Favorites");
        viewPager.setAdapter(adapter);

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
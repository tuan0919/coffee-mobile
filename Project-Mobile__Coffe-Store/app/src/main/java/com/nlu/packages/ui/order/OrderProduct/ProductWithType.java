package com.nlu.packages.ui.order.OrderProduct;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nlu.packages.CartActivity;
import com.nlu.packages.R;
import com.nlu.packages.ui.home.HomeFragment;
import com.nlu.packages.ui.order.OrderPopular.PopularDrinksRvAdapter;

import java.util.ArrayList;

public class ProductWithType extends AppCompatActivity implements ProductWithCategoryRvInterface{
    //recycler view
    RecyclerView productWithCategoryRv;
    ArrayList<String> productWithCategoryDataSource;
    //layout manager
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            //adapter
            ProductWithCategoryRvAdapter productWithCategoryRvAdapter;

            //setting for coffee for you recycle view
            productWithCategoryRv = findViewById(R.id.productWithCategoryRv);

            //setting the data source
            productWithCategoryDataSource = new ArrayList<>();
            productWithCategoryDataSource.add("Expesso");
            productWithCategoryDataSource.add("Cappuccino");
            productWithCategoryDataSource.add("Latte");
            productWithCategoryDataSource.add("Mocha");
            productWithCategoryDataSource.add("Milk Tea");
            productWithCategoryDataSource.add("Olong");
            productWithCategoryDataSource.add("Milk Caffee");
            productWithCategoryDataSource.add("Bac Xiu");

            //setting for `product with category` recycle view
            productWithCategoryRv = findViewById(R.id.productWithCategoryRv);
            layoutManager = new GridLayoutManager(this, 2);
            productWithCategoryRv.setLayoutManager(layoutManager);

            //setting the `product with category` adapter
            productWithCategoryRvAdapter = new ProductWithCategoryRvAdapter(this, productWithCategoryDataSource, this);
            productWithCategoryRv.setAdapter(productWithCategoryRvAdapter);
            productWithCategoryRv.setHasFixedSize(true);

            return insets;
        });
    }

    @Override
    public void onItemClickProductWithCategory(int position) {
        Intent intent = new Intent(ProductWithType.this, CartActivity.class);

        intent.putExtra("Name", productWithCategoryDataSource.get(position));

        startActivity(intent);
    }
}
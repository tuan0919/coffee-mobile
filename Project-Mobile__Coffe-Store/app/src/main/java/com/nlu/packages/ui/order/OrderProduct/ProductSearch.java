package com.nlu.packages.ui.order.OrderProduct;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.nlu.packages.R;
import com.nlu.packages.response_dto.product.ProductResponseDTO;
import com.nlu.packages.ui.cart.CartActivity;

import java.util.ArrayList;

public class ProductSearch extends AppCompatActivity implements ProductSearchRvInterface {
    //recycler view
    RecyclerView productWithCategoryRv;
    ArrayList<ProductResponseDTO> responseDTOS;
    //layout manager
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.product_list), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            //adapter
            ProductSearchRvAdapter productSearchRvAdapter;

            //setting for coffee for you recycle view
            productWithCategoryRv = findViewById(R.id.productWithCategoryRv);

            //setting the data source
            Intent intent = getIntent();
            responseDTOS = (ArrayList<ProductResponseDTO>) intent.getSerializableExtra("ProductOrder");

            //setting for `product with category` recycle view
            productWithCategoryRv = findViewById(R.id.productWithCategoryRv);
            layoutManager = new GridLayoutManager(this, 2);
            productWithCategoryRv.setLayoutManager(layoutManager);

            //setting the `product with category` adapter
            productSearchRvAdapter = new ProductSearchRvAdapter(this, responseDTOS, this);
            productWithCategoryRv.setAdapter(productSearchRvAdapter);
            productWithCategoryRv.setHasFixedSize(true);

            return insets;
        });
    }

    @Override
    public void onItemClickProductWithCategory(int position) {
        Intent intent = new Intent(ProductSearch.this, CartActivity.class);

        intent.putExtra("ProductItem", responseDTOS.get(position));

        startActivity(intent);
    }

}
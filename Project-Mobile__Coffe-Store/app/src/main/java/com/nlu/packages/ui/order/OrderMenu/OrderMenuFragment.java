package com.nlu.packages.ui.order.OrderMenu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.nlu.packages.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class OrderMenuFragment extends Fragment {
    OrderMenuAdapter adapter;
    ArrayList<OrderMenuTypeItem> typeItems;
    RecyclerView rvParent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_menu, container, false);
        rvParent = view.findViewById(R.id.recycleViewOrderMenu);
        String jsonTest = "[\n" +
                "    {\n" +
                "        \"categoryId\": 1,\n" +
                "        \"categoryName\": \"Cà phê máy\",\n" +
                "        \"avatar\": \"https://phadincoffee.com/wp-content/uploads/2020/07/foresto-3085-008-2.jpg\",\n" +
                "        \"type\": \"DRINK\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"categoryId\": 2,\n" +
                "        \"categoryName\": \"Cà phê Việt Nam\",\n" +
                "        \"avatar\": \"https://vinbarista.com/uploads/editer/images/1.jpg\",\n" +
                "        \"type\": \"DRINK\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"categoryId\": 3,\n" +
                "        \"categoryName\": \"Cold Brew\",\n" +
                "        \"avatar\": \"https://cdn.tgdd.vn/2020/07/CookProductThumb/thumbnew-620x620-11.jpg\",\n" +
                "        \"type\": \"DRINK\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"categoryId\": 4,\n" +
                "        \"categoryName\": \"Trái cây xay\",\n" +
                "        \"avatar\": \"https://cdn.tgdd.vn/Files/2017/04/24/975816/bi-quyet-pha-sinh-to-ngon-dung-dieu-ngay-tai-nha-1_760x507.jpg\",\n" +
                "        \"type\": \"FRUIT\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"categoryId\": 5,\n" +
                "        \"categoryName\": \"Trà trái cây\",\n" +
                "        \"avatar\": \"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR0-iURLXTn69SM_Qrll0j_aFhsYxByDDw8EA&s\",\n" +
                "        \"type\": \"FRUIT\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"categoryId\": 6,\n" +
                "        \"categoryName\": \"Trà xanh\",\n" +
                "        \"avatar\": \"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQx7MuBq9K1kD32eWKjiJm_NHG6A-KQaiVAeQ&s\",\n" +
                "        \"type\": \"DRINK\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"categoryId\": 7,\n" +
                "        \"categoryName\": \"Đá xay\",\n" +
                "        \"avatar\": \"https://noithatcaphe.vn/images/2022/07/11/%C4%91%E1%BB%93%20u%E1%BB%91ng%20%C4%91%C3%A1%20xay%202.jpg\",\n" +
                "        \"type\": \"DRINK\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"categoryId\": 8,\n" +
                "        \"categoryName\": \"Bánh ngọt\",\n" +
                "        \"avatar\": \"https://caphenguyenchat.vip/wp-content/uploads/2021/06/6-loai-banh-an-kem-cafe-6.jpg\",\n" +
                "        \"type\": \"FOOD\"\n" +
                "    }\n" +
                "]\n";
        Gson gson = new Gson();
        List<Category> categoryList = new ArrayList<>();
        JsonArray jsonArray = gson.fromJson(jsonTest, JsonArray.class);
        for (int i = 0; i < jsonArray.size(); i++) {
            Category category1 = new Category();
            category1.setCategoryId(jsonArray.get(i).getAsJsonObject().get("categoryId").getAsInt());
            category1.setCategoryName(jsonArray.get(i).getAsJsonObject().get("categoryName").getAsString());
            category1.setAvatar(jsonArray.get(i).getAsJsonObject().get("avatar").getAsString());
            category1.setType(jsonArray.get(i).getAsJsonObject().get("type").getAsString());
            categoryList.add(category1);
        }
        Map<String, List<Category>> categoryMap = categoryList.stream().collect(Collectors.groupingBy(Category::getType));

        typeItems = new ArrayList<>();
        List<Objects> items = new ArrayList<>();
        categoryMap.forEach((type, typeList) -> {
            ArrayList<OrderMenuCategoryItem> categoryItems = new ArrayList<>();
            typeList.forEach(category -> {
                OrderMenuCategoryItem productItem = new OrderMenuCategoryItem();
                productItem.setImageUrl(category.getAvatar());
                productItem.setProductName(category.getCategoryName());
                categoryItems.add(productItem);
            });
            OrderMenuTypeItem categoryItem = new OrderMenuTypeItem(type,categoryItems);
            typeItems.add(categoryItem);
        });
        adapter = new OrderMenuAdapter(this.getActivity(), typeItems);
        rvParent.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvParent.setAdapter(adapter);

        return view;
    }
}
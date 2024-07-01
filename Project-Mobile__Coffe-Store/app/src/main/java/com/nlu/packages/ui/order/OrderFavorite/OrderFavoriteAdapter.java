package com.nlu.packages.ui.order.OrderFavorite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.nlu.packages.R;
import com.nlu.packages.response_dto.MessageResponseDTO;
import com.nlu.packages.response_dto.product.ProductResponseDTO;
import com.nlu.packages.response_dto.wishlist.WishlistRequestDTO;
import com.nlu.packages.service.CoffeeApi;
import com.nlu.packages.service.CoffeeService;
import com.squareup.picasso.Picasso;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class OrderFavoriteAdapter extends RecyclerView.Adapter<OrderFavoriteAdapter.MyHolder> {
    Context context;
    List<ProductResponseDTO> data;
    private final OrderFavoriteRvInterface orderFavoriteRvInterface;
    private Consumer<ProductResponseDTO> onClickHandler;
    private CoffeeApi coffeeApi;
    private List<Long> productIds = new ArrayList<>();
    private WishlistRequestDTO wishlistRequestDTO = new WishlistRequestDTO();

    public OrderFavoriteAdapter(Context context, List<ProductResponseDTO> data,
                                OrderFavoriteRvInterface orderFavoriteRvInterface) {
        this.context = context;
        this.data = data != null ? data : new ArrayList<>();
        this.orderFavoriteRvInterface = orderFavoriteRvInterface;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favorite_order,
                        parent, false);
        return new MyHolder(view, orderFavoriteRvInterface);
    }

    //khởi tạo init favorite để lấy dữ liệu từ api
    private void initFavorite() {
        coffeeApi = CoffeeService.getRetrofitInstance("token");
        retrofit2.Call<List<ProductResponseDTO>> call = coffeeApi.getWishList();
        call.enqueue(new Callback<List<ProductResponseDTO>>() {
            @Override
            public void onResponse(retrofit2.Call<List<ProductResponseDTO>> call, Response<List<ProductResponseDTO>> response) {
                List<ProductResponseDTO> responseDTOS = response.body();
                if (response.isSuccessful()) {
                    if (responseDTOS != null) {
                        responseDTOS.forEach(e -> {
                            if (!productIds.contains(e.getProductId())) {
                                productIds.add(e.getProductId());
                            }
                        });
                        wishlistRequestDTO.setProductIds(productIds);
                    } else {
                        System.out.println("Null List");
                    }
                }else{
                    onFailure(call, new Throwable("Error"));
                }
            }

            @Override
            public void onFailure(Call<List<ProductResponseDTO>> call, Throwable throwable) {
                System.out.println(throwable);
            }
        });
        wishlistRequestDTO.setProductIds(productIds);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.productName.setText(data.get(position).getProductName());
        holder.productPrice.setText(String.valueOf(data.get(position).getBasePrice()));
        Picasso.get().load(data.get(position).getAvatar()).into(holder.imageView1);

        // Lấy danh sách sản phẩm yêu thích từ API nếu chưa có
        if (productIds == null) {
            initFavorite();
        }

        //xử lý sự kiện xoá sản phẩm trong favorite
        holder.favoriteButton.setOnClickListener(view -> {
            initFavorite();
            Call<MessageResponseDTO> call = coffeeApi.removeFromWishList(data.get(position).getProductId());
            call.enqueue(new Callback<MessageResponseDTO>() {
                @Override
                public void onResponse(Call<MessageResponseDTO> call, Response<MessageResponseDTO> response) {
                    Toast.makeText(context, "Removed from Favorite", Toast.LENGTH_SHORT).show();
                    data.remove(position);
                    orderFavoriteRvInterface.updateUI();
                }

                @Override
                public void onFailure(Call<MessageResponseDTO> call, Throwable throwable) {
                    System.out.println(throwable);
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView productName;
        TextView productPrice;
        ImageView imageView1;
        ImageButton favoriteButton;

        public MyHolder(@NonNull View itemView, OrderFavoriteRvInterface orderFavoriteRvInterface) {
            super(itemView);
            productName = itemView.findViewById(R.id.orderFavoriteProductName);
            productPrice = itemView.findViewById(R.id.orderFavoritePrice);
            imageView1 = itemView.findViewById(R.id.orderFavoriteImageRv);
            favoriteButton = itemView.findViewById(R.id.orderFavoriteChecked);

            //xử lý sự kiện khi và 1 hình ảnh được nhấn vào sẽ chuyển qua trang chi tiết sản phẩm
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (orderFavoriteRvInterface != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            orderFavoriteRvInterface.onClickedFavoriteItem(position);
                        }
                    }
                }
            });
        }

        public void renderView(ProductResponseDTO productResponseDTO) {
            favoriteButton.setOnClickListener(v -> {
                onClickHandler.accept(productResponseDTO);
            });
        }
    }

    public void updateData(List<ProductResponseDTO> newList) {
        this.data.clear();
        this.data.addAll(newList);
        notifyDataSetChanged();
    }
}

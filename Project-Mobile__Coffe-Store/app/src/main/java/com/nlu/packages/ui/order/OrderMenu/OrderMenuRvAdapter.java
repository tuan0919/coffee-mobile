package com.nlu.packages.ui.order.OrderMenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
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

public class OrderMenuRvAdapter extends RecyclerView.Adapter<OrderMenuRvAdapter.ViewHolder> {

    private Context context;
    private List<ProductResponseDTO> data;
    private final OrderMenuRvInterface orderMenuRvInterface;
    private CoffeeApi coffeeApi;
    private List<Long> productIds = new ArrayList<>();
    private WishlistRequestDTO wishlistRequestDTO = new WishlistRequestDTO();


    public OrderMenuRvAdapter(Context context, List<ProductResponseDTO> data, OrderMenuRvInterface orderMenuRvInterface) {
        this.context = context;
        this.data = data != null ? data : new ArrayList<>();
        this.orderMenuRvInterface = orderMenuRvInterface;
    }

    @NonNull
    @Override
    public OrderMenuRvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_menu_item_rv,
                        parent, false);
        return new ViewHolder(view, orderMenuRvInterface);
    }

    private void initFavorite() {
        coffeeApi = CoffeeService.getClient();
        Call<List<ProductResponseDTO>> call = coffeeApi.getWishList();
        call.enqueue(new Callback<List<ProductResponseDTO>>() {
            @Override
            public void onResponse(Call<List<ProductResponseDTO>> call, Response<List<ProductResponseDTO>> response) {
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
                } else {
                    onFailure(call, new Throwable("Uncessfull Response"));
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
    public void onBindViewHolder(@NonNull OrderMenuRvAdapter.ViewHolder holder, int position) {
        holder.productName.setText(data.get(position).getProductName());
        Picasso.get().load(data.get(position).getAvatar()).into(holder.imageView);

        initFavorite();
        if(productIds.contains(data.get(position).getProductId())){
            holder.toggleButton.setChecked(true);
        }

        // Lấy danh sách sản phẩm yêu thích từ API nếu chưa có
        if (productIds == null) {
            initFavorite();
        }

        //xử lý sự kiện cho `add to favorite`
        holder.toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                productIds.clear();
                wishlistRequestDTO.getProductIds().add(data.get(position).getProductId());
                Call<MessageResponseDTO> call = coffeeApi.addToWishList(wishlistRequestDTO);
                call.enqueue(new Callback<MessageResponseDTO>() {
                    @Override
                    public void onResponse(Call<MessageResponseDTO> call, Response<MessageResponseDTO> response) {
                        Toast.makeText(context, "Added to Favorite", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<MessageResponseDTO> call, Throwable throwable) {
                        System.out.println(throwable);
                    }
                });
            } else {
                Call<MessageResponseDTO> call = coffeeApi.removeFromWishList(data.get(position).getProductId());
                call.enqueue(new Callback<MessageResponseDTO>() {
                    @Override
                    public void onResponse(Call<MessageResponseDTO> call, Response<MessageResponseDTO> response) {
                        Toast.makeText(context, "Removed from Favorite", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<MessageResponseDTO> call, Throwable throwable) {
                        System.out.println(throwable.getMessage());
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName;
        ImageView imageView;
        ToggleButton toggleButton;

        public ViewHolder(@NonNull View itemView, OrderMenuRvInterface orderMenuRvInterface) {
            super(itemView);

            productName = itemView.findViewById(R.id.orderMenuProductName);
            imageView = itemView.findViewById(R.id.orderMenuImageRv);
            toggleButton = itemView.findViewById(R.id.orderMenuFavorite);

            //xử lý sự kiện khi và 1 hình ảnh được nhấn vào sẽ chuyển qua trang chi tiết sản phẩm
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (orderMenuRvInterface != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            orderMenuRvInterface.onClickedMenuItem(position);
                        }
                    }
                }
            });
        }
    }

    public void updateData(List<ProductResponseDTO> newList) {
        this.data.clear();
        this.data.addAll(newList);
        notifyDataSetChanged();
    }
}

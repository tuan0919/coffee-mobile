package com.nlu.packages.ui.order.OrderPopular;

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

//class nầy để tạo 1 recycle view (được gọi là adapter), được dùng để lấy dữ liệu lên trên màn hình,
//là phần code có thể mở rộng, nó là phần hỗ trợ giao diện cho mục Trend this month trên màn hình Home
public class TrendThisMonthRvAdapter extends RecyclerView.Adapter<TrendThisMonthRvAdapter.MyHolder> {
    Context context;
    ArrayList<ProductResponseDTO> data;
    private final TrendThisMonthRvInterface trendThisMonthRvInterface;
    private CoffeeApi coffeeApi;
    private List<Long> productIds = new ArrayList<>();
    private WishlistRequestDTO wishlistRequestDTO = new WishlistRequestDTO();

    public TrendThisMonthRvAdapter(Context context, ArrayList<ProductResponseDTO> data, TrendThisMonthRvInterface trendThisMonthRvInterface) {
        this.context = context;
        this.data = data != null ? data : new ArrayList<>();
        this.trendThisMonthRvInterface = trendThisMonthRvInterface;
    }

    //khỏi tạo view holder, để hiển thị giao diện lên fragment gọi nó
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trend_this_month_rv, null, false);
        return new MyHolder(view, trendThisMonthRvInterface);
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
    public void onBindViewHolder(@NonNull TrendThisMonthRvAdapter.MyHolder holder, int position) {
        holder.textView1.setText(data.get(position).getProductName());
        Picasso.get().load(data.get(position).getAvatar()).into(holder.imageView1);

        initFavorite();
        if(productIds.contains(data.get(position).getProductId())){
            holder.toggleButton.setChecked(true);
        }

        // Lấy danh sách sản phẩm yêu thích từ API nếu chưa có
        if (productIds == null) {
            initFavorite();
        }

        //xử lý sự kiện cho toggle button
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

        // Xử lý sự kiện khi item được click
        holder.itemView.setOnClickListener(view -> {
            if (trendThisMonthRvInterface != null) {
                trendThisMonthRvInterface.onItemClickTrendThisMonth(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    //khai báo textview vói image view để chứa hình ảnh với chữ
    public class MyHolder extends RecyclerView.ViewHolder {
        TextView textView1;
        ImageView imageView1;
        ToggleButton toggleButton;

        //set lại nôi dung của hình ảnh với chữ
        public MyHolder(@NonNull View itemView, TrendThisMonthRvInterface trendThisMonthRvInterface) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.trendThisMonthTitleRv);
            imageView1 = itemView.findViewById(R.id.trendThisMonthRvImage);
            toggleButton = itemView.findViewById(R.id.trendThisMonthFavorite);

            //xử lý sự kiện khi và 1 hình ảnh được nhấn vào sẽ chuyển qua trang chi tiết sản phẩm
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (trendThisMonthRvInterface != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            trendThisMonthRvInterface.onItemClickTrendThisMonth(position);
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

package com.nlu.packages.ui.home;

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
import java.util.function.Consumer;


//class nầy để tạo 1 recycle view, để lấy dữ liệu lên trên màn hình, là phần code có thể mở rộng, nó là phần hỗ
//trợ hiển thị giao diện cho phần Top Pick Coffee trên màn hình Home
class TopPickRvAdapter extends RecyclerView.Adapter<TopPickRvAdapter.MyHolder> {
    private final TopCoffeeRvInterface topCoffeeRvInterface;
    ArrayList<ProductResponseDTO> data;
    Context context;
    private Consumer<ProductResponseDTO> onClickHandler;
    private CoffeeApi coffeeApi;
    private List<Long> productIds = new ArrayList<>();
    private WishlistRequestDTO wishlistRequestDTO = new WishlistRequestDTO();

    public TopPickRvAdapter(Context context, ArrayList<ProductResponseDTO> data, TopCoffeeRvInterface topCoffeeRvInterface) {
        this.context = context;
        this.data = data != null ? data : new ArrayList<>();
        this.topCoffeeRvInterface = topCoffeeRvInterface;
    }

    public TopPickRvAdapter(Context context, ArrayList<ProductResponseDTO> data,
                            TopCoffeeRvInterface topCoffeeRvInterface,
                            Consumer<ProductResponseDTO> onClickHandler) {
        this.context = context;
        this.data = data != null ? data : new ArrayList<>();
        this.topCoffeeRvInterface = topCoffeeRvInterface;
        this.onClickHandler = onClickHandler;
    }

    //khỏi tạo view holder, để hiển thị giao diện lên fragment gọi nó
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_pick_rv, null, false);
        return new MyHolder(view, topCoffeeRvInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.textView2.setText(data.get(position).getProductName());
        Picasso.get().load(data.get(position).getAvatar()).into(holder.imageView2);
        holder.renderView(data.get(position));

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
                initFavorite();
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
                initFavorite();
                if(wishlistRequestDTO.getProductIds().contains(data.get(position).getProductId())){
                    wishlistRequestDTO.getProductIds().clear();
                    wishlistRequestDTO.getProductIds().add(data.get(position).getProductId());
                }
                Call<MessageResponseDTO> call = coffeeApi.removeFromWishList(data.get(position).getProductId());
                call.enqueue(new Callback<MessageResponseDTO>() {
                    @Override
                    public void onResponse(Call<MessageResponseDTO> call, Response<MessageResponseDTO> response) {
                        Toast.makeText(context, "Removed from Favorite", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<MessageResponseDTO> call, Throwable throwable) {
                        System.out.println(throwable);
                    }
                });
            }
        });
    }

    //khởi tạo init favorite để lấy dữ liệu từ api
    private void initFavorite() {
        coffeeApi = CoffeeService.getClient();
        Call<List<ProductResponseDTO>> call = coffeeApi.getWishList();
        call.enqueue(new Callback<List<ProductResponseDTO>>() {
            @Override
            public void onResponse(Call<List<ProductResponseDTO>> call, Response<List<ProductResponseDTO>> response) {
                List<ProductResponseDTO> responseDTOS = response.body();
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
            }

            @Override
            public void onFailure(Call<List<ProductResponseDTO>> call, Throwable throwable) {
                System.out.println(throwable);
            }
        });
        wishlistRequestDTO.setProductIds(productIds);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    //khai báo textview vói image view để chứa hình ảnh với chữ
    public class MyHolder extends RecyclerView.ViewHolder {
        TextView textView2;
        ImageView imageView2;
        ToggleButton toggleButton;

        //set lại nôi dung của hình ảnh với chữ
        public MyHolder(@NonNull View itemView, TopCoffeeRvInterface topCoffeeRvInterface) {
            super(itemView);
            textView2 = itemView.findViewById(R.id.topPickTitleRv);
            imageView2 = itemView.findViewById(R.id.topPickImageRv);
            toggleButton = itemView.findViewById(R.id.topPickFavorite);

            //xử lý sự kiện khi và 1 hình ảnh được nhấn vào sẽ chuyển qua trang chi tiết sản phẩm
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (topCoffeeRvInterface != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            topCoffeeRvInterface.onItemClickTopCoffee(position);
                        }
                    }
                }
            });
        }
        public void renderView(ProductResponseDTO productResponseDTO) {
            imageView2.setOnClickListener(v -> {
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

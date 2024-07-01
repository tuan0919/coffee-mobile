package com.nlu.packages.ui.home;

import android.content.Context;
import android.util.Log;
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
import com.nlu.packages.utils.MyUtils;
import com.squareup.picasso.Picasso;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

//class nầy để tạo 1 recycle view (được gọi là adapter), được dùng để lấy dữ liệu lên trên màn hình,
//là phần code có thể mở rộng, nó là phần hỗ trợ giao diện cho mục Coffee for you trên màn hình Home
class CoffeForYouRvAdapter extends RecyclerView.Adapter<CoffeForYouRvAdapter.MyHolder> {
    Context context;
    List<ProductResponseDTO> data;
    private CoffeeForYouRvInterface coffeeForYouRvInterface;
    private Consumer<ProductResponseDTO> onClickHandler;
    private CoffeeApi coffeeApi;
    private List<Long> productIds = new ArrayList<>();
    private WishlistRequestDTO wishlistRequestDTO = new WishlistRequestDTO();

    public CoffeForYouRvAdapter(Context context, ArrayList<ProductResponseDTO> data, CoffeeForYouRvInterface coffeeForYouRvInterface) {
        this.context = context;
        this.data = data != null ? data : new ArrayList<>();
        this.coffeeForYouRvInterface = coffeeForYouRvInterface;
    }


    public CoffeForYouRvAdapter(Context context, List<ProductResponseDTO> data,
                                CoffeeForYouRvInterface coffeeForYouRvInterface,
                                Consumer<ProductResponseDTO> onClickHandler) {
        this.context = context;
        this.data = data != null ? data : new ArrayList<>();
        this.coffeeForYouRvInterface = coffeeForYouRvInterface;
        this.onClickHandler = onClickHandler;
        initCoffeeApi();
    }

    //khởi tạo coffee api có kèm thêm token
    private void initCoffeeApi() {
        String token = MyUtils.get(context, "token");
        if (token == null || !token.contains(".")) {
            Log.e("CoffeForYouRvAdapter", "Token is invalid: " + token);
            return;
        }
        coffeeApi = CoffeeService.getRetrofitInstance(token);
    }

    //khởi tạo init favorite để lấy dữ liệu từ api
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

    //khỏi tạo view holder, để hiển thị giao diện lên fragment gọi nó
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coffee_for_you_rv, null, false);
        return new MyHolder(view, coffeeForYouRvInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.textView1.setText(data.get(position).getProductName());
        Picasso.get().load(data.get(position).getAvatar()).into(holder.imageView1);
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
                        System.out.println(throwable);
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    //khai báo textview vói image view để chứa hình ảnh với chữ
    class MyHolder extends RecyclerView.ViewHolder {
        TextView textView1;
        ImageView imageView1;
        ToggleButton toggleButton;

        //set lại nôi dung của hình ảnh với chữ
        public MyHolder(@NonNull View itemView, CoffeeForYouRvInterface coffeeForYouRvInterface) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.homeCoffeeTitleRv);
            imageView1 = itemView.findViewById(R.id.homeCoffeeImageRv);
            toggleButton = itemView.findViewById(R.id.coffeeForYouFavorite);

            //xử lý sự kiện khi và 1 hình ảnh được nhấn vào sẽ chuyển qua trang chi tiết sản phẩm
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (coffeeForYouRvInterface != null) {
                        int position = getAdapterPosition();
                        data.get(position).getProductId();
                        if (position != RecyclerView.NO_POSITION) {
                            coffeeForYouRvInterface.onItemClickCoffeeForYou(position);
                        }
                    }
                }
            });
        }

        public void renderView(ProductResponseDTO productResponseDTO) {
            imageView1.setOnClickListener(v -> {
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

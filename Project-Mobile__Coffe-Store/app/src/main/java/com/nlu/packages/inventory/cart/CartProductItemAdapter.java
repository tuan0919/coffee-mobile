package com.nlu.packages.inventory.cart;

import android.app.Activity;
import android.graphics.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.nlu.packages.R;
import com.nlu.packages.net_working.CoffeeAppService;
import com.nlu.packages.response_dto.cart.CartResponseDTO;
import com.nlu.packages.response_dto.product.ProductResponseDTO;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import lombok.var;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CartProductItemAdapter extends RecyclerView.Adapter<CartProductItemAdapter.MyViewHolder> {
    private Activity context;
    private CartResponseDTO cartResponseDTO = null;

    public CartProductItemAdapter(Activity context, CartResponseDTO cartResponseDTO) {
        this.context = context;
        this.cartResponseDTO = cartResponseDTO;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fragment_items_cart, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CartProductItemAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.renderView(cartResponseDTO, i);
    }

    @Override
    public int getItemCount() {
        return (int) cartResponseDTO.getCount();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView_productAvatar;
        private TextView textView_productName;
        private TextView textView_productPrice;
        private TextView textView_productQuantity;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            textView_productName = itemView.findViewById(R.id.cart_item_productName);
            imageView_productAvatar = itemView.findViewById(R.id.cart_item_productImage);
            textView_productPrice = itemView.findViewById(R.id.cart_item_productPrice);
            textView_productQuantity = itemView.findViewById(R.id.cart_item_productQuantity);
        }

        public void renderView(CartResponseDTO cartDTO, int position) {
            var item = cartDTO.getList().get(position);
            if (position == 0) {
                LinearLayout parent = (LinearLayout) imageView_productAvatar.getParent();
                ViewGroup.MarginLayoutParams margins = (ViewGroup.MarginLayoutParams) parent.getLayoutParams();
               margins.topMargin = 0;
                margins.bottomMargin = 0;
            }
            textView_productName.setText(item.getProduct().getName());
            Picasso.get().load(item.getProduct().getAvatar())
                    .resize(100, 100)
                    .transform(new RoundedCornersTransformation(10, 0))
                    .into(imageView_productAvatar);
            textView_productPrice.setText(item.getPrice()+"00đ");
            textView_productQuantity.setText(item.getQuantity()+"");
        }
    }

    public void redraw(CartResponseDTO responseDTO) {
        this.cartResponseDTO = responseDTO;
        notifyDataSetChanged();
    }
}

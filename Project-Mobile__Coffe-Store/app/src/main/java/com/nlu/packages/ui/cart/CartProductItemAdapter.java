package com.nlu.packages.ui.cart;

import android.app.Activity;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;
import com.nlu.packages.R;
import com.nlu.packages.response_dto.cart.CartResponseDTO;
import com.squareup.picasso.Picasso;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;

@RequiredArgsConstructor
public class CartProductItemAdapter extends RecyclerView.Adapter<CartProductItemAdapter.MyViewHolder> {
    private Activity context;
    private CartResponseDTO cartResponseDTO = null;
    private BiConsumer<CartResponseDTO.CartItemDTO, Integer> onDeleteHandler;
    private BiConsumer<View, CartResponseDTO.CartItemDTO> onChangeQuantityHandler;
    private BiConsumer<Integer, Boolean> onChooseItemHandler;
    private SparseBooleanArray checkBoxStates;

    public CartProductItemAdapter(Activity context,
                                  CartResponseDTO cartResponseDTO,
                                  BiConsumer<CartResponseDTO.CartItemDTO, Integer> onDeleteHandler,
                                  BiConsumer<View, CartResponseDTO.CartItemDTO> onChangeQuantityHandler,
                                  BiConsumer<Integer, Boolean> onChooseItemHandler) {
        this.context = context;
        this.cartResponseDTO = cartResponseDTO;
        this.onDeleteHandler = onDeleteHandler;
        this.onChangeQuantityHandler = onChangeQuantityHandler;
        this.onChooseItemHandler = onChooseItemHandler;
        this.checkBoxStates = new SparseBooleanArray();
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fragment_items_cart, viewGroup, false);
        var myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CartProductItemAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.renderView(cartResponseDTO, i);
    }

    @Override
    public int getItemCount() {
        return cartResponseDTO.getList().size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView_productAvatar;
        private TextView textView_productName;
        private TextView textView_productPrice;
        private TextView textView_productQuantity;
        private ImageButton imageButton_cartItemRemoveBtn;
        private AppCompatButton appCompatButton_cartItemQuantityPlusBtn;
        private AppCompatButton appCompatButton_cartItemQuantityMinusBtn;
        private CheckBox checkBox_cartItemChoose;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            textView_productName = itemView.findViewById(R.id.cart_item_productName);
            imageView_productAvatar = itemView.findViewById(R.id.cart_item_productImage);
            textView_productPrice = itemView.findViewById(R.id.cart_item_productPrice);
            textView_productQuantity = itemView.findViewById(R.id.cart_item_productQuantity);
            imageButton_cartItemRemoveBtn = itemView.findViewById(R.id.cart_item_removeBtn);
            appCompatButton_cartItemQuantityPlusBtn = itemView.findViewById(R.id.cart_item_quantityBtn_plus);
            appCompatButton_cartItemQuantityMinusBtn = itemView.findViewById(R.id.cart_item_quantityBtn_minus);
            checkBox_cartItemChoose = itemView.findViewById(R.id.cart_item_checkBox);
        }

        public void renderView(CartResponseDTO cartDTO, int position) {
            if (cartDTO.getList().isEmpty()) {
                return;
            }
            var item = cartDTO.getList().get(position);
            if (position == 0) {
                LinearLayout parent = (LinearLayout)(imageView_productAvatar.getParent().getParent());
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
            imageButton_cartItemRemoveBtn.setOnClickListener(view ->  {
                // Execute Event Handler
                onDeleteHandler.accept(item, position);
            });
            appCompatButton_cartItemQuantityPlusBtn.setOnClickListener(button -> {
                onChangeQuantityHandler.accept(button, item);
            });
            appCompatButton_cartItemQuantityMinusBtn.setOnClickListener(button -> {
                onChangeQuantityHandler.accept(button, item);
            });
            checkBox_cartItemChoose.setChecked(checkBoxStates.get(position, false));
            checkBox_cartItemChoose.setOnCheckedChangeListener((button, isChecked) -> {
                checkBoxStates.put(position, isChecked);
                onChooseItemHandler.accept(position, isChecked);
            });
        }
    }

    public void redraw(CartResponseDTO responseDTO) {
        this.cartResponseDTO = responseDTO;
        notifyDataSetChanged();
    }
}

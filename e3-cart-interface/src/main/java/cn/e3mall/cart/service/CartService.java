package cn.e3mall.cart.service;

import cn.e3mall.pojo.TbItem;
import cn.e3mall.utils.E3Result;

import java.util.List;

public interface CartService {
    E3Result cartAddItem(Long itemId,Long userId,Integer num);
    E3Result changeCartItemCount(Long itemId,Long userId,Integer num);
    List<TbItem> getCartList(Long userId);
    E3Result cartAddItemList(Long userId,List<TbItem> itemList);
    void deleteCartItem(Long itemId,Long userId);
    void clearCart(Long userId);
}

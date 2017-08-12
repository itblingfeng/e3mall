package cn.e3mall.cart.controller;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.service.ItemService;
import cn.e3mall.utils.CookieUtils;
import cn.e3mall.utils.E3Result;
import cn.e3mall.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private CartService cartService;
    @RequestMapping("/cart/add/{itemId}.html")
    public String cartAddItem(@PathVariable Long itemId, @RequestParam(defaultValue = "1") Integer num, HttpServletRequest request, HttpServletResponse response) {

        /*如果用户已经登录，则将商品保存在redis中*/
        TbUser user = (TbUser) request.getAttribute("user");
        if (user != null) {
            cartService.cartAddItem(itemId, user.getId(), num);
            return "cartSuccess";
        }
        /*如果没有登录*/
        /*如果cookie中存在该商品，则该商品的数量增加，否则添加该商品*/
        List<TbItem> cartList = getCartCookie(request);
        Boolean flag = false;
        for (TbItem item : cartList) {
            if (itemId.longValue() == item.getId()) {
                flag = true;
                item.setNum(item.getNum() + num);
                break;
            }
        }
        if (!flag) {
            TbItem item = itemService.getItemById(itemId);
            item.setNum(num);
            String image = item.getImage();
            if (StringUtils.isNotBlank(image)) {
                String[] img = image.split(",");
                item.setImage(img[0]);
            }
            cartList.add(item);

        }
        CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList), true);

        return "cartSuccess";
    }

    private List<TbItem> getCartCookie(HttpServletRequest request) {
        String json = CookieUtils.getCookieValue(request, "cart", true);
        if (StringUtils.isNotBlank(json))
            return JsonUtils.jsonToList(json, TbItem.class);
        return new ArrayList<>();
    }

    @RequestMapping("cart/cart.html")
    public String showCart(HttpServletRequest request, HttpServletResponse response) {
        /*判断是否登录，若登录，并将cookie中的列表合并，保存在redis 中，清除cookie*/
        /*获得cart cookie
         获得商品列表
         */
        List<TbItem> cartList = getCartCookie(request);
        TbUser user = (TbUser) request.getAttribute("user");
        if (user != null) {
            cartService.cartAddItemList(user.getId(), cartList);
            cartList = cartService.getCartList(user.getId());
            /*清除cookie*/
            CookieUtils.deleteCookie(request, response, "cart");
        }

        request.setAttribute("cartList", cartList);
        return "cart";
    }

    @RequestMapping("/cart/update/num/{itemId}/{num}.action")
    public @ResponseBody
    E3Result changeCartItemCount(@PathVariable Long itemId, @PathVariable Integer num, HttpServletRequest request, HttpServletResponse response) {
        TbUser user = (TbUser) request.getAttribute("user");
        if (user != null) {
            cartService.changeCartItemCount(itemId, user.getId(), num);

        }
        List<TbItem> cartList = getCartCookie(request);
        for (TbItem item : cartList) {
            if (item.getId().longValue() == itemId) {
                item.setNum(num);
                break;
            }
        }
        CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList), true);
        return E3Result.ok();

    }

    @RequestMapping("/cart/delete/{itemId}.html")
    public String deleteCartItem(@PathVariable Long itemId, HttpServletRequest request, HttpServletResponse response) {
        TbUser user = (TbUser) request.getAttribute("user");
        if(user!=null){
            cartService.deleteCartItem(itemId,user.getId());
            return "redirect:/cart/cart.html";
        }
        List<TbItem> cartList = getCartCookie(request);
        for (TbItem item : cartList) {
            if (item.getId().longValue() == itemId) {
                cartList.remove(item);
                break;
            }
        }
        CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList));
        return "redirect:/cart/cart.html";
    }
}

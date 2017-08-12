package cn.e3mall.order.controller;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.order.pojo.OrderInfo;
import cn.e3mall.order.service.OrderService;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;
    @RequestMapping("/order/order-cart.html")
    public String showOrder(HttpServletRequest request){
        /*从reuest域中获取user*/
        TbUser user = (TbUser) request.getAttribute("user");
         /*根据userId从redis中获取购物车列表*/
        List<TbItem> cartList = cartService.getCartList(user.getId());
        request.setAttribute("cartList",cartList);
        return "order-cart";
    }
    @RequestMapping(value="/order/create.html",method= RequestMethod.POST)
    public String createOrder(OrderInfo orderInfo,HttpServletRequest request){
        TbUser user = (TbUser) request.getAttribute("user");
        E3Result e3Result = orderService.createOrder(orderInfo, user);
        if(e3Result.getStatus()==200){
            /*清空购物车*/
            cartService.clearCart(user.getId());
        }
        request.setAttribute("payment",orderInfo.getPayment());
        request.setAttribute("orderId",e3Result.getData());
        return "success";
    }
}

package cn.e3mall.order.interceptor;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.TokenService;
import cn.e3mall.utils.CookieUtils;
import cn.e3mall.utils.E3Result;
import cn.e3mall.utils.JedisClientCluster;
import cn.e3mall.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class LoginInterceptor implements HandlerInterceptor{
    @Value("${LOGIN_REDIRECT}")
    private String LOGIN_REDIRECT;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private CartService cartService;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        /*获取cookie中token的值*/
        String token = CookieUtils.getCookieValue(httpServletRequest, "token");
        /*若获得的token为空，则用户没有登录*/
        if (StringUtils.isBlank(token)) {
            /*跳转登录页面*/
            httpServletResponse.sendRedirect(LOGIN_REDIRECT+"?redirect="+httpServletRequest.getRequestURL());
            return false;
        }
        E3Result userByToken = tokenService.getUserByToken(token);
        TbUser user = (TbUser) userByToken.getData();
        /*登录失效*/
        if(user == null){
            httpServletResponse.sendRedirect(LOGIN_REDIRECT+"?redirect="+httpServletRequest.getRequestURL());
            return false;
        }
        /*若为登录状态，则合并cookie和redis中的商品*/
        /*获取cookie中的*/
        String cookieValue = CookieUtils.getCookieValue(httpServletRequest, "Cart:" + user.getId());
        /*若cookie中不为空，则合并*/
        if(StringUtils.isNotBlank(cookieValue)){
            List<TbItem> itemList = JsonUtils.jsonToList(cookieValue, TbItem.class);
            cartService.cartAddItemList(user.getId(),itemList);
        }
        /*合并过后清除cookie*/
        CookieUtils.deleteCookie(httpServletRequest,httpServletResponse,"Cart:"+user.getId());
        /*将user放入request域*/
        httpServletRequest.setAttribute("user",user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}

package cn.e3mall.cart.interceptor;

import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.TokenService;
import cn.e3mall.utils.CookieUtils;
import cn.e3mall.utils.E3Result;
import cn.e3mall.utils.JedisClientCluster;
import cn.e3mall.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor{
    @Autowired
    private TokenService tokenService;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        /*
        * 获取用户的token
        * 从redis中进行查找
        * 若不为空，则将去到的用户信息保存在request域中
        * 放行
        * 若为空，则放行
        * */
        String token = CookieUtils.getCookieValue(httpServletRequest, "token");
        /*没有登录*/
        if(StringUtils.isBlank(token)){
            return true;
        }
        E3Result e3Result = tokenService.getUserByToken(token);
        if(e3Result.getStatus()!=200){
            return true;
        }
        TbUser user = (TbUser) e3Result.getData();
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

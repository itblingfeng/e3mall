package cn.e3mall.sso.controller;

import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.LoginService;
import cn.e3mall.utils.CookieUtils;
import cn.e3mall.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
    @Autowired
    private LoginService loginService;
    @RequestMapping(value="/page/login")
    public String showLogin(){
        return "login";
    }
    @RequestMapping("/user/login")
    public @ResponseBody
    E3Result login(String username, String password, HttpServletRequest request, HttpServletResponse response){
        E3Result e3Result = loginService.login(username, password);
        String token = e3Result.getData().toString();
        /*将token写入cookie*/
        CookieUtils.setCookie(request,response,"token",token);
        return e3Result;
    }

}

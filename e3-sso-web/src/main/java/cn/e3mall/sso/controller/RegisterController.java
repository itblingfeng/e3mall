package cn.e3mall.sso.controller;

import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.RegisterService;
import cn.e3mall.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RegisterController {
    @Autowired
    private RegisterService registerService;

    @RequestMapping("/register")
    public String showRegister() {
        return "register";
    }

    @RequestMapping("/user/check/{usernameOrPhone}/{param}")
    public @ResponseBody
    E3Result checkInfo(@PathVariable String usernameOrPhone, @PathVariable Integer param) {
        if (param == 1) {
            return registerService.checkUsernameIsExist(usernameOrPhone);
        }
        if (param == 2) {
            return registerService.checkPhoneIsExist(usernameOrPhone);
        }
        return E3Result.ok();
    }

    @RequestMapping("/user/register")
    public @ResponseBody E3Result register(TbUser user) {
     return   registerService.register(user);
    }
}

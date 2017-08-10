package cn.e3mall.sso.controller;

import cn.e3mall.sso.service.TokenService;
import cn.e3mall.utils.E3Result;
import cn.e3mall.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TokenController {
    @Autowired
    private TokenService tokenService;
    @RequestMapping(value="/user/token/{token}",produces = "application/json;charset=UTF-8")
    public @ResponseBody Object getUserByToken(@PathVariable String token,String callback){
       /* E3Result user = tokenService.getUserByToken(token);
        if(StringUtils.isNotBlank(callback)){
            return callback+"("+ JsonUtils.objectToJson(user) +");";
        }
        return JsonUtils.objectToJson(user);*/
       E3Result user = tokenService.getUserByToken(token);
       if(StringUtils.isNotBlank(callback)){
           MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(user);
           mappingJacksonValue.setJsonpFunction(callback);
           return mappingJacksonValue;
       }
       return E3Result.ok();
    }
}

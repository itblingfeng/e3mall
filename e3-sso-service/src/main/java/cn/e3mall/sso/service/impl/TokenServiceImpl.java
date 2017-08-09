package cn.e3mall.sso.service.impl;

import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.TokenService;
import cn.e3mall.utils.E3Result;
import cn.e3mall.utils.JedisClientCluster;
import cn.e3mall.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService{
     @Autowired
     private JedisClientCluster jedisClientCluster;
    @Override
    public E3Result getUserByToken(String token) {
        String json = jedisClientCluster.get("Session:" + token);
        if(StringUtils.isBlank(json)){
            return E3Result.build(201,"用户登录已过期");
        }
        jedisClientCluster.expire("Session:"+token,1800);
        return E3Result.ok(JsonUtils.jsonToPojo(json,TbUser.class));
    }
}

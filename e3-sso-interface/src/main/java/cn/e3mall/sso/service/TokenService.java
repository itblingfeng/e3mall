package cn.e3mall.sso.service;

import cn.e3mall.pojo.TbUser;
import cn.e3mall.utils.E3Result;

public interface TokenService {
    E3Result getUserByToken(String token);
}

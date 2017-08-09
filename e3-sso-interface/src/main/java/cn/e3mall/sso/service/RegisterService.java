package cn.e3mall.sso.service;

import cn.e3mall.pojo.TbUser;
import cn.e3mall.utils.E3Result;

public interface RegisterService {
    E3Result checkUsernameIsExist(String username);
    E3Result checkPhoneIsExist(String phone);
    E3Result register(TbUser user);
}

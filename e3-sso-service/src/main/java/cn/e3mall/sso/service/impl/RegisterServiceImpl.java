package cn.e3mall.sso.service.impl;

import cn.e3mall.dao.TbUserMapper;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.pojo.TbUserExample;
import cn.e3mall.sso.service.RegisterService;
import cn.e3mall.utils.E3Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private TbUserMapper userMapper;

    @Override
    public E3Result checkUsernameIsExist(String username) {
        TbUserExample example = new TbUserExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<TbUser> tbUserList = userMapper.selectByExample(example);
        if (tbUserList.size()==0||tbUserList==null) {
              return E3Result.ok(true);
        }
        return E3Result.ok(false);
    }

    @Override
    public E3Result checkPhoneIsExist(String phone) {
        TbUserExample example = new TbUserExample();
        example.createCriteria().andPhoneEqualTo(phone);
        List<TbUser> tbUserList = userMapper.selectByExample(example);
        if(tbUserList.size()==0||tbUserList==null){
            return E3Result.ok(true);
        }
        return E3Result.ok(false);
    }

    @Override
    public E3Result register(TbUser user) {
        if(StringUtils.isNotBlank(user.getUsername())||StringUtils.isNotBlank(user.getPhone())) {
            user.setCreated(new Date());
            user.setUpdated(new Date());
            user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
            userMapper.insert(user);
            return E3Result.ok();
        }
        return E3Result.ok(201);

    }
}

package cn.e3mall.sso.service.impl;

import cn.e3mall.dao.TbUserMapper;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.pojo.TbUserExample;
import cn.e3mall.sso.service.LoginService;
import cn.e3mall.utils.E3Result;
import cn.e3mall.utils.JedisClientCluster;
import cn.e3mall.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;

@Service
public class LoginServiceImpl implements LoginService{
    @Autowired
    private TbUserMapper tbUserMapper;
    @Autowired
    private JedisClientCluster jedisClientCluster;
    @Override
    public E3Result login(String username,String password) {
        /*根据用户名得到TbUser对象
        * 若对象为空则，返回xxx
        * 若对象不为空，则进行密码判断
        * */
        TbUserExample example = new TbUserExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<TbUser> users = tbUserMapper.selectByExample(example);
        if(users==null||users.size()==0){
            return E3Result.build(401,"用户名或密码错误");
        }
        if(!DigestUtils.md5DigestAsHex(password.getBytes()).equals(users.get(0).getPassword())){
            return E3Result.build(401,"用户名或密码错误");
        }
        /*创建一个uuid作为token,默认过期时间*/
        String token = UUID.randomUUID().toString();
        jedisClientCluster.set("Session:"+token, JsonUtils.objectToJson(users.get(0)));
        jedisClientCluster.expire("Session:"+token,1800);
        return E3Result.ok(token);
    }
}

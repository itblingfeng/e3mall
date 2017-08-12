package cn.e3mall.cart.service.impl;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.dao.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.utils.E3Result;
import cn.e3mall.utils.JedisClientCluster;
import cn.e3mall.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService{
    @Autowired
    private JedisClientCluster jedisClientCluster;
    @Autowired
    private TbItemMapper itemMapper;
    @Override
    public E3Result cartAddItem(Long itemId, Long userId,Integer num) {
        /*在redis中，若已存在该商品，则数量增加，若不存在，则创建该商品*/
        String item = jedisClientCluster.hget("Cart:" + userId, itemId + "");
        if(StringUtils.isNotBlank(item)){
            TbItem tbItem = JsonUtils.jsonToPojo(item, TbItem.class);
            tbItem.setNum(tbItem.getNum()+num);
            jedisClientCluster.hset("Cart:"+userId,itemId+"",JsonUtils.objectToJson(tbItem));
            return E3Result.ok();
        }
        /*若商品不存在，首先从数据库中查找该商品*/
        TbItem tbItem = itemMapper.selectByPrimaryKey(itemId);
        String images = tbItem.getImage();
        if(StringUtils.isNotBlank(images)){
            String[] image = images.split(",");
            tbItem.setImage(image[0]);
        }
        tbItem.setNum(num);
        jedisClientCluster.hset("Cart:"+userId,itemId+"",JsonUtils.objectToJson(tbItem));
        return E3Result.ok();
    }

    @Override
    public E3Result changeCartItemCount(Long itemId, Long userId, Integer num) {
        String item = jedisClientCluster.hget("Cart:" + userId, itemId + "");
        TbItem tbItem = JsonUtils.jsonToPojo(item,TbItem.class);
        tbItem.setNum(num);
        jedisClientCluster.hset("Cart:"+userId,itemId+"",JsonUtils.objectToJson(tbItem));
        return E3Result.ok();
    }

    @Override
    public List<TbItem> getCartList(Long userId) {
        List<String> string = jedisClientCluster.hval("Cart:" + userId);
        List<TbItem> itemList = new ArrayList<>();
        for(String s:string){
            TbItem item = JsonUtils.jsonToPojo(s, TbItem.class);
            itemList.add(item);
        }
        return itemList;
    }

    @Override
    public E3Result cartAddItemList(Long userId, List<TbItem> itemList) {
        /*先从redis中获取商品列表，再进行*/
        for(TbItem item:itemList){
            cartAddItem(item.getId(),userId,item.getNum());
        }
        return E3Result.ok();
    }

    @Override
    public void deleteCartItem(Long itemId, Long userId) {
        jedisClientCluster.hdel("Cart:"+userId,itemId+"");
    }

    @Override
    public void clearCart(Long userId) {
        jedisClientCluster.del("Cart:"+userId);
    }

}

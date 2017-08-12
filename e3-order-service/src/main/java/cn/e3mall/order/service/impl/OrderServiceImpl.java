package cn.e3mall.order.service.impl;

import cn.e3mall.dao.TbOrderItemMapper;
import cn.e3mall.dao.TbOrderMapper;
import cn.e3mall.dao.TbOrderShippingMapper;
import cn.e3mall.order.pojo.OrderInfo;
import cn.e3mall.order.service.OrderService;
import cn.e3mall.pojo.TbOrderItem;
import cn.e3mall.pojo.TbOrderShipping;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.utils.E3Result;
import cn.e3mall.utils.JedisClientCluster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private TbOrderMapper orderMapper;
    @Autowired
    private TbOrderShippingMapper orderShippingMapper;
    @Autowired
    private TbOrderItemMapper orderItemMapper;
    @Autowired
    private JedisClientCluster jedisClientCluster;
    @Value("${ORDER_ID_GEN}")
    private String ORDER_ID_GEN;
    @Value("${ORDER_ID_KEY}")
    private String ORDER_ID_KEY;
    @Value("${ORDER_DETAIL_GEN_KEY}")
    private String ORDER_DETAIL_GEN_KEY;
    @Override
    public E3Result createOrder(OrderInfo orderInfo, TbUser user) {
        /*填补订单表*/
        /*填补订单商品详情表*/
        /*填补收货信息表*/
        /*清空购物车*/
        /*1.通过redis生成订单号*/
        if(!jedisClientCluster.exists(ORDER_ID_KEY)){
            jedisClientCluster.set(ORDER_ID_KEY,ORDER_ID_GEN);
        }
        String orderId = jedisClientCluster.incr(ORDER_ID_KEY).toString();
        orderInfo.setOrderId(orderId);
        /*1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭*/
        orderInfo.setStatus(1);
        orderInfo.setCreateTime(new Date());
        orderInfo.setUpdateTime(new Date());
        orderInfo.setBuyerNick(user.getUsername());
        orderInfo.setUserId(user.getId());
        orderInfo.setBuyerRate(0);
        /*订单信息插入数据库*/
        orderMapper.insert(orderInfo);
        List<TbOrderItem> orderItems = orderInfo.getOrderItems();
        for(TbOrderItem item:orderItems){
            item.setId(jedisClientCluster.incr(ORDER_DETAIL_GEN_KEY).toString());
            item.setOrderId(orderId);
            orderItemMapper.insert(item);
        }
        TbOrderShipping orderShipping = orderInfo.getOrderShipping();
        orderShipping.setOrderId(orderId);
        orderShipping.setCreated(new Date());
        orderShipping.setUpdated(new Date());
        orderShippingMapper.insert(orderShipping);
        return E3Result.ok(orderId);

    }
}

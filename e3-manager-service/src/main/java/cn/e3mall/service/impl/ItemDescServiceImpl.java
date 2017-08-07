package cn.e3mall.service.impl;

import cn.e3mall.dao.TbItemDescMapper;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.service.ItemDescService;
import cn.e3mall.utils.ItemDescResult;
import cn.e3mall.utils.JedisClientCluster;
import cn.e3mall.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemDescServiceImpl implements ItemDescService {
    @Autowired
    private TbItemDescMapper itemDescMapper;
    @Autowired
    private JedisClientCluster jedisClientCluster;

    @Override
    public ItemDescResult selectDescById(Long id) {
        TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(id);
        ItemDescResult descResult = new ItemDescResult();
        descResult.setData(itemDesc);
        descResult.setStatus(200);
        return descResult;
    }

    @Override
    public TbItemDesc getItemDescById(Long id) {
        try {
            String json = jedisClientCluster.get("Item_Desc:" + id);
            if (StringUtils.isNotBlank(json)){
                TbItemDesc itemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
                jedisClientCluster.expire("Item_Desc"+id,3600);
                return itemDesc;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(id);
        try{
            jedisClientCluster.set("Item_Desc:"+id,JsonUtils.objectToJson(itemDesc));
            jedisClientCluster.expire("Item_Desc:"+id,3600);
        }catch(Exception e){
            e.printStackTrace();
        }
        return itemDesc;
    }
}

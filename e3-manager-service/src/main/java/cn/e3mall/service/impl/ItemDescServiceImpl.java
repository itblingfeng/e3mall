package cn.e3mall.service.impl;

import cn.e3mall.dao.TbItemDescMapper;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.service.ItemDescService;
import cn.e3mall.utils.ItemDescResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemDescServiceImpl implements ItemDescService {
    @Autowired
    private TbItemDescMapper itemDescMapper;

    @Override
    public ItemDescResult selectDescById(Long id) {
        TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(id);
        ItemDescResult descResult = new ItemDescResult();
        descResult.setData(itemDesc);
        descResult.setStatus(200);
        return descResult;
    }
}

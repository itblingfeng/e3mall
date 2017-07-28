package cn.e3mall.service.impl;

import cn.e3mall.dao.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.service.Tb_ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Tb_ItemServiceImpl implements Tb_ItemService {
     @Autowired
    private TbItemMapper itemMapper;
    public TbItem selectItemById(Long id) {
        return itemMapper.selectByPrimaryKey(id);
    }
}

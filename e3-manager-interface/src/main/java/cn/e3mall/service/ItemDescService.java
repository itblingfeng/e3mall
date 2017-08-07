package cn.e3mall.service;

import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.utils.ItemDescResult;

public interface ItemDescService {
    ItemDescResult selectDescById(Long id);
    TbItemDesc getItemDescById(Long id);
}

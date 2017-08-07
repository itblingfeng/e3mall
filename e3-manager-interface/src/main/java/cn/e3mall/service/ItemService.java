package cn.e3mall.service;

import cn.e3mall.pojo.TbItem;
import cn.e3mall.utils.E3Result;
import cn.e3mall.utils.EasyUiResult;
import cn.e3mall.utils.EasyUiTreeNode;

public interface ItemService {
     EasyUiResult listItem(Integer page,Integer rows);
     E3Result addItem(TbItem item,String desc);
     E3Result deleteItems(Long ids[]);
     E3Result instockItems(Long ids[]);
     E3Result reshelfItems(Long ids[]);
     TbItem getItemById(Long id);
}

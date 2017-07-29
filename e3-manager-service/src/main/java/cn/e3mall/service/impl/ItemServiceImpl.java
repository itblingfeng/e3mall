package cn.e3mall.service.impl;

import cn.e3mall.service.ItemService;
import cn.e3mall.utils.EasyUiResult;
import cn.e3mall.dao.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService{
    @Autowired
    private TbItemMapper itemMapper;
   public EasyUiResult listItem(Integer page, Integer rows){
        PageHelper.startPage(page,rows);
        TbItemExample example = new TbItemExample();
        List<TbItem> itemList = itemMapper.selectByExample(example);
        EasyUiResult easyUiResult = new EasyUiResult();
        PageInfo<TbItem> pageInfo = new PageInfo<>(itemList);
        easyUiResult.setRows(itemList);
        easyUiResult.setTotal(pageInfo.getTotal());
        return easyUiResult;
    }
}

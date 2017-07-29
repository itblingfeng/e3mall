package cn.e3mall.service.impl;

import cn.e3mall.dao.TbItemCatMapper;
import cn.e3mall.pojo.TbItemCat;
import cn.e3mall.pojo.TbItemCatExample;
import cn.e3mall.service.ItemCatService;
import cn.e3mall.utils.EasyUiTreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ItemCatServiceImpl implements ItemCatService{
    @Autowired
    private TbItemCatMapper itemCatMapper;
    public List<EasyUiTreeNode> selectItemCat(Long parent) {
        TbItemCatExample example = new TbItemCatExample();
        example.createCriteria().andParentIdEqualTo(parent);
        List<TbItemCat> itemCats = itemCatMapper.selectByExample(example);
        List<EasyUiTreeNode> treeNodes = new ArrayList<EasyUiTreeNode>();
        for(TbItemCat itemCat:itemCats){
            EasyUiTreeNode node = new EasyUiTreeNode();
            node.setId(itemCat.getId());
            node.setText(itemCat.getName());
            node.setState(itemCat.getIsParent()?"closed":"open");
            treeNodes.add(node);
        }
        return treeNodes;
    }
}

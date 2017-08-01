package cn.e3mall.content.service.impl;

import cn.e3mall.content.service.ContentCatService;
import cn.e3mall.dao.TbContentCategoryMapper;
import cn.e3mall.pojo.TbContentCategory;
import cn.e3mall.pojo.TbContentCategoryExample;
import cn.e3mall.utils.E3Result;
import cn.e3mall.utils.EasyUiTreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class ContentCatServiceImpl implements ContentCatService{
    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;
    @Override
    public List<EasyUiTreeNode> selectContentCat(Long parent) {
        TbContentCategoryExample example = new TbContentCategoryExample();
        example.createCriteria().andParentIdEqualTo(parent);
        List<TbContentCategory> categoryList = contentCategoryMapper.selectByExample(example);
        List<EasyUiTreeNode> nodeList = new ArrayList<>();
        for (TbContentCategory category:categoryList){
            EasyUiTreeNode node = new EasyUiTreeNode();
            node.setId(category.getId());
            node.setText(category.getName());
            node.setState(category.getIsParent()?"closed":"open");
            nodeList.add(node);
        }
        return nodeList;
    }

    @Override
    public E3Result addContentCat(Long parentId, String name) {
        TbContentCategory category = new TbContentCategory();
        /*设置是否为父亲节点*/
        category.setIsParent(false);
        category.setName(name);
        category.setParentId(parentId);
        /*排列序号都为1*/
        category.setSortOrder(1);
        /*1(正常),2(删除)',*/
        category.setStatus(1);
        category.setCreated(new Date());
        category.setUpdated(new Date());
        contentCategoryMapper.insert(category);
        E3Result e3Result = new E3Result();
        e3Result.setData(category);
        e3Result.setStatus(200);
        TbContentCategory parentCategory = contentCategoryMapper.selectByPrimaryKey(parentId);
        if(!parentCategory.getIsParent()){
            parentCategory.setIsParent(true);
            contentCategoryMapper.updateByPrimaryKey(parentCategory);
        }
        return e3Result;
    }

    @Override
    public void updateContentCat(Long id, String name) {
        TbContentCategory category = new TbContentCategory();
        /*修改更新时间*/
        category.setUpdated(new Date());
        /*修改名称*/
        category.setName(name);
        category.setId(id);
        /*仅修改更新时间和名称*/
        contentCategoryMapper.updateByPrimaryKeySelective(category);
    }

    @Override
    public void deleteContentCat(Long id) {
        TbContentCategory category = contentCategoryMapper.selectByPrimaryKey(id);
        if(category.getIsParent()){
            TbContentCategoryExample example = new TbContentCategoryExample();
            example.createCriteria().andParentIdEqualTo(id);
            contentCategoryMapper.deleteByExample(example);
        }
        contentCategoryMapper.deleteByPrimaryKey(id);
    }
}

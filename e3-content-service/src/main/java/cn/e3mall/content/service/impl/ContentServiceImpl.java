package cn.e3mall.content.service.impl;

import cn.e3mall.content.service.ContentService;
import cn.e3mall.dao.TbContentMapper;
import cn.e3mall.pojo.TbContent;
import cn.e3mall.pojo.TbContentExample;
import cn.e3mall.utils.E3Result;
import cn.e3mall.utils.EasyUiResult;
import cn.e3mall.utils.JedisClient;
import cn.e3mall.utils.JsonUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
@Service
public class ContentServiceImpl implements ContentService{
    @Autowired
    private TbContentMapper contentMapper;
    @Autowired
    private JedisClient jedisClient;
    @Override
    public EasyUiResult selectCatContent(Long categoryId,Integer page,Integer rows) {
        EasyUiResult easyUiResult = new EasyUiResult();
        PageHelper.startPage(page,rows);
        TbContentExample example = new TbContentExample();
        example.createCriteria().andCategoryIdEqualTo(categoryId);
        List<TbContent> tbContents = contentMapper.selectByExample(example);
        easyUiResult.setRows(tbContents);
        PageInfo<TbContent> pageInfo = new PageInfo<TbContent>(tbContents);
        easyUiResult.setTotal(pageInfo.getTotal());
        return easyUiResult;
    }

    @Override
    public E3Result saveContent(TbContent content) {
        content.setCreated(new Date());
        content.setUpdated(new Date());
        contentMapper.insert(content);
        try{
            jedisClient.hdel("ContentList",content.getCategoryId()+"");
        }catch (Exception e){
            e.printStackTrace();
        }
        return E3Result.ok();
    }

    @Override
    public E3Result deleteContents(Long[] ids) {
        TbContentExample example = new TbContentExample();
          /*将数组转换成集合*/
        List<Long> idList = Arrays.asList(ids);
        example.createCriteria().andIdIn(idList);
        TbContent tbContent = contentMapper.selectByPrimaryKey(ids[0]);
        Long categoryId = tbContent.getCategoryId();
        /*执行删除*/
        contentMapper.deleteByExample(example);

        try {
            jedisClient.hdel("ContentList",categoryId+"");
        }catch (Exception e){
            e.printStackTrace();
        }
        return E3Result.ok();
    }

    @Override
    public E3Result updateContent(TbContent content) {
        content.setUpdated(new Date());
        contentMapper.updateByPrimaryKeySelective(content);
        try{
            jedisClient.hdel("ContentList",content.getCategoryId()+"");
        }catch(Exception e){
            e.printStackTrace();
        }
        return E3Result.ok();
    }

    @Override
    public List<TbContent> getContentListByCatId(Long categoryId) {
        try{
            String contentList = jedisClient.hget("ContentList", categoryId + "");
            if (org.apache.commons.lang3.StringUtils.isNotBlank(contentList)){
               return JsonUtils.jsonToList(contentList,TbContent.class);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        TbContentExample example = new TbContentExample();
        example.createCriteria().andCategoryIdEqualTo(categoryId);
        List<TbContent> ad1List = contentMapper.selectByExampleWithBLOBs(example);
        try{
            jedisClient.hset("ContentList",categoryId+"",JsonUtils.objectToJson(ad1List));
        }catch (Exception e){
            e.printStackTrace();
        }
        return ad1List;
    }
}

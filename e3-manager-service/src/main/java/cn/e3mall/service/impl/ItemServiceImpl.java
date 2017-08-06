package cn.e3mall.service.impl;

import cn.e3mall.dao.TbItemDescMapper;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.service.ItemService;
import cn.e3mall.utils.E3Result;
import cn.e3mall.utils.EasyUiResult;
import cn.e3mall.dao.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemExample;
import cn.e3mall.utils.IDUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.*;
import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService{
    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemDescMapper itemDescMapper;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Resource
    private Destination topicDestination;
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

    @Override
    public E3Result addItem(TbItem item, String desc) {
//       填充参数
        final long itemId = IDUtils.genItemId();
        item.setId(itemId);
        item.setStatus((byte) 1);
        item.setCreated(new Date());
        item.setUpdated(new Date());
//        插入商品
        itemMapper.insert(item);
//        商品描述
        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemDesc(desc);
        itemDesc.setItemId(itemId);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());
        itemDescMapper.insert(itemDesc);
        jmsTemplate.send(topicDestination, new MessageCreator() {

            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage(itemId + "");
                return textMessage;
            }
        });
        return E3Result.ok();
    }

    @Override
    public E3Result deleteItems(Long [] ids) {
       itemMapper.deleteItems(ids);
        return E3Result.ok();
    }


    public E3Result instockItems(Long[] ids) {
        itemMapper.instockItems(ids);
        return E3Result.ok();
    }


    public E3Result reshelfItems(Long[] ids) {
       itemMapper.reshelfItems(ids);
        return E3Result.ok();
    }

}

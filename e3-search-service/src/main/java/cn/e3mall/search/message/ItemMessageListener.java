package cn.e3mall.search.message;

import cn.e3mall.mapper.ItemMapper;
import cn.e3mall.utils.SearchItem;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.IOException;

public class ItemMessageListener implements MessageListener {
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private SolrServer solrServer;
    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            /*获得发送的id*/
            Long id = new Long(textMessage.getText());
            /*休眠1秒保证事务提交后再查询数据库*/
            Thread.sleep(1000);
           /*通过id查询数据库得到SearchItem对象*/
            SearchItem item = itemMapper.getItemById(id);
            /*将item添加进索引*/
            SolrInputDocument document = new SolrInputDocument();
            document.addField("id", item.getId() + "");
            document.addField("item_title", item.getTitle());
            document.addField("item_price", item.getPrice());
            document.addField("item_image", item.getImage());
            document.addField("item_sell_point", item.getSell_point());
            document.addField("item_category_name", item.getCategory_name());
            solrServer.add(document);
            solrServer.commit();
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

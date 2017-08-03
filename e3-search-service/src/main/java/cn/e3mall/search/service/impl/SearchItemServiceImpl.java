package cn.e3mall.search.service.impl;

import cn.e3mall.mapper.ItemMapper;
import cn.e3mall.search.service.SearchItemService;
import cn.e3mall.utils.E3Result;
import cn.e3mall.utils.SearchItem;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

public class SearchItemServiceImpl implements SearchItemService{
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private SolrServer solrServer;
    @Override
    public E3Result importAllItems() {
        List<SearchItem> itemList = itemMapper.getItemList();
        for (SearchItem item:itemList){
            SolrInputDocument document = new SolrInputDocument();
            document.addField("id",item.getId()+"");
            document.addField("item_title",item.getTitle());
            document.addField("item_price",item.getPrice());
            document.addField("item_image",item.getImage());
            document.addField("item_sell_point",item.getSell_point());
            document.addField("item_category_name",item.getCategory_name());
            try {
                solrServer.add(document);
                solrServer.commit();
            } catch (SolrServerException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return E3Result.ok();
    }
}

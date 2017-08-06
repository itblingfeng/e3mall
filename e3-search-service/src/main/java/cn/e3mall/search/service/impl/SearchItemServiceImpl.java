package cn.e3mall.search.service.impl;

import cn.e3mall.mapper.ItemMapper;
import cn.e3mall.search.dao.SearchItemDao;
import cn.e3mall.search.service.SearchItemService;
import cn.e3mall.utils.E3Result;
import cn.e3mall.utils.SearchItem;
import cn.e3mall.utils.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class SearchItemServiceImpl implements SearchItemService{
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private SolrServer solrServer;
    @Autowired
    private SearchItemDao searchItemDao;
    @Override
    public E3Result importAllItems() {
       try{
        List<SearchItem> itemList = itemMapper.getItemList();
        for (SearchItem item:itemList) {
            SolrInputDocument document = new SolrInputDocument();
            document.addField("id", item.getId() + "");
            document.addField("item_title", item.getTitle());
            document.addField("item_price", item.getPrice());
            document.addField("item_image", item.getImage());
            document.addField("item_sell_point", item.getSell_point());
            document.addField("item_category_name", item.getCategory_name());
            solrServer.add(document);
            solrServer.commit();
        }
           return E3Result.ok();
        }catch(Exception e){
           return E3Result.build(500,"服务器异常添加索引失败");
        }
       }

    @Override
    public SearchResult searchItems(String keyword, Integer page, Integer rows) throws Exception {
        SolrQuery query = new SolrQuery();
        /*设置查询条件*/
        query.setQuery(keyword);
        /*设置默认搜索域*/
        query.add("df","item_title");
        query.setStart(page);
        query.setRows(rows);
        /*开启高亮*/
        query.setHighlight(true);
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<em style=\"color:red\">");
        query.setHighlightSimplePost("</em>");
        SearchResult searchResult = searchItemDao.searchItems(query);
        Long recourdCount = searchResult.getRecourdCount();
        int totalPage = (int) (recourdCount/rows);
        if(recourdCount%rows>0)
            totalPage++;
        searchResult.setTotalPages(totalPage);
        return searchResult;
    }
}

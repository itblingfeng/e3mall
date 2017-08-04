package cn.e3mall.search.dao;

import cn.e3mall.utils.SearchItem;
import cn.e3mall.utils.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public class SearchItemDao {
     @Autowired
    private SolrServer solrServer;
     public SearchResult searchItems(SolrQuery query) throws SolrServerException {
         QueryResponse queryResponse = solrServer.query(query);
         SolrDocumentList solrDocuments = queryResponse.getResults();
         /*创建SearchResult对象*/
         SearchResult searchResult = new SearchResult();
         /*获得总记录数*/
         long numFound = solrDocuments.getNumFound();
         /*创建结果集合*/
         List<SearchItem> itemList = new ArrayList<>();
         searchResult.setRecourdCount(numFound);
         for(SolrDocument document:solrDocuments){
             SearchItem item = new SearchItem();
             item.setId((String) document.get("id"));
             item.setCategory_name((String) document.get("item_title"));
             String item_image = (String) document.get("item_image");
             String[] split = item_image.split(",");
             item.setImage(split[0]);
             item.setPrice((Long) document.get("item_price"));
             item.setSell_point((String) document.get("item_sell_point"));
             item.setTitle((String) document.get("item_title"));
             itemList.add(item);
         }
         searchResult.setItemList(itemList);
          return searchResult;
     }
}

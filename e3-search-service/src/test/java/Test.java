import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;

public class Test {
    public static void main(String[] args){
        SolrServer solrServer = new HttpSolrServer("http://192.168.25.111:8082/solr/collection1");
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id","123");
        document.addField("item_title"," 测试");
        document.addField("item_price",20);
        try {
            solrServer.add(document);
            solrServer.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

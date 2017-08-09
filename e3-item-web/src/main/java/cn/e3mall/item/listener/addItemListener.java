package cn.e3mall.item.listener;

import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.service.ItemDescService;
import cn.e3mall.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class addItemListener implements MessageListener {
    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemDescService itemDescService;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            Thread.sleep(1000);
            String text = textMessage.getText();
            Long id = new Long(text);
            TbItem tbItem = itemService.getItemById(id);
            cn.e3mall.item.pojo.TbItem item = new cn.e3mall.item.pojo.TbItem(tbItem);
            TbItemDesc itemDesc = itemDescService.getItemDescById(id);
            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            try {
                Template template = configuration.getTemplate("item.ftl");
                Map data = new HashMap();
                data.put("item",item);
                data.put("itemDesc",itemDesc);
                Writer out = new FileWriter("D:\\freemarker\\item\\"+id+".html");
                template.process(data,out);
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TemplateException e) {
                e.printStackTrace();
            }
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

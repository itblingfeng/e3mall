package cn.e3mall.portal.controller;

import cn.e3mall.content.service.ContentService;
import cn.e3mall.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private ContentService contentService;
    @Value("${LUNBO_CATEGORYID}")
    private Long categoryId;
    @RequestMapping(value = "/index.html")
    public String index(Model model) {
        List<TbContent> ad1List = contentService.getContentListByCatId(89l);
        model.addAttribute("ad1List",ad1List);
        return "index";
    }
}

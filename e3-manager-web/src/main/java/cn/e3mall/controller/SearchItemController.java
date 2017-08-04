package cn.e3mall.controller;

import cn.e3mall.search.service.SearchItemService;
import cn.e3mall.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SearchItemController {
    @Autowired
    private SearchItemService searchItemService;
    @RequestMapping(value = "/index/item/import")
    public @ResponseBody
    E3Result importAllItems() {
        E3Result e3Result = searchItemService.importAllItems();
        return e3Result;
    }
}

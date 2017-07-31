package cn.e3mall.controller;

import cn.e3mall.service.ItemDescService;
import cn.e3mall.utils.E3Result;
import cn.e3mall.utils.ItemDescResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ItemDescContoller {
    @Autowired
    private ItemDescService itemDescService;
    @RequestMapping("/rest/item/query/item/desc/{id}")
    public @ResponseBody
    ItemDescResult showItemDesc(@PathVariable(value = "id") Long id){
        return itemDescService.selectDescById(id);
    }
}

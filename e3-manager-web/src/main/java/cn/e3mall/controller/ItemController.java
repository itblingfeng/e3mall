package cn.e3mall.controller;

import cn.e3mall.pojo.TbItem;
import cn.e3mall.service.Tb_ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ItemController {
    @Autowired
    private Tb_ItemService itemService;
    @RequestMapping(value="/item/{itemid}")
    public @ResponseBody TbItem select(@PathVariable Long itemid){
        return itemService.selectItemById(itemid);
    }
}

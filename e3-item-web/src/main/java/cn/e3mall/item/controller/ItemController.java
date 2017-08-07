package cn.e3mall.item.controller;

import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.service.ItemDescService;
import cn.e3mall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemDescService itemDescService;
    @RequestMapping(value = "/item/{itemId}.html")
    public String itemInfo(@PathVariable Long itemId, Model model){
        TbItem tbItem = itemService.getItemById(itemId);
        cn.e3mall.item.pojo.TbItem item = new cn.e3mall.item.pojo.TbItem(tbItem);
        TbItemDesc itemDesc = itemDescService.getItemDescById(itemId);
        model.addAttribute("item",item);
        model.addAttribute("itemDesc",itemDesc);
        return "item";
    }
}

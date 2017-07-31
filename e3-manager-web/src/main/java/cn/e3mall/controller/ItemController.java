package cn.e3mall.controller;

import cn.e3mall.pojo.TbItem;
import cn.e3mall.service.ItemService;
import cn.e3mall.utils.E3Result;
import cn.e3mall.utils.EasyUiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "/item/list")
    public @ResponseBody
    EasyUiResult listItem(Integer page, Integer rows) {
        return itemService.listItem(page, rows);
    }

    /*商品添加*/
    @RequestMapping(value = "/item/save", method = RequestMethod.POST)
    public @ResponseBody
    E3Result addItem(TbItem item, String desc) {
        return itemService.addItem(item, desc);
    }

    /*商品删除*/
    @RequestMapping(value = "/rest/item/delete")
    public @ResponseBody
    E3Result deleteItems(Long ids[]) {
        return itemService.deleteItems(ids);
    }
/*商品下架*/
    @RequestMapping(value="/rest/item/instock")
    public @ResponseBody E3Result instockItems(Long ids[]){
      return itemService.instockItems(ids);
    }
    /*商品上架*/
    @RequestMapping(value="/rest/item/reshelf")
    public @ResponseBody E3Result reshelfItems(Long ids[]){
        return itemService.reshelfItems(ids);
    }
}

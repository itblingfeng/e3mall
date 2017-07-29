package cn.e3mall.controller;

import cn.e3mall.service.ItemService;
import cn.e3mall.utils.EasyUiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
}

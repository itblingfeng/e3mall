package cn.e3mall.controller;

import cn.e3mall.service.ItemCatService;
import cn.e3mall.utils.EasyUiTreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@Controller
public class ItemCatController {
    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping(value="/item/cat/list")
    public @ResponseBody List<EasyUiTreeNode> selectTreeNode(@RequestParam(name = "id",defaultValue = "0") Long parent){
         return itemCatService.selectItemCat(parent);
    }
}

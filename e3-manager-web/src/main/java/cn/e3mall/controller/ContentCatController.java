package cn.e3mall.controller;

import cn.e3mall.content.service.ContentCatService;
import cn.e3mall.utils.E3Result;
import cn.e3mall.utils.EasyUiTreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ContentCatController {
    @Autowired
    private ContentCatService contentCatService;
    @RequestMapping(value="/content/category/list")
    public @ResponseBody
    List<EasyUiTreeNode> selectConttentCat(@RequestParam(value="id",defaultValue = "0")Long parent){
       return contentCatService.selectContentCat(parent);
    }
   @RequestMapping(value="/content/category/create")
    public @ResponseBody E3Result addContentCat(Long parentId,String name){
        return contentCatService.addContentCat(parentId,name);
   }
   @RequestMapping(value="/content/category/update")
    public void updateContentCat(Long id,String name){
        contentCatService.updateContentCat(id,name);
   }
   @RequestMapping(value="/content/category/delete")
    public String deleteContentCat(@RequestParam(value="id") Long id){
        contentCatService.deleteContentCat(id);
        return "content-category";
   }
}

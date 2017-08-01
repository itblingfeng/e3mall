package cn.e3mall.controller;

import cn.e3mall.content.service.ContentService;
import cn.e3mall.pojo.TbContent;
import cn.e3mall.utils.E3Result;
import cn.e3mall.utils.EasyUiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ContentController {
    @Autowired
    private ContentService contentService;
    @RequestMapping(value="/content/query/list")
    public @ResponseBody
    EasyUiResult selectContentList(Long categoryId,Integer page,Integer rows){
        return contentService.selectCatContent(categoryId,page,rows);
    }
    @RequestMapping(value="/content/save")
    public @ResponseBody
    E3Result saveContent(TbContent content){
      return contentService.saveContent(content);
    }
    @RequestMapping(value="/content/delete")
    public @ResponseBody E3Result deleteContents(Long ids[]){
        return contentService.deleteContents(ids);
    }
    @RequestMapping(value="/rest/content/edit")
    public @ResponseBody E3Result updateContent(TbContent content){
        return contentService.updateContent(content);
    }

}

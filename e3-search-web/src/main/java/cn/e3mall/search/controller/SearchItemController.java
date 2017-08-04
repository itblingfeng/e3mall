package cn.e3mall.search.controller;

import cn.e3mall.search.service.SearchItemService;
import cn.e3mall.utils.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class SearchItemController {
    @Autowired
    private SearchItemService searchItemService;
    @Value("${SEARCH_ITEM_ROWS}")
    private Integer rows;
    @RequestMapping(value="/search.html")
    public String searchItems(String keyword, @RequestParam(defaultValue = "1") Integer page, Model model) throws Exception {
        keyword = new String(keyword.getBytes("iso-8859-1"),"utf-8");
        SearchResult searchResult = searchItemService.searchItems(keyword, page, rows);
        model.addAttribute("query",keyword);
        model.addAttribute("totalPages",searchResult.getTotalPages());
        model.addAttribute("page",page);
        model.addAttribute("itemList",searchResult.getItemList());
        model.addAttribute("recourdCount",searchResult.getRecourdCount());
        return "search";
    }
}

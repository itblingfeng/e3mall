package cn.e3mall.search.service;

import cn.e3mall.utils.E3Result;
import cn.e3mall.utils.SearchResult;

public interface SearchItemService {
      E3Result importAllItems();
      SearchResult searchItems(String keyword,Integer page,Integer rows) throws Exception;
}

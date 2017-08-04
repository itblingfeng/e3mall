package cn.e3mall.utils;

import java.io.Serializable;
import java.util.List;

public class SearchResult implements Serializable{
    private Integer totalPages;
    private Long recourdCount;
    private List<SearchItem> itemList;

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Long getRecourdCount() {
        return recourdCount;
    }

    public void setRecourdCount(Long recourdCount) {
        this.recourdCount = recourdCount;
    }

    public List<SearchItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<SearchItem> itemList) {
        this.itemList = itemList;
    }
}

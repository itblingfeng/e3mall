package cn.e3mall.service;

import cn.e3mall.utils.EasyUiTreeNode;

import java.util.List;

public interface ItemCatService {
    List<EasyUiTreeNode> selectItemCat(Long parent);
}

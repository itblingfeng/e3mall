package cn.e3mall.content.service;

import cn.e3mall.utils.E3Result;
import cn.e3mall.utils.EasyUiTreeNode;

import java.util.List;

public interface ContentCatService {
    List<EasyUiTreeNode> selectContentCat(Long parent);
    E3Result addContentCat(Long parentId,String name);
    void updateContentCat(Long id,String name);
    void deleteContentCat(Long id);
}

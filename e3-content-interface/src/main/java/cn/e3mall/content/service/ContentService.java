package cn.e3mall.content.service;

import cn.e3mall.pojo.TbContent;
import cn.e3mall.utils.E3Result;
import cn.e3mall.utils.EasyUiResult;

import java.util.List;

public interface ContentService {
    EasyUiResult selectCatContent(Long categoryId,Integer page,Integer rows);
    E3Result saveContent(TbContent content);
    E3Result deleteContents(Long ids[]);
    E3Result updateContent(TbContent content);
    List<TbContent> getContentListByCatId(Long categoryId);
}

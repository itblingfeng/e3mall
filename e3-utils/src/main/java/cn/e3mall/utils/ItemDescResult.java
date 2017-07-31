package cn.e3mall.utils;

import cn.e3mall.pojo.TbItemDesc;

import java.io.Serializable;

public class ItemDescResult implements Serializable{
    private Integer status;
    private TbItemDesc data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public TbItemDesc getData() {
        return data;
    }

    public void setData(TbItemDesc data) {
        this.data = data;
    }
}

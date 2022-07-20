package com.zlingchun.mybatisplus.constant;

/**
 * @author achun
 * @create 2022/7/18
 * @description descrip
 */
public enum ResultConstant {

    FIND(200, "查询成功！"),
    UPDATE(201, "更新成功！"),
    SAVE(202, "新增成功！"),
    REMOVE(203, "删除成功！"),

    UPDATEFAILED(401, "更新失败！"),
    SAVEFAILED(402, "新增失败！"),
    REMOVEFAILED(403, "删除成功！");

    private Integer code;
    private String desc;

    ResultConstant(Integer code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}

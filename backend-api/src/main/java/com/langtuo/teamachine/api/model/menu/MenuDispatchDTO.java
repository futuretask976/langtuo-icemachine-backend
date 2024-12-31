package com.langtuo.teamachine.api.model.menu;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MenuDispatchDTO implements Serializable {
    /**
     * 菜单编码
     */
    private String menuCode;

    /**
     * 店铺编码
     */
    private List<String> shopGroupCodeList;
}

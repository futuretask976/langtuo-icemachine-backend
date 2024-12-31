package com.langtuo.teamachine.api.model.menu;

import lombok.Data;

import java.io.Serializable;

@Data
public class MenuSeriesRelDTO implements Serializable {
    /**
     * 菜单编码
     */
    private String menuCode;

    /**
     * 系列编码
     */
    private String seriesCode;
}

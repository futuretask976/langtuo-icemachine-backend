package com.langtuo.teamachine.api.model.menu;

import lombok.Data;

import java.io.Serializable;

@Data
public class SeriesTeaRelDTO implements Serializable {
    /**
     * 系列编码
     */
    private String seriesCode;

    /**
     * 茶饮编码
     */
    private String teaCode;
}

package com.langtuo.teamachine.api.model.device;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AndroidAppDispatchDTO implements Serializable {
    /**
     * 菜单编码
     */
    private String version;

    /**
     * 店铺编码
     */
    private List<String> shopGroupCodeList;
}

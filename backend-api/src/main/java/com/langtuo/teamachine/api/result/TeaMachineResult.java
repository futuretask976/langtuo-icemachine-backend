package com.langtuo.teamachine.api.result;

import com.langtuo.teamachine.api.model.ErrorMsgDTO;
import lombok.Data;

import java.util.List;

@Data
public class TeaMachineResult<T> {
    private boolean success;

    private String errorCode;

    private String errorMsg;

    private T model;

    public static <U> TeaMachineResult success(U model) {
        TeaMachineResult<U> teaMachineResult = new TeaMachineResult<>();
        teaMachineResult.setSuccess(true);
        teaMachineResult.setModel(model);
        return teaMachineResult;
    }

    public static <U> TeaMachineResult success() {
        TeaMachineResult<U> teaMachineResult = new TeaMachineResult<>();
        teaMachineResult.setSuccess(true);
        return teaMachineResult;
    }

    public static <U> TeaMachineResult error(String errorCode, String errorMsg) {
        TeaMachineResult<U> teaMachineResult = new TeaMachineResult<>();
        teaMachineResult.setSuccess(false);
        teaMachineResult.setErrorCode(errorCode);
        teaMachineResult.setErrorMsg(errorMsg);
        return teaMachineResult;
    }

    public static <U> TeaMachineResult error(ErrorMsgDTO errorMsgDTO) {
        TeaMachineResult<U> teaMachineResult = new TeaMachineResult<>();
        teaMachineResult.setSuccess(false);
        teaMachineResult.setErrorCode(errorMsgDTO.getErrorCode());
        teaMachineResult.setErrorMsg(errorMsgDTO.getErrorMsg());
        return teaMachineResult;
    }

    public static <T> T getModel(TeaMachineResult<T> result) {
        if (result == null || !result.isSuccess() || result.getModel() == null) {
            return null;
        }
        return result.getModel();
    }

    public static <T> List<T> getListModel(TeaMachineResult<List<T>> result) {
        if (result == null || !result.isSuccess() || result.getModel() == null) {
            return null;
        }
        List<T> list = result.getModel();
        if (list == null || list.size() == 0) {
            return null;
        }
        return list;
    }
}

package com.langtuo.teamachine.api.result;

import com.langtuo.teamachine.api.model.ErrorMsgDTO;
import lombok.Data;

import java.util.List;

@Data
public class IceMachineResult<T> {
    private boolean success;

    private String errorCode;

    private String errorMsg;

    private T model;

    public static <U> IceMachineResult success(U model) {
        IceMachineResult<U> iceMachineResult = new IceMachineResult<>();
        iceMachineResult.setSuccess(true);
        iceMachineResult.setModel(model);
        return iceMachineResult;
    }

    public static <U> IceMachineResult success() {
        IceMachineResult<U> iceMachineResult = new IceMachineResult<>();
        iceMachineResult.setSuccess(true);
        return iceMachineResult;
    }

    public static <U> IceMachineResult error(String errorCode, String errorMsg) {
        IceMachineResult<U> iceMachineResult = new IceMachineResult<>();
        iceMachineResult.setSuccess(false);
        iceMachineResult.setErrorCode(errorCode);
        iceMachineResult.setErrorMsg(errorMsg);
        return iceMachineResult;
    }

    public static <U> IceMachineResult error(ErrorMsgDTO errorMsgDTO) {
        IceMachineResult<U> iceMachineResult = new IceMachineResult<>();
        iceMachineResult.setSuccess(false);
        iceMachineResult.setErrorCode(errorMsgDTO.getErrorCode());
        iceMachineResult.setErrorMsg(errorMsgDTO.getErrorMsg());
        return iceMachineResult;
    }

    public static <T> T getModel(IceMachineResult<T> result) {
        if (result == null || !result.isSuccess() || result.getModel() == null) {
            return null;
        }
        return result.getModel();
    }

    public static <T> List<T> getListModel(IceMachineResult<List<T>> result) {
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

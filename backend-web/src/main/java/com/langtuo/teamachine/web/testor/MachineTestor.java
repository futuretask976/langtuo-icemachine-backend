package com.langtuo.teamachine.web.testor;

import com.alibaba.fastjson.JSONObject;
import com.langtuo.teamachine.api.request.device.MachineActivatePutRequest;

public class MachineTestor {
    public static void main(String args[]) {
        MachineTestor machineTestor = new MachineTestor();
        machineTestor.test();
    }

    public void test() {
        MachineActivatePutRequest request = new MachineActivatePutRequest();
        request.setDeployCode("Rb1x0DQCYieDRrcrApmv");
        request.setMachineCode("202408140011");
        request.setElecBoardCode("1234567890");
        request.setScreenCode("0987654321");
        System.out.println(JSONObject.toJSON(request));
    }
}

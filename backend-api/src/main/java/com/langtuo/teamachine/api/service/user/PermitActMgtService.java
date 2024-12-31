package com.langtuo.teamachine.api.service.user;

import com.langtuo.teamachine.api.model.user.PermitActDTO;
import com.langtuo.teamachine.api.model.user.PermitActGroupDTO;
import com.langtuo.teamachine.api.result.TeaMachineResult;

import java.util.List;

public interface PermitActMgtService {
    /**
     *
     * @return
     */
    TeaMachineResult<List<PermitActGroupDTO>> listPermitActGroup();

    /**
     *
     * @return
     */
    TeaMachineResult<List<PermitActDTO>> listPermitAct();
}

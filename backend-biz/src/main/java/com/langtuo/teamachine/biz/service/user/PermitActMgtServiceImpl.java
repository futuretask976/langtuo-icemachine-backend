package com.langtuo.teamachine.biz.service.user;

import com.langtuo.teamachine.api.model.user.PermitActDTO;
import com.langtuo.teamachine.api.model.user.PermitActGroupDTO;
import com.langtuo.teamachine.api.result.TeaMachineResult;
import com.langtuo.teamachine.api.service.user.PermitActMgtService;
import com.langtuo.teamachine.internal.util.LocaleUtils;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PermitActMgtServiceImpl implements PermitActMgtService {
    @Resource
    private PermitActAccessor permitActAccessor;

    @Override
    public TeaMachineResult<List<PermitActGroupDTO>> listPermitActGroup() {
        List<PermitActGroupDTO> list = Lists.newArrayList();
        for (PermitActGroupPO po : permitActAccessor.selectPermitActGroupList()) {
            PermitActGroupDTO permitActGroup = convert(po);
            List<PermitActDTO> permitActList = convert(permitActAccessor.selectPermitActList(
                    po.getPermitActGroupCode()));
            permitActGroup.setPermitActList(permitActList);
            list.add(permitActGroup);
        }
        return TeaMachineResult.success(list);
    }

    @Override
    public TeaMachineResult<List<PermitActDTO>> listPermitAct() {
        List<PermitActDTO> list = Lists.newArrayList();
        for (PermitActPO po : permitActAccessor.selectPermitActList()) {
            PermitActDTO permitAct = convert(po);
            list.add(permitAct);
        }
        return TeaMachineResult.success(list);
    }

    private PermitActGroupDTO convert(PermitActGroupPO permitActGroupPO) {
        if (permitActGroupPO == null) {
            return null;
        }

        PermitActGroupDTO dto = new PermitActGroupDTO();
        dto.setPermitActGroupCode(permitActGroupPO.getPermitActGroupCode());
        dto.setPermitActGroupName(LocaleUtils.getLang(permitActGroupPO.getPermitActGroupCode()));
        return dto;
    }

    private PermitActDTO convert(PermitActPO po) {
        if (po == null) {
            return null;
        }

        PermitActDTO dto = new PermitActDTO();
        dto.setPermitActCode(po.getPermitActCode());
        dto.setPermitActName(LocaleUtils.getLang(po.getPermitActCode()));
        dto.setPermitActGroupCode(po.getPermitActGroupCode());
        return dto;
    }

    private List<PermitActDTO> convert(List<PermitActPO> poList) {
        if (CollectionUtils.isEmpty(poList)) {
            return null;
        }

        List<PermitActDTO> list = poList.stream()
                .map(po -> convert(po))
                .collect(Collectors.toList());
        return list;
    }
}

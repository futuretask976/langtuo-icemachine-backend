package com.langtuo.teamachine.dao.accessor.user;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.langtuo.teamachine.dao.constant.PermitActEnum;
import com.langtuo.teamachine.dao.constant.PermitActGroupEnum;
import com.langtuo.teamachine.dao.po.user.PermitActGroupPO;
import com.langtuo.teamachine.dao.po.user.PermitActPO;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PermitActAccessor {
    /**
     * key: permitActGroupCode
     * value: List<permitActEnum>
     */
    private static Map<String, List<PermitActEnum>> permitActMapByGroup = Maps.newHashMap();

    /**
     * key: permitActGroupCode
     * value: permitActGroupEnum
     */
    private static Map<String, PermitActGroupEnum> permitActGroupMap = Maps.newHashMap();

    /**
     * key: permitActCode
     * value: permitActEnum
     */
    private static Map<String, PermitActEnum> permitActMap = new HashMap<>();

    // 初始化
    static {
        for (PermitActEnum permitActEnum : PermitActEnum.values()) {
            permitActMap.put(permitActEnum.getPermitActCode(), permitActEnum);

            String permitActGroupCode = permitActEnum.getPermitActGroupCode();
            PermitActGroupEnum permitActGroupEnum = PermitActGroupEnum.valueOfByCode(permitActGroupCode);

            if (!permitActMapByGroup.containsKey(permitActGroupCode)) {
                permitActMapByGroup.put(permitActGroupCode, new ArrayList<>());
                permitActGroupMap.put(permitActGroupCode, permitActGroupEnum);
            }
            permitActMapByGroup.get(permitActGroupCode).add(permitActEnum);
        }
    }

    public PermitActPO selectPermitAct(String permActCode) {
        PermitActEnum permitActEnum = permitActMap.get(permActCode);
        return convert(permitActEnum);
    }

    public PermitActGroupPO selectPermitActGroup(String permActGroupCode) {
        PermitActGroupEnum permitActGroupEnum = permitActGroupMap.get(permActGroupCode);
        return convert(permitActGroupEnum);
    }

    public List<PermitActGroupPO> selectPermitActGroupList() {
        List<PermitActGroupPO> permitActGroupList = Lists.newArrayList();
        for (Map.Entry<String, PermitActGroupEnum> map : permitActGroupMap.entrySet()) {
            permitActGroupList.add(convert(map.getValue()));
        }
        return permitActGroupList;
    }

    public List<PermitActPO> selectPermitActList(String permitActGroupCode) {
        List<PermitActPO> permitActList = Lists.newArrayList();
        List<PermitActEnum> permitActEnumList = permitActMapByGroup.get(permitActGroupCode);
        for (PermitActEnum permitActEnum : permitActEnumList) {
            permitActList.add(convert(permitActEnum));
        }
        return permitActList;
    }

    public List<PermitActPO> selectPermitActList() {
        List<PermitActPO> permitActList = Lists.newArrayList();
        for (Map.Entry<String, List<PermitActEnum>> entry : permitActMapByGroup.entrySet()) {
            List<PermitActEnum> permitActEnumList = entry.getValue();
            for (PermitActEnum permitActEnum : permitActEnumList) {
                permitActList.add(convert(permitActEnum));
            }
        }
        return permitActList;
    }

    private PermitActPO convert(PermitActEnum permitActEnum) {
        if (permitActEnum == null) {
            return null;
        }
        PermitActPO po = new PermitActPO();
        po.setPermitActCode(permitActEnum.getPermitActCode());
        po.setPermitActName(permitActEnum.getPermitActName());
        po.setPermitActGroupCode(permitActEnum.getPermitActGroupCode());
        return po;
    }

    private PermitActGroupPO convert(PermitActGroupEnum permitGroupActEnum) {
        if (permitGroupActEnum == null) {
            return null;
        }
        PermitActGroupPO po = new PermitActGroupPO();
        po.setPermitActGroupCode(permitGroupActEnum.getPermitActGroupCode());
        po.setPermitActGroupName(permitGroupActEnum.getPermitActGroupName());
        return po;
    }
}

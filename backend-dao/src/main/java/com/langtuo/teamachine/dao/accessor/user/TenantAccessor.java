package com.langtuo.teamachine.dao.accessor.user;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.langtuo.teamachine.dao.mapper.user.TenantMapper;
import com.langtuo.teamachine.dao.po.user.TenantPO;
import com.langtuo.teamachine.dao.query.user.TenantQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class TenantAccessor {
    @Resource
    private TenantMapper mapper;

    public TenantPO selectOneByTenantCode(String tenantCode) {
        return mapper.selectOne(tenantCode);
    }

    public List<TenantPO> list() {
        List<TenantPO> list = mapper.selectList();

        return list;
    }

    public PageInfo<TenantPO> search(String tenantName, String contactPerson, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        TenantQuery tenantQuery = new TenantQuery();
        tenantQuery.setTenantName(StringUtils.isBlank(tenantName) ? null : tenantName);
        tenantQuery.setContactPerson(StringUtils.isBlank(contactPerson) ? null : contactPerson);
        List<TenantPO> list = mapper.search(tenantQuery);

        PageInfo<TenantPO> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    public int insert(TenantPO tenantPO) {
        return mapper.insert(tenantPO);
    }

    public int update(TenantPO tenantPO) {
        return mapper.update(tenantPO);
    }

    public int deleteByTenantCode(String tenantCode) {
        return mapper.delete(tenantCode);
    }
}

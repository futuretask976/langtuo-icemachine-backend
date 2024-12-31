package com.langtuo.teamachine.dao.accessor.menu;

import com.langtuo.teamachine.dao.mapper.menu.MenuDispatchCacheMapper;
import com.langtuo.teamachine.dao.po.menu.MenuDispatchCachePO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用于记录已经分发的菜单信息，避免在茶品、菜单没有变动的时候，点击分发后重复创建文件上传到 OSS
 * 这部分信息其实也可以放到 Redis 种，但是由于 Redis 是自行搭建，稳定性存疑，因此先放在数据库中
 */
@Component
public class MenuDispatchCacheAccessor {
    @Resource
    private MenuDispatchCacheMapper mapper;

    public MenuDispatchCachePO getByFileName(String tenantCode, int init, String fileName) {
        MenuDispatchCachePO menuDispatchCachePO = mapper.selectOne(tenantCode, init, fileName);
        return menuDispatchCachePO;
    }

    public int insert(MenuDispatchCachePO po) {
        int inserted = mapper.insert(po);
        return inserted;
    }

    public int deleteByFileNameList(String tenantCode, int init, List<String> fileNameList) {
        int deleted = mapper.deleteByFileNameList(tenantCode, init, fileNameList);
        return deleted;
    }

    public int clear(String tenantCode) {
        int deleted = mapper.clear(tenantCode);
        return deleted;
    }
}

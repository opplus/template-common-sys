package com.vigekoo.service;

import com.vigekoo.common.utils.PageUtils;
import com.vigekoo.entity.SysLog;

import java.util.List;
import java.util.Map;

public interface SysLogService {

    SysLog queryObject(Long id);

    PageUtils queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void save(SysLog sysLog);

    void delete(Long id);

    void deleteBatch(Long[] ids);
}

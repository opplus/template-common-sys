package com.vigekoo.service;

import com.vigekoo.common.utils.PageUtils;
import com.vigekoo.entity.SysAttachment;

import java.util.List;
import java.util.Map;

/**
 * @author oplus
 * @Description: TODO()
 * @date 2017-7-10 17:01
 */
public interface SysAttachmentService {

    /**
     * 获取List列表
     */
    PageUtils queryList(Map<String, Object> map);

    /**
     * 获取总记录数
     */
    int queryTotal(Map<String, Object> map);

    SysAttachment queryObject(Long id);

    void deleteBatch(Long[] ids);

    void save(SysAttachment sysAttachment);

}

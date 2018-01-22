package com.vigekoo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vigekoo.common.utils.PageUtils;
import com.vigekoo.entity.SysAttachment;
import com.vigekoo.service.SysAttachmentService;
import com.vigekoo.dao.SysAttachmentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author oplus
 * @Description: TODO()
 * @date 2017-7-10 17:02
 */
@Service("sysAttachmentService")
public class SysAttachmentServiceImpl implements SysAttachmentService {

    @Autowired
    private SysAttachmentDao sysAttachmentDao;

    @Override
    public PageUtils queryList(Map<String, Object> map) {
        PageHelper.startPage((int)map.get("page"), (int)map.get("limit"));
        List<SysAttachment> list = this.sysAttachmentDao.queryList(map);
        PageInfo<SysAttachment> page = new PageInfo(list);
        PageUtils pageUtil = new PageUtils(list, page.getTotal(), (int)map.get("page"), (int)map.get("limit"));
        return pageUtil;
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return sysAttachmentDao.queryTotal(map);
    }

    @Override
    public SysAttachment queryObject(Long id) {
        return sysAttachmentDao.queryObject(id);
    }

    @Override
    @Transactional
    public void deleteBatch(Long[] ids) {
        sysAttachmentDao.deleteBatch(ids);
    }

    @Override
    @Transactional
    public void save(SysAttachment sysAttachment) {
        sysAttachmentDao.save(sysAttachment);
    }

}

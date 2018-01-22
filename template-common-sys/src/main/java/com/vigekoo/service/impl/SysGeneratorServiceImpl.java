package com.vigekoo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vigekoo.common.datasource.DataSourceNames;
import com.vigekoo.common.datasource.annotation.DataSource;
import com.vigekoo.common.utils.GeneratorUtils;
import com.vigekoo.common.utils.PageUtils;
import com.vigekoo.dao.SysGeneratorDao;
import com.vigekoo.service.SysGeneratorService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * @author oplus
 * @Description: TODO()
 * @date 2017-6-23 15:07
 */

@Service("sysGeneratorService")
public class SysGeneratorServiceImpl implements SysGeneratorService {

    @Autowired
    private SysGeneratorDao sysGeneratorDao;


    @Override
    public PageUtils queryList(Map<String, Object> map) {
        PageHelper.startPage((int) map.get("page"), (int) map.get("limit"));
        List<Map<String, Object>> list = this.sysGeneratorDao.queryList(map);
        PageInfo<List<Map<String, Object>>> page = new PageInfo(list);
        PageUtils pageUtil = new PageUtils(list, page.getTotal(), (int) map.get("page"), (int) map.get("limit"));
        return pageUtil;
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return sysGeneratorDao.queryTotal(map);
    }

    @Override
    public Map<String, String> queryTable(String tableName) {
        return sysGeneratorDao.queryTable(tableName);
    }

    @Override
    public List<Map<String, String>> queryColumns(String tableName) {
        return sysGeneratorDao.queryColumns(tableName);
    }

    @Override
    public byte[] generatorCode(String[] tableNames) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);

        for (String tableName : tableNames) {
            //查询表信息
            Map<String, String> table = queryTable(tableName);
            //查询列信息
            List<Map<String, String>> columns = queryColumns(tableName);
            //生成代码
            GeneratorUtils.generatorCode(table, columns, zip);
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }
}

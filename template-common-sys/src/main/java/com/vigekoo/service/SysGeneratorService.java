package com.vigekoo.service;

import com.vigekoo.common.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * @author oplus
 * @Description: TODO()
 * @date 2017-6-23 15:07
 */
public interface SysGeneratorService {

    PageUtils queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    Map<String, String> queryTable(String tableName);

    List<Map<String, String>> queryColumns(String tableName);

    byte[] generatorCode(String[] tableNames);

}

package com.vigekoo;

import com.vigekoo.service.SysGeneratorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Autowired
    private SysGeneratorService sysGeneratorService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void generatorCode() {
        String[] tableNames = {"carousel"};

        sysGeneratorService.generatorCode(tableNames);
    }


}

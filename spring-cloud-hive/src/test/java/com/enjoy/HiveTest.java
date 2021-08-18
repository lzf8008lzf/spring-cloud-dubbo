package com.enjoy;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HiveApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class HiveTest {
    @Autowired
    private JdbcTemplate hiveJdbcTemplate;

    @Test
    public void hiveTest(){
        String sql="INSERT into table test10(year_num, month_num, sales)\n" +
                "values\n" +
                "(1,1,1)";

        hiveJdbcTemplate.update(sql);

        sql="select * from test10";
        List<Map<String, Object>> maps = hiveJdbcTemplate.queryForList(sql);
        System.out.println(JSONUtil.toJsonStr(maps));
    }
}

package com.git.hui.rabbit.spring;

import com.git.hui.rabbit.spring.util.YamlUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by yihui in 11:11 18/6/19.
 */
public class YamlUtilTest {

    @Test
    public void testYamlUtil() throws IOException {
        TC map = YamlUtil.loadConf("test.yml", TC.class);
        System.out.println(map);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TC {
        private String comment;
        private String skip;
    }
}

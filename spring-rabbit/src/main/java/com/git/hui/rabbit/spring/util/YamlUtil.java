package com.git.hui.rabbit.spring.util;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by yihui in 17:38 18/6/5.
 */
public class YamlUtil {

    public static InputStream loadStream(String path) throws IOException {
        if (path.startsWith("http")) {
            URL url = new URL(path);
            return url.openStream();
        } else if (path.startsWith("/")) {
            return new FileInputStream(path);
        } else {
            return YamlUtil.class.getClassLoader().getResourceAsStream(path);
        }
    }

    public <T> T loadConf(String path, Class<T> clz) throws IOException {
        try (InputStream inputStream = loadStream(path)) {
            Yaml yaml = new Yaml();
            return yaml.loadAs(inputStream, clz);
        }
    }

}

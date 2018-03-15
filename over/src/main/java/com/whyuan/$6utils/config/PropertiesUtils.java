package com.whyuan.$6utils.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 传入配置文件路径，将内容转为Properties
 */
public class PropertiesUtils {
    public static final Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);

    public static Properties fromFile(final String configFilePath) throws IOException {
        Properties props = new Properties();
        FileInputStream in = null;
        try {
            in = new FileInputStream(configFilePath);
            props.load(new InputStreamReader(in,"UTF-8"));
            return props;
        } catch (FileNotFoundException ex) {
            logger.error("Config file not found", ex);
        } catch (IOException ex) {
            logger.error("Error while reading config file", ex);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                logger.warn("An error ocurred while closing the config file", ex);
            }
        }
        return null;
    }
}

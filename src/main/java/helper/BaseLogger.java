package helper;

import api.Application;
import data.cache.CacheRetriever;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Properties;

public class BaseLogger {
    private Logger LOG;
    public BaseLogger(Class clazz){
        LOG = LogManager.getLogger(clazz.getName());
    }

    public void addEvent(Properties properties) {
        LOG.info(properties);
    }
}

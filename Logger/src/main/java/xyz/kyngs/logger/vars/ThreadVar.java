package xyz.kyngs.logger.vars;

import xyz.kyngs.logger.LogVar;
import xyz.kyngs.logger.util.PropertyMap;

public class ThreadVar implements LogVar {
    @Override
    public String provide(PropertyMap propertyMap) {
        return propertyMap.<Thread>get("thread").getName();
    }
}

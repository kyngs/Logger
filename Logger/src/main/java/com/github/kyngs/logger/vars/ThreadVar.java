package com.github.kyngs.logger.vars;

import com.github.kyngs.logger.LogVar;
import com.github.kyngs.logger.util.PropertyMap;

public class ThreadVar implements LogVar {
    @Override
    public String provide(PropertyMap propertyMap) {
        return propertyMap.<Thread>get("thread").getName();
    }
}

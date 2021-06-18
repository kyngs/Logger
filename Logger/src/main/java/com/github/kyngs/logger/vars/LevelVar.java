package com.github.kyngs.logger.vars;

import com.github.kyngs.logger.LogLevel;
import com.github.kyngs.logger.LogVar;
import com.github.kyngs.logger.util.PropertyMap;

public class LevelVar implements LogVar {
    @Override
    public String provide(PropertyMap propertyMap) {
        return propertyMap.<LogLevel>get("level").name();
    }
}

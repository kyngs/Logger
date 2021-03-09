package xyz.kyngs.logger.vars;

import xyz.kyngs.logger.LogLevel;
import xyz.kyngs.logger.LogVar;
import xyz.kyngs.logger.util.PropertyMap;

public class LevelVar implements LogVar {
    @Override
    public String provide(PropertyMap propertyMap) {
        return propertyMap.<LogLevel>get("level").name();
    }
}

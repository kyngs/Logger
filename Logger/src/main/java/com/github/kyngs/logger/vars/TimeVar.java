package com.github.kyngs.logger.vars;

import com.github.kyngs.logger.LogVar;
import com.github.kyngs.logger.util.PropertyMap;

import java.time.format.DateTimeFormatter;

public class TimeVar implements LogVar {

    private final DateTimeFormatter formatter;

    public TimeVar(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public String provide(PropertyMap propertyMap) {
        return formatter.format(propertyMap.get("time"));
    }
}

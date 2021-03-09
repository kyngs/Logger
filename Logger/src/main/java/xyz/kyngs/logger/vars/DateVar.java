package xyz.kyngs.logger.vars;

import xyz.kyngs.logger.LogVar;
import xyz.kyngs.logger.util.PropertyMap;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateVar implements LogVar {

    private final DateTimeFormatter formatter;

    public DateVar(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public String provide(PropertyMap propertyMap) {
        return formatter.format(LocalDate.now());
    }
}

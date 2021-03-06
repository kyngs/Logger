package com.github.kyngs.logger.vars;

import com.github.kyngs.logger.LogVar;
import com.github.kyngs.logger.util.PropertyMap;
import com.github.kyngs.logger.util.StackTraceInfo;

public class StackTraceVar implements LogVar {

    private final StackTraceInfo info;

    public StackTraceVar(StackTraceInfo info) {
        this.info = info;
    }

    @Override
    public String provide(PropertyMap propertyMap) {
        StackTraceElement element = propertyMap.<Throwable>get("stack_gen").getStackTrace()[propertyMap.<Integer>get("stack_offset")];
        switch (info) {
            case METHOD: {
                return element.getMethodName();
            }
            case CLASS: {
                String[] split = element.getClassName().split("\\.");
                ;
                return split[split.length - 1];
            }
            case LINE: {
                return String.valueOf(element.getLineNumber());
            }
            default: {
                return "";
            }
        }
    }
}

package com.saveslave.commons.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.logging.Log;

@Slf4j
public class DefaultLogOutImpl implements Log {


    public DefaultLogOutImpl(String clazz) {

    }

    @Override
    public boolean isDebugEnabled() {
        return log.isInfoEnabled();
    }

    @Override
    public boolean isTraceEnabled() {
        return log.isTraceEnabled();
    }

    @Override
    public void error(String s, Throwable throwable) {
        log.error(s,throwable);
    }

    @Override
    public void error(String s) {
        log.error(s);
    }

    @Override
    public void debug(String s) {
        log.info(s);
    }

    @Override
    public void trace(String s) {
        log.trace(s);
    }

    @Override
    public void warn(String s) {
        log.warn(s);
    }
}

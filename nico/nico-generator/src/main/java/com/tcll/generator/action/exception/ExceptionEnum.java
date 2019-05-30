package com.tcll.generator.action.exception;

/**
 * 抽象接口
 */
public interface ExceptionEnum {

    /**
     * 获取异常编码
     */
    Integer getCode();

    /**
     * 获取异常信息
     */
    String getMessage();
}

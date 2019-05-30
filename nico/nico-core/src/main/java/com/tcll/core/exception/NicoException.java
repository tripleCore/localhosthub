package com.tcll.core.exception;

/**
 * 封装Nico的异常
 *
 */
public class NicoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private Integer code;

    private String message;

    public NicoException(ServiceExceptionEnum serviceExceptionEnum) {
        this.code = serviceExceptionEnum.getCode();
        this.message = serviceExceptionEnum.getMessage();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

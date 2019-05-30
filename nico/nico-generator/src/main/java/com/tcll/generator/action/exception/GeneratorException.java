package com.tcll.generator.action.exception;

/**
 * 封装Generator的异常
 */
public class GeneratorException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer code;

    private String message;

    public GeneratorException(ExceptionEnum serviceExceptionEnum) {
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

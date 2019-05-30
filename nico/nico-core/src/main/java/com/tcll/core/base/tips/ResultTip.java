package com.tcll.core.base.tips;

/**
 * 结果集封装
 * 
 */
public class ResultTip<T> extends Tip{
	private T data;
	private ResultTip(T data) {
		this.code = 200;
		this.message = "成功";
		this.data = data;
	}
	private ResultTip(CodeMsg cm) {
		if(cm == null){
			return;
		}
		this.code = cm.getCode();
		this.message = cm.getMessage();
	}
	
	private ResultTip(int code,String message) {
		this.code = code;
		this.message = message;
	}
	/**
	 * 成功传参
	 * @return
	 */
	public static <T> ResultTip<T> success(T data){
		return new ResultTip<T>(data);
	}
	/**
	 * 成功，不传参
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> ResultTip<T> success(){
		return (ResultTip<T>) success("");
	}
	
	public static <T> ResultTip<T> error() {
		return new ResultTip<T>(CodeMsg.ERROR);
	}
	/**
	 * 失败
	 * @return
	 */
	public static <T> ResultTip<T> error(CodeMsg cm){
		return new ResultTip<T>(cm);
	}
	
	/**
	 * 失败
	 * @return
	 */
	public static <T> ResultTip<T> error(int code,String cm){
		return new ResultTip<T>(code,cm);
	}
	/**
	 * 失败,扩展消息参数
	 * @param cm
	 * @param msg
	 * @return
	 */
	public static <T> ResultTip<T> error(CodeMsg cm,String msg){
		cm.setMessage(cm.getMessage()+"--"+msg);
		return new ResultTip<T>(cm);
	}
	public T getData() {
		return data;
	}
	public String getMessage() {
		return message;
	}
	public int getCode() {
		return code;
	}
}
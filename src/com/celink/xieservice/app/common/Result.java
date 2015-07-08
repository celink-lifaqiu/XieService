package com.celink.xieservice.app.common;

import net.sf.json.JSONObject;

public class Result {

	private int code = 0; // 操作成功
	private Object result = new Object();
	private String err = "";
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	public String getErr() {
		return err;
	}
	public void setErr(String err) {
		this.err = err;
	}
	
	public String toJson(){
		return JSONObject.fromObject(this).toString();
	}
	
	public static void main(String[] args) {
		System.out.println(new Result().toJson());
	}
	
}

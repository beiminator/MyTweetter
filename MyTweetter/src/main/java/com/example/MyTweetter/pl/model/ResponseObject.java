package com.example.MyTweetter.pl.model;

import java.util.List;

public class ResponseObject {
	private List<String> errors;
	private boolean isSuccess;
	private Object result;
	
	public static ResponseObject ok () {
		return ok(null);
	}
	public static ResponseObject ok (Object result) {
		ResponseObject ro = new ResponseObject();
		
		ro.setSuccess(true);
		ro.setResult(result);
		
		return ro;
	}
	public static ResponseObject ko (List<String> errors) {
		ResponseObject ro = new ResponseObject();
		
		ro.setSuccess(false);
		ro.setErrors(errors);
		
		return ro;
	}
	
	public List<String> getErrors() {
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
}

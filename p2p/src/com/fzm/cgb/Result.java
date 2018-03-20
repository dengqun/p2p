package com.fzm.cgb;

import java.util.ArrayList;

public class Result {
private String jsonrpc;
private String id;
private ArrayList<Object> result;
private String error;

public String getJsonrpc() {
	return jsonrpc;
}
public void setJsonrpc(String jsonrpc) {
	this.jsonrpc = jsonrpc;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public ArrayList<Object> getResult() {
	return result;
}
public void setResult(ArrayList<Object> result) {
	this.result = result;
}
public String getError() {
	return error;
}
public void setError(String error) {
	this.error = error;
}


}

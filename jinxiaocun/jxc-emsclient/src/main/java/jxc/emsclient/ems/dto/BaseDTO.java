package jxc.emsclient.ems.dto;

import java.io.Serializable;

public class BaseDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}

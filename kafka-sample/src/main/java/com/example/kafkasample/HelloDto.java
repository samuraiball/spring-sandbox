package com.example.kafkasample;

import java.io.Serializable;

public class HelloDto implements Serializable {


	public HelloDto(String hello) {
		this.hello = hello;
	}

	private String hello;

	public void setHello(String hello) {
		this.hello = hello;
	}

	public String getHello() {
		return hello;
	}
}

package com.huigou.common.rabbitmq;

public class ConsumeMessage {
	public void listen(String foo) {
		System.out.println("invoke");
		System.out.println(foo);
	}
}

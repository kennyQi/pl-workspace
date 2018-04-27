package com.huigou.common.rabbitmq;

import hg.common.util.DateUtil;

import java.util.Date;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SendMessage {
	
	public static void test1() throws Exception{

		String path = "applicationContext-amqp.xml";
		AbstractApplicationContext ctx = new ClassPathXmlApplicationContext(path);
		RabbitTemplate template = ctx.getBean(RabbitTemplate.class);
		template.convertAndSend("Hello, world!");
		template.convertAndSend("hg", "xxxxxxxxx");
		Thread.sleep(1000);
		ctx.close();
		ctx.destroy();
	}
	

	public static void testProducer() throws InterruptedException {
		String path = "applicationContext-amqp-producer.xml";
		AbstractApplicationContext ctx = new ClassPathXmlApplicationContext(path);
		RabbitTemplate template = ctx.getBean(RabbitTemplate.class);
		template.convertAndSend("hg", "TIME:"+DateUtil.formatDateTime1(new Date()));
		Thread.sleep(1000);
		ctx.close();
		ctx.destroy();
	}
	
	@SuppressWarnings({ "resource" })
	public static void testConsumer() throws InterruptedException {
		String path = "applicationContext-amqp-consumer.xml";
		new ClassPathXmlApplicationContext(path);
	}

	public static void main(String[] agrs) throws Exception {
		testProducer();
		testConsumer();
	}
}

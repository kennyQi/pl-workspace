package com.huigou.common.rabbitmq;

import hg.common.rabbitmq.ConnInit;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class MQtest {

	public final static String EXCHANGE_NAME = "test007";

	public static class Producer {

		public static void main(String[] args) throws IOException {
			ConnInit connInit = new ConnInit();
			Channel channel = connInit.initConnection();
			channel.exchangeDeclare(EXCHANGE_NAME, "fanout");// 声明Exchange
			// channel.exchangeDeclare(EXCHANGE_NAME, "topic");// 声明Exchange
			for (int i = 0; i <= 2; i++) {
				String message = "hello word!" + i;
				channel.basicPublish(EXCHANGE_NAME, "", null,
						message.getBytes()); // fanout的情况下，队列为默认，
				System.out.println(" [x] Sent '" + message + "'");
			}
			connInit.closeConnection();
		}

	}

	public static class Consumer1 {
		public static void main(String[] args) throws IOException,
				ShutdownSignalException, ConsumerCancelledException,
				InterruptedException {

			ConnInit connInit = new ConnInit();
			Channel channel = connInit.initConnection();
			channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
			String queueName = "log-fb1"; // 队列1名称
			channel.queueDeclare(queueName, false, false, false, null);
			channel.queueBind(queueName, EXCHANGE_NAME, "");// 把Queue、Exchange绑定
			QueueingConsumer consumer = new QueueingConsumer(channel);
			channel.basicConsume(queueName, true, consumer);
			while (true) {
				QueueingConsumer.Delivery delivery = consumer.nextDelivery();
				String message = new String(delivery.getBody());
				System.out.println(" [x] Received '" + message + "'");
			}
			// connInit.closeConnection();
		}
	}

	public static class Consumer2 {
		public static void main(String[] args) throws IOException,
				ShutdownSignalException, ConsumerCancelledException,
				InterruptedException {

			ConnInit connInit = new ConnInit();
			Channel channel = connInit.initConnection();
			channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
			String queueName = "log-fb2"; // 队列2名称
			channel.queueDeclare(queueName, false, false, false, null);
			channel.queueBind(queueName, EXCHANGE_NAME, "");// 把Queue、Exchange绑定
			QueueingConsumer consumer = new QueueingConsumer(channel);
			channel.basicConsume(queueName, true, consumer);
			while (true) {
				QueueingConsumer.Delivery delivery = consumer.nextDelivery();
				String message = new String(delivery.getBody());
				System.out.println(" [x] Received '" + message + "'");
			}

		}
	}

}

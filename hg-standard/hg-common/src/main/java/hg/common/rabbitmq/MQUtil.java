package hg.common.rabbitmq;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.client.QueueingConsumer;

public class MQUtil {
	
	/**
	 * 交换机类型
	 */
	public static String DIRECT_TYPE = "direct";
	public static String TOPIC_TYPE = "topic";
	public static String FANOUT_TYPE = "fanout";
	
	/**
	 * direct方式 生产者
	 * 声明队列和交换机，并用routingkey绑定；发送消息；
	 */
	public void direct_producer(String message,String queueName,String exchangeName,String routingkey){
		
		ConnInit conninit = null;
		try{
			
			conninit = new ConnInit();
			Channel channel = conninit.initConnection();
	
			 //生产者声明queue和exchange，并绑定；以防止生产者先启动，exchange没有绑定queue，造成消息丢失
			 //此处声明的queue和exchange 都是持久的
			channel.queueDeclare(queueName, true, false, false, null);
			channel.exchangeDeclare(exchangeName,"direct", true, false, null);
			channel.queueBind(queueName, exchangeName, routingkey);
			
			System.out.println("[direct send]:" + message);
			//MessageProperties.PERSISTENT_TEXT_PLAIN 表示消息需要持久化
			channel.basicPublish(exchangeName,routingkey,MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if(conninit != null){
					conninit.closeConnection();					
				}
			}catch (IOException e1) {
				e1.printStackTrace();
			}

		}
		
	}
	
	
	
	/**
	 * topic方式 生产者
	 * 声明队列和交换机，并用bindingkey绑定；发送消息；
	 */
	public void topic_producer(String message,String queueName,String exchangeName,String routingkey,String bindingkey){
		
		ConnInit conninit = null;
		try {
			
			conninit = new ConnInit();
			Channel channel = conninit.initConnection();
			
			//生产者声明queue和exchange，并绑定；以防止生产者先启动，exchange没有绑定queue，造成消息丢失
			//此处声明的queue和exchange 都是持久的
			channel.queueDeclare(queueName, true, false, false, null);
			channel.exchangeDeclare(exchangeName,"topic", true, false, null);
			channel.queueBind(queueName, exchangeName, bindingkey);
			
			System.out.println("[topic send]:" + message);
			//MessageProperties.PERSISTENT_TEXT_PLAIN 表示消息需要持久化
			channel.basicPublish(exchangeName, routingkey, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(conninit != null){
					conninit.closeConnection();					
				}
			}catch (IOException e1) {
				e1.printStackTrace();
			}

		}
		
	}
	
	
	
	/**
	 * fanout方式  生产者
	 * 声明队列和交换机，并绑定；发送消息；
	 */
	public void fanout_producer(String message,String queueName,String exchangeName){
		
		ConnInit conninit = null; 
		try{
			
			conninit = new ConnInit();
			Channel channel = conninit.initConnection();
			
			//生产者声明queue和exchange，并绑定；以防止生产者先启动，exchange没有绑定queue，造成消息丢失
			//此处声明的queue和exchange 都是持久的
			channel.queueDeclare(queueName, true, false, false, null);
			channel.exchangeDeclare(exchangeName,"fanout", true, false, null);
			channel.queueBind(queueName, exchangeName, "");
			
			System.out.println("[fanout send]:" + message);
			//MessageProperties.PERSISTENT_TEXT_PLAIN 表示消息需要持久化
			channel.basicPublish(exchangeName, "", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if(conninit != null){
					conninit.closeConnection();					
				}
			}catch (IOException e1) {
				e1.printStackTrace();
			}

		}
	}
	
	
	/**
	 * 消费者
	 * 接收指定队列的消息
	 * @param queueName
	 */
    public void consumer(String queueName){
		
		ConnInit conninit = null;
		
		try{
			
			conninit = new ConnInit();
			Channel channel = conninit.initConnection();
			
			//消费者需声明queue,以防止消费者先启动，找不到的queue；尝试建立已存在的消息队列，rabbitmq会返回建立成功
			channel.queueDeclare(queueName, true, false, false, null);
			
			QueueingConsumer consumer = new QueueingConsumer(channel);
			//第二个参数autoAck如果设置为true,consumer在收到消息之后会马上自动返回ack,rabbitmq会删除这条消息
			//如果设置为false,需要手动ack，如果忘记手动返回ack,rabbimtq不会释放消息，会侵占越来越多的内存
			channel.basicConsume(queueName, false, consumer);
			
			//告诉rabbitmq，同一时刻，不要发送超过1条消息给一个consumer ，直到它已经处理了上一条消息并做出了响应；
			channel.basicQos(1);
			
			while(true){
				
				//如果没有消息，这一步会一直阻塞
				QueueingConsumer.Delivery delivery = consumer.nextDelivery();
				String message = new String(delivery.getBody());
				System.out.println("[receive]:" + message);
				

				//....任务处理
				
				//channel.basicConsume方法的autoAck参数设置为false,任务处理完后，需要手动返回ack给rabbitmq，确认消息已经收到
				channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
				
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				if(conninit != null){
					conninit.closeConnection();					
				}
			}catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}
    
    
    /**
     * 添加指定类型的交换机;添加队列;并绑定交换机和队列;
     * 
     */
    public void add_bind(String queueName,String exchangeName,String bindingkey,String type){
    	
    	ConnInit conninit = null;
		try{
			
			conninit = new ConnInit();
			Channel channel = conninit.initConnection();
	
			//此处声明的queue和exchange 都是持久的
			channel.queueDeclare(queueName, true, false, false, null);
			channel.exchangeDeclare(exchangeName,type, true, false, null);
			channel.queueBind(queueName, exchangeName, bindingkey);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if(conninit != null){
					conninit.closeConnection();					
				}
			}catch (IOException e1) {
				e1.printStackTrace();
			}

		}
    }
    
    /**
     * 解绑交换机和队列
     * @param queueName
     * @param exchangeName
     * @param routingKey
     */
    public void un_bind(String queueName,String exchangeName,String bindingkey){
    	
    	ConnInit conninit = null;
		try{
			
			conninit = new ConnInit();
			Channel channel = conninit.initConnection();
	
			channel.queueUnbind(queueName, exchangeName, bindingkey);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if(conninit != null){
					conninit.closeConnection();					
				}
			}catch (IOException e1) {
				e1.printStackTrace();
			}

		}
    	
    }
    
    
    /**
     * 发送消息
     */
    public void product_message(String message,String exchangeName,String routingkey){
    	ConnInit conninit = null;
		try{
			
			conninit = new ConnInit();
			Channel channel = conninit.initConnection();
			
			System.out.println("[send]:" + message);
			//MessageProperties.PERSISTENT_TEXT_PLAIN 表示消息需要持久化
			channel.basicPublish(exchangeName,routingkey,MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if(conninit != null){
					conninit.closeConnection();					
				}
			}catch (IOException e1) {
				e1.printStackTrace();
			}

		}
    }
    
    /**
     * 删除交换机
     * @param exchangeName
     */
    public void deleteExchange(String exchangeName){
    	
    	ConnInit conninit = null;
    	try{
    		
    		conninit = new ConnInit();
    		Channel channel = conninit.initConnection();
    		channel.exchangeDelete(exchangeName);
    		
    	}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if(conninit != null){
					conninit.closeConnection();					
				}
			}catch (IOException e1) {
				e1.printStackTrace();
			}

		}
    }
   
    /**
     * 删除队列
     * @param queueName
     */
    public void deleteQueue(String queueName){
    	
    	ConnInit conninit = null;
    	try{
    		
    		conninit = new ConnInit();
    		Channel channel = conninit.initConnection();
    		channel.queueDelete(queueName);
    		
    	}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if(conninit != null){
					conninit.closeConnection();					
				}
			}catch (IOException e1) {
				e1.printStackTrace();
			}

		}
    }
    

	
	public static void main(String args[]){
		MQUtil util = new MQUtil();
		util.add_bind("topic_queue", "topic_exchange", "topic.*", MQUtil.TOPIC_TYPE);
		util.product_message("topic test", "topic_exchange", "topic.word");
		System.exit(1);
	}
	


}

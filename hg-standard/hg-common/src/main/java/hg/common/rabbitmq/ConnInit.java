package hg.common.rabbitmq;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConnInit {

	RabbitmqProperties prop = RabbitmqProperties.getInstance();
	
	private Connection conn = null;
	private Channel channel = null;
	
	public  Channel initConnection() throws IOException{
		
		String host = prop.get("host");
		Integer port = Integer.parseInt(prop.get("port"));
		String username = prop.get("username");
		String password = prop.get("password");
				
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(host);
		factory.setPort(port);
		factory.setUsername(username);
		factory.setPassword(password);
		
		conn = factory.newConnection();
		channel = conn.createChannel();
		
		return channel;
		
	}
	
	public void closeConnection() throws IOException{
		
		if(channel != null){
			channel.close();
		}
		if(conn != null){
			conn.close();
		}
		
	}
}

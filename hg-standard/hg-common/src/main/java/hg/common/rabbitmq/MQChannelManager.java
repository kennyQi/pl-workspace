package hg.common.rabbitmq;

import java.io.IOException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

//@Component("mqChannelManager")
public class MQChannelManager {

//	@Autowired
	private ConnectionFactory factory;
	
	public Channel getChannel() throws IOException {

		// String host = prop.get("host");
		// Integer port = Integer.parseInt(prop.get("port"));
		// String username = prop.get("username");
		// String password = prop.get("password");

//		ConnectionFactory factory = new ConnectionFactory();
//		factory.setHost(host);
//		factory.setPort(port);
//		factory.setUsername(username);
//		factory.setPassword(password);

		return factory.newConnection().createChannel();

	}

	public void closeChannel(Channel channel) throws IOException {

		if (channel.getConnection() != null) {
			channel.getConnection().close();
		}

		if (channel != null) {
			channel.close();
		}

	}

}

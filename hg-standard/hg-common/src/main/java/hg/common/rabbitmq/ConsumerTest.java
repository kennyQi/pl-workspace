package hg.common.rabbitmq;

public class ConsumerTest {

	public static void main(String args[]){
		MQUtil util = new MQUtil();
		util.consumer("topic_queue");
	}
}

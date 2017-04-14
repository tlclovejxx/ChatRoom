package biz.tlc.biz.ChatRoom;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.MessageProperties;

public class User {
	private String userName;
	private String hostIp;
	private String queueName;
	private String exchangeName;
	
	private ConnectionFactory factory;
	private Connection connection;
	private Channel channel;
	
	private MessageListener listener;
	public void setListener(MessageListener listener){
        this.listener = listener;
    }
	
	public User(String userName,String hostIp) {
		this.userName = userName;
		this.hostIp = hostIp;
		queueName = UUID.randomUUID().toString();
	}
	
	public void addRoom(String roomName) throws IOException, TimeoutException{
		exchangeName = roomName;
		factory = new ConnectionFactory();
		factory.setHost(hostIp);
		connection = factory.newConnection();
		channel = connection.createChannel();
		channel.exchangeDeclare(roomName, "fanout",false);
		channel.queueDeclare(queueName, false, false, false, null);
		channel.queueBind(queueName, exchangeName, "biz.*");
	}
	
	public void sendMessage(String message) throws IOException{
		message = userName+" said : "+message;
		channel.basicPublish(exchangeName, "",
				MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("utf-8"));
	}
	
	public void listenMessage() throws IOException{
		Consumer consumer = new DefaultConsumer(channel) {
		     @Override
		     public void handleDelivery(String consumerTag, Envelope envelope,
		                                 AMQP.BasicProperties properties, byte[] body) throws IOException {
		    	 String message = new String(body, "UTF-8");
		    	 listener.getMessage(message); 
		     }
		};
		channel.basicConsume(queueName, true, consumer);
	}
	public void userClose() throws IOException, TimeoutException{
		channel.queueDelete(queueName);
		channel.close();    
        connection.close();
	}
}

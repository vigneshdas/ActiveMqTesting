package activemq.objectmessage;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class ObjectSender {
	
	private ConnectionFactory factory = null;
	private Connection connection = null;
	private Session session = null;
	private Destination destination = null;
	private MessageProducer producer = null;
	
	
	public void sendMessage(){
		try{
			
			//Config Fatory to default Active MQ broker URL
			//We can change it to JNDI lookup if we have external queue or topic configuration
			factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
			connection = factory.createConnection();
			connection.start();
			
			session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue("sampleObjectQueue");
			producer = session.createProducer(destination);
			
			/*TextMessage message = session.createTextMessage();
			message.setText("Hello ...This is a sample message..sending from FirstClient");
			producer.send(message);
			System.out.println("Sent: " + message.getText());*/
			
			ObjectMessage objMessage = session.createObjectMessage();
			objMessage.setObject(new Employee("Vignesh", "100"));
			
			producer.send(objMessage);
			
			System.out.println("Sent: " + objMessage);
			
		}catch(JMSException e){
			e.printStackTrace();
			
		}
		
	}
	
	public static void main(String[] args) {
		ObjectSender objSender = new ObjectSender();
		objSender.sendMessage();
	}

}

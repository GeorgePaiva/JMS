package br.com.caelum.jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;

public class TesteProdutorTopico {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {

		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");

		Connection connection = factory.createConnection();
		connection.start();
		// abstrai o trabalho transacional e confirmação do recebimento da mensagem.
		// o primeiro parâmetro(false) define se queremos usar o tratamento da transação
		// como explícito.
		// segundo parametro serve para confirmar automaticamente o recebimento da
		// messagem JMS.
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		// organizar o recebimento e a entrega das mensagens
		Destination topico = (Destination) context.lookup("loja");
		MessageProducer producer = session.createProducer(topico);
		
		
	
		Message message = session.createTextMessage("<pedido><id>222</id></pedido>");
		producer.send(message);
		

		session.close();
		connection.close();
		context.close();

	}

}

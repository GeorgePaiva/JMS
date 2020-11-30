package br.com.caelum.jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;

public class TesteProdutor {

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
		Destination fila = (Destination) context.lookup("financeiro");
		
		
		MessageProducer producer = session.createProducer(fila);
		
		
		for (int i = 0; i < 1000; i++) {
			Message message = session.createTextMessage("<pedido><id>" + i + "</id></pedido>");
			producer.send(message);
		}
		

		//new Scanner(System.in).nextLine();

		session.close();
		connection.close();
		context.close();

	}

}

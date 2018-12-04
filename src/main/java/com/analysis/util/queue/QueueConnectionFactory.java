package com.analysis.util.queue;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQPrefetchPolicy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class QueueConnectionFactory {

	private static final Logger LOGGER = LogManager.getLogger(QueueConnectionFactory.class);

	public static Connection createActiveMQConnection(String url) {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
				ActiveMQConnection.DEFAULT_PASSWORD, url);
		connectionFactory.setCopyMessageOnSend(false);
		connectionFactory.setMessagePrioritySupported(true);
		ActiveMQPrefetchPolicy prefetchPolicy = new ActiveMQPrefetchPolicy();
		prefetchPolicy.setAll(10);
		connectionFactory.setPrefetchPolicy(prefetchPolicy);
		Connection connection = null;
		try {
			connection = connectionFactory.createConnection();
			connection.start();
		} catch (Exception e) {
			LOGGER.error("failed to connect to activeMQ : " + e.getMessage(), e);
			return null;
		}
		return connection;
	}

	public static Session createSession(Connection connection) {
		Session session = null;
		try {
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		} catch (JMSException e) {
			LOGGER.error("failed to create session : " + e.getMessage(), e);
			return null;
		} catch (NullPointerException ne) {
			LOGGER.error("failed to create session : " + ne.getMessage(), ne);
			return null;
		}
		return session;
	}
	
	public static MessageProducer createPersistentProducer(Session session) {
		MessageProducer messageProducer = null;
		try {
			messageProducer = session.createProducer(null);
			messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
		} catch (JMSException e) {
			LOGGER.error("failed to create message producer : " + e.getMessage(), e);
			return null;
		} catch (NullPointerException ne) {
			LOGGER.error("failed to create message producer : " + ne.getMessage(), ne);
			return null;
		}
		return messageProducer;
	}

	public static MessageProducer createPersistentProducer(Session session, String queueName) {
		MessageProducer messageProducer = null;
		Destination destination;
		try {
			destination = session.createQueue(queueName);
			messageProducer = session.createProducer(destination);
			messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
		} catch (JMSException e) {
			LOGGER.error("failed to create message producer : " + e.getMessage(), e);
			return null;
		} catch (NullPointerException ne) {
			LOGGER.error("failed to create message producer : " + ne.getMessage(), ne);
			return null;
		}
		return messageProducer;
	}

	public static MessageProducer createInstantProducer(Session session, String queueName, String selector) {
		MessageProducer messageProducer = null;
		Destination destination;
		try {
			if (selector != null) {
				destination = session.createQueue(selector + ":" + queueName);
			} else {
				destination = session.createQueue(queueName);
			}
			messageProducer = session.createProducer(destination);
			messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		} catch (JMSException e) {
			LOGGER.error("failed to create message producer : " + e.getMessage(), e);
			return null;
		} catch (NullPointerException ne) {
			LOGGER.error("failed to create message producer : " + ne.getMessage(), ne);
			return null;
		}
		return messageProducer;
	}

	public static MessageConsumer createConsumer(Session session, String queueName) {
		MessageConsumer consumer = null;
		Destination destination;
		try {
			destination = session.createQueue(queueName);
			consumer = session.createConsumer(destination);
		} catch (JMSException e) {
			LOGGER.error("failed to create message consumer : " + e.getMessage(), e);
			return null;
		} catch (NullPointerException ne) {
			LOGGER.error("failed to create message consumer : " + ne.getMessage(), ne);
			return null;
		}
		return consumer;
	}

	public static MessageConsumer createConsumer(Session session, String queueName, String selector, String condition) {
		MessageConsumer consumer = null;
		Destination destination;
		try {
			if (selector != null) {
				destination = session.createQueue(selector + ":" + queueName);
			} else {
				destination = session.createQueue(queueName);
			}
			consumer = session.createConsumer(destination, condition);
		} catch (JMSException e) {
			LOGGER.error("failed to create message consumer : " + e.getMessage(), e);
			return null;
		} catch (NullPointerException ne) {
			LOGGER.error("failed to create message consumer : " + ne.getMessage(), ne);
			return null;
		}
		return consumer;
	}

	public static boolean closeQueueConnection(Connection connection) {
		try {
			connection.close();
		} catch (JMSException e) {
			LOGGER.error("failed to close connection : " + e.getMessage(), e);
			return false;
		} catch (NullPointerException ne) {
			LOGGER.error("failed to close connection : " + ne.getMessage(), ne);
			return false;
		}
		return true;
	}

	public static boolean closeSession(Session session) {
		try {
			session.close();
		} catch (JMSException e) {
			LOGGER.error("failed to close session : " + e.getMessage(), e);
			return false;
		} catch (NullPointerException ne) {
			LOGGER.error("failed to close session : " + ne.getMessage(), ne);
			return false;
		}
		return true;
	}

	public static boolean closeMessageProducer(MessageProducer MessageProducer) {
		try {
			MessageProducer.close();
		} catch (JMSException e) {
			LOGGER.error("failed to close message producer : " + e.getMessage(), e);
			return false;
		} catch (NullPointerException ne) {
			LOGGER.error("failed to close message producer : " + ne.getMessage(), ne);
			return false;
		}
		return true;
	}

	public static boolean closeMessageConsumer(MessageConsumer messageConsumer) {
		try {
			messageConsumer.close();
		} catch (JMSException e) {
			LOGGER.error("failed to close message consumer : " + e.getMessage(), e);
			return false;
		} catch (NullPointerException ne) {
			LOGGER.error("failed to close message consumer : " + ne.getMessage(), ne);
			return false;
		}
		return true;
	}
}

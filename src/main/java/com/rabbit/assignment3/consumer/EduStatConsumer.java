package com.rabbit.assignment3.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class EduStatConsumer {

	@RabbitListener(queues = "topic1")
	public void listen1(String in) {
	    System.out.println("Message read from Queue topic1 : " + in);
	}

	@RabbitListener(queues = "topic2")
	public void listen2(String in) {
	    System.out.println("Message read from Queue topic2 : " + in);
	}

	@RabbitListener(queues = "topic3")
	public void listen3(String in) {
	    System.out.println("Message read from Queue topic3 : " + in);
	}

	@RabbitListener(queues = "topic4")
	public void listen4(String in) {
	    System.out.println("Message read from Queue topic4 : " + in);
	}

	@RabbitListener(queues = "topic5")
	public void listen5(String in) {
	    System.out.println("Message read from Queue topic5 : " + in);
	}

}

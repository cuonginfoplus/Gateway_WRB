package gateway.wrb.controllers;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Publisher {
	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Value("${wrb.rabbitmq.exchange}")
	private String exchange;

	@Value("${wrb.rabbitmq.routingkey}")
	private String routingKey;

	public void produceMsg(String msg) {
		rabbitTemplate.convertAndSend(exchange, routingKey, msg);
		System.out.println("Send msg = " + msg);
	}
}

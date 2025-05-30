package com.Shivesh.AuthService.eventProducer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.Shivesh.AuthService.dto.UserInfoDto;

@Service
public class UserInfoProducer {

	
	private final KafkaTemplate<String, UserInfoDto> kafkaTemplate;
	
	@Value("${spring.kafka.topic.name}")
	private String topicName;
	
	
//	"hello ny namses sadjasbdjasbd"
	
	@Autowired
	UserInfoProducer(KafkaTemplate<String, UserInfoDto> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}
	
	public void sendEventToKafka(UserInfoDto userInfoDto) {
		Message<UserInfoDto> message = MessageBuilder.withPayload(userInfoDto)
													 .setHeader(KafkaHeaders.TOPIC,topicName)
													 .build();
		kafkaTemplate.send(message);
	}
}

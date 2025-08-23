package vti.microservice.department.consumers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumerService {

    @KafkaListener(topics ="test-topic",groupId = "department-group")
    public void consumeMessaga(String message){
        log.info("Received message {}",message);
    }
}

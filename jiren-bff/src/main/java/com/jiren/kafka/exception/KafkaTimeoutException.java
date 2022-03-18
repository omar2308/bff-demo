package com.jiren.kafka.exception;

/**
 *
 * @author Omar
 */
public class KafkaTimeoutException extends KafkaException{

    public KafkaTimeoutException() {
    }

    public KafkaTimeoutException(String message) {
        super(message);
    }

    public KafkaTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }

    public KafkaTimeoutException(Throwable cause) {
        super(cause);
    }
    
}

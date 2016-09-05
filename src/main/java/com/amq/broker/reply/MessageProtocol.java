package com.amq.broker.reply;

/**
 * @author chen
 * @date 2016/9/6 0:05
 */
public class MessageProtocol {
    public String handleProtocolMessage(String messageText) {
        String responseText;
        if ("MyProtocolMessage".equalsIgnoreCase(messageText)) {
            responseText = "I recognize your protocol message";
        } else {
            responseText = "Unknown protocol message: " + messageText;
        }
        return responseText;
    }
}
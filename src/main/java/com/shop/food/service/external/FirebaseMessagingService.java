package com.shop.food.service.external;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.shop.food.dto.NotificationMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FirebaseMessagingService {

    @Autowired
    private FirebaseMessaging firebaseMessaging;

    public String sendNotification(NotificationMessage notificationMessage) throws Exception {
        Message message = Message.builder()
                .setNotification(Notification.builder()
                        .setTitle(notificationMessage.getTitle())
                        .setBody(notificationMessage.getBody())
                        .setImage(notificationMessage.getImage())
                        .build())
                .setToken(notificationMessage.getRecipientToken())
                .build();

        try {
            return "Sending Notification Successfully: " + firebaseMessaging.send(message);
        }
        catch (FirebaseMessagingException e) {
            e.printStackTrace();
            return "Error Sending Notification ";
        }
    }
}

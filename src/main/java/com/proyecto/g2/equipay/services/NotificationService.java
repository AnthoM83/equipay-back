package com.proyecto.g2.equipay.services;

import com.proyecto.g2.equipay.commons.dtos.notification.NotificationRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {
   private static final String EXPO_PUSH_API_URL = "https://exp.host/--/api/v2/push/send";
   public void sendNotification(String to, String title, String body) {
      try {
         RestTemplate restTemplate = new RestTemplate();
         NotificationRequest notificacionRequest = new NotificationRequest(to, title, body);
         String response = restTemplate.postForObject(EXPO_PUSH_API_URL, notificacionRequest, String.class);

         System.out.println("Notificacion enviada con exito : " + response);
      }
      catch (Exception e) {
         e.printStackTrace();
         System.out.println("Error al enviar notificacion: " + e.getMessage());
      }
   }
}

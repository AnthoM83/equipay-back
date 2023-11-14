package com.proyecto.g2.equipay.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.g2.equipay.commons.dtos.notification.ExpoServiceResponse;
import com.proyecto.g2.equipay.commons.dtos.notification.NotificationDto;
import com.proyecto.g2.equipay.commons.dtos.notification.NotificationRequest;
import com.proyecto.g2.equipay.models.Notification;
import com.proyecto.g2.equipay.models.Usuario;
import com.proyecto.g2.equipay.repositories.INotificationRepository;
import com.proyecto.g2.equipay.repositories.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

   @Autowired
   INotificationRepository notificationRepo;

   @Autowired
   IUsuarioRepository usuarioRepo;

   private static final String EXPO_PUSH_API_URL = "https://exp.host/--/api/v2/push/send";

   public void crearNotificacion(String id, String message, String status, String recibe, String envia){
      Notification notification = new Notification();
      notification.setId(id);
      notification.setMessage(message);
      notification.setFecha(LocalDate.now());
      notification.setLeido(false);
      notification.setStatus(status);
      notification.setEnvia(envia);

      Usuario usuario = usuarioRepo.findById(recibe).orElseThrow();
      notification.setRecibe(usuario);

      notificationRepo.save(notification);
   }
   public void sendNotification(String envia, String recibe, String to, String title, String body) {
      try {
         RestTemplate restTemplate = new RestTemplate();
         NotificationRequest notificacionRequest = new NotificationRequest(to, title, body);
         String response = restTemplate.postForObject(EXPO_PUSH_API_URL, notificacionRequest, String.class);

         System.out.println("Notificacion enviada con exito : " + response);

         ObjectMapper objectMapper = new ObjectMapper();
         ExpoServiceResponse expoServiceResponse = objectMapper.readValue(response, ExpoServiceResponse.class);
         crearNotificacion(expoServiceResponse.getData().getId(), body, expoServiceResponse.getData().getStatus(), recibe, envia);
      }
      catch (Exception e) {
         e.printStackTrace();
         System.out.println("Error al enviar notificacion: " + e.getMessage());
      }
   }

   public List<NotificationDto> listarNotificacionesUsuario(String idUsuario){
      Usuario usuario = usuarioRepo.findById(idUsuario).orElseThrow();

      List<Notification> notifications = usuario.getNotificaciones();
      List<NotificationDto> dto = new ArrayList<>();
      for (Notification notification : notifications) {
         dto.add(new NotificationDto(notification.getId(), notification.getMessage(), notification.getFecha(), notification.getEnvia()));
      }
      return dto;
   }
}

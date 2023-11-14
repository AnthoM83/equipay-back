package com.proyecto.g2.equipay.commons.dtos.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequest {
   private String to;
   private String title;
   private String body;
}

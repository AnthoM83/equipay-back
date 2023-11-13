package com.proyecto.g2.equipay.commons.dtos.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {
   String id;
   String message;
   LocalDate fecha;
   String envia;
}

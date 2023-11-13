package com.proyecto.g2.equipay.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Notification {
   @Id
   String id;
   String message;
   LocalDate fecha;
   Boolean leido;
   String status;
   String envia;

   @ManyToOne
   @JoinColumn(name = "usuario_recibe")
   Usuario recibe;

}

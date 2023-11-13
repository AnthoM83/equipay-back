package com.proyecto.g2.equipay.repositories;

import com.proyecto.g2.equipay.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface INotificationRepository extends JpaRepository<Notification, String> {}

package com.proyecto.g2.equipay.models;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class UsuarioBase {
    
    @Id
    private String correo;
    private String nombre;
    private String apellido;
    private String password;
}

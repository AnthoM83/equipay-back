package com.proyecto.g2.equipay.models;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
public abstract class UsuarioBase {

    // Propiedades
    @Id
    private String correo;
    private String nombre;
    private String apellido;
    private String password;
}

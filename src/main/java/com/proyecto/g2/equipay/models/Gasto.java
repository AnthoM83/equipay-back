package com.proyecto.g2.equipay.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Gasto {

    // Propiedades
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Long monto;
    private String moneda;
    private String descripcion;
    private LocalDate fecha;

    // Asociaciones
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "grupo_id")
    private Grupo grupo;

    @ManyToOne
    @JoinColumn(name = "cubierto_por_id")
    private Usuario cubiertoPor;

    @ManyToMany
    @JoinTable(name = "beneficiado_gasto",
            joinColumns = @JoinColumn(name = "gasto_id"),
            inverseJoinColumns = @JoinColumn(name = "beneficiado_id"))
    private List<Usuario> beneficiados;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

}

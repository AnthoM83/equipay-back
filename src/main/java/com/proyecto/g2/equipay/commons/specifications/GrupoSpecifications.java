package com.proyecto.g2.equipay.commons.specifications;

import com.proyecto.g2.equipay.models.Grupo;
import com.proyecto.g2.equipay.models.Usuario;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class GrupoSpecifications {

    public static Specification<Grupo> hasUsuario(String idUsuario) {
        return (root, query, criteriaBuilder) -> {
            Join<Usuario, Grupo> grupoContieneUsuario = root.join("miembros");
            return criteriaBuilder.equal(grupoContieneUsuario.get("id"), idUsuario);
        };
    }

}

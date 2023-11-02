package com.proyecto.g2.equipay.commons.specifications;

import com.proyecto.g2.equipay.models.Grupo;
import com.proyecto.g2.equipay.models.Usuario;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class UsuarioSpecifications {

    public static Specification<Usuario> hasGrupo(Integer idGrupo) {
        return (root, query, criteriaBuilder) -> {
            Join<Grupo, Usuario> usuarioContieneGrupo = root.join("miembroDe");
            return criteriaBuilder.equal(usuarioContieneGrupo.get("id"), idGrupo);
        };
    }

}

package com.proyecto.g2.equipay.services;

import com.proyecto.g2.equipay.models.Categoria;
import com.proyecto.g2.equipay.repositories.ICategoriaRepository;
import jakarta.persistence.EntityExistsException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

    // Dependencias
    @Autowired
    ICategoriaRepository repo;

    // Métodos
    public Boolean existeCategoria(Integer id) {
        return repo.existsById(id);
    }

    public Categoria buscarCategoria(Integer id) {
        return repo.findById(id).orElseThrow();
    }

    public List<Categoria> listarCategorias() {
        return repo.findAll();
    }

    public void crearCategoria(Categoria categoria) {
        Optional<Categoria> categoriaExistente = repo.findById(categoria.getId());
        if (categoriaExistente.isEmpty()) {
            repo.save(categoria);
        } else {
            throw new EntityExistsException();
        }
    }

    public void modificarCategoria(Integer id, Categoria categoria) {
        if (repo.existsById(id)) {
            repo.save(categoria);
        } else {
            throw new NoSuchElementException();
        }
    }

    public void eliminarCategoria(Integer id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
        } else {
            throw new NoSuchElementException();
        }
    }

}

package com.proyecto.g2.equipay.services;

import com.proyecto.g2.equipay.commons.dtos.categoria.AddEditCategoriaDto;
import com.proyecto.g2.equipay.models.Categoria;
import com.proyecto.g2.equipay.repositories.ICategoriaRepository;
import java.util.List;
import java.util.NoSuchElementException;
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

    public void crearCategoria(AddEditCategoriaDto dto) {
        Categoria categoria = new Categoria();
        categoria.setNombre(dto.getNombre());
        repo.save(categoria);
    }

    public void modificarCategoria(Integer id, AddEditCategoriaDto dto) {
        Categoria categoria = repo.findById(id).orElseThrow();
        categoria.setNombre(dto.getNombre());
        repo.save(categoria);

    }

    public void eliminarCategoria(Integer id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
        } else {
            throw new NoSuchElementException();
        }
    }

}

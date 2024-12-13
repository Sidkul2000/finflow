package com.java.project.FinFlow.Services;

import com.java.project.FinFlow.DataRepositories.CategoryRepository;
import com.java.project.FinFlow.Tables.Categories;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    final private CategoryRepository repo;

    public CategoryService(CategoryRepository repo) {
        this.repo = repo;
    }

    public List<Categories> getAll() {
        return repo.findAll();
    }

    public Categories getById(Long id) {
        Optional<Categories> found = repo.findById(id);
        if (found.isPresent()) {
            return found.get();
        } else {
            throw new EntityNotFoundException("No category with id: " + id);
        }
    }

    public String getNameById(Long id) {
        Optional<String> name = repo.getCategoryById(id);
        if (name.isPresent()) {
            return name.get();
        } else {
            throw new EntityNotFoundException("No category with id: " + id);
        }
    }

    public Long getIdByName(String name) {
        Optional<Long> id = repo.getIdByCategoryName(name);
        if (id.isPresent()) {
            return id.get();
        } else {
            throw new EntityNotFoundException("No category with name: " + name);
        }
    }
}

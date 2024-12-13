package com.java.project.FinFlow.Controllers;

import com.java.project.FinFlow.Assemblers.CategoryAssembler;
import com.java.project.FinFlow.Services.CategoryService;
import com.java.project.FinFlow.Tables.Categories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private final CategoryService service;

    @Autowired
    private final CategoryAssembler assembler;

    public CategoryController(CategoryService service, CategoryAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping("/all")
    public List<Categories> all() {
        List<Categories> all = service.getAll();
        return all;
    }

    @GetMapping("/detail/{id}")
    public EntityModel<Categories> detail(@PathVariable Long id) {
        Categories c = service.getById(id);
        return assembler.toModel(c);
    }
}

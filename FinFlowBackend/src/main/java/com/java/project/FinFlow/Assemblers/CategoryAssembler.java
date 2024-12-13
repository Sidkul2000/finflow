package com.java.project.FinFlow.Assemblers;

import com.java.project.FinFlow.Controllers.CategoryController;
import com.java.project.FinFlow.Tables.Categories;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class CategoryAssembler implements RepresentationModelAssembler<Categories, EntityModel<Categories>> {
    @Override
    public @NonNull EntityModel<Categories> toModel(@NonNull Categories entity) {
        EntityModel<Categories> model = EntityModel.of(entity);
        model.add(linkTo(methodOn(CategoryController.class).detail(entity.getId())).withSelfRel());
        model.add(linkTo(methodOn(CategoryController.class).all()).withRel("all_categories"));
        return model;
    }
}

package com.java.project.FinFlow.Assemblers;

import com.java.project.FinFlow.Controllers.CategoryController;
import com.java.project.FinFlow.Controllers.RecordController;
import com.java.project.FinFlow.Tables.Records;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class RecordAssembler implements RepresentationModelAssembler<Records, EntityModel<Records>> {
    /*
     * Functionality:
     * - toModel(Records entity): Converts a given Records entity into an EntityModel of Records.
     *   Input: A Records entity (entity) that represents the data to be transformed.
     *   Output: An EntityModel of Records which includes the original entity and hyperlinks.
     *            links are:
     *           1. A 'self' link that points to the detailed view of the record itself.
     *           2. A 'category' link that points to the detailed view of the category associated with the record.
     *           3. More links to add if needed...
     */


    @Override
    public @NonNull EntityModel<Records> toModel(@NonNull Records entity) {
        EntityModel<Records> model = EntityModel.of(entity);
        model.add(linkTo(methodOn(RecordController.class)
                .detail(entity.getCategory().getId()))
                .withSelfRel());
        model.add(linkTo(methodOn(CategoryController.class)
                .detail(entity.getCategory().getId()))
                .withRel("category"));
        return model;
    }
}

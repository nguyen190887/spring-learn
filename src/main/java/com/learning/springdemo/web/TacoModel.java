package com.learning.springdemo.web;

import com.learning.springdemo.Taco;
import lombok.Getter;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;

public class TacoModel extends RepresentationModel<Taco> {
    private final IngredientModelAssembler ingredientAssembler = new IngredientModelAssembler();

    @Getter
    private final String name;

    @Getter
    private final Date createdAt;

    @Getter
    private final CollectionModel<IngredientModel> ingredients;

    public TacoModel(Taco taco) {
        this.name = taco.getName();
        this.createdAt = taco.getCreatedAt();
        this.ingredients = ingredientAssembler.toCollectionModel(taco.getIngredients());
    }
}

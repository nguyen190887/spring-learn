package com.learning.springdemo.web;

import com.learning.springdemo.Ingredient;
import com.learning.springdemo.Ingredient.Type;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

public class IngredientModel extends RepresentationModel<Ingredient> {
    @Getter
    private final String name;

    @Getter
    private final Type type;

    public IngredientModel(Ingredient ingredient) {
        this.name = ingredient.getName();
        this.type = ingredient.getType();
    }
}

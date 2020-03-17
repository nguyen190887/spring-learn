package com.learning.springdemo.data;

import com.learning.springdemo.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
//    Iterable<Ingredient> findAll();
//
//    Ingredient findOne(String id);
//
//    Ingredient save(Ingredient ingredient);
}

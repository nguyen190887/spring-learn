package com.learning.springdemo.web;

import com.learning.springdemo.Taco;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public class TacoModelAssembler extends RepresentationModelAssemblerSupport<Taco, TacoModel> {

    public TacoModelAssembler() {
        super(RestDesignTacoController.class, TacoModel.class);
    }

    @Override
    protected TacoModel instantiateModel(Taco entity) {
        return new TacoModel(entity);
    }

    @Override
    public TacoModel toModel(Taco entity) {
        return createModelWithId(entity.getId(), entity);
    }
}

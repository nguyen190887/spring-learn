package com.learning.springdemo.web;

import com.learning.springdemo.Taco;
import com.learning.springdemo.data.TacoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/api/design", produces = "application/json")
@CrossOrigin(origins = "*")
public class RestDesignTacoController {
    private TacoRepository tacoRepo;

    @Autowired
    EntityLinks entityLinks;

    public RestDesignTacoController(TacoRepository tacoRepo) {
        this.tacoRepo = tacoRepo;
    }

    //FIXME - got SQL error: Column "TACO0_.CREATED_AT" not found
    @GetMapping("/recent")
    public CollectionModel<EntityModel<Taco>> recentTacos() {
        PageRequest page = PageRequest.of(
                0, 12, Sort.by(("createdAt")).descending());
        List<Taco> tacos = tacoRepo.findAll(page).getContent();

        CollectionModel<EntityModel<Taco>> recentResources = CollectionModel.wrap(tacos);
//        recentResources.add(
//                ControllerLinkBuilder.linkTo(RestDesignTacoController.class)
//                        .slash("recent")
//                        .withRel("recents"));
        recentResources.add(
                linkTo(methodOn(RestDesignTacoController.class).recentTacos())
                .withRel("recents"));
        return recentResources;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {
        Optional<Taco> optTaco = tacoRepo.findById(id);
        if (optTaco.isPresent()) {
            return new ResponseEntity<>(optTaco.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Taco postTaco(@RequestBody Taco taco) {
        return tacoRepo.save(taco);
    }
}

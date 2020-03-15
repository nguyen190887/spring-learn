package com.learning.springdemo.data;

import com.learning.springdemo.Taco;
import org.springframework.data.repository.CrudRepository;

public interface TacoRepository extends CrudRepository<Taco, Long> {
    Taco save(Taco taco);
}

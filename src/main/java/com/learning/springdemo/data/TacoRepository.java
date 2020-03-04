package com.learning.springdemo.data;

import com.learning.springdemo.Taco;

public interface TacoRepository {
    Taco save(Taco taco);
}

package com.makul.fitness.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makul.fitness.dto.UsersDto;
import com.makul.fitness.model.Users;
import com.makul.fitness.service.api.UsersService;
import org.springframework.web.bind.annotation.*;

@RestController
public class UsersController {

    private final ObjectMapper objectMapper;
    private final UsersService usersService;
    public UsersController(ObjectMapper objectMapper, UsersService usersService) {
        this.objectMapper = objectMapper;
        this.usersService = usersService;
    }

    @PostMapping(value = "/user")
    public UsersDto createUser(@RequestBody UsersDto usersDto){
        Users user = objectMapper.convertValue(usersDto,Users.class);
        return objectMapper.convertValue(usersService.create(user),UsersDto.class);
    }
}

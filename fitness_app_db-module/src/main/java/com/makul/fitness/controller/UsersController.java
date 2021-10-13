package com.makul.fitness.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makul.fitness.dto.UsersDto;
import com.makul.fitness.model.Users;
import com.makul.fitness.service.api.UsersService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping(value = "/user/{id}")
    public UsersDto showUserById(@PathVariable("id") long id){
        return objectMapper.convertValue(usersService.read(id),UsersDto.class);
    }

    @PutMapping(value = "/user")
    public UsersDto updateUser(@RequestBody UsersDto usersDto){
        Users user = objectMapper.convertValue(usersDto,Users.class);
        return objectMapper.convertValue(usersService.update(user),UsersDto.class);
    }
}

package com.makul.fitness.controller;

import com.makul.fitness.dto.UsersDto;
import com.makul.fitness.model.Users;
import com.makul.fitness.service.api.UsersService;
import com.makul.fitness.util.CustomMapperUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Api(tags = "Controller for user entity")
public class UsersController {

    private final UsersService usersService;

    @PostMapping(value = "/user")
    @ApiOperation(value = "Create new User")
    public UsersDto createUser(@RequestBody UsersDto usersDto){
        Users user = CustomMapperUtil.convertToDto(usersDto,Users.class);
        return CustomMapperUtil.convertToDto(usersService.create(user),UsersDto.class);
    }

    @GetMapping(value = "/user/{id}")
    @ApiOperation(value = "Get user by id")
    public UsersDto readUserById (@ApiParam(defaultValue = "3") @PathVariable UUID id){
        return CustomMapperUtil.convertToDto(usersService.read(id),UsersDto.class);
    }

    @GetMapping(value = "/user/{firstName}/{lastName}")
    @ApiOperation(value = "Get user by first and last name")
    public Page <UsersDto> readUserByNameLastName(@ApiParam(defaultValue = "Petr") @PathVariable String firstName,
                                                 @ApiParam(defaultValue = "Ivanov") @PathVariable String lastName,
                                                 @ApiParam(defaultValue = "0") @RequestParam int page,
                                                 @ApiParam(defaultValue = "10") @RequestParam int size){
        return CustomMapperUtil.convertToDto(usersService.readUserByFirstLastName(firstName,lastName, page, size),
                UsersDto.class);
    }
}

package com.example.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/employees")
@CrossOrigin("*")
@AllArgsConstructor(onConstructor=@__(@Autowired))
public class EmployeeController {

}

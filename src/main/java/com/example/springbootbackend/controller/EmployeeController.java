package com.example.springbootbackend.controller;

import com.example.springbootbackend.exception.ResourceNotFoundException;
import com.example.springbootbackend.model.Employee;
import com.example.springbootbackend.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;
    @GetMapping
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }
    //build create employee Rest Api
    @PostMapping
    //@PostMapping(/employees) c'est la meme que de requestmapping  c'est pourquoi en le mettre vide
    public Employee createEmployee(@RequestBody Employee em){
        return employeeRepository.save(em);
    }
    //build get employee bu id Rest Api
    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployeeByid(@PathVariable Long id){
        Employee employee= employeeRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Employee not exist with this id="+id));
        return ResponseEntity.ok(employee);
    }
    //build update  employee Rest Api
    @PutMapping("{id}")
    public ResponseEntity<Employee>  updateEmployee(@PathVariable Long id ,@RequestBody Employee employeeDetails){
        Employee updateEmployee = employeeRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Employee not exist with this id="+id));
        updateEmployee.setFirstName(employeeDetails.getFirstName());
        updateEmployee.setLastName(employeeDetails.getLastName());
        updateEmployee.setEmailId(employeeDetails.getEmailId());
        employeeRepository.save(updateEmployee);
        return ResponseEntity.ok(updateEmployee);
    }
    //build delete employee Rest Api
    @DeleteMapping("{id}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable Long id){
        Employee employee= employeeRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Employee not exist with this id="+id));
        employeeRepository.delete(employee);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

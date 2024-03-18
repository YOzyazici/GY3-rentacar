package com.turkcell.rentacar.api.controllers;

import com.turkcell.rentacar.business.abstracts.ModelService;
import com.turkcell.rentacar.entities.concretes.Model;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/models")
public class ModelsController {

    private ModelService modelService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Model add(@RequestBody Model model) {
        return modelService.add(model);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Model> getAll() {
        return modelService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Model getById(@PathVariable int id) {
        return modelService.getById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Model update(@PathVariable int id, @RequestBody Model model) {
        return modelService.update(id, model);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable int id) {
        modelService.delete(id);
    }

}

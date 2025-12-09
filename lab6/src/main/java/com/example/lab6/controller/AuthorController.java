package com.example.lab6.controller;


import com.example.lab6.model.Author;
import com.example.lab6.request.author.AuthorCreateRequest;
import com.example.lab6.request.author.AuthorUpdateRequest;
import com.example.lab6.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class AuthorController
{
    AuthorService service;

    @Autowired
    public AuthorController(AuthorService service)
    {
        this.service = service;
    }

    @GetMapping("/selectors/authors")
    public ResponseEntity<?> selector()
    {
        return new ResponseEntity<>(service.getSelector(), HttpStatus.OK);
    }

    @GetMapping("/authors")
    public ResponseEntity<?> list
    (
        @RequestParam(value = "page", required = false) Integer page,
        @RequestParam(value = "size", required = false) Integer size,
        @RequestParam(value = "search", required = false) String search
    )
    {
        return new ResponseEntity<>(service.getAllPaginated(page, size, search), HttpStatus.OK);
    }

    @GetMapping("/authors/{id}")
    public ResponseEntity<?> retrieve(@PathVariable Integer id)
    {
        Author response = service.findItemById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/authors")
    public ResponseEntity<?> create(@RequestBody AuthorCreateRequest data)
    {
        return new ResponseEntity<>(service.saveItem(data), HttpStatus.CREATED);
    }

    @PutMapping("/authors/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody AuthorUpdateRequest data)
    {
        return new ResponseEntity<>(service.updateItem(id, data), HttpStatus.OK);
    }

    @DeleteMapping("/authors/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id)
    {
        return new ResponseEntity<>(service.deleteItemById(id), HttpStatus.OK);
    }
}

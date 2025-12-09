package com.example.lab6.service;

import com.example.lab6.errors.InvalidDataException;
import com.example.lab6.errors.ResourceNotFoundException;
import com.example.lab6.model.Author;
import com.example.lab6.repository.AuthorRepository;
import com.example.lab6.request.author.AuthorCreateRequest;
import com.example.lab6.request.author.AuthorSelectorRequest;
import com.example.lab6.request.author.AuthorUpdateRequest;
import com.example.lab6.validator.AuthorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService
{
    AuthorRepository repository;
    AuthorValidator validator = new AuthorValidator();

    @Autowired
    public AuthorService(AuthorRepository repository)
    {
        this.repository = repository;
    }

    public List<Author> getAll()
    {
        return repository.findAll();
    }

    public List<AuthorSelectorRequest> getSelector()
    {
        return getAll().stream().map(
            item -> new AuthorSelectorRequest(
                    item.getId(),
                    item.getFullName()
            )
        ).toList();
    }

    public Page<Author> getAllPaginated(Integer page, Integer size, String search)
    {
        int pageNumber = (page != null && page > 0) ? page - 1 : 0;
        int pageSize = (size != null && size > 0) ? size : 25;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        if (search != null) {
            return repository.findByFullNameContainingIgnoreCase(search.trim(), pageable);
        }

        return repository.findAll(pageable);
    }

    public Author findItemById(Integer id)
    {
        Optional<Author> item = repository.findById(id);
        if (item.isEmpty()) throw new ResourceNotFoundException("Автор с ID " + id + " не найден");
        return item.get();
    }

    public Author saveItem(AuthorCreateRequest data)
    {
        validator.validate(data);
        return repository.save(new Author(
            data.getName(),
            data.getSurname(),
            data.getLastname()
        ));
    }

    public Author updateItem(Integer id, AuthorUpdateRequest data)
    {
        Author item = findItemById(id);
        validator.validate(data);
        item.setName(data.getName());
        item.setSurname(data.getSurname());
        item.setLastname(data.getLastname());
        return repository.save(item);
    }

    public Author deleteItemById(Integer id)
    {
        Author item = findItemById(id);
        repository.deleteById(id);
        return item;
    }
}

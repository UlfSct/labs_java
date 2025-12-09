package com.example.lab6.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="authors")
public class Author
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "lastname")
    private String lastname;

    public Author(String name, String surname, String lastname)
    {
        this.name = name;
        this.surname = surname;
        this.lastname = lastname;
    }

    @Transient
    @JsonIgnore
    public String getFullName() {
        if (lastname != null && !lastname.isEmpty()) {
            return surname + " " + name + " " + lastname;
        }
        return surname + " " + name;
    }

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Book> books;
}

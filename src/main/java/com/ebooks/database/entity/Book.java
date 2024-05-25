package com.ebooks.database.entity;

import jakarta.persistence.*;


import java.sql.Date;
import java.util.Base64;
import java.util.List;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "image", nullable = true, columnDefinition = "bytea")
    private byte[] image;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<OperationsOnBooks> operations;
    @Column(columnDefinition = "TEXT")
    private String description;

    private String title;
    private String author;
    private Date yearOfPublication;
    private String genre;
    private Integer NumberOfCopies;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<OperationsOnBooks> getOperations() {
        return operations;
    }

    public void setOperations(List<OperationsOnBooks> operations) {
        this.operations = operations;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(Date yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getNumberOfCopies() {
        return NumberOfCopies;
    }

    public void setNumberOfCopies(Integer numberOfCopies) {
        NumberOfCopies = numberOfCopies;
    }

    public String getImage() {
        return Base64.getEncoder().encodeToString(image);
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
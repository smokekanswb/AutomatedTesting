package com.ebooks.database.entity;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
public class OperationsOnBooks {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private Date issueDate;
    private Date returnTerm;
    private String receiver;
    private String sender;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getReturnTerm() {
        return returnTerm;
    }

    public void setReturnTerm(Date returnTerm) {
        this.returnTerm = returnTerm;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}

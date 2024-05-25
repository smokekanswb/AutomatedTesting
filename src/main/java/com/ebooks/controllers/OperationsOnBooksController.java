package com.ebooks.controllers;


import com.ebooks.database.entity.OperationsOnBooks;

import com.ebooks.database.repos.OperationsOnBooksRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.time.LocalDate;

@Controller
public class OperationsOnBooksController {

    @Autowired
    private OperationsOnBooksRepos operationsOnBooksRepos;

    // Метод обробки HTTP POST-запиту для створення нової операції (оренди книги).
    // Використовує об'єкт, отриманий з форми на сторінці, та зберігає його в репозиторії.
    // Встановлює дату видачі як поточну дату.
    @PostMapping("/operations/save")
    public String createNewOperation(@ModelAttribute("operation") OperationsOnBooks operationsOnBooks) {
        // Встановлюємо дату видачі як поточну дату.
        operationsOnBooks.setIssueDate(Date.valueOf(LocalDate.now()));

        // Зберігаємо операцію в репозиторії.
        operationsOnBooksRepos.save(operationsOnBooks);

        // Після успішного збереження повертаємо користувача на головну сторінку.
        return "redirect:/";
    }

    // Метод обробки HTTP GET-запиту для видалення операції (повернення книги).
    // Видаляє операцію за її ідентифікатором з репозиторію.
    @GetMapping("/operations/delete/")
    public String deleteBookById(@RequestParam("operationId") Integer operationId) {
        // Видаляємо операцію за ідентифікатором з репозиторію.
        operationsOnBooksRepos.deleteById(operationId);

        // Після успішного видалення повертаємо користувача на головну сторінку.
        return "redirect:/";
    }
}


package com.ebooks.controllers;

import com.ebooks.database.entity.Book;
import com.ebooks.database.entity.OperationsOnBooks;
import com.ebooks.database.repos.BookRepos;
import com.ebooks.database.repos.OperationsOnBooksRepos;
import com.ebooks.utils.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Controller
public class BookController {

    @Autowired
    private BookRepos bookRepository;
    @Autowired
    private OperationsOnBooksRepos operationsOnBooksRepos;
    @Autowired
    private BookService bookService;

    // Метод обробки HTTP GET-запиту на головну сторінку. Він завантажує всі книги з репозиторію та передає їх до представлення "index".
    @GetMapping("/")
    public String index(Model model) {
        List<Book> books = (List<Book>) bookRepository.findAll();
        model.addAttribute("books", books);

        return "index";
    }

    // Метод обробки HTTP POST-запиту на пошук книги за ключовим словом. Знайдені книги передаються до представлення "index".
    @PostMapping("/search")
    public String search(@RequestParam("keyword") String keyword, Model model) {
        List<Book> books = bookRepository.findByTitleContainingOrAuthorContainingOrGenreContaining(keyword, keyword, keyword);
        model.addAttribute("books", books);

        return "index";
    }

    // Метод обробки HTTP GET-запиту для сортування книг за обраним параметром сортування.
    // Результат сортування передається до представлення "index".
    @GetMapping("/books")
    public String sort(@RequestParam("sortBy") String sortBy, Model model) {
        List<Book> books = bookService.getSortedBooks(sortBy, "asc");
        model.addAttribute("books", books);

        return "index";
    }

    // Метод обробки HTTP GET-запиту для перегляду деталей про окрему книгу.
    // Він завантажує книгу та відповідні операції, а потім передає їх до представлення "BookPage".
    @GetMapping("/book/")
    public String getBookById(@RequestParam("bookId") Integer bookId, Model model) {
        Book book = bookRepository.findBookById(bookId);
        List<OperationsOnBooks> operations = operationsOnBooksRepos.findOperationsOnBooksByBookId(book.getId());
        book.setNumberOfCopies(book.getNumberOfCopies() - operations.size());
        model.addAttribute("book", book);
        model.addAttribute("operations", operations);

        return "BookPage";
    }

    // Метод обробки HTTP POST-запиту для створення нової операції (оренди) книги.
    // Він приймає дані з форми, зберігає обкладинку та оновлює книгу в репозиторії.
    @PostMapping("/book/save")
    public String createNewOperation(@RequestParam("coverImage") MultipartFile file, @ModelAttribute("book") Book book) throws IOException {
        book.setImage(file.getBytes());
        bookRepository.save(book);

        return "redirect:/";
    }

    // Метод обробки HTTP GET-запиту для видалення книги з репозиторію за її ідентифікатором.
    @GetMapping("/book/delete/")
    public String deleteBookById(@RequestParam("bookId") Integer bookId) {
        bookRepository.deleteById(bookId);

        return "redirect:/";
    }

    // Метод обробки HTTP POST-запиту для оновлення інформації про книгу (включаючи обкладинку).
    // Він приймає дані з форми, оновлює обкладинку та оновлює інформацію про книгу в репозиторії.
    @PostMapping("/book/update")
    public String updateBook(@RequestParam("coverImage") MultipartFile file,
                             @ModelAttribute("book") Book updatedBook, Model model) throws IOException {

        if (file.isEmpty()) {
            // Якщо обкладинка не була змінена, використовуємо попередню обкладинку.
            updatedBook.setImage(Base64.getDecoder().decode(bookRepository.findBookById(updatedBook.getId()).getImage()));
        } else {
            // Інакше завантажуємо нову обкладинку.
            updatedBook.setImage(file.getBytes());
        }

        // Зберігаємо оновлену інформацію про книгу в репозиторії та оновлюємо список операцій.
        bookRepository.save(updatedBook);
        List<OperationsOnBooks> operations = operationsOnBooksRepos.findOperationsOnBooksByBookId(updatedBook.getId());
        model.addAttribute("book", updatedBook);
        model.addAttribute("operations", operations);

        return "BookPage";
    }
}

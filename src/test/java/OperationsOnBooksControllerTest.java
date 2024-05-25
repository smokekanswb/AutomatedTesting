import com.ebooks.controllers.OperationsOnBooksController;
import com.ebooks.database.entity.Book;
import com.ebooks.database.entity.OperationsOnBooks;
import com.ebooks.database.repos.OperationsOnBooksRepos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OperationsOnBooksControllerTest {

    @Mock
    private OperationsOnBooksRepos operationsOnBooksRepos;

    @Mock
    private Model model;

    @InjectMocks
    private OperationsOnBooksController operationsOnBooksController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateNewOperationWithNewBook() {
        // Створюємо тестовий об'єкт OperationsOnBooks без книги
        OperationsOnBooks operationsOnBooks = new OperationsOnBooks();
        operationsOnBooks.setReceiver("John Doe");
        operationsOnBooks.setReturnTerm(Date.valueOf(LocalDate.now().plusDays(30)));

        // Викликаємо метод createNewOperation
        String viewName = operationsOnBooksController.createNewOperation(operationsOnBooks);

        // Перевіряємо, що дата видачі була встановлена
        assertEquals(Date.valueOf(LocalDate.now()), operationsOnBooks.getIssueDate());

        // Перевіряємо, що метод save був викликаний один раз
        verify(operationsOnBooksRepos, times(1)).save(any(OperationsOnBooks.class));

        // Перевіряємо, що метод повертає "redirect:/"
        assertEquals("redirect:/", viewName);
    }

    @Test
    void testCreateNewOperationWithExistingBook() {
        // Створюємо тестові об'єкти Book та OperationsOnBooks
        Book book = new Book();
        book.setId(1);
        book.setTitle("Test Book");

        OperationsOnBooks operationsOnBooks = new OperationsOnBooks();
        operationsOnBooks.setBook(book);
        operationsOnBooks.setReceiver("John Doe");
        operationsOnBooks.setReturnTerm(Date.valueOf(LocalDate.now().plusDays(30)));

        // Налаштовуємо поведінку mock-об'єкта operationsOnBooksRepos
        when(operationsOnBooksRepos.findById(1)).thenReturn(Optional.of(operationsOnBooks));
        when(operationsOnBooksRepos.save(any(OperationsOnBooks.class))).thenReturn(operationsOnBooks);

        // Викликаємо метод createNewOperation
        String viewName = operationsOnBooksController.createNewOperation(operationsOnBooks);

        // Перевіряємо, що дата видачі була встановлена
        assertEquals(Date.valueOf(LocalDate.now()), operationsOnBooks.getIssueDate());

        // Перевіряємо, що метод save був викликаний один раз
        verify(operationsOnBooksRepos, times(1)).save(any(OperationsOnBooks.class));

        // Перевіряємо, що метод повертає "redirect:/"
        assertEquals("redirect:/", viewName);
    }

    @Test
    void testDeleteBookById() {
        // Викликаємо метод deleteBookById з тестовим operationId
        String viewName = operationsOnBooksController.deleteBookById(1);

        // Перевіряємо, що метод deleteById був викликаний один раз
        verify(operationsOnBooksRepos, times(1)).deleteById(1);

        // Перевіряємо, що метод повертає "redirect:/"
        assertEquals("redirect:/", viewName);
    }

    @Test
    void testDeleteBookByIdWithNonExistingOperation() {
        // Налаштовуємо поведінку mock-об'єкта operationsOnBooksRepos
        doThrow(new IllegalArgumentException("Invalid operation ID"))
                .when(operationsOnBooksRepos).deleteById(2);

        try {
            // Викликаємо метод deleteBookById з невірним operationId
            operationsOnBooksController.deleteBookById(2);
        } catch (IllegalArgumentException e) {
            // Перевіряємо, що був викинутий очікуваний виняток
            assertEquals("Invalid operation ID", e.getMessage());
        }

        // Перевіряємо, що метод deleteById був викликаний один раз
        verify(operationsOnBooksRepos, times(1)).deleteById(2);
    }
}
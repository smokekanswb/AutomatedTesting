import com.ebooks.controllers.BookController;
import com.ebooks.database.entity.Book;
import com.ebooks.database.entity.OperationsOnBooks;
import com.ebooks.database.repos.BookRepos;
import com.ebooks.database.repos.OperationsOnBooksRepos;
import com.ebooks.utils.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookControllerTest {

    @Mock
    private BookRepos bookRepository;

    @Mock
    private OperationsOnBooksRepos operationsOnBooksRepos;

    @Mock
    private BookService bookService;

    @Mock
    private Model model;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIndex() {
        List<Book> books = new ArrayList<>();
        when(bookRepository.findAll()).thenReturn(books);

        String viewName = bookController.index(model);

        verify(model, times(1)).addAttribute("books", books);
        assertEquals("index", viewName);
    }

    @Test
    void testSearch() {
        List<Book> books = new ArrayList<>();
        when(bookRepository.findByTitleContainingOrAuthorContainingOrGenreContaining(anyString(), anyString(), anyString()))
                .thenReturn(books);

        String viewName = bookController.search("keyword", model);

        verify(model, times(1)).addAttribute("books", books);
        assertEquals("index", viewName);
    }

    @Test
    void testSort() {
        List<Book> books = new ArrayList<>();
        when(bookService.getSortedBooks(anyString(), anyString())).thenReturn(books);

        String viewName = bookController.sort("title", model);

        verify(model, times(1)).addAttribute("books", books);
        assertEquals("index", viewName);
    }

    @Test
    void testGetBookById() {
        Book book = new Book();
        book.setNumberOfCopies(10); // Встановлюємо значення NumberOfCopies
        List<OperationsOnBooks> operations = new ArrayList<>();
        when(bookRepository.findBookById(anyInt())).thenReturn(book);
        when(operationsOnBooksRepos.findOperationsOnBooksByBookId(anyInt())).thenReturn(operations);

        String viewName = bookController.getBookById(1, model);

        verify(model, times(1)).addAttribute("book", book);
        verify(model, times(1)).addAttribute("operations", operations);
        assertEquals("BookPage", viewName);
    }

    @Test
    void testCreateNewOperation() throws IOException {
        Book book = new Book();
        MultipartFile file = new MockMultipartFile("coverImage", new byte[]{});
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        String viewName = bookController.createNewOperation(file, book);

        verify(bookRepository, times(1)).save(book);
        assertEquals("redirect:/", viewName);
    }

    @Test
    void testDeleteBookById() {
        String viewName = bookController.deleteBookById(1);

        verify(bookRepository, times(1)).deleteById(1);
        assertEquals("redirect:/", viewName);
    }

    @Test
    void testUpdateBook() throws IOException {
        Book book = new Book();
        book.setId(1);
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setGenre("Test Genre");
        book.setYearOfPublication(Date.valueOf("2022-01-01"));
        book.setNumberOfCopies(10);
        book.setDescription("Test Description");
        book.setImage(new byte[]{1, 2, 3}); // Встановлюємо непорожній масив для image

        MultipartFile file = new MockMultipartFile("coverImage", new byte[]{});
        when(bookRepository.findBookById(1)).thenReturn(book);
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        List<OperationsOnBooks> operations = new ArrayList<>();
        when(operationsOnBooksRepos.findOperationsOnBooksByBookId(1)).thenReturn(operations);

        String viewName = bookController.updateBook(file, book, model);

        verify(bookRepository, times(1)).save(book);
        verify(model, times(1)).addAttribute("book", book);
        verify(model, times(1)).addAttribute("operations", operations);
        assertEquals("BookPage", viewName);
    }
}
package controller;

import controller.exceptions.BookIdMismatchException;
import controller.exceptions.BookNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import persistence.model.Book;
import persistence.repository.BookRepository;

/**
 * @author cstan
 * @since 07.01.2018
 */
@RestController
@RequestMapping("/api/books")
public class BookController {

  @Autowired
  private BookRepository bookRepository;

  @GetMapping
  public Iterable<Book> findAll() {
    return bookRepository.findAll();
  }

  @GetMapping("/title/{bookTitle}")
  public List<Book> findByTitle(@PathVariable String bookTitle) {
    return bookRepository.findByTitle(bookTitle);
  }

  @GetMapping("/{id}")
  public Book findOne(@PathVariable Long id) throws BookNotFoundException {
    return Optional.ofNullable(bookRepository.findOne(id)).orElseThrow(BookNotFoundException::new);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Book create(@RequestBody Book book) {
    return bookRepository.save(book);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) throws BookNotFoundException {
    findOne(id);
    bookRepository.delete(id);
  }

  @PutMapping("/{id}")
  public Book updateBook(@RequestBody Book book, @PathVariable Long id)
      throws BookIdMismatchException, BookNotFoundException {
    if (book.getId() != id) {
      throw new BookIdMismatchException();
    }
    findOne(id);
    return bookRepository.save(book);
  }
}

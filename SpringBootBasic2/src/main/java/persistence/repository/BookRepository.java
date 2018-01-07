package persistence.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import persistence.model.Book;

/**
 * @author cstan
 * @since 07.01.2018
 */
public interface BookRepository extends CrudRepository<Book, Long> {

  List<Book> findByTitle(String title);
}

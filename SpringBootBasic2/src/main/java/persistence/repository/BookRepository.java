package persistence.repository;

import persistence.model.Book;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

/**
 * @author cstan
 * @since 07.01.2018
 */
public interface BookRepository extends CrudRepository<Book, Long> {

  List<Book> findByTitle(String title);

//  Optional<Book> findOne(long id);
}

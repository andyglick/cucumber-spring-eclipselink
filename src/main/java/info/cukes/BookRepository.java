package info.cukes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * <p>BookRepository interface.</p>
 *
 * @author glick
 */
public interface BookRepository extends JpaRepository<Book, Long>, QuerydslPredicateExecutor<Book>
{
  Book findByTitle(String title);
}

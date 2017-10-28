package info.cukes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * <p>AuthorRepository interface.</p>
 *
 * @author glick
 */
public interface AuthorRepository extends JpaRepository<Author, Long>, QuerydslPredicateExecutor<Author>
{
    Author findByAuthorName(String name);
}

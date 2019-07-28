package info.cukes;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.assertj.core.api.Assertions;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * <p>RepositoryQueriesIT test class.</p>
 *
 * @author glick
 */
@SuppressWarnings("CdiInjectionPointsInspection")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@Transactional
@EnableTransactionManagement
public class RepositoryQueriesIT
{
  private static final String T_S_Elliot = "T S Elliot";

  @Inject
  AuthorRepository authorRepository;

  @Inject
  BookRepository bookRepository;

  @Test
  public void testFindByAuthorName()
  {
    List<String> authorNames = Arrays.asList("Andy Glick", T_S_Elliot);

    saveAuthorsToPersistentStore(authorNames);

    validatePersistenceOfTheAuthors(authorNames);
  }

  public void validatePersistenceOfTheAuthors(List<String> authorNames)
  {
    for (String authorName : authorNames)
    {
      validateAuthorPersistence(authorName);
    }
  }

  public void saveAuthorsToPersistentStore(List<String> authorNames)
  {
    for (String authorName : authorNames)
    {
      saveAuthorToPersistentStore(authorName);
    }
  }

  public void saveAuthorToPersistentStore(String authorName)
  {
    Author anAuthor = new Author(authorName);

    authorRepository.save(anAuthor);
  }

  public void validateAuthorPersistence(String authorName)
  {
    Author anAuthor = authorRepository.findByAuthorName(authorName);

    Assertions.assertThat(anAuthor).isNotNull();
    Assertions.assertThat(anAuthor.getAuthorName()).isEqualTo(authorName);
  }

  @Test
  @Transactional
  public void testFindBookByTitle()
  {
    List<String> authorNames = Collections.singletonList(T_S_Elliot);

    saveAuthorsToPersistentStore(authorNames);

    validatePersistenceOfTheAuthors(authorNames);

    Author tsEliot = authorRepository.findByAuthorName(T_S_Elliot);

    List<String> bookTitles = Arrays.asList("The Wasteland and Other Poems",
      "Four Quartets");

    saveBooksToPersistentStore(bookTitles, tsEliot);

    validatePersistenceOfBooks(bookTitles, tsEliot);
  }

  public void validatePersistenceOfBooks(List<String> bookTitles, Author tsEliot)
  {
    for (String bookTitle : bookTitles)
    {
      validatePersistenceOfBook(bookTitle, tsEliot);
    }
  }

  public void saveBooksToPersistentStore(List<String> bookTitles, Author author)
  {
    for (String bookTitle : bookTitles)
    {
      saveBookToPersistentStore(bookTitle, author);
    }
  }

  public void saveBookToPersistentStore(String bookTitle, Author author)
  {
    Book aBook = new Book(bookTitle);

    aBook.addAnAuthor(author);

    bookRepository.save(aBook);
  }

  public void validatePersistenceOfBook(String title, Author author)
  {
    Book persistentBook = bookRepository.findByTitle(title);

    Assertions.assertThat(persistentBook).isNotNull();
    Assertions.assertThat(persistentBook.getTitle()).isEqualTo(title);

    Assertions.assertThat(persistentBook.getBookAuthors()).hasSize(1);

    Author persistentAuthor = persistentBook.getBookAuthors().get(0);

    Assertions.assertThat(persistentAuthor.getAuthorName()).isEqualTo(author.getAuthorName());
  }
}

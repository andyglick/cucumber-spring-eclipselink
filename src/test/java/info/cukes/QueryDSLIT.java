package info.cukes;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.assertj.core.api.Assertions;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Iterables;
import com.querydsl.codegen.ClassPathUtils;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

/**
 * <p>QueryDSLIT test class.</p>
 *
 * @author glick
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@EnableTransactionManagement
public class QueryDSLIT
{
  private static final String ANDY_GLICK = "Andy Glick";
  private static final String WALTER_WINCHELL = "Walter Wichell";
  private static final String EDWARD_R_MURROW = "Edward R Murrow";


  @BeforeClass
  public static void onlyOnce() throws IOException
  {
    ClassPathUtils.scanPackage(Thread.currentThread().getContextClassLoader(), "info.cukes");
  }

  @Inject
  AuthorDelegate authorDelegate;

  @SuppressWarnings("CdiInjectionPointsInspection")
  @Inject
  AuthorRepository authorRepository;

  @SuppressWarnings("CdiInjectionPointsInspection")
  @Inject
  BookRepository bookRepository;

  @Test
  @Transactional
  public void testQuerySome()
  {
    List<String> moreAuthorNames = Arrays.asList("Ralph Sport Sport", ANDY_GLICK, WALTER_WINCHELL,
      EDWARD_R_MURROW, "Amelia Earhart", "Bumper Crop");

    int moreAuthorNamesCount = moreAuthorNames.size();

    Author anAuthor;

    for (String authorName : moreAuthorNames)
    {
      anAuthor = new Author(authorName);

      authorRepository.save(anAuthor);
    }

    authorRepository.flush();
    bookRepository.flush();

    List<Author> allPersistentAuthors = authorRepository.findAll();

    Assertions.assertThat(allPersistentAuthors).hasSize(moreAuthorNamesCount);

    QAuthor author = QAuthor.author;

    BooleanExpression authorsToLookup = author.authorName.eq(ANDY_GLICK)
      .or(author.authorName.eq(WALTER_WINCHELL)).or(author.authorName.eq(EDWARD_R_MURROW));

    Iterable<Author> locatedAuthors = authorRepository.findAll(authorsToLookup);

    List<Author> authorList = new ArrayList<>();

    Iterables.addAll(authorList, locatedAuthors);

    List<String> authorNameList = authorDelegate.getListOfAuthorNames(authorList);

    List<String> authorNamesToFind = Arrays.asList(ANDY_GLICK, WALTER_WINCHELL, EDWARD_R_MURROW);

    Assertions.assertThat(authorNameList).hasSameSizeAs(authorNamesToFind);

    Assertions.assertThat(authorNameList).containsAll(authorNamesToFind);

    Assertions.assertThat(authorNamesToFind).containsAll(authorNameList);
  }
}

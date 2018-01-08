package main;

import static com.jayway.restassured.RestAssured.preemptive;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import persistence.model.Book;

/**
 * @author cstan
 * @since 07.01.2018
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = WebEnvironment.RANDOM_PORT)
public class LiveTest {

  @LocalServerPort
  private int port;

  private String API_ROOT;

  @Before
  public void setUp() {
    API_ROOT = "http://localhost:" + port + "/api/books";
    RestAssured.authentication = preemptive().basic("john", "123");
  }

  private Book createRandomBook() {
    Book book = new Book();
    book.setTitle(randomAlphabetic(10));
    book.setAuthor(randomAlphabetic(15));
    return book;
  }

  private String createBookAndReturnUri(Book book) {
    Response response = createBookAndReturnResponse(book);
    return API_ROOT + "/" + response.jsonPath().get("id");
  }

  private Response createBookAndReturnResponse(Book book) {
    return RestAssured.given()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(book)
        .post(API_ROOT);
  }

  @Test
  public void whenGetAllBooks_thenOK() {
    Response response = RestAssured.get(API_ROOT);

    assertThat(response.getStatusCode(), equalTo(HttpStatus.OK.value()));
  }

  @Test
  public void whenGetBooksByTitle_thenOK() {
    Book book = createRandomBook();
    createBookAndReturnUri(book);
    Response response = RestAssured.get(API_ROOT + "/title/" + book.getTitle());

    assertThat(response.getStatusCode(), equalTo(HttpStatus.OK.value()));
    assertTrue(response.as(List.class).size() > 0);
  }

  @Test
  public void whenGetCreatedBookById_thenOK() {
    Book book = createRandomBook();
    String location = createBookAndReturnUri(book);
    Response response = RestAssured.get(location);

    assertThat(response.getStatusCode(), equalTo(HttpStatus.OK.value()));
    assertThat(response.jsonPath().get("title"), equalTo(book.getTitle()));
  }

  @Test
  public void whenGetNotExistBookById_thenNotFound() {
    Response response = RestAssured.get(API_ROOT + "/" + randomNumeric(4));

    assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND.value()));
  }

  @Test
  public void whenCreateNewBook_thenCreated() {
    Book book = createRandomBook();
    Response response = createBookAndReturnResponse(book);

    assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED.value()));
  }

  @Test
  public void whenInvalidBook_thenError() {
    Book book = createRandomBook();
    book.setAuthor(null);
    Response response = createBookAndReturnResponse(book);

    assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST.value()));
  }

  @Test
  public void whenUpdateCreatedBook_thenUpdated() {
    Book book = createRandomBook();
    String location = createBookAndReturnUri(book);

    book.setId(Long.parseLong(location.split("api/books/")[1]));
    book.setAuthor("newAuthor");

    Response response = RestAssured.given()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(book)
        .put(location);

    assertThat(response.getStatusCode(), equalTo(HttpStatus.OK.value()));

    response = RestAssured.get(location);

    assertThat(response.getStatusCode(), equalTo(HttpStatus.OK.value()));
    assertThat(response.jsonPath().get("author"), equalTo("newAuthor"));
  }

  @Test
  public void whenDeleteCreatedBook_thenOk() {
    Book book = createRandomBook();
    String location = createBookAndReturnUri(book);

    Response response = RestAssured.delete(location);
    assertThat(response.getStatusCode(), equalTo(HttpStatus.OK.value()));

    response = RestAssured.get(location);
    assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND.value()));
  }

  @Test
  public void whenDeleteMissingBook_thenNotFound() {
    Response response = RestAssured.delete(API_ROOT + "/" + randomNumeric(4));
    assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND.value()));
  }
}
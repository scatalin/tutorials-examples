package hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The class is flagged as a @RestController, meaning it’s ready for use by Spring MVC to handle web requests.
 *
 * When invoked from a browser or using curl on the command line, the method returns pure text.
 * That’s because @RestController combines @Controller and @ResponseBody, two annotations that results in web requests returning data rather than a view.
 *
 * @author cstan
 * @since 07.01.2018
 */
@RestController
public class HelloController {

  /**
   * #RequestMapping maps / to the index() method.
   */
  @RequestMapping("/")
  public String index() {
    return "Greetings from Spring Boot!";
  }
}

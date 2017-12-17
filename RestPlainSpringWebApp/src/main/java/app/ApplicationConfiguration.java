package app;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * ApplicationConfiguration: this is the configuration class of the application, it is basically used
 * to instruct the servlet container to load REST resources from specific package.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "rest")
public class ApplicationConfiguration {
}
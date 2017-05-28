package ru.javabegin.library;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = {"ru.javabegin.library"})
public class ServletInitializer extends SpringBootServletInitializer {

}

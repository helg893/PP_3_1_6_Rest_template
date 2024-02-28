package ru.kata.katapp316;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.kata.katapp316.configuration.MyConfig;
import ru.kata.katapp316.model.User;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);

        Communication communication = context.getBean(Communication.class);
        List<User> allUsers = communication.getAllUsers();
        System.out.println(allUsers);

        System.out.println(communication.getCookieSessionId());


    }
}

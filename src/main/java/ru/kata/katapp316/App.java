package ru.kata.katapp316;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.kata.katapp316.configuration.MyConfig;
import ru.kata.katapp316.model.User;

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

        User user = new User();
        user.setId(3L);
        user.setName("James");
        user.setLastName("Brown");
        user.setAge((byte)42);

        communication.getAllUsers().forEach(System.out::println);
        String partOne = communication.createUser(user);
        user.setName("Thomas");
        user.setLastName("Shelby");
        String partTwo = communication.updateUser(user);
        String partThree = communication.deleteUser(3L);

        System.out.printf("partOne='%s'\n", partOne);
        System.out.printf("partTwo='%s'\n", partTwo);
        System.out.printf("partThree='%s'\n", partThree);
        System.out.printf("in full='%s'\n", partOne + partTwo + partThree);

    }
}

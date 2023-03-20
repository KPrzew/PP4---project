package kPrzewoźnik;

import org.junit.jupiter.api.Test;

public class FirstTest {

    @Test
    void myFirstTest(){
        assert  true == true;
    }
    @Test
    void mySecondTest(){
        String name = "jakub";
        String hello = String.format("Hello %s", name);
        assert hello.equals("Hello jakub");
    }

}

package pl.przewoznikkarol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello Szymon");

        List<String> names = Arrays.asList("Jakub", "Michał", "Agnieszka", "Kasia");
        Greeter greeter = new Greeter();
        String[] namesAsArray = {"Jakub"};

        ArrayList<String> ladies = new ArrayList<>();

        names.stream()
                .filter(name -> name.endsWith("a"))
                .filter(name -> name.startsWith("A"))
                .map(String::toUpperCase)
                .forEach(greeter::greet);
    }
}

package kPrzewoźnik;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Karol","Krzyś","Popo","Ana","Magda");

        Greeter greeter = new Greeter();

        List<String> ladies = new ArrayList<String>();
        for(String name: names){
                if(name.endsWith("a")){
                    ladies.add(name);
            }
        }
        for (String ladyName: ladies){
            greeter.greet(ladyName);
        }
        names.stream()
                .filter(name -> name.endsWith("a")) //Lambda name: name[-1] == "a"
                .filter(name -> name.startsWith("A"))
                .map(String::toUpperCase)
                .forEach(greeter::greet);
    }
}

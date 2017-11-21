package core.PlagiarismDetection;

import core.PlagiarizerFactory.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

// dummy to test; no where used! Will be removed on final submission
public class Main {
    public static void main(String... args) throws IOException {
        Factory factory = new Factory();



        ObjectMapper om = new ObjectMapper();

        String s = om.writeValueAsString("{");
        System.out.println(s);



    }
}

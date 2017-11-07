package PlagiarismDetection;

import java.io.IOException;
import java.util.Iterator;

public class Main {
    public static void main(String... args) throws IOException{



        Assignment a = Assignment.createAssignment();
        Iterator<Submission> s = a.getSubmissions().iterator();
        while(s.hasNext())
            System.out.println(s.next().getStudentName());


    }
}

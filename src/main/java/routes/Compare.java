package routes;

import core.algorithm.ast.NGramComparison;
import core.configuration.ApplicationConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import routes.model.CompareStudents;
import routes.model.Students;

import java.io.IOException;

@RestController
@CrossOrigin(origins = {
        ApplicationConfig.local,
        ApplicationConfig.herokuNoHttps,
        ApplicationConfig.heroku})
public class Compare {

    @RequestMapping(value = "/compare", method = RequestMethod.POST)
    public ResponseEntity<?> assignments(@RequestBody CompareStudents students) throws IOException {
        // students contains the two students selected for comparing and their corresponding files
        Students[] selectedStudents = students.getStudents();

        // dummy , only for testing
        for(int i = 0; i < selectedStudents.length; i++) {
            System.out.println(selectedStudents[i].toString());

        }

        if(selectedStudents.length < 2) {
            System.out.println("In here!");
            return ApplicationConfig.ErrorResponse();
        } else {
            // Code for comparison goes here! For now we are comparing only 2 students
            double ASTResult;
            double LCSResult = 100.0;

            // AST Based comparison
            try {
                ASTResult = ASTComparison(selectedStudents[0].getFilePaths(), selectedStudents[1].getFilePaths());
                System.out.println("Astresult: " + ASTResult);
            } catch (Exception e) {
                System.out.println("Exception!!!");
                return ApplicationConfig.ErrorResponse();
            }

            String responseJSON = "{\"success\": true, \"astResult\": "+ ASTResult +", \"lcsResult\": "+ LCSResult +"}";
            try {
                Thread.sleep(5000);
                return ResponseEntity.ok(responseJSON);
            } catch(Exception e) {
                System.out.println("Exception! in thread!");
                e.printStackTrace();
                return ApplicationConfig.ErrorResponse();
            }

        }


    }


    private double ASTComparison(String[] student1Files,String[] student2Files) throws Exception{
        NGramComparison astComparison = new NGramComparison();
        astComparison.nGramComparison(student1Files,student2Files);
        double result = astComparison.getResult();
        System.out.println("Result: " + result);
        return result;
    }
}
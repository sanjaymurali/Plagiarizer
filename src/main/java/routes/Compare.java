package routes;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.algorithm.ast.NGramComparison;
import core.algorithm.lcs.CodeComparisonScores;
import core.configuration.ApplicationConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import routes.model.CompareStudents;
import routes.model.Students;

@RestController
@CrossOrigin(origins = {
        ApplicationConfig.testFrontEnd,
        ApplicationConfig.local,
        ApplicationConfig.herokuNoHttps,
        ApplicationConfig.heroku})
public class Compare {

    ObjectMapper om = new ObjectMapper();

    @RequestMapping(value = "/compare", method = RequestMethod.POST)
    public ResponseEntity<?> assignments(@RequestBody CompareStudents students){
        System.out.println("Its hit!");
        // students contains the two students selected for comparing and their corresponding files
        Students[] selectedStudents = students.getStudents();

        if(selectedStudents == null) {
            return ApplicationConfig.ErrorResponse();
        } else {
            if(selectedStudents.length == 2) { // Allow exactly only two students to be compared

                String[] project1 = selectedStudents[0].getFilePaths();
                String[] project2 = selectedStudents[1].getFilePaths();

                try {
                    double[] results = this.AllComparisons(project1, project2);
                    String resultString = om.writeValueAsString(results);
                    String responseJSON = "{\"success\": true, \"result\": \"" + resultString + "\"}";

                    return ResponseEntity.ok(responseJSON);
                } catch(Exception e) {
                    e.printStackTrace();
                    return ApplicationConfig.ErrorResponse();
                }

            } else {
                return ApplicationConfig.ErrorResponse();
            }
        }
    }

    private double[] AllComparisons(String[] project1, String[] project2) throws Exception {

        double[] arrayOfResults = new double[4];

        // AST Comparison
        NGramComparison astComparison = new NGramComparison();
        astComparison.nGramComparison(project1,project2);
        // LCS and LD Comparison
        CodeComparisonScores textDiffScores = new CodeComparisonScores(project1, project2);

        // Retrieve scores via getters and store them in array for easier output use as a JSON object.
        arrayOfResults[0] = astComparison.getResult()*100;
        arrayOfResults[1] = textDiffScores.getScoreForLCS();
        arrayOfResults[2] = textDiffScores.getScoreForLD();

        double textDiffAvg = textDiffScores.getOverallTextDiffScore();

        arrayOfResults[3] = (textDiffAvg + arrayOfResults[0])/2;


        return arrayOfResults;
    }
}

package routes;

import core.configuration.ApplicationConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import routes.model.CompareStudents;
import routes.model.Students;

@RestController
@CrossOrigin(origins = {
        ApplicationConfig.local,
        ApplicationConfig.herokuNoHttps,
        ApplicationConfig.heroku})
public class Compare {

    @RequestMapping(value = "/compare", method = RequestMethod.POST)
    public ResponseEntity<?> assignments(@RequestBody CompareStudents students){
        // students contains the two students selected for comparing and their corresponding files
        Students[] selectedStudents = students.getStudents();

        // selectedStudents contains filePaths in it!

        // Write code for Comparison here!


        // dummy , only for testing
        for(int i = 0; i < selectedStudents.length; i++) {
           System.out.println(selectedStudents[i].toString());

        }



        String responseJSON = "{\"success\": true, \"message\": \"Hello!\"}";
        try {
            Thread.sleep(5000);
            return ResponseEntity.ok(responseJSON);
        } catch(Exception e) {
            System.out.println("Exception!");
            e.printStackTrace();
            return ApplicationConfig.ErrorResponse();
        }





    }
}

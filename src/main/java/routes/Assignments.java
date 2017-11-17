package routes;

import IO.Writer;
import PlagiarismDetection.Assignment;
import PlagiarizerFactory.Factory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import configuration.ApplicationConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Assignment class holds the routes for "assignment" URL.
 */
@RestController
@CrossOrigin(origins = {
        ApplicationConfig.local,
        ApplicationConfig.herokuNoHttps,
        ApplicationConfig.heroku})
public class Assignments {
    Factory factory = new Factory();
    Assignment a = factory.createAssignment();
    Writer writer = factory.Writer();

    ObjectMapper om = new ObjectMapper();

    // to show all submissions
    @RequestMapping("assignment")
    public ResponseEntity<?> assignments() throws JsonProcessingException{
        String s = "";
        try {
            s = om.writeValueAsString(a.getSubmissions());
        }
        catch(Exception e) {
            return ApplicationConfig.ErrorResponse();
        }

        return ResponseEntity.ok(s);
    }

    // to show a particular student's submission
    @RequestMapping("assignment/{id}")
    public ResponseEntity<?> findSubmission(@PathVariable("id") String id) {
        String foundSubmission = "";
        try {
            int studentID = Integer.parseInt(id);
            foundSubmission = om.writeValueAsString(a.findSubmission(studentID));
        }
        catch(Exception e) {
            return ApplicationConfig.ErrorResponse();
        }

        return ResponseEntity.ok(foundSubmission);
    }


    // to remove all uploads
    @RequestMapping("cleanse")
    public String internalCleanse() {
        a.deleteSubmissions();
        writer.deleteAllUploads();
        return "Done!";
    }

}

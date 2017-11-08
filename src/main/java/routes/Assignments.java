package routes;

import PlagiarizerFactory.Factory;
import PlagiarismDetection.Assignment;
import PlagiarismDetection.Submission;
import configuration.ApplicationConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;


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

    // to show all submissions
    @RequestMapping("assignment")
    public String assignments() {
        Iterator<Submission> s = a.getSubmissions().iterator();
        String s1 = "";
        while (s.hasNext()) {
            Submission cu = s.next();
            s1 += cu.toString();
        }

        return s1;
    }

    // to show a particular student's submission
    @RequestMapping("assignment/{id}")
    public ResponseEntity<?> findSubmission(@PathVariable("id") String id) {
        int studentID = Integer.parseInt(id);

        Submission foundSubmission = a.findSubmission(studentID);

        if (foundSubmission == null) {
            String notFound = "{\"success\": false, \"message\": \"Submission not found\"}";
            return ResponseEntity.status(404).body(notFound);
        }

        String submissionAsString = foundSubmission.toString();
        return ResponseEntity.ok(submissionAsString);
    }


}

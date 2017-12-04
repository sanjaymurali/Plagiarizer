package routes;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.IO.Writer;
import core.PlagiarismDetection.Assignment;
import core.PlagiarizerFactory.Factory;
import core.configuration.ApplicationConfig;
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
        ApplicationConfig.testFrontEnd,
        ApplicationConfig.local,
        ApplicationConfig.herokuNoHttps,
        ApplicationConfig.heroku})
public class Assignments {
    Factory factory = new Factory();
    Assignment a = factory.createAssignment();
    Writer writer = factory.Writer();
    ObjectMapper om = new ObjectMapper(); // ObjectMapper is used to write a value as String

    /*
        ObjectMapper Works as follows: Lets assume the class A, with members "int age" and "String name", it would
        convert it into String as follows: {'age': <Your-age>, 'name': '<Your-name'>}
     */

    /**
     * This is the url : "/assignment"
     *
     * @return error if there was error processing request else return all the submissions
     */
    @RequestMapping("assignment")
    public ResponseEntity<?> assignments() {
        String s = "";

        try {
            s = om.writeValueAsString(a.getSubmissions());
        } catch (Exception e) {
            return ApplicationConfig.ErrorResponse();
        }

        return ResponseEntity.ok(s);
    }

    /**
     * This is the url : "/assignment/<id>"
     *
     * @param id is the StudentID whose Submission we need to retrieve
     * @return Submission as String.
     */
    @RequestMapping("assignment/{id}")
    public ResponseEntity<?> findSubmission(@PathVariable("id") String id) {
        String foundSubmission = "";
        try {
            int studentID = Integer.parseInt(id);
            foundSubmission = om.writeValueAsString(a.findSubmission(studentID));
            if (foundSubmission.equals("null"))
                return ApplicationConfig.ErrorResponse();
        } catch (Exception e) {
            return ApplicationConfig.ErrorResponse();
        }

        return ResponseEntity.ok(foundSubmission);
    }


    /**
     * This is for internal use only and its no where used in the frontend. To remove all uploads
     *
     * @return a String to show completion of operation
     */
    @RequestMapping("cleanse")
    public String internalCleanse() {
        a.deleteSubmissions();
        writer.deleteAllUploads();
        return "Done!";
    }

}

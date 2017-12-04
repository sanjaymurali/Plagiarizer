package routes;

import core.IO.Reader;
import core.PlagiarismDetection.Assignment;
import core.PlagiarismDetection.Submission;
import core.PlagiarizerFactory.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.configuration.ApplicationConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = {
        ApplicationConfig.testFrontEnd,
        ApplicationConfig.local,
        ApplicationConfig.herokuNoHttps,
        ApplicationConfig.heroku})
public class Submissions {
    Factory factory = new Factory();
    Assignment a = factory.createAssignment();
    Reader reader = factory.Reader();
    ObjectMapper om = new ObjectMapper();

    /**
     * @param id   is the unique ID given to each Student
     * @param name is the name of the file
     * @return error if there is no file with the given name, else return the contents of the file
     */

    @RequestMapping("submission")
    public ResponseEntity<?> findSubmission(@RequestParam(value = "studentID") String id,
                                            @RequestParam(value = "fileName") String name) {
        Submission submission; // to temporarily hold a submission
        String program;

        try {
            int studentID = Integer.parseInt(id);
            submission = (a.findSubmission(studentID));

            // if no submission is present, return error
            if (submission == null) {
                return ApplicationConfig.ErrorResponse();
            } else {
                // if no file is present, return error
                String absolutePathtoFile = submission.getAbsolutePath(name);
                if (absolutePathtoFile == null)
                    return ApplicationConfig.ErrorResponse();
                else
                    program = om.writeValueAsString(reader.getFile(absolutePathtoFile));
            }
        } catch (Exception e) {
            return ApplicationConfig.ErrorResponse();
        }

        return ResponseEntity.ok(program);
    }

}

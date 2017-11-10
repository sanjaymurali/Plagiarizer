package routes;

import IO.Reader;
import PlagiarismDetection.Assignment;
import PlagiarismDetection.Submission;
import PlagiarizerFactory.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;
import configuration.ApplicationConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = {
        ApplicationConfig.local,
        ApplicationConfig.herokuNoHttps,
        ApplicationConfig.heroku})
public class Submissions {
    Factory factory = new Factory();
    Assignment a = factory.createAssignment();
    Reader reader = factory.Reader();
    ObjectMapper om = new ObjectMapper();


    @RequestMapping("submission")
    public ResponseEntity<?> findSubmission(
            @RequestParam(value="studentID") String id,
            @RequestParam(value="fileName") String name) {
        Submission submission;
        String program;

        try {
            int studentID = Integer.parseInt(id);
            submission = (a.findSubmission(studentID));

            if(submission == null) {
                return ApplicationConfig.ErrorResponse();
            } else {
                String absolutePathtoFile = submission.getAbsolutePath(name);
                if(absolutePathtoFile == null)
                    return ApplicationConfig.ErrorResponse();
                else
                    program = om.writeValueAsString(reader.getFile(absolutePathtoFile));
            }

        } catch(Exception e) {
            return ApplicationConfig.ErrorResponse();
        }

        return ResponseEntity.ok(program);
    }

}

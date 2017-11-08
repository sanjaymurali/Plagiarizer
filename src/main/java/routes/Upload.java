package routes;

import PlagiarismDetection.Assignment;
import PlagiarizerFactory.Factory;
import IO.*;
import PlagiarismDetection.Submission;
import configuration.ApplicationConfig;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Iterator;
import java.util.List;

/**
 * Upload class has the routes for "upload" URL. We use CrossOrigin annotation to make sure that CORS is enabled for the
 * resources
 */
@RestController
@CrossOrigin(origins = {
        ApplicationConfig.local,
        ApplicationConfig.herokuNoHttps,
        ApplicationConfig.heroku})
public class Upload {

    Factory factory = new Factory();
    Assignment a = factory.createAssignment();
    Writer writer = factory.Writer();

    int currentStudentID = 0;

    // Uploading Files
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity<?> uploadSubmission(@RequestParam("files") List<MultipartFile> files, @RequestParam("name") String name) {

        Iterator<MultipartFile> filesIterator = files.iterator();

        // if no files are uploaded, send back an error
        if (files.size() == 0) {
            String errorJSON = "{\"success\": false, \"message\": \"Please choose File(s) for uploading!\"}";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorJSON);
        } else {
            Submission submission = factory.createSubmission(currentStudentID++, name); // Every Upload is a new submission
            String[] paths = new String[files.size()]; // absolute paths to the files
            int i = 0;
            // Iterate over the uploaded files
            while (filesIterator.hasNext()) {
                try {
                    MultipartFile currentFile = filesIterator.next();
                    // File Content and File name
                    byte[] x = currentFile.getBytes();
                    String fileName = currentFile.getOriginalFilename();
                    // Absolute path in disk to uploaded file
                    paths[i] = submission.storeSubmission(writer, fileName, x);
                    i++;
                } catch (Exception e) {
                    e.printStackTrace();
                    String errorJSON = "{\"success\": false, \"message\": \"An Error occured while uploading!\"}";
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorJSON);
                }
            }

            submission.setFilePaths(paths); // this sets all uploaded files to a particular student

            // Push the submission into the assignment
            a.pushSubmissions(submission);

            String successJSON = "{\"success\": true, \"message\": \"File was successfully uploaded!\"}";
            return ResponseEntity.status(HttpStatus.OK).body(successJSON);
        }
    }

    // to remove all uploads
    @RequestMapping("cleanse")
    public String internalCleanse() {
        writer.deleteAllUploads();
        return "Done!";
    }
}
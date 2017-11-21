package routes;

import core.PlagiarismDetection.Assignment;
import core.PlagiarizerFactory.Factory;
import core.IO.*;
import core.PlagiarismDetection.Submission;
import core.configuration.ApplicationConfig;

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

    /*
        Each Upload (set of files) is considered as student submission, hence each upload is assigned an unique ID
     */

    Factory factory = new Factory();
    Assignment a = factory.createAssignment();
    Writer writer = factory.Writer();

    int currentStudentID = 0; // This is to generate unique ID for each student

    /**
     *
     * @param files is the files (Java Programs)
     * @param name is the name of the student
     * @return error if there was an error writing files on to the disk, else return status of 200 with a message
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity<?> uploadSubmission(@RequestParam("files") List<MultipartFile> files, @RequestParam("name") String name) {
        Iterator<MultipartFile> filesIterator = files.iterator();
        // if no files are uploaded, send back an error
        if (files.size() == 0) {
            String errorJSON = "{\"success\": false, \"message\": \"Please choose File(s) for uploading!\"}";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorJSON);
        } else if(name.isEmpty()) {
            String errorJSON = "{\"success\": false, \"message\": \"No Student name provided!\"}";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorJSON);
        } else{
            Submission submission = factory.createSubmission(currentStudentID++, name); // Every Upload is a new submission
            String[] paths = new String[files.size()]; // absolute paths to the files
            String[] fileNames = new String[files.size()]; // file names
            int i = 0;
            // Iterate over the uploaded files

            while (filesIterator.hasNext()) {
                try {
                    MultipartFile currentFile = filesIterator.next();
                    // File Content and File name
                    byte[] x = currentFile.getBytes();
                    String fileName = currentFile.getOriginalFilename();
                    fileNames[i] = fileName;
                    // Absolute path in disk to uploaded file
                    paths[i] = submission.storeSubmission(writer, fileName, x);
                    i++;
                } catch (Exception e) {
                    return ApplicationConfig.ErrorResponse();
                }
            }


            submission.setFilePaths(paths); // this sets absolute path of all uploaded files to a particular student
            submission.setFileNames(fileNames); // this sets files names of all uploaded files to a particular student
            System.out.println(submission.toString());
            // Push the submission into the assignment
            a.pushSubmissions(submission);

            String successJSON = "{\"success\": true, \"message\": \"File was successfully uploaded!\"}";
            return ResponseEntity.status(HttpStatus.OK).body(successJSON);
        }
    }
}
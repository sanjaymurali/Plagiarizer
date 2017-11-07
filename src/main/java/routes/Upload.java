package routes;

import PlagiarismDetection.Assignment;
import PlagiarismDetection.Submission;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Iterator;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200","http://plagiarizer.herokuapp.com", "https://plagiarizer.herokuapp.com"})
@RestController
public class Upload {
    Assignment a = Assignment.createAssignment();
    int currentStudentID = 0;

    @RequestMapping("/upload")
    public String hello() {
        return "Hello World!";
    }

    @RequestMapping(value = "/upload", method= RequestMethod.POST)
    public ResponseEntity<?> uploadSumission(@RequestParam("files") List<MultipartFile> files, @RequestParam("name") String name) {
        Submission submission = new Submission(); // Every Upload is a new submission

        Iterator<MultipartFile> filesIterator = files.iterator();

        // if no files are uploaded, send back an error
        if(files.size() == 0) {
            String errorJSON = "{\"success\": false, \"message\": \"Please choose File(s) for uploading!\"}";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorJSON);
        } else {
            submission.setStudentID(currentStudentID++); // the current student id for uploaded files

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
                    paths[i] = submission.storeFileOnDisk(fileName, x);
                    i++;
                } catch (Exception e) {
                    e.printStackTrace();
                    String errorJSON = "{\"success\": false, \"message\": \"An Error occured while uploading!\"}";
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorJSON);
                }
            }
            // set Submission
            submission.storeSubmission(name, paths);
            // Push the submission into the assignment
            a.pushSubmissions(submission);

            String successJSON = "{\"success\": true, \"message\": \"File was successfully uploaded!\"}";
            return ResponseEntity.status(HttpStatus.OK).body(successJSON);
        }
    }

    @RequestMapping("cleanse")
    public String internalCleanse() {
        Submission s = new Submission();
        s.deleteAllUploads();
        return "Done!";
    }

    @RequestMapping("assignment")
    public String assignment() {
        Assignment a = Assignment.createAssignment();
        Iterator<Submission> s = a.getSubmissions().iterator();
        String s1 = "";
        while(s.hasNext()) {
            Submission cu = s.next();
            s1 += cu.toString();
        }

        return s1;
    }
}
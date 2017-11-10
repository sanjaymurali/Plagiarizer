package configuration;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Contains all the configuration for the application.
 */
public class ApplicationConfig {

    public static final String local = "http://localhost:4200";
    public static final String herokuNoHttps = "http://plagiarizer.herokuapp.com";
    public static final String heroku = "https://plagiarizer.herokuapp.com";

    public static final String pathToUploadFolder = "src/main/resources/uploads/";

    public static final ResponseEntity<?> ErrorResponse() {
        String errorJSON = "{\"success\": false, \"message\": \"An Error occured!\"}";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorJSON);
    }

}

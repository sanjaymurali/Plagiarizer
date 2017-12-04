package core.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.File;

/**
 * Contains all the core.configuration for the application.
 */
public class ApplicationConfig {

    // URLs for the Front-end.
    public static final String local = "http://localhost:4200"; //Local URL
    public static final String herokuNoHttps = "http://plagiarizer.herokuapp.com"; // URL in heroku
    public static final String heroku = "https://plagiarizer.herokuapp.com"; // URL in heroku with https
    public static final String testFrontEnd = "http://localhost:49152";

    public static final String pathToUploadFolder = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "uploads" + File.separator; // Path where the Java files get uploaded

    /**
     * This method is used when an error has occured in a route
     *
     * @return a ResponseEntity which sets a status of 400 (Bad Request) and sends an JSON with error message
     */
    public static final ResponseEntity<?> ErrorResponse() {
        String errorJSON = "{\"success\": false, \"message\": \"An Error occured!\"}";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorJSON);
    }

}

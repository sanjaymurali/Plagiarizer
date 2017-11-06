package routes;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Iterator;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200", "http://plagiarizer.herokuapp.com"})
@RestController
public class Upload {

    @RequestMapping("/upload")
    public String hello() {
        return "Hello World!";
    }

    @RequestMapping(value = "/upload", method= RequestMethod.POST)
    public ResponseEntity<?> hey(@RequestParam("files") List<MultipartFile> files, @RequestParam("name") String name) {
        System.out.println("name: "+ name);
        Iterator<MultipartFile> filesIterator = files.iterator();

        while(filesIterator.hasNext()) {
            try {
                byte[] x = filesIterator.next().getBytes();
                String s = new String(x);
                System.out.println(s);
            }
            catch(Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{}");
            }
        }


        return ResponseEntity.status(HttpStatus.OK).body("{}");
    }
}

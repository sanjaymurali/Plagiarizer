package PlagiarismDetector;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import routes.Compare;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(Compare.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CompareRouteTests {

    @InjectMocks
    Compare compare;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(compare).build();
    }

    @Test
    public void test0NoBody() throws Exception {
        String requestBody = "{\"students\":[] }";

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/compare")
                .contentType("application/json").content(requestBody);



        String responseJSON = "{\"success\": false, \"message\": \"An Error occured!\"}";

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(content().string(responseJSON));
    }

    @Test
    public void test1BadBody() throws Exception {
        String requestBody = "{\"studentss\":[] }"; //bad request

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/compare")
                .contentType("application/json").content(requestBody);

        String responseJSON = "{\"success\": false, \"message\": \"An Error occured!\"}";

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(content().string(responseJSON));
    }

    @Test
    public void test2OneStudent() throws Exception {

        String requestBody = "{\"students\":[\n" +
                "\t{\"studentID\":1, \"studentName\": \"Suvi\", \"fileNames\":[\"try.java\", \"try copy.java\"],\n" +
                "\t\"filePaths\":[\n" +
                "\t\"/Users/sanjaymurali/Desktop/PlagiarismDetector/src/main/resources/uploads/0/0-try.java\",\n" +
                "\t\"/Users/sanjaymurali/Desktop/PlagiarismDetector/src/main/resources/uploads/0/0-try copy.java\"\n" +
                "\t]}]\n" +
                "}";

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/compare")
                .contentType("application/json").content(requestBody);

        String responseJSON = "{\"success\": false, \"message\": \"An Error occured!\"}";

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(content().string(responseJSON));
    }

    @Test
    public void test3ThreeStudents() throws Exception {
        String requestBody = "{\"students\":[\n" +
                "\t{\"studentID\":1, \"studentName\": \"Suvi\", \"fileNames\":[\"try.java\", \"try copy.java\"],\n" +
                "\t\"filePaths\":[\n" +
                "\t\"/Users/sanjaymurali/Desktop/PlagiarismDetector/src/main/resources/uploads/0/0-try.java\",\n" +
                "\t\"/Users/sanjaymurali/Desktop/PlagiarismDetector/src/main/resources/uploads/0/0-try copy.java\"\n" +
                "\t]},\n" +
                "\t{\"studentID\":0, \"studentName\": \"Sanjay\", \"fileNames\":[\"try.java\", \"try copy.java\"], \n" +
                "\t\"filePaths\":[\n" +
                "\t\"/Users/sanjaymurali/Desktop/PlagiarismDetector/src/main/resources/uploads/1/1-try.java\",\n" +
                "\t\"/Users/sanjaymurali/Desktop/PlagiarismDetector/src/main/resources/uploads/1/1-try copy.java\"\n" +
                "\t]},\n" +
                "\t{\"studentID\":2, \"studentName\": \"Sanjay1\", \"fileNames\":[\"try.java\", \"try copy.java\"], \n" +
                "\t\"filePaths\":[\n" +
                "\t\"/Users/sanjaymurali/Desktop/PlagiarismDetector/src/main/resources/uploads/1/1-try.java\",\n" +
                "\t\"/Users/sanjaymurali/Desktop/PlagiarismDetector/src/main/resources/uploads/1/1-try copy.java\"\n" +
                "\t]}\n" +
                "\t]\n" +
                "}";

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/compare")
                .contentType("application/json").content(requestBody);

        String responseJSON = "{\"success\": false, \"message\": \"An Error occured!\"}";

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(content().string(responseJSON));

    }

    @Test
    public void test4TwoStudentsNonReadableFiles() throws Exception {
        String requestBody = "{\"students\":[\n" +
                "\t{\"studentID\":1, \"studentName\": \"Suvi\", \"fileNames\":[\"try.java\", \"try copy.java\"],\n" +
                "\t\"filePaths\":[\n" +
                "\t\"try.java\",\n" +
                "\t\"/Users/sanjaymurali/Desktop/PlagiarismDetector/src/main/resources/uploads/0/0-try copy.java\"\n" +
                "\t]},\n" +
                "\t{\"studentID\":0, \"studentName\": \"Sanjay\", \"fileNames\":[\"try.java\", \"try copy.java\"], \n" +
                "\t\"filePaths\":[\n" +
                "\t\"/Users/sanjaymurali/Desktop/PlagiarismDetector/src/main/resources/uploads/1/1-try.java\",\n" +
                "\t\"/Users/sanjaymurali/Desktop/PlagiarismDetector/src/main/resources/uploads/1/1-try copy.java\"\n" +
                "\t]}\n" +
                "\t]\n" +
                "}";

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/compare")
                .contentType("application/json").content(requestBody);

        String responseJSON = "{\"success\": false, \"message\": \"An Error occured!\"}";

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(content().string(responseJSON));

    }

    @Test
    public void test5TwoStudentSameFiles() throws Exception {
        String requestBody = "{\"students\":[\n" +
                "\t{\"studentID\":1, \"studentName\": \"Suvi\", \"fileNames\":[\"Rectangle.java\"],\n" +
                "\t\"filePaths\":[\n" +
                "\t\"src/main/resources/tests/Rectangle.java\"\n" +
                "\t]},\n" +
                "\t{\"studentID\":0, \"studentName\": \"Sanjay\", \"fileNames\":[\"Rectangle.java\"], \n" +
                "\t\"filePaths\":[\n" +
                "\t\"src/main/resources/tests/Rectangle.java\"]}\n" +
                "\t]\n" +
                "}";

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/compare")
                .contentType("application/json").content(requestBody);

        String responseJSON = "{\"success\": true, \"result\": \"[100.0,100.0,100.0,100.0]\"}"; //perfect match!

        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(responseJSON));
    }

    @Test
    public void test6TwoStudentDifferentFiles() throws Exception {
        String requestBody = "{\"students\":[\n" +
                "\t{\"studentID\":1, \"studentName\": \"Suvi\", \"fileNames\":[\"Rectangle.java\"],\n" +
                "\t\"filePaths\":[\n" +
                "\t\"src/main/resources/tests/Rectangle.java\"\n" +
                "\t]},\n" +
                "\t{\"studentID\":0, \"studentName\": \"Sanjay\", \"fileNames\":[\"test.java\"], \n" +
                "\t\"filePaths\":[\n" +
                "\t\"src/main/resources/tests/test.java\"]}\n" +
                "\t]\n" +
                "}";

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/compare")
                .contentType("application/json").content(requestBody);

        String responseJSON = "{\"success\": true, \"result\": \"[0.0,4.735376044568245,19.220055710306408,5.988857938718663]\"}"; //perfect match!

        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(responseJSON));
    }

}

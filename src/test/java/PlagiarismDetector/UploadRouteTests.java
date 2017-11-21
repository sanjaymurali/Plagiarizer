package PlagiarismDetector;

import core.PlagiarismDetection.Submission;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import routes.Upload;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(Upload.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UploadRouteTests {

    @InjectMocks
    Upload upload;

    @Mock
    Submission submission;

    private MockMvc mockMvc;

    MockMultipartFile firstFile, secondFile;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(upload).build();

        firstFile = new MockMultipartFile("files", "1.java", "text/plain", "1st Program here!".getBytes());
        secondFile = new MockMultipartFile("files", "2.java", "text/plain", "2nd Program here!".getBytes());
    }


    // Test two files being uploaded with a student name
    @Test
    public void test1InitialAssignment() throws Exception {

        when(submission.storeSubmission(any(), eq("1.java"), any())).thenReturn("1.java");
        when(submission.storeSubmission(any(), eq("2.java"), any())).thenReturn("2.java");

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.multipart("/upload")
                                                                .file(firstFile)
                                                                .file(secondFile)
                                                                .param("name", "sanjay");

        String responseJSON = "{\"success\": true, \"message\": \"File was successfully uploaded!\"}";

        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(responseJSON));

    }

    // test no files with a student name
    @Test
    public void test2Nofiles() throws Exception {

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.multipart("/upload")
                .param("files", "")
                .param("name", "sanjay");

        String responseJSON = "{\"success\": false, \"message\": \"Please choose File(s) for uploading!\"}";

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(content().string(responseJSON));
    }

    // test with files present with no student name
    @Test
    public void test3NoName() throws Exception {

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.multipart("/upload")
                .file(firstFile)
                .param("name", "");

        String responseJSON = "{\"success\": false, \"message\": \"No Student name provided!\"}";

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(content().string(responseJSON));
    }

    // test with none of the parameters present
    @Test
    public void test4NoParams() throws Exception {

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.multipart("/upload")
                .param("files", "")
                .param("name", "");

        String responseJSON = "{\"success\": false, \"message\": \"Please choose File(s) for uploading!\"}";

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(content().string(responseJSON));
    }
}

package ssafy.narou.pjt;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(FileUploadController.class)
public class FileUploadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AmazonS3Client amazonS3Client;

    @Test
    @WithMockUser
    public void uploadFile_ShouldReturnFileUrl_WhenUploadedSuccessfully() throws Exception {
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "testfile.txt",
                "text/plain",
                "Test content".getBytes());

        // Configure the mock to do nothing when putObject is called
        // doNothing().when(amazonS3Client).putObject(any(PutObjectRequest.class));

        PutObjectResult mockPutObjectResult = new PutObjectResult();
        when(amazonS3Client.putObject(any(PutObjectRequest.class))).thenReturn(mockPutObjectResult);

        // Assuming baseUrl is configured correctly in the application.properties for the test context
        String expectedFileUrl = "https://naroubucket.s3.ap-northeast-2.amazonaws.com/testfile.txt";

        mockMvc.perform(multipart("/api/picture/upload").file(mockFile)
                        // .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(content().string(expectedFileUrl));
    }
}


//package ssafy.narou.pjt;
//
//import com.amazonaws.services.s3.AmazonS3Client;
//import com.amazonaws.services.s3.model.PutObjectRequest;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
//import org.springframework.data.jpa.util.JpaMetamodel;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.doNothing;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@MockBean(JpaMetamodelMappingContext.class)
//@WebMvcTest(FileUploadController.class)
//public class FileUploadControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private AmazonS3Client amazonS3Client;
//
//    @Test
//    public void uploadFileTest() throws Exception{
//        MockMultipartFile file = new MockMultipartFile(
//                "file",
//                "filename.txt",
//                "text/plain",
//                "some xml".getBytes()
//        );
//
//        doNothing().when(amazonS3Client).putObject(any(PutObjectRequest.class));
//
//        String expectedUrl = "https://naroubucket.s3.ap-northeast-2.amazonaws.com/filename.txt";
//
//        mockMvc.perform(multipart("api/picture/upload").file(file))
//                .andExpect(status().isOk())
//                .andExpect(content().string(expectedUrl));
//    }
//}

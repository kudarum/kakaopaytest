package com.kakaopay.housingfinance.util;

import com.kakaopay.housingfinance.cmm.BaseTest;
import com.kakaopay.housingfinance.cmm.TestDescription;
import com.kakaopay.housingfinance.model.Account;
import com.kakaopay.housingfinance.repository.AccountRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.io.FileInputStream;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class FileUtilTest extends BaseTest {

    private String testFileFath;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private String token;


    @Before
    public void setUp() {
        testFileFath = "src/test/resources/data.csv";

        //token 발급.
        Account admin = accountRepository.findByUsername("admin");
        token = "Bearer " +jwtUtil.createJwt(admin.getUsername(),admin.getRoles());
    }


    @Test
    @TestDescription("파일 업로드 정상 처리")
    public void test_FileUtil_Success() throws Exception{

        File testFile = new File(testFileFath);
        MockMultipartFile file = new MockMultipartFile(
                "file", testFile.getName(), MediaType.MULTIPART_FORM_DATA_VALUE, new FileInputStream(testFile));


        mockMvc.perform(MockMvcRequestBuilders.fileUpload("/files/fundCsvRead").file(file)
                .header("Authorization",token)
                    .contentType(MediaType.MULTIPART_FORM_DATA) // 요청에 JSON 담아 보낼께
                    .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk()) // http status 200
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_UTF8_VALUE)); // content type은 JSON 형태
    }

    @Test
    @TestDescription("파일 없이 업로드한 경우 Not Found Exception이 발생해야함.")
    public void test_FileUtil_NotFound() throws Exception{
        // 파일이 없는 경우

        mockMvc.perform(post("/files/fundCsvRead")
                    .header("Authorization",token)
                    .contentType(MediaType.MULTIPART_FORM_DATA) // 요청에 JSON 담아 보낼께
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .param("file",""))
                .andDo(print())
                .andExpect(status().isNotFound()) // 404 error 가 나오길 원함.
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_UTF8_VALUE)); // content type은 JSON 형태

    }

    @Test
    @TestDescription("토큰 없이 파일 업로드 처리 시 400 오류 발생")
    public void test_FileUtil_Success_NoToken() throws Exception{

        File testFile = new File(testFileFath);
        MockMultipartFile file = new MockMultipartFile(
                "file", testFile.getName(), MediaType.MULTIPART_FORM_DATA_VALUE, new FileInputStream(testFile));


        mockMvc.perform(MockMvcRequestBuilders.fileUpload("/files/fundCsvRead").file(file)
                .contentType(MediaType.MULTIPART_FORM_DATA) // 요청에 JSON 담아 보낼께
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isBadRequest()) // http status 200
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("message").exists());
    }

}
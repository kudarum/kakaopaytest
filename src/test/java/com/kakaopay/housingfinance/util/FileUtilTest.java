package com.kakaopay.housingfinance.util;

import com.kakaopay.housingfinance.cmm.BaseControllerTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.io.FileInputStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FileUtilTest extends BaseControllerTest {

    String testFileFath;

    @Autowired
    MockMvc mockMvc;

    @Before
    public void setUp() {
        testFileFath = "src/test/resources/data.csv";
    }

    // file Read 테스트
    @Test
    public void instituteFundCsvReadTest() throws Exception{

        File testFile = new File(testFileFath);
        MockMultipartFile file = new MockMultipartFile(
                "file", testFile.getName(), null, new FileInputStream(testFile));


        mockMvc.perform(MockMvcRequestBuilders.fileUpload("/file/institute/fundCsvRead").file(file)
                    .contentType(MediaType.MULTIPART_FORM_DATA) // 요청에 JSON 담아 보낼께
                    .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

}
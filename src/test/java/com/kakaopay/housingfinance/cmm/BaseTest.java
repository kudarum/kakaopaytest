package com.kakaopay.housingfinance.cmm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaopay.housingfinance.common.response.ApiResponseMessage;
import com.kakaopay.housingfinance.repository.FundRepository;
import com.kakaopay.housingfinance.util.CsvUtil;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Ignore
public class BaseTest {

    protected String test_file_fath = "src/test/resources/data.csv";

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected CsvUtil csvUtil;

    @Autowired
    FundRepository fundRepositoryTest;

    protected void fundDataInsert() throws Exception{
        File testFile = new File(test_file_fath);
        MultipartFile multipartFile = new MockMultipartFile(testFile.getName(), new FileInputStream(testFile));

        csvUtil.instituteFundCsvRead(multipartFile);
    }

    protected void fundDataDelete(){
        fundRepositoryTest.deleteAll();
    }
}

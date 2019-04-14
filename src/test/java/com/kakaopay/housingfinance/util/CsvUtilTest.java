package com.kakaopay.housingfinance.util;

import com.kakaopay.housingfinance.cmm.TestDescription;
import com.kakaopay.housingfinance.common.response.ApiResponseMessage;
import com.kakaopay.housingfinance.repository.FundRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CsvUtilTest {

    private String test_file_fath;

    private String test_empty_file_fath;

    private String test_not_match_file_fath;

    @Autowired
    private CsvUtil csvUtil;

    @Autowired
    private FundRepository fundRepository;

    @Before
    public void setUp() {
        test_file_fath = "src/test/resources/data.csv";
        test_empty_file_fath = "src/test/resources/dataEmpty.csv";
        test_not_match_file_fath = "src/test/resources/dataColunmNotMatch.csv";

        testDataDeleteAll();

    }

    @Test
    @TestDescription("csv file 을 정상적으로 읽는지 테스트시 정상처리 되야함.")
    public void test_CsvUtilFile() throws Exception{
        File testFile = new File(test_file_fath);
        MultipartFile multipartFile = new MockMultipartFile(testFile.getName(), new FileInputStream(testFile));

        assertThat(csvUtil.instituteFundCsvRead(multipartFile)).isEqualTo(ApiResponseMessage.RESPONSE_SUCCESS.getMessage());
    }

    @Test
    @TestDescription("csv file 내의 데이터가 비어있는 않는 경우 데이터는 없으나 정상 처리가 되야함.")
    public void test_CsvUtil_EmptyInnerData() throws Exception {
        File testEmptyFile = new File(test_empty_file_fath);
        MultipartFile multipartFile = new MockMultipartFile(testEmptyFile.getName(), new FileInputStream(testEmptyFile));

        assertThat(csvUtil.instituteFundCsvRead(multipartFile)).isEqualTo(ApiResponseMessage.RESPONSE_SUCCESS.getMessage());
    }

    @Test
    @TestDescription("csv file 내의 컬럼이 매치되지 않는 경우. Null Point Exception 발생 해야함.")
    public void test_CsvUtil_NotMatchColumn() throws Exception {
        File testNotMatchFile = new File(test_not_match_file_fath);
        MultipartFile multipartFile = new MockMultipartFile(testNotMatchFile.getName(), new FileInputStream(testNotMatchFile));

        assertThatThrownBy(() -> {
            csvUtil.instituteFundCsvRead(multipartFile);
        }).isInstanceOf(NullPointerException.class);
    }

    // 데이터 모두 삭제
    public void testDataDeleteAll(){
        fundRepository.deleteAll();
    }

}
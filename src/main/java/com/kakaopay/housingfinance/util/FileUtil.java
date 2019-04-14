package com.kakaopay.housingfinance.util;

import com.kakaopay.housingfinance.common.response.ApiResponseBody;
import com.kakaopay.housingfinance.common.response.ApiResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 파일 업로드 Util
 */
@RestController
@RequestMapping(value = "/files", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class FileUtil {

    @Autowired
    CsvUtil csvUtil;

    /**
     * 기관 지원금 csv파일 읽어 데이터 DB에 저장.
     * @param multipartHttpServletRequest
     * @param multipartFile
     * @return
     * @throws Exception
     */
    @PostMapping("/fundCsvRead")
    public ResponseEntity instituteFundCsvRead(MultipartHttpServletRequest multipartHttpServletRequest
            , @RequestParam(value = "file", required = false) MultipartFile multipartFile) throws Exception {

        String result = "";

        // 업로드 파일이 없는 경우.
        if(multipartFile == null) {
            throw new FileNotFoundException(ApiResponseMessage.ERROR_FILE_NOT_FOUND.getMessage());
        }

        Iterator<String> itr = multipartHttpServletRequest.getFileNames();
        List<MultipartFile> multipartFiles = new ArrayList<>();

        if(itr.hasNext()){
            multipartFiles.add(multipartHttpServletRequest.getFile(itr.next()));
            result = csvUtil.instituteFundCsvRead(multipartFile);
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseBody<>(HttpStatus.OK,ApiResponseMessage.RESPONSE_SUCCESS_FILE.getMessage(),null));
    }
}

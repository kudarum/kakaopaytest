package com.kakaopay.housingfinance.util;

import com.kakaopay.housingfinance.common.exception.ApiExceptionMessage;
import com.kakaopay.housingfinance.common.exception.handler.ApiExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = "/file", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
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
    @PostMapping("/institute/fundCsvRead")
    public ResponseEntity instituteFundCsvRead(MultipartHttpServletRequest multipartHttpServletRequest
            , @RequestParam(value = "file", required = false) MultipartFile multipartFile) throws Exception {

        // 업로드 파일이 없는 경우.
        if(multipartFile == null) {
            throw new FileNotFoundException(ApiExceptionMessage.FILE_NOT_FOUND_EXCEPTION.getMessage());
        }

        Iterator<String> itr = multipartHttpServletRequest.getFileNames();
        List<MultipartFile> multipartFiles = new ArrayList<>();

        if(itr.hasNext()){
            multipartFiles.add(multipartHttpServletRequest.getFile(itr.next()));
            csvUtil.instituteFundCsvRead(multipartFile);
        }



        return ResponseEntity.ok("");
    }
}

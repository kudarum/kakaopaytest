package com.kakaopay.housingfinance.util;

import com.kakaopay.housingfinance.model.Institute;
import com.kakaopay.housingfinance.model.Fund;
import com.kakaopay.housingfinance.repository.InstituteRepository;
import com.kakaopay.housingfinance.repository.FundRepository;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Csv 읽기 Util
 */
public class CsvUtil {

    @Autowired
    InstituteRepository instituteRepository;

    @Autowired
    FundRepository fundRepository;

    @Autowired
    NumberUtil numberUtil;

    /**
     * 기관 지원금 csv파일 읽어 데이터 DB에 저장.
     * @param multipartFile
     * @return
     */
    @Transactional
    public ResponseEntity instituteFundCsvRead(MultipartFile multipartFile) {

        if (multipartFile.isEmpty()) {

        }

        try {
            InputStreamReader inputStreamReader = new InputStreamReader(multipartFile.getInputStream(), "UTF-8");
            List<String[]> elements = (new CSVReader(inputStreamReader)).readAll();
            AtomicInteger row = new AtomicInteger(0);
            List<String> instituteCodeFilterList = new ArrayList<>();
            List<Fund> fundList = new ArrayList<>();

            List<Institute> instituteList = instituteRepository.findAll();

            elements.forEach(cellDataArray -> {

                // 첫번째 라인은 컬럼이기 때문에 기관코드와 컬럼 명과 맴핑
                if(row.get() == 0) {

                    // 기관 항목 순서 정리
                    for(int cellCount = 2; cellCount < cellDataArray.length; cellCount++) {
                        String instituteName = cellDataArray[cellCount];
                        instituteCodeFilterList.add(getInstitureCodeFilterName(instituteList, instituteName));
                    }

                } else {
                    // 두번째 라인부터 지원금 데이터

                    // 연도와 월 셋팅
                    Integer year = Integer.parseInt(cellDataArray[0]);
                    Integer month = Integer.parseInt(cellDataArray[1]);

                    // 각 컬럼의 데이터를 연, 월, 기관코드, 지원금 데이터를 LIST에 저장.
                    for(int cellCount = 2; cellCount < cellDataArray.length; cellCount++){

                        Long supportAmmount = numberUtil.removeFormatNumberLong(cellDataArray[cellCount]);
                        Fund support = Fund.builder()
                                .year(year)
                                .month(month)
                                // 2번 셀부터 실제 기관 값이기 때문에 cellCount-2 수행.
                                .code(instituteCodeFilterList.get(cellCount-2))
                                .amount(supportAmmount)
                                .build();

                        fundList.add(support);

                    }
                }

                // line 하나 증가.
                row.incrementAndGet();
            });

            // 지원금 데이터 저장.
            fundRepository.saveAll(fundList);

        } catch (IOException e) {

        }

        return ResponseEntity.ok("");
    }

    /**
     * 기관 코드와 컬럼 맵핑하여 정렬 수행
     * @param instituteList
     * @param instituteName
     * @return
     */
    @NotEmpty
    private String getInstitureCodeFilterName(List<Institute> instituteList, String instituteName) {

        try{
            return instituteList.stream()
                    .filter(institute -> instituteName.matches(String.format(".*%s.*", institute.getName())))
                    .findAny()
                    .orElseThrow(NullPointerException::new)
                    .getCode();
        } catch (Exception e) {
        }

        return null;

    }
}

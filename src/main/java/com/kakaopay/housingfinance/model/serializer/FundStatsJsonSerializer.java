package com.kakaopay.housingfinance.model.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.kakaopay.housingfinance.model.dto.FundStatsDto;
import com.kakaopay.housingfinance.model.dto.FundYearSumDto;
import com.kakaopay.housingfinance.util.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.util.List;

@JsonComponent
public class FundStatsJsonSerializer extends JsonSerializer<FundStatsDto> {

    @Autowired
    NumberUtil numberUtil;

    @Override
    public void serialize(FundStatsDto fundStatsDto, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartArray();

        gen.writeStartObject();
        gen.writeStringField("name", fundStatsDto.getName());
        gen.writeEndObject();

        gen.writeStartArray();

        List<FundYearSumDto> fundYearSumDtoList = fundStatsDto.getResultList();

        Integer year = 0;

        for (int yearSumCount = 0; yearSumCount < fundYearSumDtoList.size(); yearSumCount++) {

            FundYearSumDto tempDto = fundYearSumDtoList.get(yearSumCount);

            if (yearSumCount == 0) {
                year = tempDto.getYear();
                gen = startJsonGenerator(gen, tempDto);
            }

            if (!year.equals(tempDto.getYear())) {
                year = tempDto.getYear();
                gen = endJsonGenerator(gen);
                gen = startJsonGenerator(gen, tempDto);

            }

            gen.writeNumberField(tempDto.getInstitute_name(), tempDto.getSupport_amount());

            if (yearSumCount == fundYearSumDtoList.size() - 1) {
                gen = endJsonGenerator(gen);
            }


        }

        gen.writeEndArray();

        gen.writeEndArray();
    }



    private JsonGenerator startJsonGenerator(JsonGenerator gen, FundYearSumDto tempDto) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("year", tempDto.getYear() +" 년");
        gen.writeNumberField("total_amount", tempDto.getTotal_amount());
        gen.writeArrayFieldStart("detail_amount");
        gen.writeStartObject();

        return gen;
    }

    private JsonGenerator endJsonGenerator(JsonGenerator gen) throws IOException {
        gen.writeEndObject();
        gen.writeEndArray();
        gen.writeEndObject();

        return gen;
    }
}

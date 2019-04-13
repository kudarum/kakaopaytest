package com.kakaopay.housingfinance.cmm;

import org.junit.Ignore;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE) // 얼마나 오래 가져갈 것이냐
@Ignore
public @interface TestDescription {
    String value();
}

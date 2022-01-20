package org.onecell.spring.template.exception.impl;


import org.onecell.spring.template.exception.ServiceException;


/// 기간 오류
public class PeriodOverDayException extends ServiceException {
    public PeriodOverDayException(Integer days) {
        setArguments(days);
    }
}

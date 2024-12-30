package com.parkee.techtest.mapper;

import com.parkee.techtest.bean.LoanRequestBean;
import com.parkee.techtest.model.LoanHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LoanMapper {
    @Mapping(target = "loanDateStart", source = "bean.loanDate")
    @Mapping(target = "loanDateEnd", source = "bean.returnDate")
    @Mapping(target = "status", constant = "LOANED")
    LoanHistory toEntity(LoanRequestBean bean);
}

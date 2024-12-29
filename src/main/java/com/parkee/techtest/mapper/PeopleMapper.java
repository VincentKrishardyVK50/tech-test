package com.parkee.techtest.mapper;

import com.parkee.techtest.bean.PeopleRequestBean;
import com.parkee.techtest.bean.PeopleResponseBean;
import com.parkee.techtest.model.People;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PeopleMapper {
    People toEntity(PeopleRequestBean bean);

    PeopleResponseBean toBean(People entity);
}

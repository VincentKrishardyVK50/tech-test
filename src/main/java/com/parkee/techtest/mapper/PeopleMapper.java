package com.parkee.techtest.mapper;

import com.parkee.techtest.bean.PeopleRequestBean;
import com.parkee.techtest.bean.PeopleResponseBean;
import com.parkee.techtest.model.People;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PeopleMapper {

    @Mapping(target = "id", ignore = true)
    People toEntity(PeopleRequestBean bean);

    PeopleResponseBean toBean(People entity);

    // method for mapping into entity which want to update
    // source: https://medium.com/@bectorhimanshu/part-2-advanced-mapping-with-mapstruct-using-mappingtarget-in-spring-boot-c1c7111f3bb5
    void toUpdateEntity(PeopleRequestBean bean, @MappingTarget People entity);
}

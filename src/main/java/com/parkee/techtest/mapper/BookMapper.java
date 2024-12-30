package com.parkee.techtest.mapper;

import com.parkee.techtest.bean.BookRequestBean;
import com.parkee.techtest.bean.BookResponseBean;
import com.parkee.techtest.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(target = "id", ignore = true)
    Book toEntity (BookRequestBean request);

    BookResponseBean toBean (Book entity);

    // method for mapping into entity which want to update
    // source: https://medium.com/@bectorhimanshu/part-2-advanced-mapping-with-mapstruct-using-mappingtarget-in-spring-boot-c1c7111f3bb5
    void toUpdatedEntity(BookRequestBean request, @MappingTarget Book entity);
}

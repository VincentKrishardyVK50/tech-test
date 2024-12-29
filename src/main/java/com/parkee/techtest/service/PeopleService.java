package com.parkee.techtest.service;

import com.parkee.techtest.bean.PeopleRequestBean;
import com.parkee.techtest.bean.PeopleResponseBean;
import com.parkee.techtest.mapper.PeopleMapper;
import com.parkee.techtest.model.People;
import com.parkee.techtest.repository.PeopleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PeopleService {
    private final PeopleRepository peopleRepository;
    private final PeopleMapper peopleMapper;

    public PeopleService(PeopleRepository peopleRepository, PeopleMapper peopleMapper) {
        this.peopleRepository = peopleRepository;
        this.peopleMapper = peopleMapper;
    }

    @Transactional
    public PeopleResponseBean createNewPeople(PeopleRequestBean request) {
        People people = peopleMapper.toEntity(request); // mapping using mapstruct too instead set each attributes manually
        people = peopleRepository.save(people);
        return peopleMapper.toBean(people);
    }

    /**
     * get list of people which can filter by nik, name, or email based on given keyword
     */
    public Page<PeopleResponseBean> getListPeople(Pageable pageable, String keyword) {
        return peopleRepository
                .findBooksByKeyword(pageable, keyword)
                .map(peopleMapper::toBean);
    }
}

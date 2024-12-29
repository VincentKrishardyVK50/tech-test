package com.parkee.techtest.service;

import com.parkee.techtest.bean.PeopleRequestBean;
import com.parkee.techtest.bean.PeopleResponseBean;
import com.parkee.techtest.mapper.PeopleMapper;
import com.parkee.techtest.model.People;
import com.parkee.techtest.repository.PeopleRepository;
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
        People people = peopleMapper.toEntity(request);
        people = peopleRepository.save(people);
        return peopleMapper.toBean(people);
    }
}

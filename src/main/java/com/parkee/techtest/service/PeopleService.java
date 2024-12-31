package com.parkee.techtest.service;

import com.parkee.techtest.bean.PeopleRequestBean;
import com.parkee.techtest.bean.PeopleResponseBean;
import com.parkee.techtest.mapper.PeopleMapper;
import com.parkee.techtest.model.People;
import com.parkee.techtest.repository.PeopleRepository;
import jakarta.persistence.EntityNotFoundException;
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
    public void createNewPeople(PeopleRequestBean request) {
        People people = peopleMapper.toEntity(request); // mapping using mapstruct too instead set each attributes manually
        peopleRepository.save(people);
    }

    @Transactional
    public void updateCurrentPeople(PeopleRequestBean request) {
        // find current people by id which want to update
        People people = peopleRepository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException("Data tidak ditemukan!"));
        peopleMapper.toUpdateEntity(request, people);
        peopleRepository.save(people);
    }

    @Transactional
    public void deletePeople(long id) {
        peopleRepository.findById(id).ifPresentOrElse(entity -> {
            // if exists it will soft-deleted by set true in "deleted" column
            entity.setDeleted(true);
            peopleRepository.save(entity);
        }, () -> {
            // in case given id is not found
            throw new EntityNotFoundException("Data tidak ditemukan");
        });
    }
    /**
     * get list of people which can filter by nik, name, or email based on given keyword
     */
    public Page<PeopleResponseBean> getListPeople(Pageable pageable, String keyword) {
        return peopleRepository
                .findBooksByKeyword(pageable, keyword)
                .map(peopleMapper::toBean);
    }

    public PeopleResponseBean getDetailPeople(long id) {
        // find by id, if not found throw exception
        return peopleRepository.findById(id)
                .map(peopleMapper::toBean)
                .orElseThrow(() -> new EntityNotFoundException("Data tidak ditemukan!"));
    }
}

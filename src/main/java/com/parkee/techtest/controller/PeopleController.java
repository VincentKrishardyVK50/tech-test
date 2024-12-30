package com.parkee.techtest.controller;

import com.parkee.techtest.bean.*;
import com.parkee.techtest.service.PeopleService;
import com.parkee.techtest.validation.PeopleValidation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/back-office/people")
public class PeopleController {
    private final PeopleService peopleService;
    private final PeopleValidation peopleValidation;

    public PeopleController(PeopleService peopleService, PeopleValidation peopleValidation) {
        this.peopleService = peopleService;
        this.peopleValidation = peopleValidation;
    }

    @PostMapping("/create")
    public ResponseEntity<GeneralResponseBean> create(@RequestBody PeopleRequestBean bean){
        peopleValidation.validateNewPeople(bean);
        GeneralResponseBean<PeopleResponseBean> response =
                new GeneralResponseBean<>(peopleService.createNewPeople(bean), HttpStatus.CREATED);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<GeneralResponseBean> list(Pageable pageable,
                                                    @RequestParam(required = false, defaultValue = "") String keyword) {
        GeneralResponseBean<Page<PeopleResponseBean>> response =
                new GeneralResponseBean<>(peopleService.getListPeople(pageable, keyword), HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeneralResponseBean> detail(@PathVariable long id) {
        GeneralResponseBean<PeopleResponseBean> response =
                new GeneralResponseBean<>(peopleService.getDetailPeople(id), HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<GeneralResponseBean> update(@RequestBody PeopleRequestBean bean){
        peopleValidation.validateUpdatedPeople(bean);
        GeneralResponseBean<PeopleResponseBean> response =
                new GeneralResponseBean<>(peopleService.updateCurrentPeople(bean), HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GeneralResponseBean> delete(@PathVariable long id){
        peopleService.deletePeople(id);
        GeneralResponseBean<Void> response = new GeneralResponseBean<>( null , HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

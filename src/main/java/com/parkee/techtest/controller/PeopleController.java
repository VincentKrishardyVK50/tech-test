package com.parkee.techtest.controller;

import com.parkee.techtest.bean.BookResponseBean;
import com.parkee.techtest.bean.GeneralResponseBean;
import com.parkee.techtest.bean.PeopleRequestBean;
import com.parkee.techtest.bean.PeopleResponseBean;
import com.parkee.techtest.service.PeopleService;
import com.parkee.techtest.validation.PeopleValidation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

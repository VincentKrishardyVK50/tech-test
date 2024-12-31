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

    /**
     * endpoint for create people into database
     * @param bean which contains name, email, and nik
     * @return success message only
     */
    @PostMapping("/create")
    public ResponseEntity<GeneralResponseBean> create(@RequestBody PeopleRequestBean bean){
        peopleValidation.validateNewPeople(bean);
        peopleService.createNewPeople(bean);
        GeneralResponseBean<Void> response = new GeneralResponseBean<>(null, HttpStatus.CREATED);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * endpoint for listing of created people
     * @param pageable
     * @param keyword
     * @return list of peoples
     */
    @GetMapping("")
    public ResponseEntity<GeneralResponseBean> list(Pageable pageable,
                                                    @RequestParam(required = false, defaultValue = "") String keyword) {
        GeneralResponseBean<Page<PeopleResponseBean>> response =
                new GeneralResponseBean<>(peopleService.getListPeople(pageable, keyword), HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * endpoint for detail of people based on given id
     * @param id
     * @return detail information of people
     */
    @GetMapping("/{id}")
    public ResponseEntity<GeneralResponseBean> detail(@PathVariable long id) {
        GeneralResponseBean<PeopleResponseBean> response =
                new GeneralResponseBean<>(peopleService.getDetailPeople(id), HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * endpoint for updating people information
     * @param bean
     * @return success message only
     */
    @PutMapping("/update")
    public ResponseEntity<GeneralResponseBean> update(@RequestBody PeopleRequestBean bean){
        peopleValidation.validateUpdatedPeople(bean);
        peopleService.updateCurrentPeople(bean);
        GeneralResponseBean<Void> response =new GeneralResponseBean<>(null, HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * endpoint for deleting people by given id
     * @param id
     * @return success message only
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GeneralResponseBean> delete(@PathVariable long id){
        peopleService.deletePeople(id);
        GeneralResponseBean<Void> response = new GeneralResponseBean<>( null , HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

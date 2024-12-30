package com.parkee.techtest.controller;

import com.parkee.techtest.bean.GeneralResponseBean;
import com.parkee.techtest.bean.LoanRequestBean;
import com.parkee.techtest.projection.LoanHistoryProjection;
import com.parkee.techtest.service.LoanService;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/back-office")
public class LoanController {
    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/loan")
    public ResponseEntity<GeneralResponseBean<Void>> loanBook(@RequestBody LoanRequestBean bean) {
        loanService.processLoanBook(bean);
        GeneralResponseBean<Void> response = new GeneralResponseBean<>( null , HttpStatus.CREATED);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/return/{loanId}")
    public ResponseEntity<GeneralResponseBean<Void>> returnBook(@PathVariable long loanId) {
        loanService.processReturnBook(loanId);
        GeneralResponseBean<Void> response = new GeneralResponseBean<>( null , HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping ("/loan/list")
    public ResponseEntity loanBookList(Pageable pageable,
                                       @RequestParam(required = false, defaultValue = "") String keyword) {
        GeneralResponseBean<Page<LoanHistoryProjection>> responseBean =
                new GeneralResponseBean<>( loanService.getListLoanHistory(pageable, keyword) , HttpStatus.OK);
        return new ResponseEntity<>(responseBean, HttpStatus.OK);
    }
}

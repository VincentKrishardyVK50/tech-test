package com.parkee.techtest.validation;

import com.parkee.techtest.bean.PeopleRequestBean;
import com.parkee.techtest.repository.PeopleRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PeopleValidation {
    private final PeopleRepository peopleRepository;

    private final String EMAIL_REGEX  = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]+"; // source: https://stackoverflow.com/a/21608456 with few adjustment

    public PeopleValidation(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public void validateNewPeople(PeopleRequestBean bean) {
        validateNik(bean.getNik());
        validateEmail(bean.getEmail());
        validateName(bean.getName());
    }

    public void validateUpdatedPeople(PeopleRequestBean bean) {
        if(bean.getId() == 0) {
            throw new RuntimeException("ID buku harus diisi!");
        }
        validateUpdatedNik(bean.getNik(), bean.getId());
        validateUpdatedEmail(bean.getEmail(), bean.getId());
        validateName(bean.getName());
    }

    private void validateNik(String nik) {
        // check request attribute must not be empty
        if (StringUtils.isBlank(nik)) {
            throw new RuntimeException("NIK harus diisi!");
        }

        // check length of nik (always 16 character, source: https://id.wikipedia.org/wiki/Nomor_Induk_Kependudukan)
        if (nik.length() != 16) {
            throw new RuntimeException("NIK harus terdiri dari 16 angka!");
        }

        // check if nik already in database (nik value is unique for each people)
        if(peopleRepository.findByNikAndDeletedFalse(nik).isPresent()) {
            throw new RuntimeException("NIK sudah terdaftar!");
        }
    }

    private void validateUpdatedNik(String nik, long id) {
        // check request attribute must not be empty
        if (StringUtils.isBlank(nik)) {
            throw new RuntimeException("NIK harus diisi!");
        }

        // check length of nik (always 16 character)
        if (nik.length() != 16) {
            throw new RuntimeException("NIK harus terdiri dari 16 angka!");
        }

        // check if nik already in database and used in other people (nik value is unique for each people)
        if(peopleRepository.findByNikAndIdNotAndDeletedFalse(nik, id).isPresent()) {
            throw new RuntimeException("NIK sudah terdaftar!");
        }
    }

    private void validateEmail(String email) {
        // check request attribute must not be empty
        if (StringUtils.isBlank(email)) {
            throw new RuntimeException("Email harus diisi!");
        }

        // check email value pattern using regular expression
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        if(!matcher.matches()) {
            throw new RuntimeException("Email tidak valid!");
        }

        // check if email already exists in database
        if(peopleRepository.findByEmailAndDeletedFalse(email).isPresent()) {
            throw new RuntimeException("Email sudah terdaftar!");
        }
    }

    private void validateUpdatedEmail(String email, long id) {
        // check request attribute must not be empty
        if (StringUtils.isBlank(email)) {
            throw new RuntimeException("Email harus diisi!");
        }

        // check email value pattern using regular expression
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        if(!matcher.matches()) {
            throw new RuntimeException("Email tidak valid!");
        }

        // check if email already exists in database
        if(peopleRepository.findByEmailAndIdNotAndDeletedFalse(email, id).isPresent()) {
            throw new RuntimeException("Email sudah terdaftar!");
        }
    }

    private void validateName(String name) {
        // check request attribute must not be empty
        if (StringUtils.isBlank(name)) {
            throw new RuntimeException("Nama harus diisi!");
        }
    }
}

# README

### Design of Relational Database
Before develop REST API for the assessment, I designed relational database like the attached design below.
![image](./src/main/resources/design_db.png)

### List of REST API

REST API for Book Management: 

* Create new book: POST /api/back-office/book/create (**MANDATORY**)
* Get list of book: GET /api/back-office/book?keyword={keyword}
* Get detail of book: GET /api/back-office/book/{id}


REST API for People Management: 

* Create new people: POST /api/back-office/people/create (**MANDATORY**)
* Get list of people: GET /api/back-office/people?keyword={keyword}
* Get detail of people:  GET /api/back-office/book/{id}

REST API for Loan Book Processing: 

### Additional Assumptions

* There's possible that have duplicated title book and name of people

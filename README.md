# README

### Design of Relational Database
Before develop REST API for the assessment, I designed relational database like the attached design below.
![image](./src/main/resources/design_db.png)

### List of REST API

REST API for Book Management: 

* POST /api/back-office/book/create
* GET /api/back-office/book?keyword={keyword}


REST API for People Management: 

* POST /api/back-office/people/create
* GET /api/back-office/people?keyword={keyword}

REST API for Loan Book Processing: 

### Additional Assumptions

* There's possible that have duplicated title book and name of people

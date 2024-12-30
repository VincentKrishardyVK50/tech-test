CREATE TABLE `book` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `book_title` varchar(255) DEFAULT NULL,
    `isbn_number` varchar(255) DEFAULT NULL,
    `stock` bigint NOT NULL,
    PRIMARY KEY (`id`)
)

CREATE TABLE `loan_history` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `status` varchar(255) DEFAULT NULL,
    `book_id` bigint DEFAULT NULL,
    `people_id` bigint DEFAULT NULL,
    `loan_date_end` date DEFAULT NULL,
    `loan_date_start` date DEFAULT NULL,
    `return_date` date DEFAULT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `FK_book` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`),
    CONSTRAINT `FK_people` FOREIGN KEY (`people_id`) REFERENCES `people` (`id`)
)

CREATE TABLE `people` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `email` varchar(255) DEFAULT NULL,
    `name` varchar(255) DEFAULT NULL,
    `nik` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)

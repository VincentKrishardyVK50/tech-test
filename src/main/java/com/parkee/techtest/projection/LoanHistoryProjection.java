package com.parkee.techtest.projection;

import java.time.LocalDate;

public interface LoanHistoryProjection {
    Long getId();
    String getPeopleName();
    String getTitleBook();
    LocalDate getLoanDate();
    LocalDate getReturnDate();
    String getStatus();
}

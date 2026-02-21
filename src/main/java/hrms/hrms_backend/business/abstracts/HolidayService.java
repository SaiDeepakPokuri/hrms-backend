package hrms.hrms_backend.business.abstracts;

import hrms.hrms_backend.entities.concretes.Holiday;

import java.util.List;

public interface HolidayService {

    List<Holiday> getHolidays(int year);
}

package hrms.hrms_backend.dataacceess.abstracts;

import hrms.hrms_backend.entities.concretes.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface HolidayRepository extends JpaRepository<Holiday, Long> {

    List<Holiday> findByYear(int year);

    boolean existsByHolidayDate(LocalDate holidayDate);
}


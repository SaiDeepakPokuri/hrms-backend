package hrms.hrms_backend.business.concretes;

import hrms.hrms_backend.business.abstracts.HolidayService;
import hrms.hrms_backend.dataacceess.abstracts.HolidayRepository;
import hrms.hrms_backend.entities.concretes.Holiday;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HolidayServiceManager implements HolidayService {

    private final HolidayRepository holidayRepository;

    public HolidayServiceManager(HolidayRepository holidayRepository) {
        this.holidayRepository = holidayRepository;
    }

    @Override
    public List<Holiday> getHolidays(int year) {
        return holidayRepository.findByYear(year);
    }
}

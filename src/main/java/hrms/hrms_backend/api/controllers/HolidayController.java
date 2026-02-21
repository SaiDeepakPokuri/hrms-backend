package hrms.hrms_backend.api.controllers;

import hrms.hrms_backend.business.abstracts.HolidayService;
import hrms.hrms_backend.entities.concretes.Holiday;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/hrm/holidays")
public class HolidayController {

    private final HolidayService holidayService;

    public HolidayController(HolidayService holidayService) {
        this.holidayService = holidayService;
    }

    @GetMapping("/{year}")
    public ResponseEntity<List<Holiday>> getHolidays(@PathVariable int year) {
        return ResponseEntity.ok(holidayService.getHolidays(year));
    }
}

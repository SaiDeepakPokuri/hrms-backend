package hrms.hrms_backend.api.controllers;

import hrms.hrms_backend.business.abstracts.AttendanceService;
import hrms.hrms_backend.dataacceess.abstracts.AttendanceRepository;
import hrms.hrms_backend.entities.concretes.Attendance;
import hrms.hrms_backend.entities.enums.AttendanceStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hrm/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final AttendanceRepository attendanceRepository;

    public AttendanceController(AttendanceService attendanceService, AttendanceRepository attendanceRepository) {
        this.attendanceService = attendanceService;
        this.attendanceRepository = attendanceRepository;
    }

    @PostMapping
    public ResponseEntity<Attendance> markAttendance(
            @RequestBody Attendance attendance) {

        Attendance saved = attendanceService.markAttendance(
                attendance.getEmployee().getId(),
                attendance.getAttendanceDate(),
                attendance.getStatus()
        );

        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<?> getAttendance(@PathVariable Long employeeId) {
        return ResponseEntity.ok(
                attendanceService.getAttendanceByEmployee(employeeId)
        );
    }

    @PutMapping("/{attendanceId}")
    public ResponseEntity<Attendance> updateAttendance(
            @PathVariable Long attendanceId,
            @RequestBody Attendance attendance) {

        Attendance updated = attendanceService.updateAttendance(
                attendanceId,
                attendance.getAttendanceDate(),
                attendance.getStatus()
        );

        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{attendanceId}/request-correction")
    public ResponseEntity<Attendance> requestCorrection(
            @PathVariable Long attendanceId,
            @RequestBody Attendance request) {

        Attendance updated = attendanceService.requestCorrection(
                attendanceId, request.getRequestedStatus(), request.getCorrectionReason() );

        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{attendanceId}/approve")
    public ResponseEntity<Attendance> approveCorrection(
            @PathVariable Long attendanceId) {

        return ResponseEntity.ok(attendanceService.approveCorrection(attendanceId));
    }

    @PutMapping("/{attendanceId}/reject")
    public ResponseEntity<Attendance> rejectCorrection(
            @PathVariable Long attendanceId) {

        return ResponseEntity.ok(attendanceService.rejectCorrection(attendanceId));
    }

    @GetMapping("/summary/today")
    public List<Object[]> todayAttendanceSummary() {
        return attendanceRepository.getTodayAttendanceSummary();
    }

    @GetMapping("/summary/monthly")
    public List<Object[]> monthlyAttendance() {
        return attendanceRepository.monthlyAttendance();
    }
}

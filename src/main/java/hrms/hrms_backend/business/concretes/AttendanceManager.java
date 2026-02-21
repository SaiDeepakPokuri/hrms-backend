package hrms.hrms_backend.business.concretes;

import hrms.hrms_backend.api.controllers.exception.EmployeeNotFoundException;
import hrms.hrms_backend.business.abstracts.AttendanceService;
import hrms.hrms_backend.dataacceess.abstracts.AttendanceRepository;
import hrms.hrms_backend.dataacceess.abstracts.HrmEmployeeRepository;
import hrms.hrms_backend.entities.concretes.Attendance;
import hrms.hrms_backend.entities.concretes.HrmEmployee;
import hrms.hrms_backend.entities.enums.AttendanceStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AttendanceManager implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final HrmEmployeeRepository hrmEmployeeRepository;

    public AttendanceManager(AttendanceRepository attendanceRepository,
                             HrmEmployeeRepository hrmEmployeeRepository) {
        this.attendanceRepository = attendanceRepository;
        this.hrmEmployeeRepository = hrmEmployeeRepository;
    }

    @Override
    public Attendance markAttendance(
            Long employeeId,
            LocalDate date,
            AttendanceStatus status) {

        HrmEmployee employee = hrmEmployeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new EmployeeNotFoundException("Employee not found"));

        if (attendanceRepository.existsByEmployeeAndAttendanceDate(employee, date)) {
            throw new RuntimeException("Attendance already marked for this date");
        }

        Attendance attendance = new Attendance();
        attendance.setEmployee(employee);
        attendance.setAttendanceDate(date);
        attendance.setStatus(status);

        return attendanceRepository.save(attendance);
    }

    @Override
    public List<Attendance> getAttendanceByEmployee(Long employeeId) {
        return attendanceRepository.findByEmployeeId(employeeId);
    }

    @Override
    public Attendance updateAttendance(Long attendanceId, LocalDate date, AttendanceStatus status) {
        Attendance attendance = attendanceRepository
                .findById(attendanceId)
                .orElseThrow(() -> new RuntimeException("Attendance not found"));

        attendance.setAttendanceDate(date);
        attendance.setStatus(status);

        return attendanceRepository.save(attendance);
    }

    @Override
    public Attendance requestCorrection(Long attendanceId, AttendanceStatus requestedStatus, String reason) {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new RuntimeException("Attendance not found"));

        attendance.setRequestedStatus(requestedStatus);
        attendance.setCorrectionReason(reason);
        return attendanceRepository.save(attendance);
    }

    @Override
    public Attendance approveCorrection(Long attendanceId) {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new RuntimeException("Attendance not found"));

        if(attendance.getRequestedStatus() == null) {
            throw new RuntimeException("No correction requested");
        }
        attendance.setStatus(attendance.getRequestedStatus());
        attendance.setRequestedStatus(null);
        attendance.setCorrectionReason(null);

        return attendanceRepository.save(attendance);
    }

    @Override
    public Attendance rejectCorrection(Long attendanceId) {

        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new RuntimeException("Attendance not found"));

        if (attendance.getRequestedStatus() == null) {
            throw new RuntimeException("No correction request found");
        }

        attendance.setRequestedStatus(null);
        attendance.setCorrectionReason(null);

        return attendanceRepository.save(attendance);
    }
}


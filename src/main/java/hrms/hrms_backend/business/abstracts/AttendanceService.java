package hrms.hrms_backend.business.abstracts;

import hrms.hrms_backend.entities.concretes.Attendance;
import hrms.hrms_backend.entities.enums.AttendanceStatus;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {

    Attendance markAttendance(Long employeeId, LocalDate date, AttendanceStatus status);

    List<Attendance> getAttendanceByEmployee(Long employeeId);

    Attendance updateAttendance(Long attendanceId, LocalDate date, AttendanceStatus status);

    Attendance requestCorrection(Long attendanceId, AttendanceStatus requestedStatus, String reason);

    Attendance approveCorrection(Long attendanceId);

    Attendance rejectCorrection(Long attendanceId);
}


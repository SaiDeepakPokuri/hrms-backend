package hrms.hrms_backend.dataacceess.abstracts;

import hrms.hrms_backend.entities.concretes.Attendance;
import hrms.hrms_backend.entities.concretes.HrmEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    boolean existsByEmployeeAndAttendanceDate(
            HrmEmployee employee,
            LocalDate attendanceDate
    );

    List<Attendance> findByEmployeeId(Long employeeId);

    @Query("""
    SELECT a.status, COUNT(a)
    FROM Attendance a
    WHERE a.attendanceDate = CURRENT_DATE
    GROUP BY a.status
    """)
    List<Object[]> getTodayAttendanceSummary();

    @Query("""
    SELECT a.attendanceDate, COUNT(a)
    FROM Attendance a
    WHERE MONTH(a.attendanceDate) = MONTH(CURRENT_DATE)
    GROUP BY a.attendanceDate
    ORDER BY a.attendanceDate
    """)
    List<Object[]> monthlyAttendance();
}


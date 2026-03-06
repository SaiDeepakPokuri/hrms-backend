package hrms.hrms_backend.dataacceess.abstracts;

import hrms.hrms_backend.entities.concretes.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    List<LeaveRequest> findByEmployee_Id(Long employeeId);

    @Query("""
    SELECT l.leaveType, COUNT(l)
    FROM LeaveRequest l
    WHERE l.status = 'APPROVED'
    GROUP BY l.leaveType
    """)
    List<Object[]> getLeaveUsageSummary();
}

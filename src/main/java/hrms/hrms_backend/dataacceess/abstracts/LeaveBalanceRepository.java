package hrms.hrms_backend.dataacceess.abstracts;

import hrms.hrms_backend.entities.concretes.LeaveBalance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, Long> {

    Optional<LeaveBalance> findByEmployee_Id(Long employeeId);
}

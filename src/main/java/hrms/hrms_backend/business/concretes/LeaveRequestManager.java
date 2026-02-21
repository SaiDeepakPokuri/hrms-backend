package hrms.hrms_backend.business.concretes;

import hrms.hrms_backend.business.abstracts.LeaveRequestService;
import hrms.hrms_backend.dataacceess.abstracts.LeaveBalanceRepository;
import hrms.hrms_backend.dataacceess.abstracts.LeaveRequestRepository;
import hrms.hrms_backend.entities.concretes.LeaveBalance;
import hrms.hrms_backend.entities.concretes.LeaveRequest;
import hrms.hrms_backend.entities.enums.LeaveStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LeaveRequestManager implements LeaveRequestService {

    private final LeaveRequestRepository leaveRequestRepository;
    private final LeaveBalanceRepository leaveBalanceRepository;

    public LeaveRequestManager(LeaveRequestRepository leaveRequestRepository,
                               LeaveBalanceRepository leaveBalanceRepository) {
        this.leaveRequestRepository = leaveRequestRepository;
        this.leaveBalanceRepository = leaveBalanceRepository;
    }

    @Override
    public LeaveRequest submitLeave(LeaveRequest leaveRequest) {
        leaveRequest.setStatus(LeaveStatus.PENDING);
        return leaveRequestRepository.save(leaveRequest);
    }

    @Override
    public List<LeaveRequest> getLeavesByEmployee(Long employeeId) {
        return leaveRequestRepository.findByEmployee_Id(employeeId);
    }

    @Override
    @Transactional
    public LeaveRequest approveLeave(Long leaveId) {
        LeaveRequest leave = leaveRequestRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));

        if (leave.getStatus() != LeaveStatus.PENDING) {
            throw new RuntimeException("Only pending leaves can be approved");
        }

        LeaveBalance balance = leaveBalanceRepository
                .findByEmployee_Id(leave.getEmployee().getId())
                .orElseThrow(() -> new RuntimeException("Leave balance not initialized"));

        int days = (int) ChronoUnit.DAYS.between(leave.getStartDate(), leave.getEndDate()) + 1;

        switch (leave.getLeaveType()) {
            case CASUAL:
                if (balance.getCasualLeaves() < days)
                    throw new RuntimeException("Insufficient casual leave balance");
                balance.setCasualLeaves(balance.getCasualLeaves() - days);
                break;

            case SICK:
                if (balance.getSickLeaves() < days)
                    throw new RuntimeException("Insufficient sick leave balance");
                balance.setSickLeaves(balance.getSickLeaves() - days);
                break;

            case PAID:
                if (balance.getPaidLeaves() < days)
                    throw new RuntimeException("Insufficient paid leave balance");
                balance.setPaidLeaves(balance.getPaidLeaves() - days);
                break;
        }
        leave.setStatus(LeaveStatus.APPROVED);
        leaveBalanceRepository.save(balance);
        return leaveRequestRepository.save(leave);
    }

    @Override
    public LeaveRequest rejectLeave(Long leaveId) {
        LeaveRequest leave = leaveRequestRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));

        if (leave.getStatus() != LeaveStatus.PENDING) {
            throw new RuntimeException("Only pending leaves can be rejected");
        }

        leave.setStatus(LeaveStatus.REJECTED);
        return leaveRequestRepository.save(leave);
    }
}

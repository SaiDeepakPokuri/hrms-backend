package hrms.hrms_backend.business.abstracts;

import hrms.hrms_backend.entities.concretes.LeaveRequest;

import java.util.List;

public interface LeaveRequestService {

    LeaveRequest submitLeave(LeaveRequest leaveRequest);

    List<LeaveRequest> getLeavesByEmployee(Long employeeId);

    LeaveRequest approveLeave(Long leaveId);

    LeaveRequest rejectLeave(Long leaveId);
}

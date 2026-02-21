package hrms.hrms_backend.api.controllers;

import hrms.hrms_backend.business.abstracts.LeaveRequestService;
import hrms.hrms_backend.dataacceess.abstracts.LeaveBalanceRepository;
import hrms.hrms_backend.dataacceess.abstracts.LeaveRequestRepository;
import hrms.hrms_backend.entities.concretes.LeaveBalance;
import hrms.hrms_backend.entities.concretes.LeaveRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hrm/leaves")
public class LeaveRequestController {

    private final LeaveRequestService leaveRequestService;
    private final LeaveRequestRepository leaveRequestRepository;
    private final LeaveBalanceRepository leaveBalanceRepository;

    public LeaveRequestController(LeaveRequestService leaveRequestService, LeaveRequestRepository leaveRequestRepository,
                                  LeaveBalanceRepository leaveBalanceRepository) {
        this.leaveRequestService = leaveRequestService;
        this.leaveRequestRepository = leaveRequestRepository;
        this.leaveBalanceRepository = leaveBalanceRepository;
    }

    @PostMapping("/submit")
    public ResponseEntity<LeaveRequest> submitLeave(
            @RequestBody LeaveRequest leaveRequest) {

        LeaveRequest saved = leaveRequestService.submitLeave(leaveRequest);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<LeaveRequest>> getLeavesByEmployee(
            @PathVariable Long employeeId) {

        return ResponseEntity.ok(
                leaveRequestService.getLeavesByEmployee(employeeId)
        );
    }

    @PutMapping("/{leaveId}/approve")
    public ResponseEntity<LeaveRequest> approveLeave(@PathVariable Long leaveId) {
        return ResponseEntity.ok(
                leaveRequestService.approveLeave(leaveId)
        );
    }

    @PutMapping("/{leaveId}/reject")
    public ResponseEntity<LeaveRequest> rejectLeave(@PathVariable Long leaveId) {
        return ResponseEntity.ok(
                leaveRequestService.rejectLeave(leaveId)
        );
    }

    @GetMapping("/balance/{employeeId}")
    public ResponseEntity<LeaveBalance> getLeaveBalance(
            @PathVariable Long employeeId) {

        return ResponseEntity.ok(
                leaveBalanceRepository.findByEmployee_Id(employeeId)
                        .orElseThrow(() -> new RuntimeException("Leave balance not found"))
        );
    }

    @GetMapping("/summary/usage")
    public List<Object[]> leaveUsageSummary() {
        return leaveRequestRepository.getLeaveUsageSummary();
    }

}

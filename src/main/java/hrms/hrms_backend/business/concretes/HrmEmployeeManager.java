package hrms.hrms_backend.business.concretes;

import hrms.hrms_backend.api.controllers.exception.EmailAlreadyExistsException;
import hrms.hrms_backend.api.controllers.exception.EmployeeNotFoundException;
import hrms.hrms_backend.business.abstracts.HrmEmployeeService;
import hrms.hrms_backend.dataacceess.abstracts.HrmEmployeeRepository;
import hrms.hrms_backend.dataacceess.abstracts.LeaveBalanceRepository;
import hrms.hrms_backend.entities.concretes.HrmEmployee;
import hrms.hrms_backend.entities.concretes.LeaveBalance;
import hrms.hrms_backend.entities.enums.EmploymentStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HrmEmployeeManager implements HrmEmployeeService {

    private final HrmEmployeeRepository hrmEmployeeRepository;
    private final LeaveBalanceRepository leaveBalanceRepository;

    public HrmEmployeeManager(HrmEmployeeRepository hrmEmployeeRepository, LeaveBalanceRepository leaveBalanceRepository) {
        this.hrmEmployeeRepository = hrmEmployeeRepository;
        this.leaveBalanceRepository = leaveBalanceRepository;
    }

    @Override
    public HrmEmployee addEmployee(HrmEmployee employee) {
        if (hrmEmployeeRepository.existsByEmail(employee.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        // Employee code generation
        long count = hrmEmployeeRepository.count() + 1;
        String employeeCode = "EMP" + String.format("%03d", count);
        employee.setEmployeeCode(employeeCode);

        employee.setEmploymentStatus(EmploymentStatus.ACTIVE);

        HrmEmployee savedEmployee = hrmEmployeeRepository.save(employee);

        LeaveBalance leaveBalance = new LeaveBalance();
        leaveBalance.setEmployee(savedEmployee);
        leaveBalance.setCasualLeaves(12);
        leaveBalance.setSickLeaves(10);
        leaveBalance.setPaidLeaves(15);

        leaveBalanceRepository.save(leaveBalance);

        return savedEmployee;
    }

    @Override
    public List<HrmEmployee> getAllEmployees() {
        return hrmEmployeeRepository.findAll();
    }

    @Override
    public HrmEmployee updateEmployee(Long id, HrmEmployee updatedEmployee) {

        HrmEmployee existingEmployee = hrmEmployeeRepository
                .findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id " + id));

        existingEmployee.setFirstName(updatedEmployee.getFirstName());
        existingEmployee.setLastName(updatedEmployee.getLastName());
        existingEmployee.setPhone(updatedEmployee.getPhone());
        existingEmployee.setDepartment(updatedEmployee.getDepartment());
        existingEmployee.setRole(updatedEmployee.getRole());

        if (updatedEmployee.getEmploymentStatus() != null) {
            existingEmployee.setEmploymentStatus(updatedEmployee.getEmploymentStatus());
        }

        return hrmEmployeeRepository.save(existingEmployee);
    }

    @Override
    public HrmEmployee getEmployeeById(Long id) {
        return hrmEmployeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
    }
}

package hrms.hrms_backend.api.controllers;

import hrms.hrms_backend.api.controllers.exception.EmailAlreadyExistsException;
import hrms.hrms_backend.api.controllers.exception.EmployeeNotFoundException;
import hrms.hrms_backend.business.abstracts.HrmEmployeeService;
import hrms.hrms_backend.dataacceess.abstracts.HrmEmployeeRepository;
import hrms.hrms_backend.entities.concretes.HrmEmployee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/hrm/employees")
public class HrmEmployeeController {

    private final HrmEmployeeService hrmEmployeeService;
    private final HrmEmployeeRepository hrmEmployeeRepository;

    public HrmEmployeeController(HrmEmployeeService hrmEmployeeService, HrmEmployeeRepository hrmEmployeeRepository) {
        this.hrmEmployeeService = hrmEmployeeService;
        this.hrmEmployeeRepository = hrmEmployeeRepository;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addEmployee(@Valid @RequestBody HrmEmployee employee) {
        try {
            HrmEmployee savedEmployee = hrmEmployeeService.addEmployee(employee);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);

        } catch (EmailAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to add employee");
        }
    }

    @GetMapping("/get")
    public List<HrmEmployee> getAllEmployees() {
        return hrmEmployeeService.getAllEmployees();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(
            @PathVariable Long id,
            @RequestBody HrmEmployee employee) {

        try {
            HrmEmployee updated = hrmEmployeeService.updateEmployee(id, employee);
            return ResponseEntity.ok(updated);

        } catch (EmployeeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to update employee");
        }
    }

    @GetMapping("/count")
    public long getEmployeeCount() {
        return hrmEmployeeRepository.count();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HrmEmployee> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(
                hrmEmployeeService.getEmployeeById(id)
        );
    }
}

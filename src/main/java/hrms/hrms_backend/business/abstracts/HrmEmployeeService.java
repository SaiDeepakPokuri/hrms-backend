package hrms.hrms_backend.business.abstracts;

import hrms.hrms_backend.entities.concretes.HrmEmployee;

import java.util.List;

public interface HrmEmployeeService {

    HrmEmployee addEmployee(HrmEmployee employee);

    List<HrmEmployee> getAllEmployees();

    HrmEmployee updateEmployee(Long id, HrmEmployee updatedEmployee);

    HrmEmployee getEmployeeById(Long id);

}

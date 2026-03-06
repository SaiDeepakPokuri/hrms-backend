package hrms.hrms_backend.entities.concretes;

import com.fasterxml.jackson.annotation.JsonFormat;
import hrms.hrms_backend.entities.enums.EmploymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "hrm_employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HrmEmployee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String employeeCode;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Phone number is required")
    private String phone;

    @NotBlank(message = "Department is required")
    private String department;

    @NotBlank(message = "Role is required")
    private String role;

    @NotNull(message = "Joining date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate joiningDate;

    @Enumerated(EnumType.STRING)
    private EmploymentStatus employmentStatus;
}


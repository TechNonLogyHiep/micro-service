package vti.microservice.department.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.sql.Date;

@Getter
@Builder
public class DepartmentDTO {
    private String name;

    private String type;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdDate;
}
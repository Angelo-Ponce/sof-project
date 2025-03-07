package com.sof.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PersonDTO {

    @EqualsAndHashCode.Include
    private Long personId;

    @NotEmpty(message = "{dni.empty}")
    @Size(max = 10, message = "{dni.size}")
    private String identification;

    @NotEmpty(message = "{name.empty}")
    @Size(min = 3, max = 100, message = "{name.size}")
    private String name;

    @NotEmpty(message = "{gender.empty}")
    @Size(max = 50, message = "{gender.size.max}")
    private String gender;

    @NotNull(message = "{age.empty}")
    private Integer age;

    @Size(max = 100, message = "{address.size.max}")
    private String address;

    @Size(max = 15, message = "{phone.size.max}")
    private String phone;
}

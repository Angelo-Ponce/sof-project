package com.sof.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO extends PersonDTO {

    @NotEmpty(message = "{clientId.empty}")
    private String clientId;

    @NotEmpty(message = "{password.empty}")
    @Size(max = 25, message = "{password.size.max}")
    private String password;

    private Boolean status;
}

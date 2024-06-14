package com.sgutsev.library.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Component
public class UserPasswords {
    @NotEmpty(message = "Current password card should not be empty")
    @Size(min = 15, max = 30, message = "Password should be between 15 and 30 characters")
    private String currentPassword;

    @NotEmpty(message = "New password card should not be empty")
    @Size(min = 15, max = 30, message = "Password should be between 15 and 30 characters")
    private String newPassword;

    @NotEmpty(message = "Confirm new password card should not be empty")
    @Size(min = 15, max = 30, message = "Password should be between 15 and 30 characters")
    private String confirmNewPassword;
}

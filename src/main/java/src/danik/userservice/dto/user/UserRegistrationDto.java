package src.danik.userservice.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserRegistrationDto {
    @NotNull(message = "Username can't be null")
    @Size(min = 3, max = 32, message = "Username must be between 3-32 characters")
    private String username;

    @Email
    @NotNull(message = "Email can't be null")
    @NotBlank(message = "Email can't be empty")
    private String email;

    @NotNull(message = "Password can't be null")
    @NotBlank(message = "Password can't be empty")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    private String phone;

    private String aboutMe;
}

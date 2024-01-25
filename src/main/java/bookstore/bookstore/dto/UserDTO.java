package bookstore.bookstore.dto;

import lombok.Data;

@Data
public class UserDTO extends JwtModel{
    private final String currentPassword;
    private final String newPassword;
}

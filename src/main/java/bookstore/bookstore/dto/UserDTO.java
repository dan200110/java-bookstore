package bookstore.bookstore.dto;

import lombok.Data;

@Data
public class UserDTO extends JwtModel{
    private String currentPassword;
    private String newPassword;
}

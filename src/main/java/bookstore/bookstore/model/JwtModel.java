package bookstore.bookstore.model;

import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class JwtModel {
    private final String jwt;
}

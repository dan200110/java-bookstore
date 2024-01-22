package bookstore.bookstore.model;

import lombok.*;

@Data
@NoArgsConstructor
public class JwtModel {
    private String jwt;

    public JwtModel(String jwt) {
        this.jwt = jwt;
    }
}
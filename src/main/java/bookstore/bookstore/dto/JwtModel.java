package bookstore.bookstore.dto;

import lombok.*;

@Data
@NoArgsConstructor
public class JwtModel {
    protected String jwt;
    public JwtModel(String jwt) {
        this.jwt = jwt;
    }
}

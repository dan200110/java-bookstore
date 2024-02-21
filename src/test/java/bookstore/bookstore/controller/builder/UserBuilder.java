package bookstore.bookstore.controller.builder;

import bookstore.bookstore.model.UsersEntity;

public class UserBuilder extends BaseBuilder<UsersEntity> {
    public UserBuilder() {
        super(UsersEntity.class);
    }

    public UserBuilder customer() {
        return this
                .withId(1)
                .withUsername("user_name")
                .withRole("normal");
    }


    public UserBuilder withId(int id) {
        data.setId(id);
        return this;
    }

    public UserBuilder withUsername(String username) {
        data.setUserName(username);
        return this;
    }


    public UserBuilder withRole(String usertype) {
        data.setUserType(usertype);
        return this;
    }
}

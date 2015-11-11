package com.fortmocks.core.model.user.message;

import com.fortmocks.core.model.Output;
import com.fortmocks.core.model.user.User;
import com.fortmocks.core.model.user.dto.UserDto;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class UpdateUserOutput implements Output {

    private UserDto updatedUser;

    public UserDto getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(UserDto updatedUser) {
        this.updatedUser = updatedUser;
    }
}
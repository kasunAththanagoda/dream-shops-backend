package com.ecommerce.dreamshops.service.user;

import com.ecommerce.dreamshops.dto.UserDto;
import com.ecommerce.dreamshops.model.User;
import com.ecommerce.dreamshops.request.CreateUserRequest;
import com.ecommerce.dreamshops.request.UserUpdateRequest;

public interface IUserService {

    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);

    UserDto convertUserToDto(User user);
//
//    User getAuthenticatedUser();
}

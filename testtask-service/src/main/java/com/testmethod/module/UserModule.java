package com.testmethod.module;

import com.testmethod.common.ApiException;
import com.testmethod.models.User;

import java.util.List;

/**
 * @author Denis Yakovenko
 */
public interface UserModule {

    List<User> getUsers() throws ApiException;
}

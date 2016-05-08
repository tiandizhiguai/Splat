package org.cgw.splat.test.service;

import org.cgw.splat.test.param.UserParam;
import org.cgw.splat.test.result.UserResult;

public interface UserService {

    public UserResult getUser(UserParam param);
}

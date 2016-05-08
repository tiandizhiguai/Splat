package org.cgw.splat.test.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cgw.splat.annotation.Service;
import org.cgw.splat.test.param.UserParam;
import org.cgw.splat.test.result.User;
import org.cgw.splat.test.result.UserResult;
import org.cgw.splat.test.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

    public UserResult getUser(UserParam param) {
        UserResult result = new UserResult();

        User user = new User(param.getName(), param.getAge());
        List<User> userList = new ArrayList<User>();
        userList.add(new User("ÁõµÂ»ª", 60));
        userList.add(new User("ÁõÒà·Æ", 35));
        userList.add(new User("¸ğÓÅ", 40));
        User[] userArray = new User[2];
        userArray[0] = new User("ÎÄÕÂ", 30);
        userArray[1] = new User("ÂíÒÁ¬P", 34);
        Map<String, User> userMap = new HashMap<String, User>();
        userMap.put("liuzhiling", new User("ÁõÖ¾Áá", 20));
        userMap.put("zhaowei", new User("ÕÔŞ±", 50));
        userMap.put("huangxiaoming", new User("»ÆÏşÃ÷", 50));

        result.setUser(user);
        result.setUserArray(userArray);
        result.setUserList(userList);
        result.setUserMap(userMap);

        UserResult result2 = new UserResult();
        result2.setUserResult(result);

        return result2;
    }
}

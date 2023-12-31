/*
 * Copyright © ${project.inceptionYear} organization baomidou
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.baomidou.sample.boot3.service.impl;


import com.baomidou.dynamic.datasource.annotation.Slave;
import com.baomidou.sample.boot3.entity.User;
import com.baomidou.sample.boot3.mapper.UserMapper;
import com.baomidou.sample.boot3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> selectMasterUsers() {
        return userMapper.selectUsers();
    }

    @Slave
    @Override
    public List<User> selectSlaveUsers() {
        return userMapper.selectUsers();
    }

    @Override
    public void addUser(User user) {
        userMapper.addUser(user.getName(), user.getAge());
    }

    @Override
    public void deleteUserById(Long id) {
        userMapper.deleteUserById(id);
    }
}
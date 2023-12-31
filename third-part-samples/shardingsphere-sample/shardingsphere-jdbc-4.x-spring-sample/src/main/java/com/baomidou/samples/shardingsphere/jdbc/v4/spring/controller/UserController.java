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
package com.baomidou.samples.shardingsphere.jdbc.v4.spring.controller;


import com.baomidou.samples.shardingsphere.jdbc.v4.spring.entity.User;
import com.baomidou.samples.shardingsphere.jdbc.v4.spring.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private static final Random RANDOM = new Random();
    private final UserService userService;

    /**
     * 动态数据源的主库
     *
     * @return
     */
    @GetMapping("master")
    public List<User> master() {
        return userService.selectUsersFromMaster();
    }


    /**
     * 动态数据源代理的sharding-jdbc的从库,经过两次选择
     * 第一次:  dynamic-ds => sharding-ds
     * 第二次:  sharding-ds => slave
     *
     * @return
     */
    @GetMapping("sharding")
    public List<User> shardingSlave() {
        return userService.selectUsersFromShardingSlave();
    }

    /**
     * 动态数据源代理的sharding-jdbc的主库,经过两次选择
     * 第一次:  dynamic-ds => sharding-ds
     * 第二次:  sharding-ds => master
     *
     * @return
     */
    @PostMapping("sharding")
    public User addUser() {
        User user = new User();
        user.setName("测试用户" + RANDOM.nextInt());
        user.setAge(RANDOM.nextInt(100));
        userService.addUser(user);
        return user;
    }

    /**
     * 动态数据源代理的sharding-jdbc的主库,经过两次选择
     * 第一次:  dynamic-ds => sharding-ds
     * 第二次:  sharding-ds => master
     *
     * @return
     */
    @DeleteMapping("sharding/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return "成功删除用户" + id;
    }
}

package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.dto.Account;
import com.example.mapper.AccountMapper;
import com.example.service.AccountService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = this.findAccountByNameOrEmail(username);
        if (account == null)
            throw new UsernameNotFoundException("用户名或密码错误！");
        return User
                .withUsername(username)
//                这里不要用account的name() 我们不知道用户传来的是用户名还是密码。
                .password(account.getPassword())
                .roles(account.getRole())
                .build();
    }
    public Account findAccountByNameOrEmail(String text){
        return  this.query()
                .eq("username",text).or()
                .eq("email",text)
                .one();
    }
}

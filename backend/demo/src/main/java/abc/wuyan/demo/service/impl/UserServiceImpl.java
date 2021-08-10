package abc.wuyan.demo.service.impl;

import abc.wuyan.demo.model.User;
import abc.wuyan.demo.mapper.UserMapper;
import abc.wuyan.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> findAllUsers() {
        return userMapper.findAllUsers();
    }

    @Override
    public User findById(int id) {
        return userMapper.findUserById(id);
    }

    @Override
    public User findByName(String name) {
        return userMapper.findUserByName(name);
    }

    @Override
    public void insert(User user) {
        userMapper.insert(user);
    }

    @Override
    public void update(User user) {
        userMapper.update(user);
    }

    @Override
    public void delete(User user) {
        userMapper.delete(user);
    }

    @Override
    public User getByNamePwd(User user) {
        return userMapper.findUserByParam(user);
    }

}

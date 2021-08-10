package abc.wuyan.demo.service;

import abc.wuyan.demo.model.User;

import java.util.List;

public interface UserService {
    public List<User> findAllUsers();
    public User findById(int id);
    public User findByName(String name);
    public void insert(User user);
    public void update(User user);
    public void delete(User user);
    public User getByNamePwd(User user);
}

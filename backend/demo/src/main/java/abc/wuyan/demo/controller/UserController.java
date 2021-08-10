package abc.wuyan.demo.controller;

import abc.wuyan.demo.model.RespInfo;
import abc.wuyan.demo.model.User;
import abc.wuyan.demo.service.UserService;

import abc.wuyan.demo.util.CacheUtil;
import abc.wuyan.demo.util.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/info")
    public User findById(@RequestParam Integer uid){
        User u = userService.findById(uid);
        u.setPassword("");
        return u;
    }
    @RequestMapping("/allUsers")
    public List<User> findAllUsers(){
        return userService.findAllUsers().stream().map(u -> {
            u.setPassword("");
            return u;
        }).collect(Collectors.toList());
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public RespInfo save(@RequestBody Map<String, Object> requestMap, HttpSession session) {
        try{
            //从session中获取随机数
            String inputStr = requestMap.get("verifycode").toString();
            String random = (String) session.getAttribute("RANDOMVALIDATECODEKEY");
            if (random == null) {
                return new RespInfo(Consts.ERROR_CODE, null,"请重新获取验证码");
            }
            if (random.equals(inputStr)) {
                User user = new User();
                user.setName(requestMap.get("name").toString());
                user.setPassword(requestMap.get("password").toString());
                user.setSex(requestMap.get("sex").toString());
                user.setEmail(requestMap.get("email").toString());
                user.setRole("user");
                userService.insert(user);
                return new RespInfo(Consts.SUCCESS_CODE, user, "注册成功");
            } else {
                return new RespInfo(Consts.ERROR_CODE, null,"验证码错误");
            }
        }catch (Exception e){
            return new RespInfo(Consts.ERROR_CODE, null,"验证码校验失败");
        }
    }
}

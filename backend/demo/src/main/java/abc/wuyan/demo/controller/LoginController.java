package abc.wuyan.demo.controller;

import abc.wuyan.demo.model.User;
import abc.wuyan.demo.service.TokenService;
import abc.wuyan.demo.util.Consts;
import abc.wuyan.demo.service.UserService;
import abc.wuyan.demo.util.RandomValidateCodeUtil;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Map login(@RequestBody User user) {
        Map<Object, Object> map = new HashMap<>();
        User u = userService.getByNamePwd(user);
        int code = Consts.SUCCESS_CODE;
        if (u == null) {
            code = Consts.ERROR_CODE;
            map.put("code", code);
            map.put("message", "登录失败,密码错误或用户不存在");
            return map;
        }else {
            String token = tokenService.getToken(user);
            user.setPassword("");
            User a = userService.findByName(u.getName());
            map.put("token", token);
            map.put("user", a);
            return map;
        }
    }

    @RequestMapping("/verifyCode")
    public void verifyCode(HttpServletRequest request, HttpServletResponse response) {
        Logger logger = LoggerFactory.getLogger(LoginController.class);
        try {
            response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
            response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil();
            randomValidateCode.getRandcode(request, response);//输出验证码图片方法
        } catch (Exception e) {
            logger.error("获取验证码失败>>>> ", e);
        }
    }
}

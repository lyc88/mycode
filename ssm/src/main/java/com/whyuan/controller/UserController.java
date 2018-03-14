package com.whyuan.controller;

import com.whyuan.pojo.User;
import com.whyuan.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

//localhost:8080/user/showUser?id=1
@Controller
@RequestMapping("/user")
public class UserController extends BaseController{
    @Autowired
    private IUserService userService;

    @RequestMapping("/showUser")
    public String toIndex(HttpServletRequest request, Model model) throws Exception {
        int userId = Integer.parseInt(request.getParameter("id"));
        User user = this.userService.getUserById(userId);
        model.addAttribute("user",user);
        return "showUser";
    }

    /*
        调试：
    *   1.在Tomcat的Deploy对应的 Artifacts这里，需要选择tmall_ssm:war exploded, 不要选择第一个tmall_ssm:war.
        因为选war的话，每次修改了jsp都要重新打包成war才能看到效果，不便于观察jsp修改后的效果
    *
    *
    * 2.在Tomcat的Server 下面两个 On ： 都设置为 Update classes and resources.
        其作用是把类和资源文件修改 同步更新掉
        On 'Update' action 是手动更新，或者点击快捷键CTRL+10更新
        On frame deactivation 是当idea失去焦点，比如打开浏览器的时候自动更新
        所以都勾上就好了

        3.运行Tomcat的时候，采用debug模式，这样 勾上 Update classes and resources
        这一步导致的类自动更新就会引起Tomcat的reload，那么就不需要重新启动Tomcat也能看到效果了，
        便于修改代码和观察效果
    * */
}
   /* CREATE TABLE `t_user` (
        `id` int(11) NOT NULL,
        `username` varchar(32) COLLATE utf8_bin NOT NULL,
        `password` varchar(32) COLLATE utf8_bin NOT NULL,
        `age` int(4) NOT NULL,
        PRIMARY KEY (`id`)
        ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

        INSERT INTO `t_user` VALUES ('1', 'whyuan', '123456', '18');*/
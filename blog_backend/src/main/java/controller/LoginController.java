package controller;


import bean.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    @RequestMapping("/login")

    public @ResponseBody Map<String,Object> login(@RequestBody Map data,HttpServletRequest request){

        String username=(String)data.get("username");
        String password=(String)data.get("password");

        if("admin".equals(username) && "123".equals(password)){
            System.out.println("login");
            Person p=new Person(2,22,"zrj","Java web");
            Map<String, Object> map = new HashMap();
            map.put("user",p);
            map.put("msg","登录成功");
            //request.getSession().setAttribute("user",p);
            //System.out.println(request.getSession().getId());
            return map;

        }else{
            return null;
        }

    }
}

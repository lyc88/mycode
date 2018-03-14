package com.whyuan.controller;

import com.whyuan.controller.support.ResponseData;
import com.whyuan.controller.support.ResponseEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/index/")
public class IndexController extends BaseController{

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(HttpServletRequest request){
        if(request.getSession().getAttribute("user")==null){
            return "/login";
        }
        return "/index";
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(){


        return "/login";
    }

    @RequestMapping(value="/submitLogin",method=RequestMethod.POST)
    @ResponseBody
    public ResponseData submitLogin(HttpServletRequest request, String loginname, String password){

        ResponseData data=new ResponseData();
        data.setMessage("系统繁忙");
        data.setStatus(ResponseEnum.FAILED.getCode());
        return data;
    }

    /**
     * 退出
     * @return
     */
    @RequestMapping(value="/logout",method =RequestMethod.GET)
    public String logout(HttpServletRequest request){
        try {
            request.getSession().removeAttribute("user");
        } catch (Exception e) {
            logger.error("errorMessage:" + e.getMessage());
        }
        return redirectTo("/index/login.shtml");
    }
}

package com.whyuan.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.whyuan.pojo.Category;
import com.whyuan.service.ICategoryService;
import com.whyuan.utils.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

//localhost:8080/listCategory
//测试数据展示与分页功能
@Controller
@RequestMapping("")
public class CategoryController extends BaseController{
    @Resource
    ICategoryService categoryService;

    @RequestMapping("listCategory")
    public ModelAndView listCategory(Page page){
        ModelAndView modelAndView=new ModelAndView();
        PageHelper.offsetPage(page.getStart(),5);
        List<Category> cs=categoryService.list();
        int total=(int) new PageInfo<>(cs).getTotal();
        page.caculateLast(total);

        // 放入转发参数
        modelAndView.addObject("cs", cs);
        // 放入jsp路径
        modelAndView.setViewName("listCategory");
        return modelAndView;
    }
}

	/*
1.建库
create database how2java;
use how2java;
2.建表
CREATE TABLE category_ (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(30) ,
  PRIMARY KEY (id)
) DEFAULT CHARSET=UTF8;
3 . 准备数据
insert into category_ values(null,"category1");
insert into category_ values(null,"category2");
insert into category_ values(null,"category3");
insert into category_ values(null,"category4");
insert into category_ values(null,"category5");
select * from category_


	*
	*
	*
	* */

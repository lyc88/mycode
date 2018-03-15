package com.how2java.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.pojo.Category;
import com.how2java.service.CategoryService;
import com.how2java.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

// 告诉spring mvc这是一个控制器类
//http://localhost:8080/listCategory
@Controller
@RequestMapping("")
public class CategoryController {
	@Autowired
	CategoryService categoryService;

	@RequestMapping("listCategory")
	public ModelAndView listCategory(Page page){
		ModelAndView mav = new ModelAndView();
		PageHelper.offsetPage(page.getStart(),5);
		List<Category> cs= categoryService.list();
		int total = (int) new PageInfo<>(cs).getTotal();
		
		page.caculateLast(total);
		
		// 放入转发参数
		mav.addObject("cs", cs);
		// 放入jsp路径
		mav.setViewName("listCategory");
		return mav;
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
}

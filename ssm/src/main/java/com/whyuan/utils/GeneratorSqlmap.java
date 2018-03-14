package com.whyuan.utils;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
/*
 * mybatis逆向工程:根据数据库表设计自动生成
 * dao, mapper,pojo
 *
 *请先在generatorConfig.xml定义表
<table tableName="aaaaa" domainObjectName="AAAAA" enableCountByExample="false" enableUpdateByExample="false" enableDeleteByExample="false" enableSelectByExample="false" selectByExampleQueryId="false"/>

 * */
public class GeneratorSqlmap {
	public void generator() throws Exception{

        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        //指定 逆向工程配置文件

        ///D:/AllWorkSpace/IntellijWorkSpace/mycode/ssm/target/classes/
        String path=GeneratorSqlmap.class.getClassLoader().getResource(".").getFile();
        //D:/AllWorkSpace/IntellijWorkSpace/mycode/ssm/
        path = path.substring(1, path.lastIndexOf("target"));
        //资源文件路径
        path += "src/main/resources/";
        //定位到ssm/src/main/resources/generatorConfig.xml
        File configFile = new File(path+"generatorConfig.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,
                callback, warnings);
        myBatisGenerator.generate(null);

    } 
    public static void main(String[] args) throws Exception {
        try {
            GeneratorSqlmap generatorSqlmap = new GeneratorSqlmap();
            generatorSqlmap.generator();
            System.out.println("finished,please check {dao,mapper,pojo}");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

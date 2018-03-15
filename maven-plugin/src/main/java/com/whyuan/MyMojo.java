package com.whyuan;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.util.List;

/*
 *自定义插件：
 * 1、新建项目
 * 2、修改pom.xml打包方式：<packaging>maven-plugin</packaging>
 * 3、引入自定义插件依赖
 * 4、书写类继承AbstractMojo
 * 5、声明@Mojo name为插件的goal
 * 6、mvn clean install 下载到本地仓库或者mvn clean deploy下载到远端
 * 7、以上操作完成，其他项目就可以通过<plugin>依赖了
 */
@Mojo(name = "whyuan",defaultPhase = LifecyclePhase.PACKAGE)
public class MyMojo extends AbstractMojo {
    //参数传入，可通过<configuration>标签读取
    @Parameter
    private String msg;

    @Parameter
    private List<String> list;
    public void execute() throws MojoExecutionException,MojoFailureException{
        System.out.println("自定义插件");
    }
}

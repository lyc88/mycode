package com.gupao.mic.vip.demo;

import javax.jws.WebService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * springcxf+jaxrs实现REST webservice接口
 * JAX-RS(Java API for RESTful Web Services)
 * jaxrs充当SpringMVC的功能，最大不同，jaxrs舍弃视图解析过程，转而使用方法所返回的对象
 *
 *
 *  CXF轻量级容器：实现Web Services 的发布和使用
 *  CXF 框架是一种基于 Servlet 技术的 SOA 应用开发框架，要正常运行基于 CXF 应用框架开发的企业应用，
 *  除了 CXF 框架本身之外，还需要 JDK 和 Servlet 容器的支持。
 */
@WebService //指定将此类发布成一个ws
@Path(value="/users/")
public interface UserService {

    @GET
    @Path("/")  //http://localhost:8080/ws/users/
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    List<User> getUsers();

    @DELETE
    @Path("{id}")  //http://localhost:8080/ws/users/0
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML}) //请求accept
    Response delete(@PathParam("id") int id);

    @GET
    @Path("{id}") //http://localhost:8080/ws/users/1
    @Produces(MediaType.APPLICATION_JSON)
    User getUser(@PathParam("id") int id);

    @POST
    @Path("add")
    Response insert(User user);

    @PUT
    @Path("update")
    Response update(User user);


}


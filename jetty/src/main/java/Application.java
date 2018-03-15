import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import com.whyuan.configuration.JettyConf;
import com.whyuan.controller.Controller;

import com.whyuan.utils.UMetrics;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.BlockingArrayQueue;
import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.DispatcherType;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
*@Author: whyuan
*@Description:
 * 该类为程序入口
 * 负责Jetty配置初始化，（端口，线程池等，使用ServletContextHandler接入Controller(Filter)）
 * 服务启动，
 * Jetty状态监控。
*@Date: Created in 10:30 2017/12/29
*@Modified By:
*/
//继承LifeCycle,可用来监控Server生命周期
public class Application implements LifeCycle{
    //日志包SLF4J(Simple logging Facade for Java)：org.slf4j
    private final static Logger logger = LoggerFactory.getLogger(Application.class);
    //Jetty配置的对象映射类
    private JettyConf jettyConf;
    //Jetty Http Server
    private Server server=null;

    public static void main(String[] args) throws Exception{
        Application application=new Application();
        application.start();
    }

    @Override
    public void start()throws Exception{
        //JettyConf{port=8888, minThreads=10, maxThreads=150}
        initJettyConf();

        //嵌入式Jetty,要运行Filter,Servlet需创建ServletContextHandler.
        ServletContextHandler handler=new ServletContextHandler(/*ServletContextHandler.SESSIONS*/);
        //ServletPath设置为/common/*
        handler.addFilter(new FilterHolder(new Controller()),"/common/*", EnumSet.of(DispatcherType.REQUEST));

        //TODO 可根据不同端口来设置上下文路径
        //使用自定义默认端口8888，设置下文路径，用来测试
        if(jettyConf.getPort()==8888)handler.setContextPath("/web/test");

        //添加默认的Servlet,匹配URL路径 /
        handler.addServlet(DefaultServlet.class, "/");

        System.out.println(Application.class+"{ContextPath}："+handler.getServletContext().getContextPath());
        logger.info("{ServletContext}：{}",handler.getContextPath());
        //Jetty线程池实现QueuedThreadPool，队列实现BlockingArrayQueue
        // （别导错包：org.eclipse.jetty.util）
        final QueuedThreadPool queuedThreadPool=new QueuedThreadPool(
                jettyConf.getMaxThreads(),
                jettyConf.getMinThreads(),
                (int)TimeUnit.SECONDS.toMillis(90),
                new BlockingArrayQueue<>(10,10,1000)
        );
        queuedThreadPool.setStopTimeout((int)TimeUnit.SECONDS.toMillis(6));
        queuedThreadPool.setDetailedDump(false);

        // MetricRegistry 为metric的容器，指定reporter,name,time。
        MetricRegistry registry= UMetrics.create(logger,"application",30,TimeUnit.SECONDS);

        //注册Gauge服务，每30秒将进行一次Jetty线程池数量，队列数量统计，并以日志形式打印。
        registry.register("jetty.thread",(Gauge<Integer>)queuedThreadPool::getThreads);
        registry.register("jetty.queue.size",(Gauge<Integer>)queuedThreadPool::getQueueSize);

        server=new Server(queuedThreadPool);
        ServerConnector connector=new ServerConnector(server);
        connector.setHost("0.0.0.0");
        connector.setPort(jettyConf.getPort());
        connector.setIdleTimeout(50000);
        connector.setSoLingerTime(-1);
        connector.setAcceptorPriorityDelta(0);
        //connector.setSelectorPriorityDelta(0);
        connector.setAcceptQueueSize(0);

        server.addConnector(connector);
        server.setHandler(handler);
        server.setStopAtShutdown(true);
        server.setDumpAfterStart(false);
        server.setDumpBeforeStop(false);

        server.start();//启动服务
        server.join();//加入线程池
    }

    /**
     *@Author: whyuan
     *@Description: Jetty配置初始化
     *@Date: Created in 18:14 2017/12/27
     *@Modified By:
     */
    private void initJettyConf(){
        //TODO 放入空的Map,用来实例默认Jetty配置（若要灵活配置：可将Jetty配置写入Properties文件）
        Map<String,String> jconf=new HashMap<String,String>();
        jettyConf=new JettyConf(jconf,"jetty");
        System.out.println(Application.class+"{jettyConf}："+jettyConf);
        logger.info("{jettyConf}：{}",jettyConf);
    }
    @Override
    public void stop() throws Exception {
        server.stop();
    }

    @Override
    public boolean isRunning() {
        return server.isRunning();
    }

    @Override
    public boolean isStarted() {
        return server.isStarted();
    }

    @Override
    public boolean isStarting() {
        return server.isStarting();
    }

    @Override
    public boolean isStopping() {
        return server.isStopping();
    }

    @Override
    public boolean isStopped() {
        return server.isStopped();
    }

    @Override
    public boolean isFailed() {
        return server.isFailed();
    }

    @Override
    public void addLifeCycleListener(Listener listener) {

    }

    @Override
    public void removeLifeCycleListener(Listener listener) {

    }
}

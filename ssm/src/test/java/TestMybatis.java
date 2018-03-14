/*
import com.whyuan.dao.UserMapper;
import com.whyuan.pojo.User;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class TestMybatis {
    private static Logger logger = Logger.getLogger(TestMybatis.class);

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testInsert() throws Exception {
        User user=new User();
        user.setId(2);
        userMapper.insert(user);
    }

    @Test
    public void testList() {
        List<User> cs=userMapper.list();
        for (User u : cs) {
            System.out.println(u.getId());
        }
    }
}
*/

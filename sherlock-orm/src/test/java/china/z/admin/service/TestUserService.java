package china.z.admin.service;

import china.z.util.DateUtil;
import china.z.util.Md5Util;
import china.z.util.UUIDGenerator;
import china.z.admin.entity.TUserEntity;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring.xml", "classpath:spring/spring-hibernate.xml"})
public class TestUserService {

    private static Logger LOGING = Logger.getLogger(TestUserService.class);

    private IUserService userService;

    public IUserService getUserService() {
        return userService;
    }

    @Autowired
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    /**
     * 保存用户
     */
    @Test
    public void saveUser() {
        TUserEntity user = new TUserEntity();
        user.setUuid(UUIDGenerator.getUUID());
        user.setPhone("10086");
        user.setEmail("10086@yd.com");
        user.setRemark("测帐");
        user.setPassword(Md5Util.getMd5("admin"));
        user.setUpdateTime(DateUtil.currentFormatDate(DateUtil.DATE_TO_STRING_DETAIAL_PATTERN));
        user.setCreateTime(DateUtil.currentFormatDate(DateUtil.DATE_TO_STRING_DETAIAL_PATTERN));
        user.setState(1);
        int saveUserResult = userService.saveUser(user);
        if (saveUserResult == 1) {
            LOGING.info("saveUser()方法保存用户成功");
        } else {
            LOGING.info("saveUser()方法保存用户失败");
        }
    }

    /**
     * 验证是否有此用户
     */
    @Test
    public void userVerify() {
        boolean verify = userService.userVerify("dellzyz@qq.com");
        if (verify) {
            LOGING.info("userVerify()方法验证用户是否存在{'dellzyz@qq.com','存在'}");
        } else {
            LOGING.info("userVerify()方法验证用户是否存在{'dellzyz@qq.com','不存在'}");
        }
    }

    /**
     * 登录验证
     */
    @Test
    public void userLoginVerify() {
        boolean verify = userService.userLoginVerify("864867366@qq.com", "admin");
        LOGING.info("userLoginVerify()方法验证用户登录:" + verify);
    }

}

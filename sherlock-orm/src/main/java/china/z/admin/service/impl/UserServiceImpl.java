package china.z.admin.service.impl;

import china.z.util.Md5Util;
import china.z.util.RegExpValidatorUtils;
import china.z.admin.context.Messages;
import china.z.admin.dao.IUserDao;
import china.z.admin.entity.TUserEntity;
import china.z.admin.service.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("userService")
public class UserServiceImpl implements IUserService {

    private IUserDao userDao;

    public IUserDao getUserDao() {
        return userDao;
    }

    @Autowired
    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * 新增用户
     * @return
     */
    public int saveUser(TUserEntity user){
        try {
            userDao.save(user);
            return Messages.OK;
        }catch (Exception e){
            return Messages.NO;
        }
    }

    /**
     * 验证是否有用户
     *
     * @param code
     * @return
     */
    public boolean userVerify(String code) {
        boolean email = RegExpValidatorUtils.isEmail(code);
        boolean phone = RegExpValidatorUtils.IsHandset(code);
        if (email || phone) {
            String hql = "from TUserEntity where email=? or phone=?";
            TUserEntity userEntity = userDao.getByHql(hql, code, code);
            if (userEntity != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * 验证用户登录
     *
     * @param loginNum
     * @param pwd
     * @return
     */
    public boolean userLoginVerify(String loginNum, String pwd) {
        boolean email = RegExpValidatorUtils.isEmail(loginNum);
        boolean phone = RegExpValidatorUtils.IsHandset(loginNum);
        if (email || phone) {
            String md5 = Md5Util.getMd5(pwd);
            String hql = "from TUserEntity where (email=? or phone=?) and password=?";
            TUserEntity userEntity = userDao.getByHql(hql, loginNum, loginNum,md5);
            if (userEntity!=null){
                return true;
            }
        }
        return false;
    }
}

package china.z.admin.service;


import china.z.admin.entity.TUserEntity;

public interface IUserService {

    /**
     * 新增用户
     * @return
     */
    public int saveUser(TUserEntity user);

    /**
     * 验证是否有用户
     * @param code
     * @return
     */
    public boolean userVerify(String code);

    /**
     * 验证用户登录
     * @param loginNum
     * @param pwd
     * @return
     */
    public boolean userLoginVerify(String loginNum,String pwd);
}

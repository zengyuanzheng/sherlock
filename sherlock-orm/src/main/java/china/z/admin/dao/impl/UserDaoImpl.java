package china.z.admin.dao.impl;

import china.z.admin.dao.IUserDao;
import china.z.admin.entity.TUserEntity;
import china.z.base.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<TUserEntity> implements IUserDao {

}

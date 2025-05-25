package site.opencs.plotmax.hrm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import site.opencs.plotmax.hrm.entity.User;
import site.opencs.plotmax.hrm.mapper.EmployeeMapper;
import site.opencs.plotmax.hrm.mapper.UserMapper;
import site.opencs.plotmax.hrm.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author plotmax
 * @since 2025-05-25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User selectByUsername(String username) {
        return userMapper.selectByUsername(username);
    }
}

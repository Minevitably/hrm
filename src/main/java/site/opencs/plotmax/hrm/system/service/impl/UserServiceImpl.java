package site.opencs.plotmax.hrm.system.service.impl;

import site.opencs.plotmax.hrm.system.entity.User;
import site.opencs.plotmax.hrm.system.mapper.UserMapper;
import site.opencs.plotmax.hrm.system.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author plotmax
 * @since 2025-05-23
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}

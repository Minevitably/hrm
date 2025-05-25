package site.opencs.plotmax.hrm.mapper;

import org.apache.ibatis.annotations.Select;
import site.opencs.plotmax.hrm.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 系统用户表 Mapper 接口
 * </p>
 *
 * @author plotmax
 * @since 2025-05-25
 */
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT * FROM sys_user WHERE username = #{username} AND deleted = 0")
    User selectByUsername(String username);

    @Select("SELECT r.role_code FROM sys_role r " +
            "JOIN sys_user_role ur ON r.id = ur.role_id " +
            "JOIN sys_user u ON ur.user_id = u.id " +
            "WHERE u.username = #{username} AND r.deleted = 0 AND u.deleted = 0")
    List<String> selectRoleCodesByUsername(String username);
}

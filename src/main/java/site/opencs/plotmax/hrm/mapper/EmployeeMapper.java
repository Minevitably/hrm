package site.opencs.plotmax.hrm.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import site.opencs.plotmax.hrm.dto.response.EmployeeResponse;
import site.opencs.plotmax.hrm.entity.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 员工表 Mapper 接口
 * </p>
 *
 * @author plotmax
 * @since 2025-05-25
 */
public interface EmployeeMapper extends BaseMapper<Employee> {

    @Select("SELECT e.*, d.name as department_name, p.name as position_name " +
            "FROM sys_employee e " +
            "LEFT JOIN sys_department d ON e.department_id = d.id " +
            "LEFT JOIN sys_position p ON e.position_id = p.id " +
            "WHERE e.id = #{id}")
    EmployeeResponse selectEmployeeDetail(@Param("id") Long id);

    @Select("<script>" +
            "SELECT e.*, d.name as department_name, p.name as position_name " +
            "FROM sys_employee e " +
            "LEFT JOIN sys_department d ON e.department_id = d.id " +
            "LEFT JOIN sys_position p ON e.position_id = p.id " +
            "WHERE e.status = 1 " +
            "<if test='department != null'> AND d.name LIKE CONCAT('%', #{department}, '%') </if>" +
            "<if test='group != null'> AND d.name LIKE CONCAT('%', #{group}, '%') </if>" +
            "</script>")
    Page<EmployeeResponse> selectEmployeePage(Page<EmployeeResponse> page,
                                              @Param("department") String department,
                                              @Param("group") String group);
}

package site.opencs.plotmax.hrm.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import site.opencs.plotmax.hrm.dto.response.SalaryResponse;
import site.opencs.plotmax.hrm.entity.Salary;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 薪资记录表 Mapper 接口
 * </p>
 *
 * @author plotmax
 * @since 2025-05-25
 */
public interface SalaryMapper extends BaseMapper<Salary> {

    @Select("SELECT s.*, e.name as employee_name " +
            "FROM sys_salary s " +
            "LEFT JOIN sys_employee e ON s.employee_id = e.id " +
            "WHERE s.employee_id = #{employeeId} AND s.`year_month` = #{yearMonth}")
    SalaryResponse selectSalaryDetail(@Param("employeeId") Long employeeId,
                                      @Param("yearMonth") String yearMonth);
}

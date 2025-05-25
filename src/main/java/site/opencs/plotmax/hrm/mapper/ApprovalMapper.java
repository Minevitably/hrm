package site.opencs.plotmax.hrm.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import site.opencs.plotmax.hrm.entity.Approval;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 审批记录表 Mapper 接口
 * </p>
 *
 * @author plotmax
 * @since 2025-05-25
 */
public interface ApprovalMapper extends BaseMapper<Approval> {

    /**
     * 查询id为employeeId的员工的申请记录
     * @param page
     * @param status
     * @param type
     * @param employeeId
     * @return
     */
    @Select("<script>" +
            "SELECT a.*, e.name as employee_name, u.name as approver_name " +
            "FROM sys_approval a " +
            "LEFT JOIN sys_employee e ON a.employee_id = e.id " +
            "LEFT JOIN sys_employee u ON a.approver_id = u.id " +
            "WHERE 1=1 " +
            "<if test='status != null'> AND a.status = #{status} </if>" +
            "<if test='type != null'> AND a.type = #{type} </if>" +
            "<if test='employeeId != null'> AND a.employee_id = #{employeeId} </if>" +
            "ORDER BY a.create_time DESC " +
            "</script>")
    Page<Approval> selectApprovalPage(Page<Approval> page,
                                      @Param("status") String status,
                                      @Param("type") String type,
                                      @Param("employeeId") Long employeeId);
}

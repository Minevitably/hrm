package site.opencs.plotmax.hrm.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import site.opencs.plotmax.hrm.dto.request.ApprovalRequest;
import site.opencs.plotmax.hrm.dto.response.ApprovalProcessRequest;
import site.opencs.plotmax.hrm.entity.Approval;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDateTime;

/**
 * <p>
 * 审批记录表 服务类
 * </p>
 *
 * @author plotmax
 * @since 2025-05-25
 */
public interface IApprovalService extends IService<Approval> {

    /**
     * 提交申请
     */
    boolean submitApproval(Long employeeId, ApprovalRequest request);

    /**
     * 查询审批列表
     */
    Page<Approval> listApprovals(String status, String type, Long employeeId, int page, int size);

    /**
     * 处理审批
     */
    boolean processApproval(Long approverId, ApprovalProcessRequest request);

    /**
     * 检查时间冲突(同一员工同一时间段只能有一个申请)
     */
    boolean checkTimeConflict(Long employeeId, LocalDateTime startTime, LocalDateTime endTime);
}

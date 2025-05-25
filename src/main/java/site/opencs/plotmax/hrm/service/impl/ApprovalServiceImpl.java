package site.opencs.plotmax.hrm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import site.opencs.plotmax.hrm.dto.request.ApprovalRequest;
import site.opencs.plotmax.hrm.dto.response.ApprovalProcessRequest;
import site.opencs.plotmax.hrm.entity.Approval;
import site.opencs.plotmax.hrm.exception.BusinessException;
import site.opencs.plotmax.hrm.mapper.ApprovalMapper;
import site.opencs.plotmax.hrm.service.IApprovalService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * <p>
 * 审批记录表 服务实现类
 * </p>
 *
 * @author plotmax
 * @since 2025-05-25
 */
@Service
@RequiredArgsConstructor
public class ApprovalServiceImpl extends ServiceImpl<ApprovalMapper, Approval> implements IApprovalService {

    private final ApprovalMapper approvalMapper;

    @Override
    @Transactional
    public boolean submitApproval(Long employeeId, ApprovalRequest request) {
        // 检查时间冲突
        if (checkTimeConflict(employeeId, request.getStartTime(), request.getEndTime())) {
            throw new BusinessException("该时间段已有申请记录，请勿重复提交");
        }

        // 计算时长(小时)
        long minutes = ChronoUnit.MINUTES.between(request.getStartTime(), request.getEndTime());
        BigDecimal duration = BigDecimal.valueOf(minutes).divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);

        Approval approval = new Approval();
        approval.setEmployeeId(employeeId);
        approval.setType(request.getType());
        approval.setStatus("PENDING");
        approval.setStartTime(request.getStartTime());
        approval.setEndTime(request.getEndTime());
        approval.setDuration(duration);
        approval.setReason(request.getReason());
        approval.setCreateTime(LocalDateTime.now());

        return this.save(approval);
    }

    @Override
    public Page<Approval> listApprovals(String status, String type, Long employeeId, int page, int size) {
        Page<Approval> pageParam = new Page<>(page, size);
        return approvalMapper.selectApprovalPage(pageParam, status, type, employeeId);
    }

    @Override
    @Transactional
    public boolean processApproval(Long approverId, ApprovalProcessRequest request) {
        Approval approval = this.getById(request.getApprovalId());
        if (approval == null) {
            throw new BusinessException("审批记录不存在");
        }

        if (!"PENDING".equals(approval.getStatus())) {
            throw new BusinessException("该申请已处理，不能重复审批");
        }

        approval.setStatus(request.getApproved() ? "APPROVED" : "REJECTED");
        approval.setApproverId(approverId);
        approval.setApproveTime(LocalDateTime.now());
        approval.setApproveComment(request.getComment());
//        approval.setUpdateTime(LocalDateTime.now());

        return this.updateById(approval);
    }

    @Override
    public boolean checkTimeConflict(Long employeeId, LocalDateTime startTime, LocalDateTime endTime) {
        LambdaQueryWrapper<Approval> query = new LambdaQueryWrapper<>();
        query.eq(Approval::getEmployeeId, employeeId)
                .ne(Approval::getStatus, "REJECTED") // 已拒绝的不算冲突
                .and(wrapper -> wrapper
                        .between(Approval::getStartTime, startTime, endTime)
                        .or()
                        .between(Approval::getEndTime, startTime, endTime)
                        .or()
                        .le(Approval::getStartTime, startTime)
                        .ge(Approval::getEndTime, endTime)
                );

        return this.count(query) > 0;
    }
}

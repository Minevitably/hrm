package site.opencs.plotmax.hrm.service.impl;

import site.opencs.plotmax.hrm.entity.OperationLog;
import site.opencs.plotmax.hrm.mapper.OperationLogMapper;
import site.opencs.plotmax.hrm.service.IOperationLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 操作日志表 服务实现类
 * </p>
 *
 * @author plotmax
 * @since 2025-05-25
 */
@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog> implements IOperationLogService {

}

package site.opencs.plotmax.hrm.service.impl;

import site.opencs.plotmax.hrm.entity.Config;
import site.opencs.plotmax.hrm.mapper.ConfigMapper;
import site.opencs.plotmax.hrm.service.IConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统配置表 服务实现类
 * </p>
 *
 * @author plotmax
 * @since 2025-05-25
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements IConfigService {

}

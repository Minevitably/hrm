CREATE DATABASE IF NOT EXISTS hrm_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE hrm_db;


-- 系统用户表
CREATE TABLE `sys_user` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `username` varchar(50) NOT NULL COMMENT '用户名',
                            `password` varchar(100) NOT NULL COMMENT '密码',
                            `employee_id` bigint DEFAULT NULL COMMENT '关联员工ID',
                            `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
                            `login_fail_count` int DEFAULT '0' COMMENT '连续登录失败次数',
                            `account_locked` tinyint DEFAULT '0' COMMENT '是否锁定(1:是 0:否)',
                            `locked_until` datetime DEFAULT NULL COMMENT '锁定截止时间',
                            `status` tinyint DEFAULT '1' COMMENT '状态(1:启用 0:禁用)',
                            `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
                            `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `uk_username` (`username`),
                            UNIQUE KEY `uk_employee` (`employee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';

-- 角色表
CREATE TABLE `sys_role` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `role_name` varchar(50) NOT NULL COMMENT '角色名称',
                            `role_code` varchar(50) NOT NULL COMMENT '角色编码',
                            `description` varchar(255) DEFAULT NULL COMMENT '描述',
                            `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `uk_code` (`role_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- 用户角色关联表
CREATE TABLE `sys_user_role` (
                                 `id` bigint NOT NULL AUTO_INCREMENT,
                                 `user_id` bigint NOT NULL COMMENT '用户ID',
                                 `role_id` bigint NOT NULL COMMENT '角色ID',
                                 `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
                                 PRIMARY KEY (`id`),
                                 UNIQUE KEY `uk_user_role` (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';
#
# -- 插入初始数据
# INSERT INTO `sys_role` (`role_name`, `role_code`, `description`) VALUES
#                                                                      ('管理员', 'ADMIN', '系统管理员'),
#                                                                      ('普通用户', 'USER', '普通用户');
#
# INSERT INTO `sys_user` (`username`, `password`, `real_name`, `email`, `phone`, `status`) VALUES
#                                                                                              ('admin', '$2a$10$bDW2hs7KSe6lZI1ITGEeTeHXWKojoWuxDag/F//m0srDTG/QpGIAi', '系统管理员', 'admin@example.com', '13800138000', 1),
#                                                                                              ('user', '$2a$10$bDW2hs7KSe6lZI1ITGEeTeHXWKojoWuxDag/F//m0srDTG/QpGIAi', '普通用户', 'user@example.com', '13900139000', 1);

# INSERT INTO `sys_user_role` (`user_id`, `role_id`) VALUES
#                                                        (1, 1),  -- admin用户拥有管理员角色
#                                                        (1, 2),  -- admin用户也拥有普通用户角色
#                                                        (2, 2);  -- user用户拥有普通用户角色

-- 初始化角色数据
INSERT INTO `sys_role` (`id`, `role_name`, `role_code`, `description`) VALUES
                                                                           (1, '系统管理员', 'ADMIN', '拥有所有权限'),
                                                                           (2, '总经理', 'GM', '拥有审批和查看所有数据的权限'),
                                                                           (3, '部门主管', 'MANAGER', '拥有本部门管理权限'),
                                                                           (4, '普通员工', 'EMPLOYEE', '基本员工权限');

-- 初始化管理员用户
INSERT INTO `sys_user` (`id`, `username`, `password`, `employee_id`, `status`) VALUES
                                                                                   (1, 'admin', '$2a$10$bDW2hs7KSe6lZI1ITGEeTeHXWKojoWuxDag/F//m0srDTG/QpGIAi', NULL, 1),
                                                                                   (2, 'user', '$2a$10$bDW2hs7KSe6lZI1ITGEeTeHXWKojoWuxDag/F//m0srDTG/QpGIAi', NULL, 1);

-- 设置管理员角色
INSERT INTO `sys_user_role` (`user_id`, `role_id`) VALUES
                                                       (1, 1),
                                                       (2, 4);

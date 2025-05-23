CREATE DATABASE IF NOT EXISTS hrm_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE hrm_db;

-- 用户表
CREATE TABLE `sys_user` (
                            `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                            `username` varchar(50) NOT NULL COMMENT '用户名',
                            `password` varchar(100) NOT NULL COMMENT '密码',
                            `real_name` varchar(50) DEFAULT NULL COMMENT '真实姓名',
                            `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
                            `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
                            `status` tinyint DEFAULT '1' COMMENT '状态：1-正常，0-禁用',
                            `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            `deleted` tinyint DEFAULT '0' COMMENT '逻辑删除：0-未删除，1-已删除',
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `idx_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统用户表';

-- 角色表
CREATE TABLE `sys_role` (
                            `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                            `role_name` varchar(50) NOT NULL COMMENT '角色名称',
                            `role_code` varchar(50) NOT NULL COMMENT '角色编码',
                            `description` varchar(255) DEFAULT NULL COMMENT '描述',
                            `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            `deleted` tinyint DEFAULT '0' COMMENT '逻辑删除：0-未删除，1-已删除',
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `idx_role_code` (`role_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统角色表';

-- 用户角色关联表
CREATE TABLE `sys_user_role` (
                                 `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                 `user_id` bigint NOT NULL COMMENT '用户ID',
                                 `role_id` bigint NOT NULL COMMENT '角色ID',
                                 `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 PRIMARY KEY (`id`),
                                 UNIQUE KEY `idx_user_role` (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户角色关联表';

-- 插入初始数据
INSERT INTO `sys_role` (`role_name`, `role_code`, `description`) VALUES
                                                                     ('管理员', 'ROLE_ADMIN', '系统管理员'),
                                                                     ('普通用户', 'ROLE_USER', '普通用户');

INSERT INTO `sys_user` (`username`, `password`, `real_name`, `email`, `phone`, `status`) VALUES
                                                                                             ('admin', '$2a$10$ixJNQ8QzU.9Jv5p5J9lY.ez5VZJQ9XZJQ9XZJQ9XZJQ9XZJQ9XZJQ9', '系统管理员', 'admin@example.com', '13800138000', 1),
                                                                                             ('user', '$2a$10$ixJNQ8QzU.9Jv5p5J9lY.ez5VZJQ9XZJQ9XZJQ9XZJQ9XZJQ9XZJQ9', '普通用户', 'user@example.com', '13900139000', 1);

INSERT INTO `sys_user_role` (`user_id`, `role_id`) VALUES
                                                       (1, 1),  -- admin用户拥有管理员角色
                                                       (1, 2),  -- admin用户也拥有普通用户角色
                                                       (2, 2);  -- user用户拥有普通用户角色

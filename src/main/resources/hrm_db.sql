CREATE DATABASE IF NOT EXISTS hrm_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE hrm_db;


-- 部门表
CREATE TABLE `sys_department`
(
    `id`          bigint      NOT NULL AUTO_INCREMENT,
    `name`        varchar(50) NOT NULL COMMENT '部门名称',
    `parent_id`   bigint   DEFAULT NULL COMMENT '父部门ID',
    `manager_id`  bigint   DEFAULT NULL COMMENT '部门经理ID',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `idx_parent_id` (`parent_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='部门表';

-- 职位表
CREATE TABLE `sys_position`
(
    `id`              bigint      NOT NULL AUTO_INCREMENT,
    `name`            varchar(50) NOT NULL COMMENT '职位名称',
    `level`           int            DEFAULT '1' COMMENT '职级',
    `base_salary_min` decimal(10, 2) DEFAULT NULL COMMENT '最低基本工资',
    `base_salary_max` decimal(10, 2) DEFAULT NULL COMMENT '最高基本工资',
    `create_time`     datetime       DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='职位表';

-- 员工表
CREATE TABLE `sys_employee`
(
    `id`                      bigint         NOT NULL AUTO_INCREMENT,
    `name`                    varchar(50)    NOT NULL COMMENT '姓名',
    `gender`                  varchar(10)   DEFAULT NULL COMMENT '性别',
    `birth_date`              date          DEFAULT NULL COMMENT '出生日期',
    `id_card_number`          varchar(20)   DEFAULT NULL COMMENT '身份证号',
    `department_id`           bigint         NOT NULL COMMENT '部门ID',
    `position_id`             bigint         NOT NULL COMMENT '职位ID',
    `hire_date`               date           NOT NULL COMMENT '入职日期',
    `leave_date`              date          DEFAULT NULL COMMENT '离职日期',
    `base_salary`             decimal(10, 2) NOT NULL COMMENT '基本工资',
    `performance_coefficient` decimal(3, 2) DEFAULT '1.00' COMMENT '绩效系数',
    `education`               varchar(50)   DEFAULT NULL COMMENT '学历',
    `contact_phone`           varchar(20)   DEFAULT NULL COMMENT '联系电话',
    `emergency_contact`       varchar(50)   DEFAULT NULL COMMENT '紧急联系人',
    `emergency_phone`         varchar(20)   DEFAULT NULL COMMENT '紧急联系电话',
    `contract_file`           varchar(255)  DEFAULT NULL COMMENT '合同文件路径',
    `id_card_file`            varchar(255)  DEFAULT NULL COMMENT '身份证件路径',
    `status`                  tinyint       DEFAULT '1' COMMENT '状态(1:在职 0:离职)',
    `create_time`             datetime      DEFAULT CURRENT_TIMESTAMP,
    `update_time`             datetime      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_id_card` (`id_card_number`),
    KEY `idx_department` (`department_id`),
    KEY `idx_position` (`position_id`),
    KEY `idx_status` (`status`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='员工表';

-- 考勤记录表
CREATE TABLE `sys_attendance`
(
    `id`                  bigint NOT NULL AUTO_INCREMENT,
    `employee_id`         bigint NOT NULL COMMENT '员工ID',
    `attendance_date`     date   NOT NULL COMMENT '考勤日期',
    `check_in_time`       time         DEFAULT NULL COMMENT '上班打卡时间',
    `check_out_time`      time         DEFAULT NULL COMMENT '下班打卡时间',
    `check_in_ip`         varchar(50)  DEFAULT NULL COMMENT '打卡IP',
    `late_minutes`        int          DEFAULT '0' COMMENT '迟到分钟数',
    `early_leave_minutes` int          DEFAULT '0' COMMENT '早退分钟数',
    `absence`             tinyint      DEFAULT '0' COMMENT '是否旷工(1:是 0:否)',
    `status`              varchar(20)  DEFAULT 'PENDING' COMMENT '状态(PENDING/CONFIRMED/REJECTED)',
    `confirmed_by`        bigint       DEFAULT NULL COMMENT '确认人ID',
    `confirmed_time`      datetime     DEFAULT NULL COMMENT '确认时间',
    `remark`              varchar(255) DEFAULT NULL COMMENT '备注',
    `create_time`         datetime     DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_employee_date` (`employee_id`, `attendance_date`),
    KEY `idx_date` (`attendance_date`),
    KEY `idx_status` (`status`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='考勤记录表';

-- 薪资记录表
CREATE TABLE `sys_salary`
(
    `id`                    bigint         NOT NULL AUTO_INCREMENT,
    `employee_id`           bigint         NOT NULL COMMENT '员工ID',
    `year_month`            varchar(7)     NOT NULL COMMENT '年月(YYYY-MM)',
    `base_salary`           decimal(10, 2) NOT NULL COMMENT '基本工资',
    `performance_salary`    decimal(10, 2) DEFAULT '0.00' COMMENT '绩效工资',
    `full_attendance_bonus` decimal(10, 2) DEFAULT '0.00' COMMENT '全勤奖',
    `attendance_deduction`  decimal(10, 2) DEFAULT '0.00' COMMENT '考勤扣款',
    `insurance_deduction`   decimal(10, 2) DEFAULT '0.00' COMMENT '社保公积金扣款',
    `tax_deduction`         decimal(10, 2) DEFAULT '0.00' COMMENT '个税扣款',
    `net_salary`            decimal(10, 2) NOT NULL COMMENT '实发工资',
    `is_signed`             tinyint        DEFAULT '0' COMMENT '是否签收(1:是 0:否)',
    `sign_time`             datetime       DEFAULT NULL COMMENT '签收时间',
    `create_time`           datetime       DEFAULT CURRENT_TIMESTAMP,
    `update_time`           datetime       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_employee_month` (`employee_id`, `year_month`),
    KEY `idx_month` (`year_month`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='薪资记录表';

-- 审批记录表
CREATE TABLE `sys_approval`
(
    `id`              bigint      NOT NULL AUTO_INCREMENT,
    `employee_id`     bigint      NOT NULL COMMENT '申请人ID',
    `type`            varchar(20) NOT NULL COMMENT '类型(LEAVE/OVERTIME)',
    `start_time`      datetime    NOT NULL COMMENT '开始时间',
    `end_time`        datetime    NOT NULL COMMENT '结束时间',
    `duration`        decimal(5, 2) DEFAULT NULL COMMENT '时长(小时)',
    `reason`          varchar(500)  DEFAULT NULL COMMENT '事由',
    `status`          varchar(20)   DEFAULT 'PENDING' COMMENT '状态(PENDING/APPROVED/REJECTED)',
    `approver_id`     bigint        DEFAULT NULL COMMENT '审批人ID',
    `approve_time`    datetime      DEFAULT NULL COMMENT '审批时间',
    `approve_comment` varchar(500)  DEFAULT NULL COMMENT '审批意见',
    `create_time`     datetime      DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `idx_employee` (`employee_id`),
    KEY `idx_type_status` (`type`, `status`),
    KEY `idx_time_range` (`start_time`, `end_time`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='审批记录表';


-- 系统配置表（新增）
CREATE TABLE `sys_config`
(
    `id`           bigint       NOT NULL AUTO_INCREMENT,
    `config_key`   varchar(100) NOT NULL COMMENT '配置键',
    `config_value` varchar(500) NOT NULL COMMENT '配置值',
    `description`  varchar(255) DEFAULT NULL COMMENT '配置描述',
    `create_time`  datetime     DEFAULT CURRENT_TIMESTAMP,
    `update_time`  datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_config_key` (`config_key`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统配置表';

-- 操作日志表（新增）
CREATE TABLE `sys_operation_log`
(
    `id`             bigint       NOT NULL AUTO_INCREMENT,
    `user_id`        bigint        DEFAULT NULL COMMENT '操作用户ID',
    `operation`      varchar(100) NOT NULL COMMENT '操作类型',
    `method`         varchar(200)  DEFAULT NULL COMMENT '请求方法',
    `params`         text COMMENT '请求参数',
    `ip`             varchar(50)   DEFAULT NULL COMMENT 'IP地址',
    `status`         tinyint       DEFAULT NULL COMMENT '操作状态(1:成功 0:失败)',
    `error_msg`      varchar(2000) DEFAULT NULL COMMENT '错误消息',
    `operation_time` datetime      DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_operation_time` (`operation_time`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='操作日志表';

-- 系统用户表
CREATE TABLE `sys_user`
(
    `id`               bigint       NOT NULL AUTO_INCREMENT,
    `username`         varchar(50)  NOT NULL COMMENT '用户名',
    `password`         varchar(100) NOT NULL COMMENT '密码',
    `employee_id`      bigint   DEFAULT NULL COMMENT '关联员工ID',
    `last_login_time`  datetime DEFAULT NULL COMMENT '最后登录时间',
    `login_fail_count` int      DEFAULT '0' COMMENT '连续登录失败次数',
    `account_locked`   tinyint  DEFAULT '0' COMMENT '是否锁定(1:是 0:否)',
    `locked_until`     datetime DEFAULT NULL COMMENT '锁定截止时间',
    `status`           tinyint  DEFAULT '1' COMMENT '状态(1:启用 0:禁用)',
    `create_time`      datetime DEFAULT CURRENT_TIMESTAMP,
    `update_time`      datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`),
    UNIQUE KEY `uk_employee` (`employee_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统用户表';

-- 角色表
CREATE TABLE `sys_role`
(
    `id`          bigint      NOT NULL AUTO_INCREMENT,
    `role_name`   varchar(50) NOT NULL COMMENT '角色名称',
    `role_code`   varchar(50) NOT NULL COMMENT '角色编码',
    `description` varchar(255) DEFAULT NULL COMMENT '描述',
    `create_time` datetime     DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_code` (`role_code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色表';

-- 用户角色关联表
CREATE TABLE `sys_user_role`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `user_id`     bigint NOT NULL COMMENT '用户ID',
    `role_id`     bigint NOT NULL COMMENT '角色ID',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_role` (`user_id`, `role_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户角色关联表';

-- 初始化审批测试数据
INSERT INTO sys_approval (employee_id, type, status, start_time, end_time, duration, reason, create_time)
VALUES (1001, 'LEAVE', 'PENDING', '2023-08-01 09:00:00', '2023-08-03 18:00:00', 24.0, '年假休息', NOW()),
       (1002, 'OVERTIME', 'APPROVED', '2023-08-05 19:00:00', '2023-08-05 22:00:00', 3.0, '项目紧急上线', NOW()),
       (1003, 'LEAVE', 'REJECTED', '2023-08-10 13:00:00', '2023-08-10 17:00:00', 4.0, '家中有事', NOW());

-- 初始化考勤测试数据
INSERT INTO sys_attendance (employee_id, attendance_date, check_in_time, check_out_time,
                            late_minutes, early_leave_minutes, absence, status, create_time)
VALUES (1001, '2023-08-01', '09:00:00', '18:00:00', 0, 0, 0, 'NORMAL', NOW()),
       (1001, '2023-08-02', '09:15:00', '17:45:00', 15, 15, 0, 'LATE', NOW()),
       (1001, '2023-08-03', null, null, null, null, 1, 'ABSENCE', NOW()),
       (1002, '2023-08-01', '08:45:00', '18:30:00', 0, 0, 0, 'NORMAL', NOW());

-- 初始化部门数据
INSERT INTO `sys_department` (`id`, `name`, `parent_id`)
VALUES (1, '总经办', NULL),
       (2, '技术部', NULL),
       (3, '市场部', NULL),
       (4, '行政部', NULL),
       (5, '设计部', NULL),
       (6, '开发组', 2),
       (7, '测试组', 2),
       (8, '推广组', 3),
       (9, '商务组', 3),
       (10, '人事组', 4),
       (11, '财务后勤组', 4);

-- 初始化职位数据
INSERT INTO `sys_position` (`id`, `name`, `level`, `base_salary_min`, `base_salary_max`)
VALUES
-- 高管层
(1, '总经理', 1, 25000.00, 30000.00),

-- 技术序列
(2, '技术总监', 2, 20000.00, 25000.00),
(3, '高级工程师', 3, 15000.00, 20000.00),
(4, 'Java工程师', 4, 12000.00, 18000.00),
(5, '前端工程师', 4, 10000.00, 15000.00),
(6, '测试工程师', 4, 10000.00, 15000.00),
(7, '测试主管', 3, 15000.00, 20000.00),

-- 市场序列
(8, '市场经理', 3, 12000.00, 18000.00),
(9, '新媒体运营', 5, 8000.00, 12000.00),
(10, '商务主管', 3, 15000.00, 20000.00),
(11, '客户经理', 4, 10000.00, 15000.00),

-- 行政序列
(12, '人事经理', 3, 12000.00, 16000.00),
(13, '招聘专员', 5, 7000.00, 10000.00),
(14, '财务主管', 3, 14000.00, 18000.00),
(15, '会计', 4, 9000.00, 12000.00),
(16, '行政助理', 5, 6000.00, 9000.00),

-- 设计序列
(17, '设计总监', 2, 15000.00, 20000.00),
(18, 'UI设计师', 4, 10000.00, 15000.00),
(19, '平面设计师', 4, 8000.00, 12000.00);

-- 插入员工数据
INSERT INTO sys_employee (id, name, gender, department_id, position_id, hire_date, base_salary, education, status,
                          create_time, update_time)
VALUES
-- 总经办
(1001, '王强', '男', 1, 1, '2020-03-15', 28000.00, '硕士', 1, NOW(), NOW()),

-- 技术部-开发组
(1002, '张伟', '男', 6, 2, '2019-05-10', 22000.00, '本科', 1, NOW(), NOW()),
(1003, '李斌', '男', 6, 3, '2021-08-12', 18000.00, '本科', 1, NOW(), NOW()),
(1004, '周婷', '女', 6, 4, '2022-03-05', 15000.00, '本科', 1, NOW(), NOW()),
(1005, '陈磊', '男', 6, 5, '2023-01-18', 13000.00, '专科', 1, NOW(), NOW()),
(1006, '吴芳', '女', 6, 6, '2022-06-20', 12000.00, '本科', 1, NOW(), NOW()),

-- 技术部-测试组
(1007, '郑浩', '男', 7, 7, '2021-11-07', 16000.00, '本科', 1, NOW(), NOW()),
(1008, '林小雨', '女', 7, 6, '2023-04-03', 11000.00, '专科', 1, NOW(), NOW()),

-- 市场部-推广组
(1009, '赵明', '男', 8, 8, '2021-07-22', 14000.00, '本科', 1, NOW(), NOW()),
(1010, '孙莉', '女', 8, 9, '2023-02-14', 9000.00, '本科', 1, NOW(), NOW()),

-- 市场部-商务组
(1011, '刘洋', '男', 9, 10, '2020-09-08', 16000.00, '硕士', 1, NOW(), NOW()),
(1012, '徐娜', '女', 9, 11, '2022-05-30', 10000.00, '本科', 1, NOW(), NOW()),

-- 行政部-人事组
(1013, '张丽', '女', 10, 12, '2020-12-01', 13000.00, '本科', 1, NOW(), NOW()),
(1014, '王芳', '女', 10, 13, '2023-03-09', 8000.00, '专科', 1, NOW(), NOW()),

-- 行政部-财务后勤组
(1015, '李娜', '女', 11, 14, '2021-04-05', 15000.00, '本科', 1, NOW(), NOW()),
(1016, '周涛', '男', 11, 15, '2022-07-11', 10000.00, '本科', 1, NOW(), NOW()),
(1017, '陈雪', '女', 11, 16, '2023-06-25', 7000.00, '专科', 1, NOW(), NOW()),

-- 设计部
(1018, '黄宇', '男', 5, 17, '2020-10-17', 17000.00, '本科', 1, NOW(), NOW()),
(1019, '杨雪', '女', 5, 18, '2022-09-05', 12000.00, '本科', 1, NOW(), NOW()),
(1020, '韩磊', '男', 5, 19, '2023-05-20', 10000.00, '专科', 1, NOW(), NOW());

-- 初始化系统配置
INSERT INTO `sys_config` (`config_key`, `config_value`, `description`)
VALUES ('attendance.late_deduction_per_min', '1', '迟到每分钟扣款金额(元)'),
       ('attendance.late_deduction_over_30', '300', '迟到超过30分钟扣款金额(元)'),
       ('attendance.early_leave_deduction', '2', '早退每分钟扣款金额(元)'),
       ('attendance.absence_multiplier', '3', '旷工扣款倍数(日薪的倍数)'),
       ('salary.performance_base_rate', '0.2', '绩效工资计算基数比例'),
       ('salary.full_attendance_bonus', '500', '全勤奖金额(元)');

-- 初始化角色数据
INSERT INTO `sys_role` (`id`, `role_name`, `role_code`, `description`)
VALUES (1, '系统管理员', 'ADMIN', '拥有所有权限'),
       (2, '总经理', 'GM', '拥有审批和查看所有数据的权限'),
       (3, '部门主管', 'MANAGER', '拥有本部门管理权限'),
       (4, '普通员工', 'EMPLOYEE', '基本员工权限');

-- 初始化系统用户
INSERT INTO `sys_user` (`id`, `username`, `password`, `employee_id`, `status`)
VALUES (1, 'MrWang', '$2a$10$bDW2hs7KSe6lZI1ITGEeTeHXWKojoWuxDag/F//m0srDTG/QpGIAi', 1001, 1),
       (2, 'admin', '$2a$10$bDW2hs7KSe6lZI1ITGEeTeHXWKojoWuxDag/F//m0srDTG/QpGIAi', 1002, 1),
       (3, 'LiBin', '$2a$10$bDW2hs7KSe6lZI1ITGEeTeHXWKojoWuxDag/F//m0srDTG/QpGIAi', 1003, 1),
       (4, 'ZhouTing', '$2a$10$bDW2hs7KSe6lZI1ITGEeTeHXWKojoWuxDag/F//m0srDTG/QpGIAi', 1004, 1),
       (5, 'ChenLei', '$2a$10$bDW2hs7KSe6lZI1ITGEeTeHXWKojoWuxDag/F//m0srDTG/QpGIAi', 1005, 1),
       (6, 'WuFang', '$2a$10$bDW2hs7KSe6lZI1ITGEeTeHXWKojoWuxDag/F//m0srDTG/QpGIAi', 1006, 1),
       (7, 'ZhengHao', '$2a$10$bDW2hs7KSe6lZI1ITGEeTeHXWKojoWuxDag/F//m0srDTG/QpGIAi', 1007, 1);

-- 设置管理员角色
INSERT INTO `sys_user_role` (`user_id`, `role_id`)
VALUES (1, 2),
       (2, 1),
       (2, 3),
       (3, 4),
       (4, 4),
       (5, 4),
       (6, 4),
       (7, 4),
       (8, 4),
       (9, 3),
       (10, 4),
       (11, 4),
       (12, 4),
       (13, 3),
       (14, 4),
       (15, 4),
       (16, 4),
       (17, 4),
       (18, 3),
       (19, 4),
       (20, 4);


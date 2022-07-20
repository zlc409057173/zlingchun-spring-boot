drop table if exists t_customer;
CREATE TABLE `t_customer` (
    `cid` bigint(32) NOT NULL COMMENT '主键',
    `cus_name` varchar(64) NOT NULL DEFAULT '' COMMENT '客户名称',
    `cus_phone` varchar(16) NOT NULL DEFAULT '' COMMENT '客户手机号',
    `sex` char(2) NOT NULL DEFAULT '0' COMMENT '客户性别：0：男；1：女',
    `age` int(3) NULL COMMENT '客户年龄',
    `email` varchar(64) NULL DEFAULT '' COMMENT '客户邮箱',
    `version` int(5) NOT NULL DEFAULT '0' COMMENT '版本号',
    `status` char(1) NOT NULL DEFAULT '0' COMMENT '客户状态：0：正常；1：删除',
    `emp_id` bigint(32) NOT NULL COMMENT '员工id',
    `createBy` varchar(64) NULL DEFAULT '' COMMENT '创建人',
    `createTime` datetime NULL COMMENT '创建时间',
    `updateBy` varchar(64) NULL DEFAULT '' COMMENT '更新人',
    `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`cid`) ,
    UNIQUE INDEX `unique_cus_name_phone` (`cus_name`, `cus_phone`, `status`, `emp_id`) COMMENT '客户名和手机号唯一'
);

drop table if exists t_emp;
CREATE TABLE `t_emp` (
    `eid` bigint(32) NOT NULL COMMENT '主键',
    `emp_name` varchar(128) NOT NULL DEFAULT '' COMMENT '员工名称',
    `emp_no` varchar(32) NOT NULL DEFAULT '' COMMENT '员工号',
    `emp_phone` varchar(16) NOT NULL DEFAULT '' COMMENT '手机号',
    `salary` decimal(11,3) NULL COMMENT '薪资',
    `sex` char(2) NOT NULL DEFAULT '0' COMMENT '性别：0:男；1:女',
    `age` int(3) NULL COMMENT '年龄',
    `birth` date NULL COMMENT '生日日期',
    `address` varchar(256) NULL DEFAULT '' COMMENT '家庭住址',
    `email` varchar(64) NULL,
    `status` char(1) NOT NULL DEFAULT '0' COMMENT '逻辑状态：0：正常；1：删除',
    `version` int(5) NOT NULL DEFAULT '0' COMMENT '版本号',
    `dep_id` int(32) NOT NULL COMMENT '部门id',
    `createBy` varchar(16) NULL DEFAULT '' COMMENT '创建人',
    `createTime` datetime NULL COMMENT '创建时间',
    `updateBy` varchar(16) NULL DEFAULT '' COMMENT '更新人',
    `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`eid`) ,
    UNIQUE INDEX `unique_emp_no` (`emp_no`, `status`) COMMENT '员工号唯一',
    UNIQUE INDEX `unique_emp_phone` (`emp_phone`, `status`) COMMENT '员工手机号唯一'
);

drop table if exists t_dep;
CREATE TABLE `t_dep` (
    `did` int(32) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `dep_name` varchar(64) NOT NULL DEFAULT '' COMMENT '部门名称',
    `dep_no` varchar(32) NOT NULL DEFAULT '' COMMENT '部门编号',
    `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态编码：0：正常，1：删除',
    `version` int(5) NOT NULL DEFAULT '0' COMMENT '版本号',
    `createBy` varchar(64) NULL DEFAULT '' COMMENT '创建人',
    `createTime` datetime NULL COMMENT '创建时间',
    `updateBy` varchar(64) NULL COMMENT '更新人',
    `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`did`) ,
    UNIQUE INDEX `unique_dep_name` (`dep_name`, `status`) COMMENT '部门名称唯一',
    UNIQUE INDEX `unique_dep_no` (`dep_no`, `status`) COMMENT '部门编号唯一'
);

ALTER TABLE `t_emp` ADD CONSTRAINT `fk_t_emp_t_dep_1` FOREIGN KEY (`dep_id`) REFERENCES `t_dep` (`did`);
ALTER TABLE `t_customer` ADD CONSTRAINT `fk_t_cus_t_emp_1` FOREIGN KEY (`emp_id`) REFERENCES `t_emp` (`eid`);
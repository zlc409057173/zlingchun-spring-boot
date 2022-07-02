DROP TABLE IF EXISTS `t_dep`;
CREATE TABLE `t_dep` (
  `did` int NOT NULL AUTO_INCREMENT COMMENT '部门主键',
  `dep_name` varchar(128) COLLATE utf8mb4_general_ci NOT NULL COMMENT '部门名称',
  `createBy` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '创建人',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateBy` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '修改人',
  `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`did`),
  UNIQUE KEY `unique_dep_name` (`dep_name`) USING BTREE COMMENT '部门名称唯一'
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `t_emp`;
CREATE TABLE `t_emp` (
  `eid` bigint NOT NULL COMMENT '主键',
  `emp_name` varchar(128) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '员工姓名',
  `emp_num` varchar(64) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '员工号',
  `tel_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '手机号',
  `sex` char(2) COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '性别：0：男，1：女',
  `salary` decimal(11,3) DEFAULT '0.000' COMMENT '薪资',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `emp_address` varchar(256) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '家庭住址',
  `email` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '邮箱',
  `createBy` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateBy` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '修改人',
  `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `did` int NOT NULL,
  PRIMARY KEY (`eid`),
  UNIQUE KEY `unique_emp_num` (`emp_num`) USING BTREE COMMENT '用户账号唯一',
  UNIQUE KEY `unique_emp_tel` (`tel_number`) USING BTREE COMMENT '用户手机号唯一',
  KEY `dep_id` (`did`) USING BTREE COMMENT '用户部门唯一',
  CONSTRAINT `dep_id` FOREIGN KEY (`did`) REFERENCES `t_dep` (`did`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
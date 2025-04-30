CREATE TABLE IF NOT EXISTS wf_process_definition (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    key_ VARCHAR(32) NOT NULL COMMENT '流程定义Key',
    name VARCHAR(32) NOT NULL COMMENT '流程名称',
    category VARCHAR(32) NULL COMMENT '流程分类',
    version INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '版本号',
    content BLOB NOT NULL COMMENT '流程模型定义',
    description VARCHAR(128) NULL COMMENT '描述',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_time DATETIME NOT NULL COMMENT '更新时间',
    creator VARCHAR(32) NULL COMMENT '创建人',
    PRIMARY KEY (`id`)
)ENGINE = InnoDB COMMENT='流程定义';

CREATE TABLE IF NOT EXISTS wf_process_instance (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    parent_id INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '父流程ID',
    process_def_id INT UNSIGNED NOT NULL COMMENT '流程定义ID',
    business_key VARCHAR(32) NULL COMMENT '业务KEY',
    priority INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '优先级',
    revision INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '修订版本',
    start_user_id VARCHAR(32) NULL COMMENT '发起人',
    create_time DATETIME NOT NULL COMMENT '发起时间',
    PRIMARY KEY (`id`)
)ENGINE = InnoDB COMMENT='流程实例';

CREATE TABLE IF NOT EXISTS wf_task (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    parent_task_id INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '父任务ID',
    process_def_id INT UNSIGNED NOT NULL COMMENT '流程定义ID',
    instance_id INT UNSIGNED NOT NULL COMMENT '流程实例ID',
    task_def_key VARCHAR(64) NOT NULL COMMENT '任务定义ID',
    name VARCHAR(64) NOT NULL COMMENT '任务名称',
    owner VARCHAR(32) NULL COMMENT '所属人',
    assignee VARCHAR(32) NULL COMMENT '执行人',
    status VARCHAR(16) NOT NULL COMMENT '任务状态',
    due_time DATETIME NULL COMMENT '任务到期时间',
    revision INT UNSIGNED NULL DEFAULT 0 COMMENT '修订版本',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`)
)ENGINE = InnoDB COMMENT='任务';

CREATE TABLE IF NOT EXISTS wf_variable (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    instance_id INT UNSIGNED NOT NULL COMMENT '流程实例ID',
    task_id INT UNSIGNED NOT NULL COMMENT '任务ID',
    name VARCHAR(32) NOT NULL COMMENT '变量名称',
    type VARCHAR(16) NOT NULL COMMENT '变量类型',
    value_ VARCHAR(256) NULL COMMENT '变量值',
    revision INT UNSIGNED NULL DEFAULT 0 COMMENT '修订版本',
    PRIMARY KEY (`id`)
)ENGINE = InnoDB COMMENT='变量';

CREATE TABLE IF NOT EXISTS wf_historic_process_instance (
    id INT UNSIGNED NOT NULL COMMENT '主键ID',
    parent_id INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '父流程ID',
    process_def_id INT UNSIGNED NOT NULL COMMENT '流程定义ID',
    business_key VARCHAR(32) NULL COMMENT '业务KEY',
    priority INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '优先级',
    start_user_id VARCHAR(32) NULL COMMENT '发起人',
    revision INT NULL DEFAULT 0 COMMENT '修订版本',
    duration BIGINT NULL DEFAULT 0 COMMENT '耗时，单位是秒',
    start_time DATETIME NOT NULL COMMENT '开始时间',
    end_time DATETIME NULL COMMENT '结束时间',
    status VARCHAR(16) NOT NULL COMMENT '实例状态',
    PRIMARY KEY (`id`)
)ENGINE = InnoDB COMMENT='历史流程实例';

CREATE TABLE IF NOT EXISTS wf_historic_task (
    id INT UNSIGNED NOT NULL COMMENT '主键ID',
    parent_task_id INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '父任务ID',
    process_def_id INT UNSIGNED NOT NULL COMMENT '流程定义ID',
    instance_id INT UNSIGNED NOT NULL COMMENT '流程实例ID',
    task_def_key VARCHAR(64) NOT NULL COMMENT '任务定义ID',
    name VARCHAR(64) NOT NULL COMMENT '任务名称',
    owner VARCHAR(32) NULL COMMENT '所属人',
    assignee VARCHAR(32) NULL COMMENT '执行人',
    status VARCHAR(16) NOT NULL COMMENT '任务状态',
    revision INT UNSIGNED NULL DEFAULT 0 COMMENT '修订版本',
    duration BIGINT UNSIGNED NULL DEFAULT 0 COMMENT '耗时，单位是秒',
    start_time DATETIME NOT NULL COMMENT '开始时间',
    end_time DATETIME NULL COMMENT '结束时间',
    PRIMARY KEY (`id`)
)ENGINE = InnoDB COMMENT='历史任务';

CREATE TABLE IF NOT EXISTS wf_historic_variable (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    instance_id INT UNSIGNED NOT NULL COMMENT '流程实例ID',
    task_id INT UNSIGNED NOT NULL COMMENT '任务ID',
    name VARCHAR(32) NOT NULL COMMENT '变量名称',
    type VARCHAR(16) NOT NULL COMMENT '变量类型',
    value_ VARCHAR(256) NULL COMMENT '变量值',
    revision INT UNSIGNED NULL DEFAULT 0 COMMENT '修订版本',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    last_update_time DATETIME NOT NULL COMMENT '最后更新时间',
    PRIMARY KEY (`id`)
)ENGINE = InnoDB COMMENT='历史变量';

create index process_definition_index_key on wf_process_definition (key_);
create index process_instance_index_process_def_id on wf_process_instance (process_def_id);
create index task_index_instance_id on wf_task (instance_id);
create index task_index_process_def_id on wf_task (process_def_id);
create index task_index_parent_task_id on wf_task (parent_task_id);
create index variable_index on wf_variable (instance_id, task_id);
create index historic_instance_index_process_def_id on wf_historic_process_instance (process_def_id);
create index historic_task_index_instance_id on wf_historic_task (instance_id);
create index historic_task_index_task_def_key on wf_historic_task (task_def_key);
create index historic_task_index_parent_task_id on wf_historic_task (parent_task_id);
create index historic_variable_index on wf_historic_variable (instance_id, task_id);

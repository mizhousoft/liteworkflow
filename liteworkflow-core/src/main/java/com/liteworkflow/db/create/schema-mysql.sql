CREATE TABLE IF NOT EXISTS wf_process_definition (
	id                INT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    key_              VARCHAR(32) NOT NULL COMMENT '流程定义Key',
    name              VARCHAR(32) NOT NULL COMMENT '流程名称',
    category          VARCHAR(32) NULL COMMENT '流程分类',
    version           INT NOT NULL DEFAULT 0 COMMENT '版本号',
    content           LONGBLOB NOT NULL COMMENT '流程模型定义',
    creator           VARCHAR(32) NULL COMMENT '创建人',
    create_time       DATETIME NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`)
)ENGINE = InnoDB COMMENT='流程定义表';

CREATE TABLE IF NOT EXISTS wf_process_instance (
    id                INT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    parent_id         INT NOT NULL DEFAULT 0 COMMENT '父流程ID',
    process_def_id    INT NOT NULL COMMENT '流程定义ID',
    business_key      VARCHAR(32) NULL COMMENT '业务KEY',
    priority          INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '优先级',
    parent_node_name  VARCHAR(64) NULL COMMENT '父流程依赖的节点名称',
    variable          VARCHAR(2048) NULL COMMENT '附属变量json存储',
    revision          INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '修订版本',
    initiator     	  VARCHAR(32) NULL COMMENT '发起人',
    create_time       DATETIME NOT NULL COMMENT '发起时间',
    PRIMARY KEY (`id`)
)ENGINE = InnoDB COMMENT='流程实例表';

CREATE TABLE IF NOT EXISTS wf_task (
    id                INT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    parent_task_id    INT NOT NULL DEFAULT 0 COMMENT '父任务ID',
    process_def_id    INT NOT NULL COMMENT '流程定义ID',
    instance_id       INT NOT NULL COMMENT '流程实例ID',
    task_def_id    	  VARCHAR(64) NOT NULL COMMENT '任务定义ID',
    name      		  VARCHAR(64) NOT NULL COMMENT '任务名称',
    assignee          VARCHAR(32) NULL COMMENT '执行人',
    task_type         INT NOT NULL COMMENT '任务类型',
    expire_time       DATETIME NULL COMMENT '任务期望完成时间',
    variable          VARCHAR(2048) NULL COMMENT '附属变量json存储',
    revision          INT NULL DEFAULT 0 COMMENT '修订版本',
    create_time       DATETIME NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`)
)ENGINE = InnoDB COMMENT='任务表';

CREATE TABLE IF NOT EXISTS wf_historic_process_instance (
    id                INT NOT NULL COMMENT '主键ID',
    parent_id         INT NOT NULL DEFAULT 0 COMMENT '父流程ID',
    process_def_id    INT NOT NULL COMMENT '流程定义ID',
    business_key      VARCHAR(32) NULL COMMENT '业务KEY',
    state       	  INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '状态',
    priority          INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '优先级',
    variable          VARCHAR(2048) NULL COMMENT '附属变量json存储',
    initiator         VARCHAR(32) NULL COMMENT '发起人',
    end_time          DATETIME NULL COMMENT '结束时间',
    start_time        DATETIME NOT NULL COMMENT '开始时间',
    PRIMARY KEY (`id`)
)ENGINE = InnoDB COMMENT='历史流程实例表';

CREATE TABLE IF NOT EXISTS wf_historic_task (
    id                INT NOT NULL COMMENT '主键ID',
    parent_task_id    INT NOT NULL DEFAULT 0 COMMENT '父任务ID',
    process_def_id    INT NOT NULL COMMENT '流程定义ID',
    instance_id       INT NOT NULL COMMENT '流程实例ID',
    task_def_id    	  VARCHAR(64) NOT NULL COMMENT '任务定义ID',
    name      		  VARCHAR(64) NOT NULL COMMENT '任务名称',
    assignee          VARCHAR(32) NULL COMMENT '执行人',
    task_type         INT NOT NULL COMMENT '任务类型',
    state        	  INT NOT NULL COMMENT '任务状态',
    variable          VARCHAR(2048) NULL COMMENT '附属变量json存储',
    start_time        DATETIME NOT NULL COMMENT '开始时间',
    end_time          DATETIME NOT NULL COMMENT '结束时间',
    PRIMARY KEY (`id`)
)ENGINE = InnoDB COMMENT='历史任务表';

create index process_definition_index_key on wf_process_definition (key_);
create index process_instance_index_process_def_id on wf_process_instance (process_def_id);
create index task_index_instance_id on wf_task (instance_id);
create index task_index_task_task_def_id on wf_task (task_def_id);
create index task_index_parent_task_id on wf_task (parent_task_id);
create index historic_instance_index_process_def_id on wf_historic_process_instance (process_def_id);
create index historic_task_index_instance_id on wf_historic_task (instance_id);
create index historic_task_index_task_def_id on wf_historic_task (task_def_id);
create index historic_task_index_parent_task_id on wf_historic_task (parent_task_id);

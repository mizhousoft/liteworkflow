CREATE TABLE IF NOT EXISTS wf_process_definition (
    id                VARCHAR(32) NOT NULL comment '主键ID',
    name              VARCHAR(32) NOT NULL comment '流程名称',
    display_name      VARCHAR(64) NOT NULL comment '流程显示名称',
    category          VARCHAR(32) NULL comment '流程分类',
    version           INT NOT NULL DEFAULT 0 comment '版本号',
    content           LONGBLOB NOT NULL comment '流程模型定义',
    instance_url      VARCHAR(128) NULL comment '实例url',
    creator           VARCHAR(20) NULL comment '创建人',
    create_time       DATETIME NOT NULL comment '创建时间',
    PRIMARY KEY (`id`)
)ENGINE = InnoDB comment='流程定义表';

CREATE TABLE IF NOT EXISTS wf_process_instance (
    id                VARCHAR(32) NOT NULL comment '主键ID',
    parent_id         VARCHAR(32) NULL comment '父流程ID',
    process_def_id    VARCHAR(32) NOT NULL comment '流程定义ID',
    priority          INT UNSIGNED NOT NULL DEFAULT 0 comment '优先级',
    parent_node_name  VARCHAR(64) NULL comment '父流程依赖的节点名称',
    variable          VARCHAR(2048) NULL comment '附属变量json存储',
    revision          INT UNSIGNED NOT NULL DEFAULT 0 comment '修订版本',
    creator           VARCHAR(20) NULL comment '发起人',
    create_time       DATETIME NOT NULL comment '发起时间',
    PRIMARY KEY (`id`)
)ENGINE = InnoDB comment='流程实例表';

CREATE TABLE IF NOT EXISTS wf_task (
    id                VARCHAR(32) NOT NULL PRIMARY KEY comment '主键ID',
    instance_id       VARCHAR(32) NOT NULL comment '流程实例ID',
    task_name         VARCHAR(100) NOT NULL comment '任务名称',
    display_name      VARCHAR(200) NOT NULL comment '任务显示名称',
    task_type         TINYINT(1) NOT NULL comment '任务类型',
    perform_type      TINYINT(1) comment '参与类型',
    operator          VARCHAR(50) comment '任务处理人',
    create_time       DATETIME comment '任务创建时间',
    finish_Time       DATETIME comment '任务完成时间',
    expire_time       DATETIME comment '任务期望完成时间',
    action_url        VARCHAR(200) comment '任务处理的url',
    parent_task_id    VARCHAR(32) comment '父任务ID',
    variable          VARCHAR(2000) comment '附属变量json存储',
    revision          INT comment '修订版本'
)comment='任务表';

CREATE TABLE IF NOT EXISTS wf_task_actor (
    task_id           VARCHAR(32) not null comment '任务ID',
    actor_id          VARCHAR(50) not null comment '参与者ID'
)comment='任务参与者表';

CREATE TABLE IF NOT EXISTS wf_historic_process_instance (
    id                VARCHAR(32) not null primary key comment '主键ID',
    process_id        VARCHAR(32) not null comment '流程定义ID',
    state       	  TINYINT(1) not null comment '状态',
    creator           VARCHAR(50) comment '发起人',
    create_time       DATETIME not null comment '发起时间',
    end_time          DATETIME comment '完成时间',
    expire_time       DATETIME comment '期望完成时间',
    priority          TINYINT(1) comment '优先级',
    parent_id         VARCHAR(32) comment '父流程ID',
    variable          VARCHAR(2000) comment '附属变量json存储'
)comment='历史流程实例表';

CREATE TABLE IF NOT EXISTS wf_historic_task (
    id                VARCHAR(32) not null primary key comment '主键ID',
    instance_id       VARCHAR(32) not null comment '流程实例ID',
    task_name         VARCHAR(100) not null comment '任务名称',
    display_name      VARCHAR(200) not null comment '任务显示名称',
    task_type         TINYINT(1) not null comment '任务类型',
    perform_type      TINYINT(1) comment '参与类型',
    task_state        TINYINT(1) not null comment '任务状态',
    operator          VARCHAR(50) comment '任务处理人',
    create_time       DATETIME not null comment '任务创建时间',
    finish_Time       DATETIME comment '任务完成时间',
    expire_time       DATETIME comment '任务期望完成时间',
    action_url        VARCHAR(200) comment '任务处理url',
    parent_task_id    VARCHAR(32) comment '父任务ID',
    variable          VARCHAR(2000) comment '附属变量json存储'
)comment='历史任务表';

CREATE TABLE IF NOT EXISTS wf_historic_task_actor (
    task_id           VARCHAR(32) not null comment '任务ID',
    actor_id          VARCHAR(50) not null comment '参与者ID'
)comment='历史任务参与者表';

create index IDX_process_name on wf_process_definition (name);
create index IDX_INSTANCE_PROCESSID on wf_process_instance (process_def_id);
create index IDX_TASK_instance_id on wf_task (instance_id);
create index IDX_TASK_TASKNAME on wf_task (task_name);
create index IDX_TASK_PARENTTASK on wf_task (parent_task_id);
create index IDX_TASKACTOR_TASK on wf_task_actor (task_id);
create index IDX_HISTORIC_INSTANCE_PROCESSID on wf_historic_process_instance (process_id);
create index IDX_HISTORIC_TASK_instance_id on wf_historic_task (instance_id);
create index IDX_HISTORIC_TASK_TASKNAME on wf_historic_task (task_name);
create index IDX_HISTORIC_TASK_PARENTTASK on wf_historic_task (parent_task_id);
create index IDX_HISTORIC_TASKACTOR_TASK on wf_historic_task_actor (task_id);

alter table wf_task_actor
  add constraint FK_TASK_ACTOR_TASKID foreign key (task_id)
  references wf_task (id);
alter table wf_task
  add constraint FK_TASK_INSTANCEID foreign key (instance_id)
  references wf_process_instance (id);
alter table wf_process_instance
  add constraint FK_INSTANCE_PARENTID foreign key (parent_id)
  references wf_process_instance (id);
alter table wf_process_instance
  add constraint FK_INSTANCE_PROCESSID foreign key (process_def_id)
  references wf_process_definition (id);
alter table wf_historic_task_actor
  add constraint FK_HISTORIC_TASKACTOR foreign key (task_id)
  references wf_historic_task (id);
alter table wf_historic_task
  add constraint FK_HISTORIC_TASK_INSTANCEID foreign key (instance_id)
  references wf_historic_process_instance (id);
alter table wf_historic_process_instance
  add constraint FK_HISTORIC_INSTANCE_PARENTID foreign key (parent_id)
  references wf_historic_process_instance (id);
alter table wf_historic_process_instance
  add constraint FK_HISTORIC_INSTANCE_PROCESSID foreign key (process_id)
  references wf_process_definition (id);
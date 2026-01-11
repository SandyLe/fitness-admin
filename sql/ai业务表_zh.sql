-- 删除表（如果存在）
DROP TABLE IF EXISTS ai_model;
DROP TABLE IF EXISTS ai_knowledge_quota;
DROP TABLE IF EXISTS ai_knowledge_document;
DROP TABLE IF EXISTS ai_knowledge_base;
DROP TABLE IF EXISTS ai_feedback;
DROP TABLE IF EXISTS ai_conversation_session;
DROP TABLE IF EXISTS ai_calendar_event;
DROP TABLE IF EXISTS ai_agent;

-- 智能体表
CREATE TABLE ai_agent
(
    id                 bigint auto_increment comment '智能体ID' primary key,
    agent_name         varchar(100)                            not null comment '智能体名称',
    agent_description  varchar(500)                            null comment '智能体描述',
    model_id           bigint                                  not null comment '使用的模型ID',
    knowledge_base_ids varchar(2000)                           null comment '关联的知识库ID列表(逗号分隔)',
    user_id            bigint                                  not null comment '创建用户ID',
    system_prompt      text                                    null comment '系统提示词',
    temperature        decimal(3, 2) default 0.70              null comment '温度参数(0-1)',
    max_tokens         int           default 2000              null comment '最大token数',
    status             tinyint(1)    default 1                 not null comment '状态(0:禁用 1:启用)',
    create_time        datetime      default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time        datetime      default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
) comment '智能体表' charset = utf8mb4;
CREATE INDEX idx_model_id ON ai_agent (model_id);
CREATE INDEX idx_status ON ai_agent (status);
CREATE INDEX idx_user_id ON ai_agent (user_id);

-- 日历事件表
CREATE TABLE ai_calendar_event
(
    event_id         bigint auto_increment comment '事件ID' primary key,
    title            varchar(255)                          not null comment '事件标题',
    event_date       date                                  not null comment '事件日期',
    start_time       time                                  null comment '开始时间（可为空，表示全天事件）',
    end_time         time                                  null comment '结束时间',
    description      varchar(1000)                         null comment '预约描述',
    is_reminder      char        default '0'               null comment '是否提醒（0=否，1=是）',
    reminder_minutes int                                   null comment '提前提醒分钟数（如15,30,60）',
    status           char        default '0'               null comment '状态（0=正常，1=已完成，2=已取消）',
    create_by        varchar(64) default ''                null comment '创建者',
    create_time      datetime    default CURRENT_TIMESTAMP null comment '创建时间',
    update_by        varchar(64) default ''                null comment '更新者',
    update_time      datetime    default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间'
) comment '日历事件表' charset = utf8mb4;
CREATE INDEX idx_create_by ON ai_calendar_event (create_by);
CREATE INDEX idx_event_date ON ai_calendar_event (event_date);

-- 对话会话表
CREATE TABLE ai_conversation_session
(
    id            bigint auto_increment comment '会话ID' primary key,
    memory_id     varchar(100)                       not null comment 'MongoDB中的memoryId',
    agent_id      bigint                             not null comment '智能体ID',
    user_id       bigint                             not null comment '用户ID',
    session_title varchar(200)                       null comment '会话标题',
    total_tokens  int      default 0                 null comment '总消耗token数',
    message_count int      default 0                 null comment '消息数量',
    start_time    datetime default CURRENT_TIMESTAMP not null comment '会话开始时间',
    end_time      datetime                           null comment '会话结束时间',
    create_time   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time   datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint uk_memory_id unique (memory_id)
) comment '对话会话表' charset = utf8mb4;
CREATE INDEX idx_agent_id ON ai_conversation_session (agent_id);
CREATE INDEX idx_start_time ON ai_conversation_session (start_time);
CREATE INDEX idx_user_id ON ai_conversation_session (user_id);

-- 问题反馈表
CREATE TABLE ai_feedback
(
    feedback_id bigint auto_increment comment '反馈ID' primary key,
    title       varchar(255)                          not null comment '反馈标题',
    content     text                                  null comment '反馈描述',
    status      char        default '0'               not null comment '状态（0=待处理，1=处理中，2=已解决，3=已关闭）',
    create_by   varchar(64) default ''                null comment '创建者',
    create_time datetime    default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime    default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间'
) comment '问题反馈表' charset = utf8mb4;
CREATE INDEX idx_create_by ON ai_feedback (create_by);
CREATE INDEX idx_status ON ai_feedback (status);

-- 知识库表
CREATE TABLE ai_knowledge_base
(
    id             bigint auto_increment comment '知识库ID' primary key,
    kb_name        varchar(255)                            not null comment '知识库名称',
    kb_description varchar(1000) default ''                null comment '知识库描述',
    user_id        bigint                                  not null comment '所属用户ID',
    file_count     bigint        default 0                 not null comment '文档数量',
    status         tinyint       default 1                 not null comment '状态(0:禁用 1:启用)',
    create_time    datetime      default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time    datetime      default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    create_by      varchar(64)   default ''                null comment '创建者',
    update_by      varchar(64)   default ''                null comment '更新者',
    remark         varchar(500)  default ''                null comment '备注'
) comment '知识库表';
CREATE INDEX idx_created_time ON ai_knowledge_base (create_time);
CREATE INDEX idx_status ON ai_knowledge_base (status);
CREATE INDEX idx_user_id ON ai_knowledge_base (user_id);

-- 知识库文档表
CREATE TABLE ai_knowledge_document
(
    id             bigint auto_increment comment '文档ID' primary key,
    kb_id          bigint                               not null comment '知识库ID',
    doc_name       varchar(255)                         not null comment '文档名称',
    doc_type       varchar(50)                          not null comment '文档类型(txt/pdf/docx等)',
    file_path      varchar(500)                         null comment '文件存储路径',
    file_size      bigint     default 0                 not null comment '文件大小(字节)',
    status         tinyint(1) default 0                 not null comment '状态(0:处理中 1:完成 2:失败)',
    process_result varchar(500)                         null comment '处理结果信息',
    create_time    datetime   default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time    datetime   default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    vectorId       json                                 null comment '向量数据库的向量ID'
) comment '知识库文档表' charset = utf8mb4;
CREATE INDEX idx_created_time ON ai_knowledge_document (create_time);
CREATE INDEX idx_kb_id ON ai_knowledge_document (kb_id);
CREATE INDEX idx_status ON ai_knowledge_document (status);

-- 用户知识库配额表
CREATE TABLE ai_knowledge_quota
(
    quota_id           bigint auto_increment comment '配额ID' primary key,
    user_id            bigint                                not null comment '用户ID（关联 sys_user.user_id）',
    max_storage_bytes  bigint      default 209715200         not null comment '最大存储限额（字节），默认200M',
    max_kb_count       int         default 10                not null comment '最多可创建知识库数量，默认10个',
    used_storage_bytes bigint      default 0                 not null comment '已用存储（字节）',
    used_kb_count      int         default 0                 not null comment '已创建知识库数量',
    create_by          varchar(64) default ''                null comment '创建者',
    create_time        datetime    default CURRENT_TIMESTAMP null comment '创建时间',
    update_by          varchar(64) default ''                null comment '更新者',
    update_time        datetime    default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint uk_user_id unique (user_id)
) comment '用户知识库配额表' charset = utf8mb4;

-- AI模型配置表
CREATE TABLE ai_model
(
    id           bigint auto_increment comment '模型ID' primary key,
    model_name   varchar(100)                         not null comment '模型名称',
    model_type   varchar(50)                          not null comment '模型类型(OPENAI/CHATGLM/ERNIE/QWEN等)',
    model_config json                                 null comment '模型配置参数(JSON格式)',
    api_key      varchar(500)                         null comment 'API密钥',
    base_url     varchar(500)                         null comment 'API基础URL',
    status       tinyint(1) default 1                 not null comment '状态(0:禁用 1:启用)',
    create_by    varchar(50)                          not null comment '创建人ID',
    create_time  datetime   default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time  datetime   default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
) comment 'AI模型配置表' charset = utf8mb4;
CREATE INDEX idx_model_type ON ai_model (model_type);
CREATE INDEX idx_status ON ai_model (status);
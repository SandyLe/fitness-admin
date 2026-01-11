-- 1. 删除已有表（如果存在）
DROP TABLE IF EXISTS QRTZ_SIMPROP_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_SIMPLE_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_CRON_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_BLOB_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_JOB_DETAILS;
DROP TABLE IF EXISTS QRTZ_PAUSED_TRIGGER_GRPS;
DROP TABLE IF EXISTS QRTZ_FIRED_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_CALENDARS;
DROP TABLE IF EXISTS QRTZ_LOCKS;
DROP TABLE IF EXISTS QRTZ_SCHEDULER_STATE;
DROP TABLE IF EXISTS ai_knowledge_quota;
DROP TABLE IF EXISTS ai_knowledge_document;
DROP TABLE IF EXISTS ai_knowledge_base;
DROP TABLE IF EXISTS ai_conversation_session;
DROP TABLE IF EXISTS ai_calendar_event;
DROP TABLE IF EXISTS ai_feedback;
DROP TABLE IF EXISTS ai_agent;
DROP TABLE IF EXISTS ai_model;
DROP TABLE IF EXISTS sys_user_role;
DROP TABLE IF EXISTS sys_user_post;
DROP TABLE IF EXISTS sys_user;
DROP TABLE IF EXISTS sys_role_menu;
DROP TABLE IF EXISTS sys_role_dept;
DROP TABLE IF EXISTS sys_role;
DROP TABLE IF EXISTS sys_post;
DROP TABLE IF EXISTS sys_oper_log;
DROP TABLE IF EXISTS sys_notice;
DROP TABLE IF EXISTS sys_menu;
DROP TABLE IF EXISTS sys_logininfor;
DROP TABLE IF EXISTS sys_job_log;
DROP TABLE IF EXISTS sys_job;
DROP TABLE IF EXISTS sys_dict_type;
DROP TABLE IF EXISTS sys_dict_data;
DROP TABLE IF EXISTS sys_dept;
DROP TABLE IF EXISTS sys_config;
DROP TABLE IF EXISTS gen_table_column;
DROP TABLE IF EXISTS gen_table;

-- 2. 创建表结构（兼容英文字符）
CREATE TABLE QRTZ_CALENDARS
(
    sched_name    VARCHAR(120) NOT NULL,
    calendar_name VARCHAR(200) NOT NULL,
    calendar      BLOB         NOT NULL,
    PRIMARY KEY (sched_name, calendar_name)
);

CREATE TABLE QRTZ_FIRED_TRIGGERS
(
    sched_name        VARCHAR(120) NOT NULL,
    entry_id          VARCHAR(95)  NOT NULL,
    trigger_name      VARCHAR(200) NOT NULL,
    trigger_group     VARCHAR(200) NOT NULL,
    instance_name     VARCHAR(200) NOT NULL,
    fired_time        BIGINT       NOT NULL,
    sched_time        BIGINT       NOT NULL,
    priority          INT          NOT NULL,
    state             VARCHAR(16)  NOT NULL,
    job_name          VARCHAR(200),
    job_group         VARCHAR(200),
    is_nonconcurrent  VARCHAR(1),
    requests_recovery VARCHAR(1),
    PRIMARY KEY (sched_name, entry_id)
);

CREATE TABLE QRTZ_JOB_DETAILS
(
    sched_name        VARCHAR(120) NOT NULL,
    job_name          VARCHAR(200) NOT NULL,
    job_group         VARCHAR(200) NOT NULL,
    description       VARCHAR(250),
    job_class_name    VARCHAR(250) NOT NULL,
    is_durable        VARCHAR(1)   NOT NULL,
    is_nonconcurrent  VARCHAR(1)   NOT NULL,
    is_update_data    VARCHAR(1)   NOT NULL,
    requests_recovery VARCHAR(1)   NOT NULL,
    job_data          BLOB,
    PRIMARY KEY (sched_name, job_name, job_group)
);

CREATE TABLE QRTZ_LOCKS
(
    sched_name VARCHAR(120) NOT NULL,
    lock_name  VARCHAR(40)  NOT NULL,
    PRIMARY KEY (sched_name, lock_name)
);

CREATE TABLE QRTZ_PAUSED_TRIGGER_GRPS
(
    sched_name    VARCHAR(120) NOT NULL,
    trigger_group VARCHAR(200) NOT NULL,
    PRIMARY KEY (sched_name, trigger_group)
);

CREATE TABLE QRTZ_SCHEDULER_STATE
(
    sched_name        VARCHAR(120) NOT NULL,
    instance_name     VARCHAR(200) NOT NULL,
    last_checkin_time BIGINT       NOT NULL,
    checkin_interval  BIGINT       NOT NULL,
    PRIMARY KEY (sched_name, instance_name)
);

CREATE TABLE QRTZ_TRIGGERS
(
    sched_name     VARCHAR(120) NOT NULL,
    trigger_name   VARCHAR(200) NOT NULL,
    trigger_group  VARCHAR(200) NOT NULL,
    job_name       VARCHAR(200) NOT NULL,
    job_group      VARCHAR(200) NOT NULL,
    description    VARCHAR(250),
    next_fire_time BIGINT,
    prev_fire_time BIGINT,
    priority       INT,
    trigger_state  VARCHAR(16)  NOT NULL,
    trigger_type   VARCHAR(8)   NOT NULL,
    start_time     BIGINT       NOT NULL,
    end_time       BIGINT,
    calendar_name  VARCHAR(200),
    misfire_instr  SMALLINT,
    job_data       BLOB,
    PRIMARY KEY (sched_name, trigger_name, trigger_group),
    FOREIGN KEY (sched_name, job_name, job_group) REFERENCES QRTZ_JOB_DETAILS (sched_name, job_name, job_group)
);

CREATE INDEX idx_qrtz_triggers_job ON QRTZ_TRIGGERS (sched_name, job_name, job_group);

CREATE TABLE QRTZ_BLOB_TRIGGERS
(
    sched_name    VARCHAR(120) NOT NULL,
    trigger_name  VARCHAR(200) NOT NULL,
    trigger_group VARCHAR(200) NOT NULL,
    blob_data     BLOB,
    PRIMARY KEY (sched_name, trigger_name, trigger_group),
    FOREIGN KEY (sched_name, trigger_name, trigger_group) REFERENCES QRTZ_TRIGGERS (sched_name, trigger_name, trigger_group)
);

CREATE TABLE QRTZ_CRON_TRIGGERS
(
    sched_name      VARCHAR(120) NOT NULL,
    trigger_name    VARCHAR(200) NOT NULL,
    trigger_group   VARCHAR(200) NOT NULL,
    cron_expression VARCHAR(200) NOT NULL,
    time_zone_id    VARCHAR(80),
    PRIMARY KEY (sched_name, trigger_name, trigger_group),
    FOREIGN KEY (sched_name, trigger_name, trigger_group) REFERENCES QRTZ_TRIGGERS (sched_name, trigger_name, trigger_group)
);

CREATE TABLE QRTZ_SIMPLE_TRIGGERS
(
    sched_name      VARCHAR(120) NOT NULL,
    trigger_name    VARCHAR(200) NOT NULL,
    trigger_group   VARCHAR(200) NOT NULL,
    repeat_count    BIGINT       NOT NULL,
    repeat_interval BIGINT       NOT NULL,
    times_triggered BIGINT       NOT NULL,
    PRIMARY KEY (sched_name, trigger_name, trigger_group),
    FOREIGN KEY (sched_name, trigger_name, trigger_group) REFERENCES QRTZ_TRIGGERS (sched_name, trigger_name, trigger_group)
);

CREATE TABLE QRTZ_SIMPROP_TRIGGERS
(
    sched_name    VARCHAR(120)   NOT NULL,
    trigger_name  VARCHAR(200)   NOT NULL,
    trigger_group VARCHAR(200)   NOT NULL,
    str_prop_1    VARCHAR(512),
    str_prop_2    VARCHAR(512),
    str_prop_3    VARCHAR(512),
    int_prop_1    INT,
    int_prop_2    INT,
    long_prop_1   BIGINT,
    long_prop_2   BIGINT,
    dec_prop_1    DECIMAL(13, 4),
    dec_prop_2    DECIMAL(13, 4),
    bool_prop_1   VARCHAR(1),
    bool_prop_2   VARCHAR(1),
    PRIMARY KEY (sched_name, trigger_name, trigger_group),
    FOREIGN KEY (sched_name, trigger_name, trigger_group) REFERENCES QRTZ_TRIGGERS (sched_name, trigger_name, trigger_group)
);

CREATE TABLE ai_agent
(
    id                 BIGINT AUTO_INCREMENT PRIMARY KEY,
    agent_name         VARCHAR(255)                        NOT NULL,
    agent_description  VARCHAR(1000),
    model_id           BIGINT                              NOT NULL,
    knowledge_base_ids VARCHAR(4000),
    user_id            BIGINT                              NOT NULL,
    system_prompt      TEXT,
    temperature        DECIMAL(3, 2) DEFAULT 0.70,
    max_tokens         INT           DEFAULT 2000,
    status             TINYINT(1)    DEFAULT 1             NOT NULL,
    create_time        DATETIME      DEFAULT CURRENT_TIMESTAMP NOT NULL,
    update_time        DATETIME      DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP
) CHARSET = utf8mb4;

CREATE INDEX idx_ai_agent_model_id ON ai_agent (model_id);
CREATE INDEX idx_ai_agent_status ON ai_agent (status);
CREATE INDEX idx_ai_agent_user_id ON ai_agent (user_id);

CREATE TABLE ai_calendar_event
(
    event_id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    title            VARCHAR(500)                        NOT NULL,
    event_date       DATE                                NOT NULL,
    start_time       TIME,
    end_time         TIME,
    description      VARCHAR(2000),
    is_reminder      CHAR        DEFAULT '0',
    reminder_minutes INT,
    status           CHAR        DEFAULT '0',
    create_by        VARCHAR(255) DEFAULT '',
    create_time      DATETIME    DEFAULT CURRENT_TIMESTAMP,
    update_by        VARCHAR(255) DEFAULT '',
    update_time      DATETIME    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) CHARSET = utf8mb4;

CREATE INDEX idx_ai_calendar_event_create_by ON ai_calendar_event (create_by);
CREATE INDEX idx_ai_calendar_event_event_date ON ai_calendar_event (event_date);

CREATE TABLE ai_conversation_session
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    memory_id     VARCHAR(255)                       NOT NULL,
    agent_id      BIGINT                             NOT NULL,
    user_id       BIGINT                             NOT NULL,
    session_title VARCHAR(500),
    total_tokens  INT      DEFAULT 0,
    message_count INT      DEFAULT 0,
    start_time    DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    end_time      DATETIME,
    create_time   DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    update_time   DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_memory_id (memory_id)
) CHARSET = utf8mb4;

CREATE INDEX idx_ai_conversation_session_agent_id ON ai_conversation_session (agent_id);
CREATE INDEX idx_ai_conversation_session_start_time ON ai_conversation_session (start_time);
CREATE INDEX idx_ai_conversation_session_user_id ON ai_conversation_session (user_id);

CREATE TABLE ai_feedback
(
    feedback_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title       VARCHAR(500)                        NOT NULL,
    content     TEXT,
    status      CHAR        DEFAULT '0'             NOT NULL,
    create_by   VARCHAR(255) DEFAULT '',
    create_time DATETIME    DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) CHARSET = utf8mb4;

CREATE INDEX idx_ai_feedback_create_by ON ai_feedback (create_by);
CREATE INDEX idx_ai_feedback_status ON ai_feedback (status);

CREATE TABLE ai_knowledge_base
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    kb_name        VARCHAR(500)                        NOT NULL,
    kb_description VARCHAR(2000) DEFAULT '',
    user_id        BIGINT                              NOT NULL,
    file_count     BIGINT        DEFAULT 0             NOT NULL,
    status         TINYINT       DEFAULT 1             NOT NULL,
    create_time    DATETIME      DEFAULT CURRENT_TIMESTAMP NOT NULL,
    update_time    DATETIME      DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP,
    create_by      VARCHAR(255)  DEFAULT '',
    update_by      VARCHAR(255)  DEFAULT '',
    remark         VARCHAR(1000) DEFAULT ''
) CHARSET = utf8mb4;

CREATE INDEX idx_ai_knowledge_base_created_time ON ai_knowledge_base (create_time);
CREATE INDEX idx_ai_knowledge_base_status ON ai_knowledge_base (status);
CREATE INDEX idx_ai_knowledge_base_user_id ON ai_knowledge_base (user_id);

CREATE TABLE ai_knowledge_document
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    kb_id          BIGINT                               NOT NULL,
    doc_name       VARCHAR(500)                         NOT NULL,
    doc_type       VARCHAR(100)                         NOT NULL,
    file_path      VARCHAR(1000),
    file_size      BIGINT     DEFAULT 0                 NOT NULL,
    status         TINYINT(1) DEFAULT 0                 NOT NULL,
    process_result VARCHAR(1000),
    create_time    DATETIME   DEFAULT CURRENT_TIMESTAMP NOT NULL,
    update_time    DATETIME   DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP,
    vectorId       JSON
) CHARSET = utf8mb4;

CREATE INDEX idx_ai_knowledge_document_created_time ON ai_knowledge_document (create_time);
CREATE INDEX idx_ai_knowledge_document_kb_id ON ai_knowledge_document (kb_id);
CREATE INDEX idx_ai_knowledge_document_status ON ai_knowledge_document (status);

CREATE TABLE ai_knowledge_quota
(
    quota_id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id            BIGINT                              NOT NULL,
    max_storage_bytes  BIGINT      DEFAULT 209715200       NOT NULL,
    max_kb_count       INT         DEFAULT 10              NOT NULL,
    used_storage_bytes BIGINT      DEFAULT 0               NOT NULL,
    used_kb_count      INT         DEFAULT 0               NOT NULL,
    create_by          VARCHAR(255) DEFAULT '',
    create_time        DATETIME    DEFAULT CURRENT_TIMESTAMP,
    update_by          VARCHAR(255) DEFAULT '',
    update_time        DATETIME    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_user_id (user_id)
) CHARSET = utf8mb4;

CREATE TABLE ai_model
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    model_name   VARCHAR(255)                         NOT NULL,
    model_type   VARCHAR(100)                         NOT NULL,
    model_config JSON,
    api_key      VARCHAR(2000),
    base_url     VARCHAR(1000),
    status       TINYINT(1) DEFAULT 1                 NOT NULL,
    create_by    VARCHAR(255)                         NOT NULL,
    create_time  DATETIME   DEFAULT CURRENT_TIMESTAMP NOT NULL,
    update_time  DATETIME   DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP
) CHARSET = utf8mb4;

CREATE INDEX idx_ai_model_model_type ON ai_model (model_type);
CREATE INDEX idx_ai_model_status ON ai_model (status);

CREATE TABLE gen_table
(
    table_id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    table_name        VARCHAR(255) DEFAULT '',
    table_comment     VARCHAR(1000) DEFAULT '',
    sub_table_name    VARCHAR(255),
    sub_table_fk_name VARCHAR(255),
    class_name        VARCHAR(255) DEFAULT '',
    tpl_category      VARCHAR(255) DEFAULT 'crud',
    tpl_web_type      VARCHAR(100) DEFAULT '',
    package_name      VARCHAR(255),
    module_name       VARCHAR(100),
    business_name     VARCHAR(100),
    function_name     VARCHAR(255),
    function_author   VARCHAR(255),
    gen_type          CHAR         DEFAULT '0',
    gen_path          VARCHAR(500) DEFAULT '/',
    options           VARCHAR(2000),
    create_by         VARCHAR(255) DEFAULT '',
    create_time       DATETIME,
    update_by         VARCHAR(255) DEFAULT '',
    update_time       DATETIME,
    remark            VARCHAR(1000)
) CHARSET = utf8mb4;

CREATE TABLE gen_table_column
(
    column_id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    table_id       BIGINT,
    column_name    VARCHAR(255),
    column_comment VARCHAR(1000),
    column_type    VARCHAR(255),
    java_type      VARCHAR(1000),
    java_field     VARCHAR(255),
    is_pk          CHAR,
    is_increment   CHAR,
    is_required    CHAR,
    is_insert      CHAR,
    is_edit        CHAR,
    is_list        CHAR,
    is_query       CHAR,
    query_type     VARCHAR(255) DEFAULT 'EQ',
    html_type      VARCHAR(255),
    dict_type      VARCHAR(255) DEFAULT '',
    sort           INT,
    create_by      VARCHAR(255) DEFAULT '',
    create_time    DATETIME,
    update_by      VARCHAR(255) DEFAULT '',
    update_time    DATETIME
) CHARSET = utf8mb4;

CREATE TABLE sys_config
(
    config_id    INT AUTO_INCREMENT PRIMARY KEY,
    config_name  VARCHAR(255) DEFAULT '',
    config_key   VARCHAR(255) DEFAULT '',
    config_value VARCHAR(1000) DEFAULT '',
    config_type  CHAR         DEFAULT 'N',
    create_by    VARCHAR(255) DEFAULT '',
    create_time  DATETIME,
    update_by    VARCHAR(255) DEFAULT '',
    update_time  DATETIME,
    remark       VARCHAR(1000)
) CHARSET = utf8mb4;

CREATE TABLE sys_dept
(
    dept_id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    parent_id   BIGINT      DEFAULT 0,
    ancestors   VARCHAR(100) DEFAULT '',
    dept_name   VARCHAR(100) DEFAULT '',
    order_num   INT         DEFAULT 0,
    leader      VARCHAR(100),
    phone       VARCHAR(50),
    email       VARCHAR(255),
    status      CHAR        DEFAULT '0',
    del_flag    CHAR        DEFAULT '0',
    create_by   VARCHAR(255) DEFAULT '',
    create_time DATETIME,
    update_by   VARCHAR(255) DEFAULT '',
    update_time DATETIME
) CHARSET = utf8mb4;

CREATE TABLE sys_dict_data
(
    dict_code   BIGINT AUTO_INCREMENT PRIMARY KEY,
    dict_sort   INT          DEFAULT 0,
    dict_label  VARCHAR(255) DEFAULT '',
    dict_value  VARCHAR(255) DEFAULT '',
    dict_type   VARCHAR(255) DEFAULT '',
    css_class   VARCHAR(255),
    list_class  VARCHAR(255),
    is_default  CHAR         DEFAULT 'N',
    status      CHAR         DEFAULT '0',
    create_by   VARCHAR(255) DEFAULT '',
    create_time DATETIME,
    update_by   VARCHAR(255) DEFAULT '',
    update_time DATETIME,
    remark      VARCHAR(1000)
) CHARSET = utf8mb4;

CREATE TABLE sys_dict_type
(
    dict_id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    dict_name   VARCHAR(255) DEFAULT '',
    dict_type   VARCHAR(255) DEFAULT '',
    status      CHAR         DEFAULT '0',
    create_by   VARCHAR(255) DEFAULT '',
    create_time DATETIME,
    update_by   VARCHAR(255) DEFAULT '',
    update_time DATETIME,
    remark      VARCHAR(1000),
    UNIQUE KEY dict_type (dict_type)
) CHARSET = utf8mb4;

CREATE TABLE sys_job
(
    job_id          BIGINT AUTO_INCREMENT,
    job_name        VARCHAR(255)  DEFAULT ''        NOT NULL,
    job_group       VARCHAR(255)  DEFAULT 'DEFAULT' NOT NULL,
    invoke_target   VARCHAR(2000)                   NOT NULL,
    cron_expression VARCHAR(500)  DEFAULT '',
    misfire_policy  VARCHAR(100)  DEFAULT '3',
    concurrent      CHAR          DEFAULT '1',
    status          CHAR          DEFAULT '0',
    create_by       VARCHAR(255)  DEFAULT '',
    create_time     DATETIME,
    update_by       VARCHAR(255)  DEFAULT '',
    update_time     DATETIME,
    remark          VARCHAR(1000) DEFAULT '',
    PRIMARY KEY (job_id, job_name, job_group)
) CHARSET = utf8mb4;

CREATE TABLE sys_job_log
(
    job_log_id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    job_name       VARCHAR(255)               NOT NULL,
    job_group      VARCHAR(255)               NOT NULL,
    invoke_target  VARCHAR(2000)              NOT NULL,
    job_message    VARCHAR(1000),
    status         CHAR          DEFAULT '0',
    exception_info VARCHAR(2000) DEFAULT '',
    create_time    DATETIME
) CHARSET = utf8mb4;

CREATE TABLE sys_logininfor
(
    info_id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_name      VARCHAR(255)  DEFAULT '',
    ipaddr         VARCHAR(255)  DEFAULT '',
    login_location VARCHAR(1000) DEFAULT '',
    browser        VARCHAR(255)  DEFAULT '',
    os             VARCHAR(255)  DEFAULT '',
    status         CHAR          DEFAULT '0',
    msg            VARCHAR(1000) DEFAULT '',
    login_time     DATETIME
) CHARSET = utf8mb4;

CREATE INDEX idx_sys_logininfor_lt ON sys_logininfor (login_time);
CREATE INDEX idx_sys_logininfor_s ON sys_logininfor (status);

CREATE TABLE sys_menu
(
    menu_id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    menu_name   VARCHAR(255)              NOT NULL,
    parent_id   BIGINT       DEFAULT 0,
    order_num   INT          DEFAULT 0,
    path        VARCHAR(500) DEFAULT '',
    component   VARCHAR(1000),
    query       VARCHAR(1000),
    route_name  VARCHAR(255) DEFAULT '',
    is_frame    INT          DEFAULT 1,
    is_cache    INT          DEFAULT 0,
    menu_type   CHAR         DEFAULT '',
    visible     CHAR         DEFAULT '0',
    status      CHAR         DEFAULT '0',
    perms       VARCHAR(500),
    icon        VARCHAR(500) DEFAULT '#',
    create_by   VARCHAR(255) DEFAULT '',
    create_time DATETIME,
    update_by   VARCHAR(255) DEFAULT '',
    update_time DATETIME,
    remark      VARCHAR(1000) DEFAULT ''
) CHARSET = utf8mb4;

CREATE TABLE sys_notice
(
    notice_id      INT AUTO_INCREMENT PRIMARY KEY,
    notice_title   VARCHAR(255)             NOT NULL,
    notice_type    CHAR                     NOT NULL,
    notice_content LONGBLOB,
    status         CHAR        DEFAULT '0',
    create_by      VARCHAR(255) DEFAULT '',
    create_time    DATETIME,
    update_by      VARCHAR(255) DEFAULT '',
    update_time    DATETIME,
    remark         VARCHAR(1000)
) CHARSET = utf8mb4;

CREATE TABLE sys_oper_log
(
    oper_id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    title          VARCHAR(255)   DEFAULT '',
    business_type  INT            DEFAULT 0,
    method         VARCHAR(500)   DEFAULT '',
    request_method VARCHAR(50)    DEFAULT '',
    operator_type  INT            DEFAULT 0,
    oper_name      VARCHAR(255)   DEFAULT '',
    dept_name      VARCHAR(255)   DEFAULT '',
    oper_url       VARCHAR(1000)  DEFAULT '',
    oper_ip        VARCHAR(255)   DEFAULT '',
    oper_location  VARCHAR(1000)  DEFAULT '',
    oper_param     VARCHAR(2000)  DEFAULT '',
    json_result    VARCHAR(2000)  DEFAULT '',
    status         INT            DEFAULT 0,
    error_msg      VARCHAR(2000)  DEFAULT '',
    oper_time      DATETIME,
    cost_time      BIGINT         DEFAULT 0
) CHARSET = utf8mb4;

CREATE INDEX idx_sys_oper_log_bt ON sys_oper_log (business_type);
CREATE INDEX idx_sys_oper_log_ot ON sys_oper_log (oper_time);
CREATE INDEX idx_sys_oper_log_s ON sys_oper_log (status);

CREATE TABLE sys_post
(
    post_id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    post_code   VARCHAR(255)           NOT NULL,
    post_name   VARCHAR(255)           NOT NULL,
    post_sort   INT                    NOT NULL,
    status      CHAR                   NOT NULL,
    create_by   VARCHAR(255) DEFAULT '',
    create_time DATETIME,
    update_by   VARCHAR(255) DEFAULT '',
    update_time DATETIME,
    remark      VARCHAR(1000)
) CHARSET = utf8mb4;

CREATE TABLE sys_role
(
    role_id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_name           VARCHAR(255)             NOT NULL,
    role_key            VARCHAR(500)             NOT NULL,
    role_sort           INT                      NOT NULL,
    data_scope          CHAR        DEFAULT '1',
    menu_check_strictly TINYINT(1)  DEFAULT 1,
    dept_check_strictly TINYINT(1)  DEFAULT 1,
    status              CHAR                     NOT NULL,
    del_flag            CHAR        DEFAULT '0',
    create_by           VARCHAR(255) DEFAULT '',
    create_time         DATETIME,
    update_by           VARCHAR(255) DEFAULT '',
    update_time         DATETIME,
    remark              VARCHAR(1000)
) CHARSET = utf8mb4;

CREATE TABLE sys_role_dept
(
    role_id BIGINT NOT NULL,
    dept_id BIGINT NOT NULL,
    PRIMARY KEY (role_id, dept_id)
) CHARSET = utf8mb4;

CREATE TABLE sys_role_menu
(
    role_id BIGINT NOT NULL,
    menu_id BIGINT NOT NULL,
    PRIMARY KEY (role_id, menu_id)
) CHARSET = utf8mb4;

CREATE TABLE sys_user
(
    user_id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    dept_id         BIGINT                    NULL,
    user_name       VARCHAR(255)              NOT NULL,
    nick_name       VARCHAR(255)              NOT NULL,
    user_type       VARCHAR(100)  DEFAULT '00',
    email           VARCHAR(255)  DEFAULT '',
    phonenumber     VARCHAR(100)  DEFAULT '',
    sex             CHAR          DEFAULT '0',
    avatar          VARCHAR(500)  DEFAULT '',
    password        VARCHAR(500)  DEFAULT '',
    status          CHAR          DEFAULT '0',
    del_flag        CHAR          DEFAULT '0',
    login_ip        VARCHAR(255)  DEFAULT '',
    login_date      DATETIME,
    pwd_update_date DATETIME,
    create_by       VARCHAR(255)  DEFAULT '',
    create_time     DATETIME,
    update_by       VARCHAR(255)  DEFAULT '',
    update_time     DATETIME,
    remark          VARCHAR(1000)
) CHARSET = utf8mb4;

CREATE TABLE sys_user_post
(
    user_id BIGINT NOT NULL,
    post_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, post_id)
) CHARSET = utf8mb4;

CREATE TABLE sys_user_role
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id)
) CHARSET = utf8mb4;



INSERT INTO sys_post (post_id, post_code, post_name, post_sort, status, create_by, create_time, update_by, update_time, remark) VALUES (1, 'ceo', 'CEO', 1, '0', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_post (post_id, post_code, post_name, post_sort, status, create_by, create_time, update_by, update_time, remark) VALUES (2, 'se', 'Project Lead', 2, '0', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_post (post_id, post_code, post_name, post_sort, status, create_by, create_time, update_by, update_time, remark) VALUES (3, 'hr', 'HR Manager', 3, '0', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_post (post_id, post_code, post_name, post_sort, status, create_by, create_time, update_by, update_time, remark) VALUES (4, 'user', 'Staff Member', 4, '0', 'admin', '2025-11-16 20:44:29', '', null, '');


INSERT INTO sys_role (role_id, role_name, role_key, role_sort, data_scope, menu_check_strictly, dept_check_strictly, status, del_flag, create_by, create_time, update_by, update_time, remark) VALUES (1, 'Super Administrator', 'admin', 1, '1', 1, 1, '0', '0', 'admin', '2025-11-16 20:44:29', '', null, 'Super Administrator');
INSERT INTO sys_role (role_id, role_name, role_key, role_sort, data_scope, menu_check_strictly, dept_check_strictly, status, del_flag, create_by, create_time, update_by, update_time, remark) VALUES (2, 'User Role', 'common', 2, '2', 1, 1, '0', '0', 'admin', '2025-11-16 20:44:29', 'admin', '2025-12-11 09:59:28', 'Regular User');
INSERT INTO sys_role (role_id, role_name, role_key, role_sort, data_scope, menu_check_strictly, dept_check_strictly, status, del_flag, create_by, create_time, update_by, update_time, remark) VALUES (100, 'System Administrator', 'system_admin', 0, '1', 1, 1, '0', '0', 'admin', '2025-12-10 09:43:03', 'admin', '2025-12-12 20:13:44', null);
INSERT INTO sys_role (role_id, role_name, role_key, role_sort, data_scope, menu_check_strictly, dept_check_strictly, status, del_flag, create_by, create_time, update_by, update_time, remark) VALUES (101, 'AI Chat User', 'ai:user', 0, '1', 1, 1, '0', '0', 'admin', '2025-12-10 09:46:45', 'admin', '2025-12-12 20:13:52', null);

INSERT INTO sys_role_dept (role_id, dept_id) VALUES (2, 100);
INSERT INTO sys_role_dept (role_id, dept_id) VALUES (2, 101);
INSERT INTO sys_role_dept (role_id, dept_id) VALUES (2, 105);

INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 2);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 3);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 100);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 101);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 102);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 103);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 104);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 105);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 106);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 107);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 108);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 109);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 110);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 111);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 112);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 113);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 114);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 115);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 116);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 117);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 500);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 501);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1000);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1001);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1002);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1003);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1004);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1005);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1006);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1007);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1008);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1009);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1010);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1011);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1012);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1013);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1014);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1015);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1016);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1017);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1018);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1019);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1020);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1021);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1022);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1023);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1024);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1025);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1026);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1027);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1028);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1029);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1030);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1031);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1032);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1033);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1034);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1035);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1036);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1037);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1038);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1039);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1040);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1041);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1042);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1043);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1044);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1045);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1046);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1047);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1048);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1049);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1050);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1051);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1052);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1053);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1054);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1055);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1056);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1057);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1058);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1059);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (2, 1060);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 2);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 3);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 100);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 101);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 102);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 103);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 104);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 105);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 106);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 107);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 108);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 109);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 110);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 111);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 112);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 113);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 114);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 115);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 116);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 117);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 500);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 501);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1000);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1001);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1002);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1003);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1004);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1005);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1006);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1007);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1008);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1009);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1010);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1011);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1012);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1013);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1014);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1015);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1016);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1017);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1018);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1019);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1020);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1021);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1022);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1023);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1024);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1025);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1026);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1027);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1028);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1029);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1030);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1031);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1032);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1033);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1034);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1035);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1036);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1037);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1038);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1039);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1040);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1041);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1042);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1043);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1044);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1045);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1046);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1047);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1048);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1049);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1050);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1051);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1052);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1053);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1054);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1055);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1056);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1057);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1058);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1059);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 1060);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 2000);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 2001);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 2002);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 2003);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 2004);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 2005);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 2011);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 2012);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 2013);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 2014);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 2016);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 2017);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 2018);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 2019);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 2020);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 2021);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 2022);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 2023);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 2032);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 2033);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 2034);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 2037);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 2038);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 2039);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 2040);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 2041);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 2042);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 2043);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 2044);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 2045);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 2046);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 2047);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 2048);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 2050);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 2051);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (100, 2052);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (101, 2000);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (101, 2001);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (101, 2002);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (101, 2003);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (101, 2004);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (101, 2005);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (101, 2011);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (101, 2012);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (101, 2013);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (101, 2014);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (101, 2016);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (101, 2017);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (101, 2018);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (101, 2019);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (101, 2020);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (101, 2021);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (101, 2022);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (101, 2023);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (101, 2032);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (101, 2033);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (101, 2034);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (101, 2037);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (101, 2038);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (101, 2039);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (101, 2040);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (101, 2041);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (101, 2042);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (101, 2043);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (101, 2044);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (101, 2048);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (101, 2050);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (101, 2051);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (101, 2052);


INSERT INTO sys_user (user_id, dept_id, user_name, nick_name, user_type, email, phonenumber, sex, avatar, password, status, del_flag, login_ip, login_date, pwd_update_date, create_by, create_time, update_by, update_time, remark) VALUES (1, 103, 'admin', 'AI Customer Admin', '00', 'ry@163.com', '15888888888', '1', '', '$2a$10$B79B0Qu1j.ARXM0ErnHOv.j69Dts9AbFL9776zfDEaVRhJgVQDKj6', '0', '0', '127.0.0.1', '2025-12-13 17:01:37', '2025-12-10 16:57:57', 'admin', '2025-11-16 20:44:29', '', '2025-12-10 16:57:57', 'Administrator');
INSERT INTO sys_user (user_id, dept_id, user_name, nick_name, user_type, email, phonenumber, sex, avatar, password, status, del_flag, login_ip, login_date, pwd_update_date, create_by, create_time, update_by, update_time, remark) VALUES (2, 105, 'ry', 'RuoYi', '00', 'ry@qq.com', '15666666666', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', '2025-11-16 20:44:29', '2025-11-16 20:44:29', 'admin', '2025-11-16 20:44:29', '', null, 'Tester');
INSERT INTO sys_user (user_id, dept_id, user_name, nick_name, user_type, email, phonenumber, sex, avatar, password, status, del_flag, login_ip, login_date, pwd_update_date, create_by, create_time, update_by, update_time, remark) VALUES (100, null, 'yaochao', 'Yao Chao', '00', '', '', '0', '', '$2a$10$v/2R4R0782HtcwDcxFW3cOJxhXTWwfdK49zvbh9MRdbe7t64j9vUm', '0', '0', '180.158.252.121', '2025-12-10 09:43:44', '2025-12-10 09:39:19', 'admin', '2025-12-10 09:38:17', 'admin', '2025-12-10 09:43:24', null);
INSERT INTO sys_user (user_id, dept_id, user_name, nick_name, user_type, email, phonenumber, sex, avatar, password, status, del_flag, login_ip, login_date, pwd_update_date, create_by, create_time, update_by, update_time, remark) VALUES (101, 100, 'wuxx', 'Wu XX', '00', '', '', '0', '', '$2a$10$2vWxqHPIklUFoWPIHqaTbOfbHdAAx7mriDpXRawrn7ZMdaDK.C9Py', '0', '0', '127.0.0.1', '2025-12-13 17:01:07', '2025-12-10 09:43:45', 'admin', '2025-12-10 09:42:23', '', '2025-12-10 09:43:45', null);
INSERT INTO sys_user (user_id, dept_id, user_name, nick_name, user_type, email, phonenumber, sex, avatar, password, status, del_flag, login_ip, login_date, pwd_update_date, create_by, create_time, update_by, update_time, remark) VALUES (102, 100, 'tangsiqi', 'Tang Siqi', '00', '111@111.com', '13111111111', '1', '/profile/avatar/2025/12/11/4fae06f42a75488b866af1f72142b4bb.jpg', '$2a$10$vUHZ0Bnl1SXUZ2IjGqBaxegA/lWHgbNa2uegseoI5vTFYdfIY8r3a', '0', '0', '127.0.0.1', '2025-12-13 08:29:37', '2025-12-11 10:35:47', 'admin', '2025-12-10 15:36:10', '', '2025-12-11 10:43:48', null);
INSERT INTO sys_user (user_id, dept_id, user_name, nick_name, user_type, email, phonenumber, sex, avatar, password, status, del_flag, login_ip, login_date, pwd_update_date, create_by, create_time, update_by, update_time, remark) VALUES (103, 100, 'liuyuan', 'Liu Yuan', '00', '', '', '0', '', '$2a$10$GXFf8FNU0ctJRbodscsC7O1rYe1flDOvCMyvHNAb.ltOqNUYEE9Ya', '0', '0', '27.115.4.238', '2025-12-12 14:10:49', null, 'admin', '2025-12-10 15:36:24', '', null, null);

INSERT INTO sys_user_post (user_id, post_id) VALUES (1, 1);
INSERT INTO sys_user_post (user_id, post_id) VALUES (2, 2);
INSERT INTO sys_user_post (user_id, post_id) VALUES (100, 2);

INSERT INTO sys_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO sys_user_role (user_id, role_id) VALUES (2, 2);
INSERT INTO sys_user_role (user_id, role_id) VALUES (100, 101);
INSERT INTO sys_user_role (user_id, role_id) VALUES (101, 101);
INSERT INTO sys_user_role (user_id, role_id) VALUES (102, 100);
INSERT INTO sys_user_role (user_id, role_id) VALUES (103, 100);

INSERT INTO sys_config (config_id, config_name, config_key, config_value, config_type, create_by, create_time, update_by, update_time, remark) VALUES (1, 'Main Framework - Default Skin Style', 'sys.index.skinName', 'skin-blue', 'Y', 'admin', '2025-11-16 20:44:30', '', null, 'Blue skin-blue, Green skin-green, Purple skin-purple, Red skin-red, Yellow skin-yellow');
INSERT INTO sys_config (config_id, config_name, config_key, config_value, config_type, create_by, create_time, update_by, update_time, remark) VALUES (2, 'User Management - Initial Account Password', 'sys.user.initPassword', '123456', 'Y', 'admin', '2025-11-16 20:44:30', '', null, 'Initial password: 123456');
INSERT INTO sys_config (config_id, config_name, config_key, config_value, config_type, create_by, create_time, update_by, update_time, remark) VALUES (3, 'Main Framework - Sidebar Theme', 'sys.index.sideTheme', 'theme-dark', 'Y', 'admin', '2025-11-16 20:44:30', '', null, 'Dark theme: theme-dark, Light theme: theme-light');
INSERT INTO sys_config (config_id, config_name, config_key, config_value, config_type, create_by, create_time, update_by, update_time, remark) VALUES (4, 'Account Self-Service - CAPTCHA Feature', 'sys.account.captchaEnabled', 'true', 'Y', 'admin', '2025-11-16 20:44:30', '', null, 'Enable CAPTCHA feature (true: enabled, false: disabled)');
INSERT INTO sys_config (config_id, config_name, config_key, config_value, config_type, create_by, create_time, update_by, update_time, remark) VALUES (5, 'Account Self-Service - User Registration', 'sys.account.registerUser', 'false', 'Y', 'admin', '2025-11-16 20:44:30', '', null, 'Enable user registration feature (true: enabled, false: disabled)');
INSERT INTO sys_config (config_id, config_name, config_key, config_value, config_type, create_by, create_time, update_by, update_time, remark) VALUES (6, 'User Login - Blacklisted IPs', 'sys.login.blackIPList', '', 'Y', 'admin', '2025-11-16 20:44:30', '', null, 'Set login IP blacklist. Multiple entries separated by ; supports wildcard (*) and CIDR notation');
INSERT INTO sys_config (config_id, config_name, config_key, config_value, config_type, create_by, create_time, update_by, update_time, remark) VALUES (7, 'User Management - Initial Password Policy', 'sys.account.initPasswordModify', '1', 'Y', 'admin', '2025-11-16 20:44:30', '', null, '0: Disabled, no prompts; 1: Prompt users to change initial password at login if not modified');
INSERT INTO sys_config (config_id, config_name, config_key, config_value, config_type, create_by, create_time, update_by, update_time, remark) VALUES (8, 'User Management - Password Expiration Cycle', 'sys.account.passwordValidateDays', '0', 'Y', 'admin', '2025-11-16 20:44:30', '', null, 'Password expiration cycle in days (0: no limit, must be >0 and <365 if modified). Users will be prompted to change password after this period');

INSERT INTO sys_dept (dept_id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status, del_flag, create_by, create_time, update_by, update_time) VALUES (100, 0, '0', 'Ruoyi Technology', 0, 'Ruo Yi', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2025-11-16 20:44:29', '', null);
INSERT INTO sys_dept (dept_id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status, del_flag, create_by, create_time, update_by, update_time) VALUES (101, 100, '0,100', 'Shenzhen Headquarters', 1, 'Ruo Yi', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2025-11-16 20:44:29', '', null);
INSERT INTO sys_dept (dept_id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status, del_flag, create_by, create_time, update_by, update_time) VALUES (102, 100, '0,100', 'Changsha Branch', 2, 'Ruo Yi', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2025-11-16 20:44:29', '', null);
INSERT INTO sys_dept (dept_id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status, del_flag, create_by, create_time, update_by, update_time) VALUES (103, 101, '0,100,101', 'R&D Department', 1, 'Ruo Yi', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2025-11-16 20:44:29', '', null);
INSERT INTO sys_dept (dept_id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status, del_flag, create_by, create_time, update_by, update_time) VALUES (104, 101, '0,100,101', 'Marketing Department', 2, 'Ruo Yi', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2025-11-16 20:44:29', '', null);
INSERT INTO sys_dept (dept_id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status, del_flag, create_by, create_time, update_by, update_time) VALUES (105, 101, '0,100,101', 'QA Department', 3, 'Ruo Yi', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2025-11-16 20:44:29', '', null);
INSERT INTO sys_dept (dept_id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status, del_flag, create_by, create_time, update_by, update_time) VALUES (106, 101, '0,100,101', 'Finance Department', 4, 'Ruo Yi', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2025-11-16 20:44:29', '', null);
INSERT INTO sys_dept (dept_id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status, del_flag, create_by, create_time, update_by, update_time) VALUES (107, 101, '0,100,101', 'Operations Department', 5, 'Ruo Yi', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2025-11-16 20:44:29', '', null);
INSERT INTO sys_dept (dept_id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status, del_flag, create_by, create_time, update_by, update_time) VALUES (108, 102, '0,100,102', 'Marketing Department', 1, 'Ruo Yi', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2025-11-16 20:44:29', '', null);
INSERT INTO sys_dept (dept_id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status, del_flag, create_by, create_time, update_by, update_time) VALUES (109, 102, '0,100,102', 'Finance Department', 2, 'Ruo Yi', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2025-11-16 20:44:29', '', null);

INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark) VALUES (1, 1, 'Male', '0', 'sys_user_sex', '', '', 'Y', '0', 'admin', '2025-11-16 20:44:30', '', null, 'Gender: Male');
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark) VALUES (2, 2, 'Female', '1', 'sys_user_sex', '', '', 'N', '0', 'admin', '2025-11-16 20:44:30', '', null, 'Gender: Female');
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark) VALUES (3, 3, 'Unknown', '2', 'sys_user_sex', '', '', 'N', '0', 'admin', '2025-11-16 20:44:30', '', null, 'Gender: Unknown');
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark) VALUES (4, 1, 'Show', '0', 'sys_show_hide', '', 'primary', 'Y', '0', 'admin', '2025-11-16 20:44:30', '', null, 'Show menu');
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark) VALUES (5, 2, 'Hide', '1', 'sys_show_hide', '', 'danger', 'N', '0', 'admin', '2025-11-16 20:44:30', '', null, 'Hide menu');
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark) VALUES (6, 1, 'Active', '0', 'sys_normal_disable', '', 'primary', 'Y', '0', 'admin', '2025-11-16 20:44:30', '', null, 'Active status');
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark) VALUES (7, 2, 'Disabled', '1', 'sys_normal_disable', '', 'danger', 'N', '0', 'admin', '2025-11-16 20:44:30', '', null, 'Disabled status');
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark) VALUES (8, 1, 'Active', '0', 'sys_job_status', '', 'primary', 'Y', '0', 'admin', '2025-11-16 20:44:30', '', null, 'Active status');
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark) VALUES (9, 2, 'Paused', '1', 'sys_job_status', '', 'danger', 'N', '0', 'admin', '2025-11-16 20:44:30', '', null, 'Paused status');
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark) VALUES (10, 1, 'Default', 'DEFAULT', 'sys_job_group', '', '', 'Y', '0', 'admin', '2025-11-16 20:44:30', '', null, 'Default group');
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark) VALUES (11, 2, 'System', 'SYSTEM', 'sys_job_group', '', '', 'N', '0', 'admin', '2025-11-16 20:44:30', '', null, 'System group');
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark) VALUES (12, 1, 'Yes', 'Y', 'sys_yes_no', '', 'primary', 'Y', '0', 'admin', '2025-11-16 20:44:30', '', null, 'System default: Yes');
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark) VALUES (13, 2, 'No', 'N', 'sys_yes_no', '', 'danger', 'N', '0', 'admin', '2025-11-16 20:44:30', '', null, 'System default: No');
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark) VALUES (14, 1, 'Notification', '1', 'sys_notice_type', '', 'warning', 'Y', '0', 'admin', '2025-11-16 20:44:30', '', null, 'Notification');
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark) VALUES (15, 2, 'Announcement', '2', 'sys_notice_type', '', 'success', 'N', '0', 'admin', '2025-11-16 20:44:30', '', null, 'Announcement');
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark) VALUES (16, 1, 'Active', '0', 'sys_notice_status', '', 'primary', 'Y', '0', 'admin', '2025-11-16 20:44:30', '', null, 'Active status');
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark) VALUES (17, 2, 'Closed', '1', 'sys_notice_status', '', 'danger', 'N', '0', 'admin', '2025-11-16 20:44:30', '', null, 'Closed status');
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark) VALUES (18, 99, 'Other', '0', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2025-11-16 20:44:30', '', null, 'Other operation');
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark) VALUES (19, 1, 'Create', '1', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2025-11-16 20:44:30', '', null, 'Create operation');
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark) VALUES (20, 2, 'Update', '2', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2025-11-16 20:44:30', '', null, 'Update operation');
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark) VALUES (21, 3, 'Delete', '3', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2025-11-16 20:44:30', '', null, 'Delete operation');
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark) VALUES (22, 4, 'Authorize', '4', 'sys_oper_type', '', 'primary', 'N', '0', 'admin', '2025-11-16 20:44:30', '', null, 'Authorization operation');
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark) VALUES (23, 5, 'Export', '5', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2025-11-16 20:44:30', '', null, 'Export operation');
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark) VALUES (24, 6, 'Import', '6', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2025-11-16 20:44:30', '', null, 'Import operation');
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark) VALUES (25, 7, 'Force Logout', '7', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2025-11-16 20:44:30', '', null, 'Force logout operation');
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark) VALUES (26, 8, 'Generate Code', '8', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2025-11-16 20:44:30', '', null, 'Code generation operation');
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark) VALUES (27, 9, 'Clear Data', '9', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2025-11-16 20:44:30', '', null, 'Data clearance operation');
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark) VALUES (28, 1, 'Success', '0', 'sys_common_status', '', 'primary', 'N', '0', 'admin', '2025-11-16 20:44:30', '', null, 'Success status');
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark) VALUES (29, 2, 'Failed', '1', 'sys_common_status', '', 'danger', 'N', '0', 'admin', '2025-11-16 20:44:30', '', null, 'Failed status');
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark) VALUES (100, 0, 'OpenAI', 'OPENAI', 'ai_model_type', null, 'default', 'N', '0', 'admin', '2025-11-29 22:13:36', 'admin', '2025-11-29 22:14:34', 'Generic OpenAI API protocol');
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark) VALUES (101, 1, 'Tongyi Qianwen', 'QWEN', 'ai_model_type', null, 'default', 'N', '0', 'admin', '2025-11-29 22:13:51', 'admin', '2025-11-29 22:14:18', 'Alibaba Cloud provider');
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark) VALUES (102, 0, 'Tongyi Qianwen Plus', 'qwen-plus', 'ai_mode_name', null, 'default', 'N', '0', 'admin', '2025-11-30 20:28:39', 'admin', '2025-12-10 09:23:12', null);
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark) VALUES (103, 0, 'Tongyi Qianwen 3 Max', 'qwen3-max', 'ai_mode_name', null, 'default', 'N', '0', 'admin', '2025-12-09 10:59:11', 'admin', '2025-12-10 09:22:58', null);
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark) VALUES (104, 0, 'Tongyi Qianwen 3 Max Preview', 'qwen3-max-preview', 'ai_mode_name', null, 'default', 'N', '0', 'admin', '2025-12-10 09:22:12', 'admin', '2025-12-10 09:22:52', null);
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark) VALUES (105, 0, 'Tongyi Qianwen 3 Max 2025-09-23', 'qwen3-max-2025-09-23', 'ai_mode_name', null, 'default', 'N', '0', 'admin', '2025-12-10 10:01:46', '', null, null);
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark) VALUES (106, 0, 'Tongyi Qianwen Coder Plus', 'qwen-coder-plus', 'ai_mode_name', null, 'default', 'N', '0', 'admin', '2025-12-11 13:59:54', '', null, null);

INSERT INTO sys_dict_type (dict_id, dict_name, dict_type, status, create_by, create_time, update_by, update_time, remark) VALUES (1, 'User Gender', 'sys_user_sex', '0', 'admin', '2025-11-16 20:44:30', '', null, 'User gender list');
INSERT INTO sys_dict_type (dict_id, dict_name, dict_type, status, create_by, create_time, update_by, update_time, remark) VALUES (2, 'Menu Visibility', 'sys_show_hide', '0', 'admin', '2025-11-16 20:44:30', '', null, 'Menu visibility list');
INSERT INTO sys_dict_type (dict_id, dict_name, dict_type, status, create_by, create_time, update_by, update_time, remark) VALUES (3, 'System Status', 'sys_normal_disable', '0', 'admin', '2025-11-16 20:44:30', '', null, 'System status list');
INSERT INTO sys_dict_type (dict_id, dict_name, dict_type, status, create_by, create_time, update_by, update_time, remark) VALUES (4, 'Job Status', 'sys_job_status', '0', 'admin', '2025-11-16 20:44:30', '', null, 'Job status list');
INSERT INTO sys_dict_type (dict_id, dict_name, dict_type, status, create_by, create_time, update_by, update_time, remark) VALUES (5, 'Job Group', 'sys_job_group', '0', 'admin', '2025-11-16 20:44:30', '', null, 'Job group list');
INSERT INTO sys_dict_type (dict_id, dict_name, dict_type, status, create_by, create_time, update_by, update_time, remark) VALUES (6, 'Boolean Flag', 'sys_yes_no', '0', 'admin', '2025-11-16 20:44:30', '', null, 'Yes/No flag list');
INSERT INTO sys_dict_type (dict_id, dict_name, dict_type, status, create_by, create_time, update_by, update_time, remark) VALUES (7, 'Notification Type', 'sys_notice_type', '0', 'admin', '2025-11-16 20:44:30', '', null, 'Notification type list');
INSERT INTO sys_dict_type (dict_id, dict_name, dict_type, status, create_by, create_time, update_by, update_time, remark) VALUES (8, 'Notification Status', 'sys_notice_status', '0', 'admin', '2025-11-16 20:44:30', '', null, 'Notification status list');
INSERT INTO sys_dict_type (dict_id, dict_name, dict_type, status, create_by, create_time, update_by, update_time, remark) VALUES (9, 'Operation Type', 'sys_oper_type', '0', 'admin', '2025-11-16 20:44:30', '', null, 'Operation type list');
INSERT INTO sys_dict_type (dict_id, dict_name, dict_type, status, create_by, create_time, update_by, update_time, remark) VALUES (10, 'Common Status', 'sys_common_status', '0', 'admin', '2025-11-16 20:44:30', '', null, 'Common status list');
INSERT INTO sys_dict_type (dict_id, dict_name, dict_type, status, create_by, create_time, update_by, update_time, remark) VALUES (100, 'AI Model Type', 'ai_model_type', '0', 'admin', '2025-11-29 22:12:58', '', null, null);
INSERT INTO sys_dict_type (dict_id, dict_name, dict_type, status, create_by, create_time, update_by, update_time, remark) VALUES (101, 'AI Model Name', 'ai_mode_name', '0', 'admin', '2025-11-30 20:28:06', '', null, null);

INSERT INTO sys_job (job_id, job_name, job_group, invoke_target, cron_expression, misfire_policy, concurrent, status, create_by, create_time, update_by, update_time, remark) VALUES (1, 'System Default (No Parameters)', 'DEFAULT', 'ryTask.ryNoParams', '0/10 * * * * ?', '3', '1', '1', 'admin', '2025-11-16 20:44:30', '', null, '');
INSERT INTO sys_job (job_id, job_name, job_group, invoke_target, cron_expression, misfire_policy, concurrent, status, create_by, create_time, update_by, update_time, remark) VALUES (2, 'System Default (With Parameters)', 'DEFAULT', 'ryTask.ryParams(''ry'')', '0/15 * * * * ?', '3', '1', '1', 'admin', '2025-11-16 20:44:30', '', null, '');
INSERT INTO sys_job (job_id, job_name, job_group, invoke_target, cron_expression, misfire_policy, concurrent, status, create_by, create_time, update_by, update_time, remark) VALUES (3, 'System Default (Multiple Parameters)', 'DEFAULT', 'ryTask.ryMultipleParams(''ry'', true, 2000L, 316.50D, 100)', '0/20 * * * * ?', '3', '1', '1', 'admin', '2025-11-16 20:44:30', '', null, '');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1, 'System Management', 0, 1, 'system', null, '', '', 1, 0, 'M', '0', '0', '', 'system', 'admin', '2025-11-16 20:44:29', '', null, 'System management directory');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (2, 'System Monitoring', 0, 2, 'monitor', null, '', '', 1, 0, 'M', '0', '0', '', 'monitor', 'admin', '2025-11-16 20:44:29', '', null, 'System monitoring directory');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (3, 'System Tools', 0, 3, 'tool', null, '', '', 1, 0, 'M', '0', '0', '', 'tool', 'admin', '2025-11-16 20:44:29', '', null, 'System tools directory');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (100, 'User Management', 1, 1, 'user', 'system/user/index', '', '', 1, 0, 'C', '0', '0', 'system:user:list', 'user', 'admin', '2025-11-16 20:44:29', '', null, 'User management menu');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (101, 'Role Management', 1, 2, 'role', 'system/role/index', '', '', 1, 0, 'C', '0', '0', 'system:role:list', 'peoples', 'admin', '2025-11-16 20:44:29', '', null, 'Role management menu');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (102, 'Menu Management', 1, 3, 'menu', 'system/menu/index', '', '', 1, 0, 'C', '0', '0', 'system:menu:list', 'tree-table', 'admin', '2025-11-16 20:44:29', '', null, 'Menu management menu');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (103, 'Department Management', 1, 4, 'dept', 'system/dept/index', '', '', 1, 0, 'C', '0', '0', 'system:dept:list', 'tree', 'admin', '2025-11-16 20:44:29', '', null, 'Department management menu');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (104, 'Position Management', 1, 5, 'post', 'system/post/index', '', '', 1, 0, 'C', '0', '0', 'system:post:list', 'post', 'admin', '2025-11-16 20:44:29', '', null, 'Position management menu');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (105, 'Dictionary Management', 1, 6, 'dict', 'system/dict/index', '', '', 1, 0, 'C', '0', '0', 'system:dict:list', 'dict', 'admin', '2025-11-16 20:44:29', '', null, 'Dictionary management menu');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (106, 'Configuration Settings', 1, 7, 'config', 'system/config/index', '', '', 1, 0, 'C', '0', '0', 'system:config:list', 'edit', 'admin', '2025-11-16 20:44:29', '', null, 'Configuration settings menu');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (107, 'Notifications', 1, 8, 'notice', 'system/notice/index', '', '', 1, 0, 'C', '0', '0', 'system:notice:list', 'message', 'admin', '2025-11-16 20:44:29', '', null, 'Notifications menu');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (108, 'Log Management', 1, 9, 'log', '', '', '', 1, 0, 'M', '0', '0', '', 'log', 'admin', '2025-11-16 20:44:29', '', null, 'Log management menu');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (109, 'Online Users', 2, 1, 'online', 'monitor/online/index', '', '', 1, 0, 'C', '0', '0', 'monitor:online:list', 'online', 'admin', '2025-11-16 20:44:29', '', null, 'Online users menu');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (110, 'Scheduled Jobs', 2, 2, 'job', 'monitor/job/index', '', '', 1, 0, 'C', '0', '0', 'monitor:job:list', 'job', 'admin', '2025-11-16 20:44:29', '', null, 'Scheduled jobs menu');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (111, 'Database Monitoring', 2, 3, 'druid', 'monitor/druid/index', '', '', 1, 0, 'C', '0', '0', 'monitor:druid:list', 'druid', 'admin', '2025-11-16 20:44:29', '', null, 'Database monitoring menu');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (112, 'Server Monitoring', 2, 4, 'server', 'monitor/server/index', '', '', 1, 0, 'C', '0', '0', 'monitor:server:list', 'server', 'admin', '2025-11-16 20:44:29', '', null, 'Server monitoring menu');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (113, 'Cache Monitoring', 2, 5, 'cache', 'monitor/cache/index', '', '', 1, 0, 'C', '0', '0', 'monitor:cache:list', 'redis', 'admin', '2025-11-16 20:44:29', '', null, 'Cache monitoring menu');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (114, 'Cache List', 2, 6, 'cacheList', 'monitor/cache/list', '', '', 1, 0, 'C', '0', '0', 'monitor:cache:list', 'redis-list', 'admin', '2025-11-16 20:44:29', '', null, 'Cache list menu');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (115, 'Form Builder', 3, 1, 'build', 'tool/build/index', '', '', 1, 0, 'C', '0', '0', 'tool:build:list', 'build', 'admin', '2025-11-16 20:44:29', '', null, 'Form builder menu');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (116, 'Code Generation', 3, 2, 'gen', 'tool/gen/index', '', '', 1, 0, 'C', '0', '0', 'tool:gen:list', 'code', 'admin', '2025-11-16 20:44:29', '', null, 'Code generation menu');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (117, 'API Documentation', 3, 3, 'swagger', 'tool/swagger/index', '', '', 1, 0, 'C', '0', '0', 'tool:swagger:list', 'swagger', 'admin', '2025-11-16 20:44:29', '', null, 'API documentation menu');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (500, 'Operation Logs', 108, 1, 'operlog', 'monitor/operlog/index', '', '', 1, 0, 'C', '0', '0', 'monitor:operlog:list', 'form', 'admin', '2025-11-16 20:44:29', '', null, 'Operation logs menu');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (501, 'Login Logs', 108, 2, 'logininfor', 'monitor/logininfor/index', '', '', 1, 0, 'C', '0', '0', 'monitor:logininfor:list', 'logininfor', 'admin', '2025-11-16 20:44:29', '', null, 'Login logs menu');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1000, 'Search Users', 100, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:query', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1001, 'Add User', 100, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:add', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1002, 'Edit User', 100, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:edit', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1003, 'Delete User', 100, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:remove', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1004, 'Export Users', 100, 5, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:export', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1005, 'Import Users', 100, 6, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:import', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1006, 'Reset Password', 100, 7, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:resetPwd', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1007, 'Search Roles', 101, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:query', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1008, 'Add Role', 101, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:add', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1009, 'Edit Role', 101, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:edit', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1010, 'Delete Role', 101, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:remove', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1011, 'Export Roles', 101, 5, '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:export', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1012, 'Search Menus', 102, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:query', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1013, 'Add Menu', 102, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:add', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1014, 'Edit Menu', 102, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:edit', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1015, 'Delete Menu', 102, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:remove', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1016, 'Search Departments', 103, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:query', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1017, 'Add Department', 103, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:add', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1018, 'Edit Department', 103, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:edit', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1019, 'Delete Department', 103, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:remove', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1020, 'Search Positions', 104, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:query', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1021, 'Add Position', 104, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:add', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1022, 'Edit Position', 104, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:edit', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1023, 'Delete Position', 104, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:remove', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1024, 'Export Positions', 104, 5, '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:export', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1025, 'Search Dictionaries', 105, 1, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:query', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1026, 'Add Dictionary', 105, 2, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:add', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1027, 'Edit Dictionary', 105, 3, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:edit', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1028, 'Delete Dictionary', 105, 4, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:remove', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1029, 'Export Dictionaries', 105, 5, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:export', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1030, 'Search Configurations', 106, 1, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:query', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1031, 'Add Configuration', 106, 2, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:add', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1032, 'Edit Configuration', 106, 3, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:edit', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1033, 'Delete Configuration', 106, 4, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:remove', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1034, 'Export Configurations', 106, 5, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:export', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1035, 'Search Notifications', 107, 1, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:notice:query', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1036, 'Add Notification', 107, 2, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:notice:add', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1037, 'Edit Notification', 107, 3, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:notice:edit', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1038, 'Delete Notification', 107, 4, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:notice:remove', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1039, 'Search Operations', 500, 1, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:query', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1040, 'Delete Operations', 500, 2, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:remove', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1041, 'Export Operations', 500, 3, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:export', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1042, 'Search Logins', 501, 1, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:query', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1043, 'Delete Logins', 501, 2, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:remove', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1044, 'Export Logins', 501, 3, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:export', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1045, 'Unlock Account', 501, 4, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:unlock', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1046, 'Search Online', 109, 1, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:online:query', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1047, 'Batch Force Logout', 109, 2, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:online:batchLogout', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1048, 'Force Logout', 109, 3, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:online:forceLogout', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1049, 'Search Jobs', 110, 1, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:query', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1050, 'Add Job', 110, 2, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:add', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1051, 'Edit Job', 110, 3, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:edit', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1052, 'Delete Job', 110, 4, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:remove', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1053, 'Change Status', 110, 5, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:changeStatus', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1054, 'Export Jobs', 110, 6, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:export', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1055, 'Search Generation', 116, 1, '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:query', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1056, 'Edit Generation', 116, 2, '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:edit', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1057, 'Delete Generation', 116, 3, '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:remove', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1058, 'Import Code', 116, 4, '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:import', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1059, 'Preview Code', 116, 5, '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:preview', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (1060, 'Generate Code', 116, 6, '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:code', '#', 'admin', '2025-11-16 20:44:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (2000, 'AI Management', 0, 7, 'ai', null, null, '', 1, 0, 'M', '0', '0', '', 'example', 'admin', '2025-11-27 14:11:42', 'admin', '2025-12-11 09:53:53', '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (2001, 'AI Models', 2000, 1, 'model', 'ai/model/index', null, '', 1, 0, 'C', '0', '0', 'ai:model:list', 'monitor', 'admin', '2025-11-27 14:15:04', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (2002, 'Knowledge Base', 2000, 2, 'knowbase', 'ai/knowbase/index', null, '', 1, 0, 'C', '0', '0', 'ai:knowbase:list', 'documentation', 'admin', '2025-11-27 14:17:22', 'admin', '2025-12-11 13:52:33', '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (2003, 'AI Agents', 2000, 3, 'agent', 'ai/agent/index', null, '', 1, 0, 'C', '0', '0', 'ai:agent:list', 'dict', 'admin', '2025-11-27 14:18:34', 'admin', '2025-12-10 09:56:33', '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (2004, 'Add', 2002, 2, '', null, null, '', 1, 0, 'F', '0', '0', 'ai:knowbase:add', '#', 'admin', '2025-11-27 14:21:05', 'admin', '2025-12-11 18:30:00', '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (2005, 'Add Prompt', 2003, 1, '', null, null, '', 1, 0, 'F', '0', '0', 'ai:agent:add', '#', 'admin', '2025-11-27 14:22:00', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (2011, 'Add', 2001, 1, '', null, null, '', 1, 0, 'F', '0', '0', 'ai:model:add', '#', 'admin', '2025-11-27 15:00:29', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (2012, 'Edit', 2001, 2, '', null, null, '', 1, 0, 'F', '0', '0', 'ai:model:edit', '#', 'admin', '2025-11-27 15:00:46', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (2013, 'Delete', 2001, 3, '', null, null, '', 1, 0, 'F', '0', '0', 'ai:model:remove', '#', 'admin', '2025-11-27 15:01:05', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (2014, 'Export', 2001, 4, '', null, null, '', 1, 0, 'F', '0', '0', 'ai:model:export', '#', 'admin', '2025-11-27 15:01:21', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (2016, 'Add Document', 2002, 6, '', null, null, '', 1, 0, 'F', '0', '0', 'ai:knowdoc:list', '#', 'admin', '2025-11-27 15:02:48', 'admin', '2025-12-11 18:23:08', '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (2017, 'Edit', 2002, 3, '', null, null, '', 1, 0, 'F', '0', '0', 'ai:knowbase:edit', '#', 'admin', '2025-11-27 15:03:07', 'admin', '2025-12-11 18:30:04', '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (2018, 'Delete', 2002, 5, '', null, null, '', 1, 0, 'F', '0', '0', 'ai:knowbase:remove', '#', 'admin', '2025-11-27 15:03:22', 'admin', '2025-12-11 18:30:12', '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (2019, 'Export', 2002, 4, '', null, null, '', 1, 0, 'F', '0', '0', 'ai:knowbase:export', '#', 'admin', '2025-11-27 15:03:39', 'admin', '2025-12-11 18:30:08', '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (2020, 'Conversation History', 2003, 2, '', null, null, '', 1, 0, 'F', '0', '0', 'ai:agent:history', '#', 'admin', '2025-11-27 15:04:19', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (2021, 'Edit', 2003, 3, '', null, null, '', 1, 0, 'F', '0', '0', 'ai:agent:edit', '#', 'admin', '2025-11-27 15:04:36', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (2022, 'Delete', 2003, 4, '', null, null, '', 1, 0, 'F', '0', '0', 'ai:agent:remove', '#', 'admin', '2025-11-27 15:05:00', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (2023, 'Export', 2003, 6, '', null, null, '', 1, 0, 'F', '0', '0', 'ai:agent:export', '#', 'admin', '2025-11-27 15:05:18', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (2032, 'Chat Assistant', 0, 4, 'online', 'online/index', null, 'online', 1, 0, 'C', '0', '0', 'ai:user:chat', 'online', 'admin', '2025-12-11 09:49:29', 'admin', '2025-12-11 17:38:26', '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (2033, 'Conversation History', 0, 5, 'history', 'history/index', null, 'history', 1, 0, 'C', '0', '0', '', 'message', 'admin', '2025-12-11 09:50:45', 'admin', '2025-12-12 20:10:58', '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (2034, 'Calendar', 0, 6, 'calendar', 'calendar/index', null, 'ElectronicCalendar', 1, 0, 'C', '0', '0', 'ai:calendarevent:list', 'guide', 'admin', '2025-12-11 09:53:18', 'admin', '2025-12-12 16:20:25', '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (2037, 'Document Management', 2000, 2, 'knowdoc', 'ai/knowdoc/index', null, 'knowdoc', 1, 1, 'C', '1', '0', 'ai:knowdoc:list', 'list', 'admin', '2025-12-11 10:18:15', 'admin', '2025-12-11 18:24:24', '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (2038, 'Add', 2037, 1, '', null, null, '', 1, 0, 'F', '0', '0', 'ai:knowdoc:add', '#', 'admin', '2025-12-11 10:18:55', 'admin', '2025-12-11 15:16:11', '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (2039, 'Delete', 2037, 2, '', null, null, '', 1, 0, 'F', '0', '0', 'ai:knowdoc:remove', '#', 'admin', '2025-12-11 10:19:16', 'admin', '2025-12-11 18:21:03', '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (2040, 'Conversation Sessions', 2000, 3, 'consession', 'ai/consession/index', null, 'consession', 1, 0, 'C', '1', '0', 'ai:consession:list', 'list', 'admin', '2025-12-11 13:47:39', 'admin', '2025-12-11 13:52:25', '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (2041, 'Export', 2040, 1, '', null, null, '', 1, 0, 'F', '0', '0', 'ai:consession:export', '#', 'admin', '2025-12-11 13:48:49', 'admin', '2025-12-11 13:49:07', '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (2042, 'Search', 2002, 1, '', null, null, '', 1, 0, 'F', '0', '0', 'ai:knowbase:query', '#', 'admin', '2025-12-11 15:34:05', 'admin', '2025-12-11 18:29:56', '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (2043, 'Search', 2001, 5, '', null, null, '', 1, 0, 'F', '0', '0', 'ai:model:query', '#', 'admin', '2025-12-11 15:44:08', 'admin', '2025-12-11 15:45:33', '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (2044, 'Search', 2003, 5, '', null, null, '', 1, 0, 'F', '0', '0', 'ai:agent:query', '#', 'admin', '2025-12-11 15:45:04', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (2045, 'Search', 2037, 3, '', null, null, '', 1, 0, 'F', '0', '0', 'ai:knowdoc:query', '#', 'admin', '2025-12-11 18:01:32', 'admin', '2025-12-11 18:01:43', '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (2046, 'Edit', 2037, 4, '', null, null, '', 1, 0, 'F', '0', '0', 'ai:knowdoc:edit', '#', 'admin', '2025-12-11 18:02:14', 'admin', '2025-12-11 18:21:10', '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (2047, 'Export', 2037, 5, '', null, null, '', 1, 0, 'F', '0', '0', 'ai:knowdoc:export', '#', 'admin', '2025-12-11 18:02:37', 'admin', '2025-12-11 18:21:14', '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (2048, 'Feedback', 0, 8, 'feedback', 'feedback/index', null, 'feedback', 1, 0, 'C', '1', '0', null, 'email', 'admin', '2025-12-11 20:45:39', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (2050, 'Add', 2034, 1, '', null, null, '', 1, 0, 'F', '0', '0', 'ai:calendarevent:add', '#', 'tangsiqi', '2025-12-12 15:41:45', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (2051, 'Delete', 2034, 3, '', null, null, '', 1, 0, 'F', '0', '0', 'ai:calendarevent:remove', '#', 'admin', '2025-12-12 16:19:23', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (2052, 'List', 2034, 2, '', null, null, '', 1, 0, 'F', '0', '0', 'ai:calendarevent:list', '#', 'admin', '2025-12-12 16:19:47', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (2053, 'Quota Management', 2000, 4, 'knowquota', 'ai/knowquota/index', null, 'knowquota', 1, 0, 'C', '0', '0', 'ai:knowquota:list', 'tool', 'admin', '2025-12-12 23:09:02', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (2054, 'Add', 2053, 1, '', null, null, '', 1, 0, 'F', '0', '0', 'ai:knowquota:add', '#', 'admin', '2025-12-12 23:09:20', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (2055, 'Search', 2053, 2, '', null, null, '', 1, 0, 'F', '0', '0', 'ai:knowquota:query', '#', 'admin', '2025-12-12 23:09:40', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (2056, 'Export', 2053, 3, '', null, null, '', 1, 0, 'F', '0', '0', 'ai:knowquota:export', '#', 'admin', '2025-12-12 23:09:56', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (2057, 'Edit', 2053, 4, '', null, null, '', 1, 0, 'F', '0', '0', 'ai:knowquota:edit', '#', 'admin', '2025-12-12 23:10:12', '', null, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (2058, 'Delete', 2053, 5, '', null, null, '', 1, 0, 'F', '0', '0', 'ai:knowquota:remove', '#', 'admin', '2025-12-12 23:10:25', '', null, '');
create table IF NOT EXISTS user (
    uid INT NOT NULL AUTO_INCREMENT,
    type TINYINT(1) NOT NULL default 1 COMMENT '用户类型：1 - 普通用户; 2 - 平台（代表平台本身）',
    username VARCHAR(32) NOT NULL,
    pwd VARCHAR(64) NOT NULL,
    mobile VARCHAR(16) NOT NULL,
    create_time datetime NOT NULL default NOW(),
    PRIMARY KEY (uid),
    UNIQUE KEY unq_mobile (mobile)
)ENGINE=InnoDB AUTO_INCREMENT=1000;

# 初始化平台用户
INSERT INTO user(uid, type, username,pwd,mobile,create_time)
VALUES (999, 2, 'platform', 'lsdjfhdlskfhdsjk', '11111111111', NOW());

create table IF NOT EXISTS goods (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(64) NOT NULL COMMENT '商品名称',
    origin_price DECIMAL(8,2) NOT NULL DEFAULT '0.00',
    kill_price DECIMAL(8,2) NOT NULL DEFAULT '0.00',
    detail VARCHAR(1024) NOT NULL COMMENT '商品详情',
    start_time datetime  NOT NULL COMMENT '秒杀开始时间',
    end_time datetime NOT NULL COMMENT '秒杀结束时间',
    create_time datetime NOT NULL DEFAULT NOW() COMMENT '创建时间',
    PRIMARY KEY (id),
    key idx_start_time(start_time),
    key idx_end_time(end_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='商品表';

create table IF NOT EXISTS goods_order (
    id INT NOT NULL AUTO_INCREMENT,
    order_no VARCHAR(32) NOT NULL COMMENT '订单编号',
    uid INT NOT NULL COMMENT '用户',
    goods_id INT NOT NULL COMMENT '商品',
    status TINYINT(1) NOT NULL COMMENT '状态：1 - 待支付; 2 - 已支付; 3 - 已发货; 4 - 交易成功; 5 - 交易失败',
    origin_price DECIMAL(8,2) NOT NULL DEFAULT '0.00',
    kill_price DECIMAL(8,2) NOT NULL DEFAULT '0.00',
    create_time datetime NOT NULL DEFAULT NOW() COMMENT '创建时间',
    PRIMARY KEY (id),
    unique unq_user_goods(uid, goods_id),
    unique unq_order_no(order_no),
    key idx_status(status)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='订单表';

CREATE TABLE IF NOT EXISTS yycc_common_sequence (
  name varchar(32) NOT NULL,
  id bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO yycc_common_sequence(name, id) VALUES
  ('goods.order_no','0');

create table IF NOT EXISTS account (
    id INT NOT NULL AUTO_INCREMENT,
    uid INT NOT NULL,
    type TINYINT(1) NOT NULL default 1 COMMENT '账户类型：1 - 人民币账户; 2 - 积分账户',
    balance_amount DECIMAL(8,2) NOT NULL DEFAULT '0.00',
    freeze_amount DECIMAL(8,2) NOT NULL DEFAULT '0.00',
    create_time datetime NOT NULL default NOW(),
    PRIMARY KEY (id),
    unique unq_uid_type(uid, type),
    CHECK (balance_amount > 0 and freeze_amount > 0)
)ENGINE=InnoDB;

# 初始化平台人民币账户
INSERT INTO account(uid, type, balance_amount, freeze_amount,create_time)
VALUES (999, 1, 0, 0, NOW());

create table IF NOT EXISTS order_pay (
    id INT NOT NULL AUTO_INCREMENT,
    order_id INT NOT NULL,
    pay_type TINYINT(1) NOT NULL default 1 COMMENT '支付类型：1 - 账户余额支付; 2 - 微信支付',
    pay_amount DECIMAL(8,2) NOT NULL DEFAULT '0.00',
    create_time datetime NOT NULL default NOW(),
    PRIMARY KEY (id),
    unique unq_order_pay(order_id, pay_type)
)ENGINE=InnoDB;


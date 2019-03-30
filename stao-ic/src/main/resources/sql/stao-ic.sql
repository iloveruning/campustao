CREATE TABLE `file`
(
  `id`          bigint UNSIGNED  NOT NULL AUTO_INCREMENT,
  `name`        varchar(128)     NOT NULL COMMENT '文件名',
  `size`        bigint           NOT NULL COMMENT '文件大小',
  `type`        tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '文件类型：0->所有，1->图片，2->视频，3->文档doc',
  `uid`         varchar(64)      NOT NULL COMMENT '唯一id',
  `url`         varchar(128)     NOT NULL COMMENT '访问链接',
  `create_time` datetime         NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime         NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  INDEX `idx_uid` (`uid`) USING BTREE
) COMMENT '文件表';


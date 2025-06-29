-- 电科社区项目初始数据

-- ----------------------------
-- 1. 插入角色
-- ----------------------------
-- 注意：`common_user` 是在用户关注时自动检查并可能引用的，这里确保它一定存在。
INSERT INTO `auth_role` (`id`, `role_name`, `role_key`, `created_by`, `created_time`, `update_by`, `update_time`, `is_deleted`) VALUES (2, '系统管理员', 'admin', 'system', NOW(), 'system', NOW(), 0) ON DUPLICATE KEY UPDATE role_name='系统管理员';
-- INSERT INTO `auth_role` (`id`, `role_name`, `role_key`, `created_by`, `created_time`, `update_by`, `update_time`, `is_deleted`) VALUES (2, '普通用户', 'common_user', 'system', NOW(), 'system', NOW(), 0) ON DUPLICATE KEY UPDATE role_name='普通用户';


-- ----------------------------
-- 2. 插入权限 (菜单和操作)
-- ----------------------------
-- 顶级菜单
INSERT INTO `auth_permission` (`id`, `name`, `parent_id`, `type`, `permission_key`, `status`, `show`, `is_deleted`) VALUES (1, '系统管理', 0, 0, 'system', 0, 0, 0) ON DUPLICATE KEY UPDATE name='系统管理';
INSERT INTO `auth_permission` (`id`, `name`, `parent_id`, `type`, `permission_key`, `status`, `show`, `is_deleted`) VALUES (2, '内容管理', 0, 0, 'content', 0, 0, 0) ON DUPLICATE KEY UPDATE name='内容管理';
INSERT INTO `auth_permission` (`id`, `name`, `parent_id`, `type`, `permission_key`, `status`, `show`, `is_deleted`) VALUES (3, '题库管理', 0, 0, 'subject', 0, 0, 0) ON DUPLICATE KEY UPDATE name='题库管理';

-- 系统管理下的菜单
INSERT INTO `auth_permission` (`id`, `name`, `parent_id`, `type`, `permission_key`, `status`, `show`, `is_deleted`) VALUES (101, '用户管理', 1, 0, 'system:user', 0, 0, 0) ON DUPLICATE KEY UPDATE name='用户管理';
INSERT INTO `auth_permission` (`id`, `name`, `parent_id`, `type`, `permission_key`, `status`, `show`, `is_deleted`) VALUES (102, '角色管理', 1, 0, 'system:role', 0, 0, 0) ON DUPLICATE KEY UPDATE name='角色管理';
INSERT INTO `auth_permission` (`id`, `name`, `parent_id`, `type`, `permission_key`, `status`, `show`, `is_deleted`) VALUES (103, '权限管理', 1, 0, 'system:permission', 0, 0, 0) ON DUPLICATE KEY UPDATE name='权限管理';

-- 用户管理下的操作权限
INSERT INTO `auth_permission` (`id`, `name`, `parent_id`, `type`, `permission_key`, `status`, `show`, `is_deleted`) VALUES (10101, '查询用户', 101, 1, 'system:user:list', 0, 0, 0) ON DUPLICATE KEY UPDATE name='查询用户';
INSERT INTO `auth_permission` (`id`, `name`, `parent_id`, `type`, `permission_key`, `status`, `show`, `is_deleted`) VALUES (10102, '新增用户', 101, 1, 'system:user:add', 0, 0, 0) ON DUPLICATE KEY UPDATE name='新增用户';
INSERT INTO `auth_permission` (`id`, `name`, `parent_id`, `type`, `permission_key`, `status`, `show`, `is_deleted`) VALUES (10103, '修改用户', 101, 1, 'system:user:update', 0, 0, 0) ON DUPLICATE KEY UPDATE name='修改用户';
INSERT INTO `auth_permission` (`id`, `name`, `parent_id`, `type`, `permission_key`, `status`, `show`, `is_deleted`) VALUES (10104, '删除用户', 101, 1, 'system:user:delete', 0, 0, 0) ON DUPLICATE KEY UPDATE name='删除用户';

-- 内容管理下的菜单
INSERT INTO `auth_permission` (`id`, `name`, `parent_id`, `type`, `permission_key`, `status`, `show`, `is_deleted`) VALUES (201, '帖子管理', 2, 0, 'content:post', 0, 0, 0) ON DUPLICATE KEY UPDATE name='帖子管理';

-- 帖子管理下的操作权限
INSERT INTO `auth_permission` (`id`, `name`, `parent_id`, `type`, `permission_key`, `status`, `show`, `is_deleted`) VALUES (20101, '删除帖子', 201, 1, 'content:post:delete', 0, 0, 0) ON DUPLICATE KEY UPDATE name='删除帖子';


-- ----------------------------
-- 3. 插入角色权限关联
-- ----------------------------
-- 为'系统管理员'(role_id=1)分配所有权限
INSERT INTO `auth_role_permission` (`role_id`, `permission_id`) VALUES (1, 1), (1, 2), (1, 3), (1, 101), (1, 102), (1, 103), (1, 10101), (1, 10102), (1, 10103), (1, 10104), (1, 201), (1, 20101);

-- ----------------------------
-- 4. 插入默认管理员用户
-- ----------------------------
-- 密码'123456'使用Sa-Token默认加密方式(md5)
INSERT INTO `auth_user` (`id`, `user_name`, `nick_name`, `password`, `status`, `is_deleted`) VALUES (1, 'admin', '电科管理员', 'e10adc3949ba59abbe56e057f20f883e', 0, 0) ON DUPLICATE KEY UPDATE user_name='admin';

-- ----------------------------
-- 5. 插入用户角色关联
-- ----------------------------
-- 为'admin'(user_id=1)分配'系统管理员'(role_id=1)
INSERT INTO `auth_user_role` (`user_id`, `role_id`) VALUES (1, 1); 
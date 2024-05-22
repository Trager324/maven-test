CREATE TABLE if not exists `permissions_2024` (
                                                  `role` varchar(50) NOT NULL,
    `resource` varchar(255) NOT NULL,
    `action` varchar(8) NOT NULL,
    UNIQUE INDEX  `uk_role_permission` (`role`,`resource`,`action`) USING BTREE
    );


CREATE TABLE if not exists `permissions_2024` (
                                                  `role` varchar(50) NOT NULL,
    `resource` varchar(255) NOT NULL,
    `action` varchar(8) NOT NULL,
    UNIQUE INDEX `uk_role_permission` (`role`,`resource`,`action`) USING BTREE
    );


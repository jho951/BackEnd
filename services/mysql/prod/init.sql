-- 특정 사용자에게 최소 권한만 부여 (보안)
GRANT SELECT ON `prod_db`.* TO 'readonly_user'@'%' IDENTIFIED BY 'strongpassword';

-- 보안 관련 설정
SET GLOBAL slow_query_log = 'ON';
SET GLOBAL slow_query_log_file = '/var/log/mysql/slow.log';

USE prod_db;

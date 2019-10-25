CREATE DEFINER=`root`@`%` PROCEDURE `gateway`.`proc_get_next_seq`(
	IN fileDT VARCHAR(20),
	IN fileType VARCHAR(20),
	OUT curSeq INT)
BEGIN
	DECLARE count_vl INT;
	SELECT COUNT(*) INTO count_vl FROM gateway.sys_file_seq WHERE  gateway.sys_file_seq.file_dt = fileDT and gateway.sys_file_seq.code= fileType;

	IF (count_vl  > 0) THEN		
		UPDATE gateway.sys_file_seq SET gateway.sys_file_seq.nxt_seq = (gateway.sys_file_seq.nxt_seq + 1) WHERE gateway.sys_file_seq.code= fileType;
	ELSE
		UPDATE gateway.sys_file_seq SET gateway.sys_file_seq.file_dt = fileDT, gateway.sys_file_seq.nxt_seq = 1 WHERE gateway.sys_file_seq.code= fileType;
	END IF;

	COMMIT;
	SELECT gateway.sys_file_seq.nxt_seq INTO curSeq FROM gateway.sys_file_seq WHERE gateway.sys_file_seq.file_dt = fileDT and gateway.sys_file_seq.code= fileType;
END
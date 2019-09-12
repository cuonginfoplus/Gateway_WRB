package gateway.wrb.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "sys_file_seq")
@Data
@NamedStoredProcedureQueries({
	@NamedStoredProcedureQuery(name = "procGetNextSeq", procedureName = "proc_get_next_seq", parameters = {
			@StoredProcedureParameter(mode = ParameterMode.IN, name = "fileDT", type = String.class),
			@StoredProcedureParameter(mode = ParameterMode.IN, name = "fileType", type = String.class),
			@StoredProcedureParameter(mode = ParameterMode.OUT, name = "curSeq", type = Integer.class) }) })
public class SysFileSeqInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "code")
	private String code;

	@Column(name = "nxt_seq")
	private Integer nxtSeq;
	
	@Column(name = "file_dt")
	private String fileDT;
}

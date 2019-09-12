package gateway.wrb.model;

import lombok.Data;

@Data
public class RA001Model {
	private String tmsDt;
	private String tmsTm;
	private String coNo;
	private String actNo;
	private String dataCnt;
	private String etcAr;
	
	private String wdrActNo;
	private String aplDscd;
	private String msgTrno;
	private String trnStDt;
	private String trnClsDt;
	private String trnType;
	private String status;
	private String curCd;
	private String rcpAm;
	private String rcpCnt;
	private String outParticular;
	private String inParticular;
	private String cusIdNoCd;
	private String cusIdNo;
	private String isuDt;
	private String vldEdt;
}

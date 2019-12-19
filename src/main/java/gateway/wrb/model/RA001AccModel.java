package gateway.wrb.model;

import lombok.Data;

@Data
public class RA001AccModel {
    private String wdrActNo;
    private String msgTrno;
    private String trnStDt;
    private String trnClsDt;
    private String trnType;
    private String curCd;
    private String rcpAm;
    private String rcpCnt;
    private String outParticular;
    private String inParticular;
    private String cus_id_no_cd;
    private String cus_id_no;
    private String isuDt;
    private String vld_edt;
}

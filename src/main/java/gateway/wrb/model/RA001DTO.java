package gateway.wrb.model;

import gateway.wrb.domain.RA001Info;
import lombok.Data;

@Data
public class RA001DTO {
    private String wdrActNo;
    private String msgTrno;
    private String trnStDt;
    private String trnClsDt;
    private String status;
    private String curCd;
    private String rcpAm;
    private String rcpCnt;
    private String outParticular;
    private String inParticular;
    private String cus_id_no_cd;
    private String cus_id_no;
    private String isuDt;
    private String vld_edt;
    private String bankRcvDt;
    private String bankRcvTm;

    public void convertToDTO(RA001Info ra001Info) {
        this.wdrActNo = ra001Info.getWdrActNo();
        this.msgTrno = ra001Info.getMsgTrno();
        this.trnStDt = ra001Info.getTrnStDt();
        this.trnClsDt = ra001Info.getTrnClsDt();
        this.status = ra001Info.getStatus();
        this.curCd = ra001Info.getCurCd();
        this.rcpAm = ra001Info.getRcpAm();
        this.rcpCnt = ra001Info.getRcpCnt();
        this.outParticular = ra001Info.getOutParticular();
        this.inParticular = ra001Info.getInParticular();
        this.cus_id_no_cd = ra001Info.getCusIdNoCd();
        this.cus_id_no = ra001Info.getCusIdNo();
        this.isuDt = ra001Info.getIsuDt();
        this.vld_edt = ra001Info.getVldEdt();
    }
}

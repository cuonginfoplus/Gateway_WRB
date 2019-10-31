package gateway.wrb.model;

import lombok.Data;

@Data
public class HT002DTO {

    private String actNo;
    private String trnDt;
    private String trnTm;
    private String trnAmt;
    private String trnAfBl;
    private String trnType;
    private String refTxt;
    private String trmPrcSrno;
    private String virActNo;

    public HT002DTO() {
    }

    public HT002DTO(String actNo, String trnDt, String trnTm, String trnAmt, String trnAfBl, String trnType, String refTxt, String trmPrcSrno, String virActNo) {
        this.actNo = actNo;
        this.trnDt = trnDt;
        this.trnTm = trnTm;
        this.trnAmt = trnAmt;
        this.trnAfBl = trnAfBl;
        this.trnType = trnType;
        this.refTxt = refTxt;
        this.trmPrcSrno = trmPrcSrno;
        this.virActNo = virActNo;
    }
}
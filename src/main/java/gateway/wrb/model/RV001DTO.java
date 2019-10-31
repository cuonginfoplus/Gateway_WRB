package gateway.wrb.model;

import lombok.Data;

@Data
public class RV001DTO {
    private String virActNo;
    private String vractCusNm;
    private String trnAvlSdt;
    private String trnAvlEdt;
    private String trnAvlStm;
    private String trnAvlEtm;
    private String rgsTrnDt;
    private String bankRcvDt;
    private String bankRcvTm;

    public RV001DTO() {
    }

    public RV001DTO(String virActNo, String vractCusNm, String trnAvlSdt, String trnAvlEdt, String trnAvlStm, String trnAvlEtm, String rgsTrnDt, String bankRcvDt, String bankRcvTm) {
        this.virActNo = virActNo;
        this.vractCusNm = vractCusNm;
        this.trnAvlSdt = trnAvlSdt;
        this.trnAvlEdt = trnAvlEdt;
        this.trnAvlStm = trnAvlStm;
        this.trnAvlEtm = trnAvlEtm;
        this.rgsTrnDt = rgsTrnDt;
        this.bankRcvDt = bankRcvDt;
        this.bankRcvTm = bankRcvTm;
    }
}
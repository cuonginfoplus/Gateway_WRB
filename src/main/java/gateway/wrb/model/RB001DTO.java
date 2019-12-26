package gateway.wrb.model;

import lombok.Data;

@Data
public class RB001DTO {
    private String reqDt = "";
    private String trnDt = "";
    private String inActNo = "";
    private String trnDscd = "";
    private String msgDscd = "";
    private String rqDscd = "";
    private String multiTrnCd = "";
    private String feePreOcc = "";
    private String feeInclYn = "";
    private String trnList = "";
    private String seq = "";
    private String outActNo = "";
    private String curCd = "";
    private String trnAm = "";
    private String tobkDscd = "";
    private String istDscd = "";
    private String inCdAccGb = "";
    private String rcvbk1Cd = "";
    private String rcvbk2Cd = "";
    private String rcvbkNm = "";
    private String sndName = "";
    private String rcvacDppeNm = "";
    private String depRmk = "";
    private String wdrRmk = "";
    private String trnSrno = "";
    private String status = "";
    private String prcCd = "";
    private String errCd = "";
    private String refNo = "";
    private String bankRcvDt = "";
    private String bankRcvTm = "";

    public RB001DTO() {
    }

    public RB001DTO(String reqDt, String trnDt, String inActNo, String trnDscd, String msgDscd, String rqDscd, String multiTrnCd, String feePreOcc, String feeInclYn, String trnList, String seq, String outActNo, String curCd, String trnAm, String tobkDscd, String istDscd, String inCdAccGb, String rcvbk1Cd, String rcvbk2Cd, String rcvbkNm, String sndName, String rcvacDppeNm, String depRmk, String wdrRmk, String trnSrno, String status, String prcCd, String errCd, String refNo, String bankRcvDt, String bankRcvTm) {
        this.reqDt = reqDt;
        this.trnDt = trnDt;
        this.inActNo = inActNo;
        this.trnDscd = trnDscd;
        this.msgDscd = msgDscd;
        this.rqDscd = rqDscd;
        this.multiTrnCd = multiTrnCd;
        this.feePreOcc = feePreOcc;
        this.feeInclYn = feeInclYn;
        this.trnList = trnList;
        this.seq = seq;
        this.outActNo = outActNo;
        this.curCd = curCd;
        this.trnAm = trnAm;
        this.tobkDscd = tobkDscd;
        this.istDscd = istDscd;
        this.inCdAccGb = inCdAccGb;
        this.rcvbk1Cd = rcvbk1Cd;
        this.rcvbk2Cd = rcvbk2Cd;
        this.rcvbkNm = rcvbkNm;
        this.sndName = sndName;
        this.rcvacDppeNm = rcvacDppeNm;
        this.depRmk = depRmk;
        this.wdrRmk = wdrRmk;
        this.trnSrno = trnSrno;
        this.status = status;
        this.prcCd = prcCd;
        this.errCd = errCd;
        this.refNo = refNo;
        this.bankRcvDt = bankRcvDt;
        this.bankRcvTm = bankRcvTm;
    }
}
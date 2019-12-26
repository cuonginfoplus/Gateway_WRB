package gateway.wrb.model;

import lombok.Data;

@Data
public class ER001DTO {
    private Long er001id;
    private String noticeDt;
    private String noticeCnt;
    private String fromCcy;
    private String toCcy;
    private String baseRate;
    private String cashBuying;
    private String cashSelling;
    private String ttBuying;
    private String ttSelling;
    private String orderDscd;
    private String status;
    private String bankRcvDt;
    private String bankRcvTm;

    public ER001DTO() {
    }

    public ER001DTO(Long er001id, String noticeDt, String noticeCnt, String fromCcy, String toCcy, String baseRate, String cashBuying, String cashSelling, String ttBuying, String ttSelling, String orderDscd, String status, String bankRcvDt, String bankRcvTm) {
        this.er001id = er001id;
        this.noticeDt = noticeDt;
        this.noticeCnt = noticeCnt;
        this.fromCcy = fromCcy;
        this.toCcy = toCcy;
        this.baseRate = baseRate;
        this.cashBuying = cashBuying;
        this.cashSelling = cashSelling;
        this.ttBuying = ttBuying;
        this.ttSelling = ttSelling;
        this.orderDscd = orderDscd;
        this.status = status;
        this.bankRcvDt = bankRcvDt;
        this.bankRcvTm = bankRcvTm;
    }
}

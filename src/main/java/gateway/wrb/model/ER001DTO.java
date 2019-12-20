package gateway.wrb.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ER001DTO {
    private Long er001id;
    private String noticeDt;
    private Integer noticeCnt;
    private String fromCcy;
    private String toCcy;
    private BigDecimal baseRate;
    private BigDecimal cashBuying;
    private BigDecimal cashSelling;
    private BigDecimal ttBuying;
    private BigDecimal ttSelling;
    private String orderDscd;
    private String status;
    private String bankRcvDt;
    private String bankRcvTm;

    public ER001DTO() {
    }

    public ER001DTO(Long er001id, String noticeDt, Integer noticeCnt, String fromCcy, String toCcy, BigDecimal baseRate, BigDecimal cashBuying, BigDecimal cashSelling, BigDecimal ttBuying, BigDecimal ttSelling, String orderDscd, String status, String bankRcvDt, String bankRcvTm) {
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

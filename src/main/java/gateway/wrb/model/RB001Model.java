package gateway.wrb.model;

import lombok.Data;

import java.util.List;

@Data
public class RB001Model {
    private String orgCd;
    private String bankCd;
    private String bankCoNo;
    private String reqDt;
    private String trnDt;
    private String inActNo;
    private String trnDscd;
    private String rqDscd;
    private String multiTrnCd;
    private String feePreOcc;
    private String feeInclYn;
    private List<RB001AccModel> trnList;
}

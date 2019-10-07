package gateway.wrb.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
public class RV001Info_Resp implements Serializable {

    private Long rv001id;

    private String virActNo;
    private String vractCusNm;
    private String trnAvlSdt;
    private String trnAvlEdt;
    private String trnAvlStm;
    private String trnAvlEtm;
    private String rgsTrnDt;
    private String bankRcvDt;
    private String bankRcvTm;
}
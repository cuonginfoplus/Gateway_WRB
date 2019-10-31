package gateway.wrb.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "rb001_s")
@Data
public class RB001SInfo implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fbkname")
    private String fbkname;

    @Column(name = "msgDscd")
    private String msgDscd;

    @Column(name = "coNo")
    private String coNo;

    @Column(name = "reqDt")
    private String reqDt;

    @Column(name = "trnDt")
    private String trnDt;

    @Column(name = "inActNo")
    private String inActNo;

    @Column(name = "trnDscd")
    private String trnDscd;

    @Column(name = "rqDscd")
    private String rqDscd;

    @Column(name = "multiTrnCd")
    private String multiTrnCd;

    @Column(name = "feePreOcc")
    private String feePreOcc;

    @Column(name = "feeInclYn")
    private String feeInclYn;

    @Column(name = "firmSvrSec")
    private String firmSvrSec;

    @Column(name = "firmSvrSec2")
    private String firmSvrSec2;

    @Column(name = "comfirmDupYn")
    private String comfirmDupYn;

    @Column(name = "filler")
    private String filler;
}

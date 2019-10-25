package gateway.wrb.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "rb001")
@Data
public class RB001Info implements Serializable {
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

    @Column(name = "seq")
    private String seq;

    @Column(name = "outActNo")
    private String outActNo;

    @Column(name = "curCd")
    private String curCd;

    @Column(name = "trnAm")
    private String trnAm;

    @Column(name = "tobkDscd")
    private String tobkDscd;

    @Column(name = "istDscd")
    private String istDscd;

    @Column(name = "inCdAccGb")
    private String inCdAccGb;

    @Column(name = "rcvbk1Cd")
    private String rcvbk1Cd;

    @Column(name = "rcvbk2Cd")
    private String rcvbk2Cd;

    @Column(name = "rcvbkNm")
    private String rcvbkNm;

    @Column(name = "sndName")
    private String sndName;

    @Column(name = "rcvacDppeNm")
    private String rcvacDppeNm;

    @Column(name = "depRmk")
    private String depRmk;

    @Column(name = "wdrRmk")
    private String wdrRmk;

    @Column(name = "trnSrno")
    private String trnSrno;

    @Column(name = "status")
    private String status;

    @Column(name = "prcCd")
    private String prcCd;

    @Column(name = "errCd")
    private String errCd;

    @Column(name = "refNo")
    private String refNo;

    @Column(name = "filler")
    private String filler;
}

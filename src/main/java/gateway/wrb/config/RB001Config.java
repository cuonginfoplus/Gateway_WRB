package gateway.wrb.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class RB001Config {
    /* START */
    @Value("${rb001.length.s_msgDscd_1}")
    private Integer s_msgDscd_1;

    @Value("${rb001.length.s_coNo}")
    private Integer s_coNo;

    @Value("${rb001.length.s_reqDt}")
    private Integer s_reqDt;

    @Value("${rb001.length.s_trnDt}")
    private Integer s_trnDt;

    @Value("${rb001.length.s_inActNo}")
    private Integer s_inActNo;

    @Value("${rb001.length.s_trnDscd}")
    private Integer s_trnDscd;

    @Value("${rb001.length.s_msgDscd_2}")
    private Integer s_msgDscd_2;

    @Value("${rb001.length.s_rqDscd}")
    private Integer s_rqDscd;

    @Value("${rb001.length.s_multiTrnCd}")
    private Integer s_multiTrnCd;

    @Value("${rb001.length.s_feePreOcc}")
    private Integer s_feePreOcc;

    @Value("${rb001.length.s_feeInclYn}")
    private Integer s_feeInclYn;

    @Value("${rb001.length.s_firmSvrSec}")
    private Integer s_firmSvrSec;

    @Value("${rb001.length.s_firmSvrSec2}")
    private Integer s_firmSvrSec2;

    @Value("${rb001.length.s_comfirmDupYn}")
    private Integer s_comfirmDupYn;

    @Value("${rb001.length.s_filler}")
    private Integer s_filler;
    /* DATA */
    @Value("${rb001.length.msgDscd}")
    private Integer msgDscd;

    @Value("${rb001.length.seq}")
    private Integer seq;

    @Value("${rb001.length.outActNo}")
    private Integer outActNo;

    @Value("${rb001.length.curCd}")
    private Integer curCd;

    @Value("${rb001.length.trnAm}")
    private Integer trnAm;

    @Value("${rb001.length.tobkDscd}")
    private Integer tobkDscd;

    @Value("${rb001.length.istDscd}")
    private Integer istDscd;

    @Value("${rb001.length.inCdAccGb}")
    private Integer inCdAccGb;

    @Value("${rb001.length.rcvbk1Cd}")
    private Integer rcvbk1Cd;

    @Value("${rb001.length.rcvbk2Cd}")
    private Integer rcvbk2Cd;

    @Value("${rb001.length.rcvbkNm}")
    private Integer rcvbkNm;

    @Value("${rb001.length.sndName}")
    private Integer sndName;

    @Value("${rb001.length.rcvacDppeNm}")
    private Integer rcvacDppeNm;

    @Value("${rb001.length.depRmk}")
    private Integer depRmk;

    @Value("${rb001.length.wdrRmk}")
    private Integer wdrRmk;

    @Value("${rb001.length.trnSrno}")
    private Integer trnSrno;

    @Value("${rb001.length.status}")
    private Integer status;

    @Value("${rb001.length.prcCd}")
    private Integer prcCd;

    @Value("${rb001.length.errCd}")
    private Integer errCd;

    @Value("${rb001.length.refNo}")
    private Integer refNo;

    @Value("${rb001.length.filler}")
    private Integer filler;

    @Value("${rb001.length.e_msgDscd}")
    private Integer e_msgDscd;

    @Value("${rb001.length.e_totCnt}")
    private Integer e_totCnt;

    @Value("${rb001.length.e_totReqCnt}")
    private Integer e_totReqCnt;

    @Value("${rb001.length.e_totReqAmt}")
    private Integer e_totReqAmt;

    @Value("${rb001.length.e_totSucCnt}")
    private Integer e_totSucCnt;

    @Value("${rb001.length.e_totSucAmt}")
    private Integer e_totSucAmt;

    @Value("${rb001.length.e_failCnt}")
    private Integer e_failCnt;

    @Value("${rb001.length.e_failAmt}")
    private Integer e_failAmt;

    @Value("${rb001.length.e_inSucCnt}")
    private Integer e_inSucCnt;

    @Value("${rb001.length.e_inSucAmt}")
    private Integer e_inSucAmt;

    @Value("${rb001.length.e_outSucCnt}")
    private Integer e_outSucCnt;

    @Value("${rb001.length.e_outSucAmt}")
    private Integer e_outSucAmt;

    @Value("${rb001.length.e_filler}")
    private Integer e_filler;

}

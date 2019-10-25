package gateway.wrb.repositories;

import gateway.wrb.domain.RB001Info;

import java.util.List;

public interface RB001Repo {
    List<RB001Info> filterRB001(String orgCd, String bankCd, String bankCoNo, String trnxId);

    void save(RB001Info info);

    Integer isRB001Exist(String msgdscd, String seq, String outActNo, String curCd, String trnAm, String tobkDscd, String istDscd, String inCdAccGb, String rcvbk1Cd,
                         String rcvbk2Cd, String rcvbkNm, String sndName, String rcvacDppeNm, String depRmk, String wdrRmk, String trnSrno, String status,
                         String prcCd, String errCd, String refNo, String filler);

}

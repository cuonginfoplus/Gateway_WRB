package gateway.wrb.repositories;

import gateway.wrb.domain.RB001Info;
import gateway.wrb.domain.RB001SInfo;
import gateway.wrb.model.RB001DTO;
import gateway.wrb.model.RB001SDTO;

import java.util.List;

public interface RB001Repo {
    List<RB001DTO> filterRB001(String orgCd, String bankCd, String bankCoNo, String trnxId);

    void save(RB001Info info);

    void save_header(RB001SInfo info);

    Integer isRB001Exist(String msgdscd, String seq, String outActNo, String curCd, String trnAm, String tobkDscd, String istDscd, String inCdAccGb, String rcvbk1Cd,
                         String rcvbk2Cd, String status, String refNo);

    List<RB001SDTO> filterRB001S(String orgCd, String bankCd, String bankCoNo, String trnxId);
}

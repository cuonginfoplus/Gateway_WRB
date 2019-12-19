package gateway.wrb.repositories;

import gateway.wrb.domain.RA001Info;
import gateway.wrb.domain.RA001SInfo;
import gateway.wrb.model.RA001DTO;

import java.util.List;

public interface RA001Repo {
    List<RA001DTO> filterRA001(String orgCd, String bankCd, String bankCoNo, String outActNo, String bankRsvSdt, String bankRsvEdt);

    void save(RA001Info info);

    Integer isRA001Exist(String msgdscd, String wdrActNo, String aplDscd, String trnStDt, String trnType, String status, String curCd, String cusIdNo);

    void save_header(RA001SInfo ra001SInfo);

    void update(RA001Info info);
}

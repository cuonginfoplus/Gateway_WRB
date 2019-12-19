package gateway.wrb.repositories;

import gateway.wrb.domain.VLR001Info;
import gateway.wrb.domain.VLR001SInfo;
import gateway.wrb.model.RV001DTO;

import java.util.List;

public interface VLR001Repo {
    void addVLR001(VLR001Info vlr001Info);

    void addVLR001S(VLR001SInfo vlr001SInfo);

    List<RV001DTO> filterVLRV001(String orgCd, String bankCd, String bankCoNo, String outActNo, String rgsTrnSdt, String rgsTrnEdt);

    boolean isVLR001exist(String msgDscd, String virActNo, String aplDscd, String trnAvlSdt, String trnAvlEdt, String trnAvlStm, String trnAvlEtm, String rgsTrnDt, String stsDscd);

    void updateVLR001(VLR001Info vlr001Info);

    void deleteVLR001(Long id);
}

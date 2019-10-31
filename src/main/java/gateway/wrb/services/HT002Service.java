package gateway.wrb.services;


import gateway.wrb.domain.FbkFilesInfo;
import gateway.wrb.domain.HT002Info;
import gateway.wrb.model.HT002DTO;

import java.util.List;

public interface HT002Service {
    List<HT002Info> getAllHT002();

    List<HT002DTO> getHT002(String orgCd, String bankCd, String bankCoNo, String outActNo, String InqSdt, String InqEdt);

    void importHT002(FbkFilesInfo fbkFilesInfo);

    void updateHT002(HT002Info rv001Info);

    void deleteHT002(long id);

    boolean isHT002exist(HT002Info rv001Info);
}

package gateway.wrb.services;

import gateway.wrb.domain.FbkFilesInfo;
import gateway.wrb.domain.VLR001Info;
import gateway.wrb.model.RV001DTO;

import java.util.List;

public interface VLR001Service {
    void importVLR001(FbkFilesInfo fbkFilesInfo);

    List<VLR001Info> getAllVLR001();

    List<RV001DTO> getVLR001(String orgCd, String bankCd, String bankCoNo, String outActNo, String bankRsvSdt, String bankRsvEdt);

    void updateVLR001(VLR001Info info);

    void deleteVLR001(long id);

    boolean isVLR001exist(VLR001Info info);
}

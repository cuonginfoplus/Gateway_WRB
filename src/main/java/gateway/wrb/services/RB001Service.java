package gateway.wrb.services;

import gateway.wrb.domain.FbkFilesInfo;
import gateway.wrb.domain.RB001Info;
import gateway.wrb.model.RB001Model;

import java.util.List;

public interface RB001Service {
    List<RB001Info> getRB001(String orgCd, String bankCd, String bankCoNo, String trnxId);

    void importRB001(FbkFilesInfo fbkFilesInfo);

    boolean isRB001exist(RB001Info rb001Info);

    public void createRB001Req(String dir, RB001Model model);
}

package gateway.wrb.services;


import gateway.wrb.domain.FbkFilesInfo;
import gateway.wrb.domain.RV002Info;
import gateway.wrb.model.RV002Model;

import java.util.List;

public interface RV002Service {
    List<RV002Info> getAllRV002();

    RV002Info getRV002(long id);

    void importRV002(FbkFilesInfo fbkFilesInfo);

    void updateRV002(RV002Info rv002Info);

    void deleteRV002(long id);

    boolean isRV002exist(RV002Info info);

    void createRV002Req(String sndDir, RV002Model model);
}

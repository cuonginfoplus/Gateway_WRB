package gateway.wrb.services;

import gateway.wrb.domain.FbkFilesInfo;
import gateway.wrb.domain.RA001Info;
import gateway.wrb.model.RA001Model;

import java.util.List;

public interface RA001Service {
    List<RA001Info> getAllRA001();

    List<RA001Info> getRA001(String viracno);

    void importRA001(FbkFilesInfo fbkFilesInfo);

    void updateRA001(RA001Info RA001Info);

    void deleteRA001(String viracno);

    boolean isRA001exist(RA001Info RA001Info);

    void createRA001Req(String dir, RA001Model model);
}

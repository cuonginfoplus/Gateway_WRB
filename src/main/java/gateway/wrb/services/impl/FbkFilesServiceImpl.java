package gateway.wrb.services.impl;

import gateway.wrb.config.FbkConfig;
import gateway.wrb.constant.FileType;
import gateway.wrb.domain.FbkFilesInfo;
import gateway.wrb.repositories.FbkFilesRepo;
import gateway.wrb.services.FbkFilesService;
import gateway.wrb.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FbkFilesServiceImpl implements FbkFilesService {
    @Autowired
    private FbkFilesRepo fbkFilesRepo;

    @Autowired
    private FbkConfig fbkConfig;

    @Override
    public List<Map<String, FbkFilesInfo>> getFbkFiles(String directory) {
        List<Map<String, FbkFilesInfo>> listFbkFiles = new ArrayList<>();
        FileUtils util = new FileUtils();
        List<String> listByName = util.getFilesDirectory(directory);
        for (int i = 0; i < listByName.size(); i++) {
            String fullFileName = listByName.get(i);
            FbkFilesInfo fbkFilesInfo = new FbkFilesInfo();
            fbkFilesInfo.setFullfbkpath(fullFileName);
            String fileName = fullFileName.substring(fullFileName.lastIndexOf('/') + 1);
            if (fileName.startsWith(fbkConfig.getHeaderSnd()) && fileName.endsWith(fbkConfig.getFbkType())) {
                fbkFilesInfo.setFbkname(fileName);
                String dateTime = convertDateTime(fileName);

                if (fileName.contains(FileType.RV001)) {
                    fbkFilesInfo.setFiletype(FileType.RV001);
                } else if (fileName.contains(FileType.HT002)) {
                    fbkFilesInfo.setFiletype(FileType.HT002);
                }
                fbkFilesInfo.setTrndt(dateTime);
                // saving to DB
                //fbkFilesRepo.addFbkFile(fbkFilesInfo);

                // add to list FBK
                Map<String, FbkFilesInfo> fbkFilesInfoMap = new HashMap<>();
                fbkFilesInfoMap.put(fbkFilesInfo.getFiletype(), fbkFilesInfo);
                listFbkFiles.add(fbkFilesInfoMap);
            } else if (fileName.startsWith(fbkConfig.getHeaderVir()) && fileName.endsWith(fbkConfig.getFbkType())) {
                fbkFilesInfo.setFbkname(fileName);
                String dateTime = fileName.substring(12,20);
                fbkFilesInfo.setTrndt(dateTime);
                fbkFilesInfo.setFiletype(FileType.RV002);

                // saving to DB
                //fbkFilesRepo.addFbkFile(fbkFilesInfo);

                // add to list FBK
                Map<String, FbkFilesInfo> fbkFilesInfoMap = new HashMap<>();
                fbkFilesInfoMap.put(fbkFilesInfo.getFiletype(), fbkFilesInfo);
                listFbkFiles.add(fbkFilesInfoMap);
            }
        }
        return listFbkFiles;
    }

    private String convertDateTime(String fileName) {
        String dateTime;
        try {
            dateTime = fileName.substring(fileName.lastIndexOf("_") + 1, fileName.lastIndexOf("_") + 9);
        } catch (Exception e) {
            return fileName.substring(fileName.lastIndexOf("_") - 8, fileName.lastIndexOf("_"));
        }
        return dateTime;
    }

    @Override
    public FbkFilesInfo getFbkFiles(long id) {
        return null;
    }

    @Override
    public boolean isFbkFileExist(FbkFilesInfo fbkFilesInfo) {
        return false;
    }
}

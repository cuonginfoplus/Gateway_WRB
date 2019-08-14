package gateway.wrb.controllers;

import gateway.wrb.config.FbkConfig;
import gateway.wrb.constant.FileType;
import gateway.wrb.domain.FbkFilesInfo;
import gateway.wrb.services.FbkFilesService;
import gateway.wrb.services.HT002Service;
import gateway.wrb.services.RV001Service;
import gateway.wrb.services.RV002Service;
import gateway.wrb.util.FileUtils;
import gateway.wrb.util.SftpUtils;
import gateway.wrb.util.Validator;
import lombok.extern.log4j.Log4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Log4j
@Controller
@RequestMapping("/gateway")
public class FbkController {
    public static final Logger logger = LoggerFactory.getLogger(FbkController.class);
    @Autowired
    private FbkFilesService fbkFilesService;

    @Autowired
    private RV001Service rv001Service;

    @Autowired
    private RV002Service rv002Service;

    @Autowired
    private HT002Service ht002Service;

    @Autowired
    private FbkConfig fbkConfig;

    @GetMapping(value = "/all")
    public ResponseEntity<?> readFiles() {
        logger.info("--------- START ---------- ::" + System.currentTimeMillis());
        List<FbkFilesInfo> fbkList = importFiles();
        if (fbkList.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        logger.info("--------- END ---------- ::" + System.currentTimeMillis());
        return new ResponseEntity<>(fbkList, HttpStatus.OK);
    }

    @GetMapping(value = "/rv001")
    public ResponseEntity<?> importrv001() {
        logger.info("--------- START ---------- ::" + System.currentTimeMillis());
        List<FbkFilesInfo> rv001Files = new ArrayList<>();
        String directory = fbkConfig.getFbkPath();
        List<Map<String, FbkFilesInfo>> fbkFiles = fbkFilesService.getFbkFiles(directory);
        for (int i = 0; i < fbkFiles.size(); i++) {
            Map<String, FbkFilesInfo> filesInfoMap = fbkFiles.get(i);
            FbkFilesInfo rv001Info = filesInfoMap.get(FileType.RV001);
            if (Validator.validateObject(rv001Info)) {
                rv001Service.importRV001(rv001Info);
                rv001Files.add(rv001Info);

                // Copy and Delete
                FileUtils fileUtils = new FileUtils();
                fileUtils.moveFile(rv001Info.getFullfbkpath(), fbkConfig.getFbkPathBackup() + rv001Info.getFbkname() + ".bak");
            }
        }
        if (rv001Files.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        logger.info("--------- END ---------- ::" + System.currentTimeMillis());
        return new ResponseEntity<>(rv001Files, HttpStatus.OK);
    }

    @GetMapping(value = "/rv002")
    public ResponseEntity<?> importrv002() {
        logger.info("--------- START ---------- ::" + System.currentTimeMillis());
        List<FbkFilesInfo> rv002Files = new ArrayList<>();
        String directory = fbkConfig.getFbkPath();
        List<Map<String, FbkFilesInfo>> fbkFiles = fbkFilesService.getFbkFiles(directory);
        for (int i = 0; i < fbkFiles.size(); i++) {
            Map<String, FbkFilesInfo> filesInfoMap = fbkFiles.get(i);
            FbkFilesInfo rv002Info = filesInfoMap.get(FileType.RV002);
            if (Validator.validateObject(rv002Info)) {
                rv002Service.importRV002(rv002Info);
                rv002Files.add(rv002Info);

                // Copy and Delete
                FileUtils fileUtils = new FileUtils();
                fileUtils.moveFile(rv002Info.getFullfbkpath(), fbkConfig.getFbkPathBackup() + rv002Info.getFbkname() + ".bak");
            }
        }
        if (rv002Files.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        logger.info("--------- END ---------- ::" + System.currentTimeMillis());
        return new ResponseEntity<>(rv002Files, HttpStatus.OK);
    }


    @GetMapping(value = "/ht002")
    public ResponseEntity<?> importht002() {
        logger.info("--------- START ---------- ::" + System.currentTimeMillis());
        List<FbkFilesInfo> ht002Files = new ArrayList<>();
        String directory = fbkConfig.getFbkPath();
        List<Map<String, FbkFilesInfo>> fbkFiles = fbkFilesService.getFbkFiles(directory);

        for (int i = 0; i < fbkFiles.size(); i++) {
            Map<String, FbkFilesInfo> filesInfoMap = fbkFiles.get(i);
            FbkFilesInfo ht002Info = filesInfoMap.get(FileType.HT002);
            if (Validator.validateObject(ht002Info)) {
                ht002Service.importHT002(ht002Info);
                ht002Files.add(ht002Info);

                // Copy and Delete
                FileUtils fileUtils = new FileUtils();
                fileUtils.moveFile(ht002Info.getFullfbkpath(), fbkConfig.getFbkPathBackup() + ht002Info.getFbkname() + ".bak");
            }
        }

        if (ht002Files.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        logger.info("--------- END ---------- ::" + System.currentTimeMillis());
        return new ResponseEntity<>(ht002Files, HttpStatus.OK);
    }

    public List<FbkFilesInfo> importFiles() {
        List<FbkFilesInfo> totalFiles = new ArrayList<>();
        List<FbkFilesInfo> rv001Files = new ArrayList<>();
        List<FbkFilesInfo> rv002Files = new ArrayList<>();
        List<FbkFilesInfo> ht002Files = new ArrayList<>();
        String directory = fbkConfig.getFbkPath();
        List<Map<String, FbkFilesInfo>> fbkFiles = fbkFilesService.getFbkFiles(directory);

        for (int i = 0; i < fbkFiles.size(); i++) {
            Map<String, FbkFilesInfo> filesInfoMap = fbkFiles.get(i);

            FbkFilesInfo rv001FilesInfo = filesInfoMap.get(FileType.RV001);
            if (Validator.validateObject(rv001FilesInfo)) {
                totalFiles.add(rv001FilesInfo);
                rv001Service.importRV001(rv001FilesInfo);
                rv001Files.add(rv001FilesInfo);
            }

            FbkFilesInfo rv002FilesInfo = filesInfoMap.get(FileType.RV002);
            if (Validator.validateObject(rv002FilesInfo)) {
                totalFiles.add(rv002FilesInfo);
                rv002Service.importRV002(rv002FilesInfo);
                rv002Files.add(rv002FilesInfo);
            }

            FbkFilesInfo ht002FilesInfo = filesInfoMap.get(FileType.HT002);
            if (Validator.validateObject(ht002FilesInfo)) {
                totalFiles.add(ht002FilesInfo);
                ht002Service.importHT002(ht002FilesInfo);
                ht002Files.add(ht002FilesInfo);
            }
        }
        return totalFiles;
    }

    @Scheduled(fixedRate = 2000)
    public void scheduleFbkFiles() {
        // get Files from SFTP
        SftpUtils sftpUtils = new SftpUtils();
        String SFTPHOST = fbkConfig.getSftphost();
        String SFTPPORT = fbkConfig.getSftpport();
        String SFTPUSER = fbkConfig.getSftuser();
        String SFTPPASS = fbkConfig.getSftpassword();
        String SFTPWORKINGDIR = fbkConfig.getFbkPath();
        sftpUtils.getFilesSftp(SFTPHOST, SFTPPORT, SFTPUSER, SFTPPASS, SFTPWORKINGDIR);

        // Import Process
        List<FbkFilesInfo> rv001Files = new ArrayList<>();
        List<FbkFilesInfo> rv002Files = new ArrayList<>();
        List<FbkFilesInfo> ht002Files = new ArrayList<>();
        String directory = fbkConfig.getFbkPath();
        List<Map<String, FbkFilesInfo>> fbkFiles = fbkFilesService.getFbkFiles(directory);

        for (int i = 0; i < fbkFiles.size(); i++) {
            Map<String, FbkFilesInfo> filesInfoMap = fbkFiles.get(i);

            FbkFilesInfo rv001Info = filesInfoMap.get(FileType.RV001);
            if (Validator.validateObject(rv001Info)) {
                rv001Service.importRV001(rv001Info);
                rv001Files.add(rv001Info);

                // Copy and Delete
                FileUtils fileUtils = new FileUtils();
                if(Files.isDirectory(Paths.get(fbkConfig.getFbkPathBackup()))){
                    fileUtils.moveFile(rv001Info.getFullfbkpath(), fbkConfig.getFbkPathBackup() + rv001Info.getFbkname() + ".bak");
                }
            }

            FbkFilesInfo rv002Info = filesInfoMap.get(FileType.RV002);
            if (Validator.validateObject(rv002Info)) {
                rv002Service.importRV002(rv002Info);
                rv002Files.add(rv002Info);

                // Copy and Delete
                FileUtils fileUtils = new FileUtils();
                if(Files.isDirectory(Paths.get(fbkConfig.getFbkPathBackup()))){
                    fileUtils.moveFile(rv002Info.getFullfbkpath(), fbkConfig.getFbkPathBackup() + rv002Info.getFbkname() + ".bak");
                }
            }

            FbkFilesInfo ht002Info = filesInfoMap.get(FileType.HT002);
            if (Validator.validateObject(ht002Info)) {
                ht002Service.importHT002(ht002Info);
                ht002Files.add(ht002Info);

                // Copy and Delete
                FileUtils fileUtils = new FileUtils();
                if(Files.isDirectory(Paths.get(fbkConfig.getFbkPathBackup()))) {
                    fileUtils.moveFile(ht002Info.getFullfbkpath(), fbkConfig.getFbkPathBackup() + ht002Info.getFbkname() + ".bak");
                }
            }
        }
    }
}

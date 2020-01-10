package gateway.wrb.controllers;

import gateway.wrb.config.FbkConfig;
import gateway.wrb.constant.FileType;
import gateway.wrb.domain.FbkFilesInfo;
import gateway.wrb.model.*;
import gateway.wrb.services.*;
import gateway.wrb.util.DateUtils;
import gateway.wrb.util.FileUtils;
import gateway.wrb.util.SftpUtils;
import gateway.wrb.util.Validator;
import lombok.extern.log4j.Log4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Log4j
@Controller
@RequestMapping("/gateway")
@Validated
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
    private ER001Service er001Service;

    @Autowired
    private PR001Service pr001Service;

    @Autowired
    private VLR001Service vlr001Service;

    @Autowired
    private RA001Service ra001Service;

    @Autowired
    private RB001Service rb001Service;

    @Autowired
    private FbkConfig fbkConfig;

    @GetMapping(value = "/test")
    public ResponseEntity<?> testAPI() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> readFiles() {
        List<FbkFilesInfo> fbkList = new ArrayList<>();
        if (fbkList.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(fbkList, HttpStatus.OK);
    }

    // V1.1 Virtual Info
    @GetMapping(value = "/rv001")
    public ResponseEntity<?> getVLR001(
            @Valid @NotBlank @RequestParam(value = "orgCd", defaultValue = "") String orgCd,
            @Valid @NotBlank @RequestParam(value = "bankCd", defaultValue = "") String bankCd,
            @Valid @NotBlank @RequestParam(value = "bankCoNo", defaultValue = "") String bankCoNo,
            @Valid @NotBlank @RequestParam(value = "outActNo", defaultValue = "") String outActNo,
            @RequestParam(value = "rgsTrnSdt", defaultValue = "") String bankRsvSdt,
            @RequestParam(value = "rgsTrnEdt", defaultValue = "") String bankRsvEdt
    ) {
        List<RV001DTO> rv001DTOS = vlr001Service.getVLR001(orgCd, bankCd, bankCoNo, outActNo, bankRsvSdt, bankRsvEdt);
        return new ResponseEntity<>(rv001DTOS, HttpStatus.OK);
    }

    // V1.2 VirAccount Change
    @PostMapping(value = "/rv002", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> postRV002(@RequestBody RV002Model model) throws IOException {
        String sndDir = fbkConfig.getFbkSend();

        if (model != null) {
            //create request file
            rv002Service.createRV002Req(sndDir, model);
        }
        return new ResponseEntity<>(DateUtils.dateYYYMMDDHHMMSS(), HttpStatus.OK);
    }

    // V1.3 Vir Account Trnx List
    @GetMapping(value = "/ht002")
    public ResponseEntity<?> getHT002(
            @Valid @NotBlank @RequestParam(value = "orgCd", defaultValue = "") String orgCd,
            @Valid @NotBlank @RequestParam(value = "bankCd", defaultValue = "") String bankCd,
            @Valid @NotBlank @RequestParam(value = "bankCoNo", defaultValue = "") String bankCoNo,
            @Valid @NotBlank @RequestParam(value = "outActNo", defaultValue = "") String outActNo,
            @RequestParam(value = "InqSdt", defaultValue = "") String InqSdt,
            @RequestParam(value = "InqEdt", defaultValue = "") String InqEdt
    ) {
        List<HT002DTO> ht002InfoList = ht002Service.getHT002(orgCd, bankCd, bankCoNo, outActNo, InqSdt, InqEdt);
        return new ResponseEntity<>(ht002InfoList, HttpStatus.OK);
    }

    // V1.5 Final Admit List
    @GetMapping(value = "/ra001")
    public ResponseEntity<?> getRA001(
            @Valid @NotBlank @RequestParam(value = "orgCd", defaultValue = "") String orgCd,
            @Valid @NotBlank @RequestParam(value = "bankCd", defaultValue = "") String bankCd,
            @Valid @NotBlank @RequestParam(value = "bankCoNo", defaultValue = "") String bankCoNo,
            @RequestParam(value = "outActNo", defaultValue = "") String outActNo,
            @RequestParam(value = "bankRsvSdt", defaultValue = "") String bankRsvSdt,
            @RequestParam(value = "bankRsvEdt", defaultValue = "") String bankRsvEdt
    ) {
        List<RA001DTO> ra001InfoList = ra001Service.getRA001(orgCd, bankCd, bankCoNo, outActNo, bankRsvSdt, bankRsvEdt);
        return new ResponseEntity<>(ra001InfoList, HttpStatus.OK);
    }

    // V1.7 Result Auto Tranfer
    @GetMapping(value = "/rb001")
    public ResponseEntity<?> getRB001(
            @Valid @NotBlank @RequestParam(value = "orgCd", defaultValue = "") String orgCd,
            @Valid @NotBlank @RequestParam(value = "bankCd", defaultValue = "") String bankCd,
            @Valid @NotBlank @RequestParam(value = "bankCoNo", defaultValue = "") String bankCoNo,
            @Valid @NotBlank @RequestParam(value = "trnxId", defaultValue = "") String trnxId
    ) {
        List<RB001DTO> er001Infos = rb001Service.getRB001(orgCd, bankCd, bankCoNo, trnxId);
        return new ResponseEntity<>(er001Infos, HttpStatus.OK);
    }

    //V1.8 Result Auto Tranfer Status
    @GetMapping(value = "/rb001_s")
    public ResponseEntity<?> getRB001_S(
            @Valid @NotBlank @RequestParam(value = "orgCd", defaultValue = "") String orgCd,
            @Valid @NotBlank @RequestParam(value = "bankCd", defaultValue = "") String bankCd,
            @Valid @NotBlank @RequestParam(value = "bankCoNo", defaultValue = "") String bankCoNo,
            @Valid @NotBlank @RequestParam(value = "trnxId", defaultValue = "") String trnxId
    ) {
        List<RB001SDTO> er001Infos = rb001Service.getRB001S(orgCd, bankCd, bankCoNo, trnxId);
        return new ResponseEntity<>(er001Infos, HttpStatus.OK);
    }

    // V1.9 Exchange rate
    @GetMapping(value = "/er001")
    public ResponseEntity<?> getER001(
            @Valid @NotBlank @RequestParam(value = "orgCd", defaultValue = "") String orgCd,
            @Valid @NotBlank @RequestParam(value = "bankCd", defaultValue = "") String bankCd,
            @Valid @NotBlank @RequestParam(value = "bankCoNo", defaultValue = "") String bankCoNo,
            @RequestParam("noticeSdt") String noticeSdt,
            @RequestParam("noticeEdt") String noticeEdt
    ) {
        List<ER001DTO> er001Infos = er001Service.getER001(orgCd, bankCd, bankCoNo, noticeSdt, noticeEdt);
        return new ResponseEntity<>(er001Infos, HttpStatus.OK);
    }

    @Scheduled(cron = "0 9 12,19 * * *")
    @GetMapping(value = "/imprv001")
    public ResponseEntity<?> importrv001() throws IOException {
        List<FbkFilesInfo> rv001Files = new ArrayList<>();
        String directory = fbkConfig.getFbkPath();
        List<Map<String, FbkFilesInfo>> fbkFiles = fbkFilesService.getFbkFiles(directory); //list of file .DAT (not .BAK)
        System.out.println("Size of List FBK files: " + fbkFiles.size());
        for (int i = 0; i < fbkFiles.size(); i++) {
            Map<String, FbkFilesInfo> filesInfoMap = fbkFiles.get(i);
            FbkFilesInfo rv001Info = filesInfoMap.get(FileType.RV001);
            if (Validator.validateObject(rv001Info)) {
                // check exist
                if (!fbkFilesService.isFbkFileExist(rv001Info)) {
                    // import to DB
                    rv001Service.importRV001(rv001Info);
                    rv001Files.add(rv001Info);

                    // Copy and Delete
                    FileUtils fileUtils = new FileUtils();
                    fileUtils.moveFile(rv001Info.getFullfbkpath(), fbkConfig.getFbkPathBackup(), rv001Info.getFbkname());
                }
            }
        }
        if (rv001Files.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(rv001Files, HttpStatus.OK);
    }

    @Scheduled(cron = "0 4 12,19 * * *")
    @GetMapping(value = "/imprv002")
    public ResponseEntity<?> importrv002() throws IOException {
        List<FbkFilesInfo> rv002Files = new ArrayList<>();
        String directory = fbkConfig.getFbkPath();
        List<Map<String, FbkFilesInfo>> fbkFiles = fbkFilesService.getFbkFiles(directory);
        for (int i = 0; i < fbkFiles.size(); i++) {
            Map<String, FbkFilesInfo> filesInfoMap = fbkFiles.get(i);
            FbkFilesInfo rv002Info = filesInfoMap.get(FileType.RV002);
            if (Validator.validateObject(rv002Info)) {
                // check exist
                if (!fbkFilesService.isFbkFileExist(rv002Info)) {
                    // import to DB
                    rv002Service.importRV002(rv002Info);
                    rv002Files.add(rv002Info);

                    // Copy and Delete
                    FileUtils fileUtils = new FileUtils();
                    fileUtils.moveFile(rv002Info.getFullfbkpath(), fbkConfig.getFbkPathBackup(), rv002Info.getFbkname());
                }
            }
        }
        if (rv002Files.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(rv002Files, HttpStatus.OK);
    }

    @Scheduled(cron = "0 13 12,19 * * *")
    @GetMapping(value = "/impht002")
    public ResponseEntity<?> importht002() throws IOException {
        List<FbkFilesInfo> ht002Files = new ArrayList<>();
        String directory = fbkConfig.getFbkPath();
        List<Map<String, FbkFilesInfo>> fbkFiles = fbkFilesService.getFbkFiles(directory);

        for (int i = 0; i < fbkFiles.size(); i++) {
            Map<String, FbkFilesInfo> filesInfoMap = fbkFiles.get(i);
            FbkFilesInfo ht002Info = filesInfoMap.get(FileType.HT002);
            if (Validator.validateObject(ht002Info)) {
                // check exist
                if (!fbkFilesService.isFbkFileExist(ht002Info)) {
                    ht002Service.importHT002(ht002Info);
                    ht002Files.add(ht002Info);

                    // Copy and Delete
                    FileUtils fileUtils = new FileUtils();
                    fileUtils.moveFile(ht002Info.getFullfbkpath(),
                            fbkConfig.getFbkPathBackup(), ht002Info.getFbkname());
                }
            }
        }

        if (ht002Files.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(ht002Files, HttpStatus.OK);
    }

    /**
     * anhtn 20190909 get ER001 Exchange rate file
     *
     * @return
     */
    @Scheduled(cron = "0 8 12,19 * * *")
    @GetMapping(value = "/imper001")
    public ResponseEntity<?> importER001() throws IOException {
        List<FbkFilesInfo> er001Files = new ArrayList<>();
        String directory = fbkConfig.getFbkPath();
        List<Map<String, FbkFilesInfo>> fbkFiles = fbkFilesService.getFbkFiles(directory);

        for (int i = 0; i < fbkFiles.size(); i++) {
            Map<String, FbkFilesInfo> filesInfoMap = fbkFiles.get(i);
            FbkFilesInfo er001Info = filesInfoMap.get(FileType.ER001);
            if (Validator.validateObject(er001Info)) {
                // check exist
                if (!fbkFilesService.isFbkFileExist(er001Info)) {
                    er001Service.importER001(er001Info);
                    er001Files.add(er001Info);

                    // Copy and Delete
                    FileUtils fileUtils = new FileUtils();
                    fileUtils.moveFile(er001Info.getFullfbkpath(),
                            fbkConfig.getFbkPathBackup(), er001Info.getFbkname());
                }
            }
        }

        if (er001Files.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(er001Files, HttpStatus.OK);
    }

    /**
     * anhtn 20190910 get TR004(PR001) reconcile file
     *
     * @return
     */
    @Scheduled(cron = "0 3 12,19 * * *")
    @GetMapping(value = "/imppr001")
    public ResponseEntity<?> imporPR001() throws IOException {
        List<FbkFilesInfo> files = new ArrayList<>();
        String directory = fbkConfig.getFbkPath();
        List<Map<String, FbkFilesInfo>> fbkFiles = fbkFilesService.getFbkFiles(directory);

        for (int i = 0; i < fbkFiles.size(); i++) {
            Map<String, FbkFilesInfo> filesInfoMap = fbkFiles.get(i);
            FbkFilesInfo info = filesInfoMap.get(FileType.PR001);
            if (Validator.validateObject(info)) {
                // check exist
                if (!fbkFilesService.isFbkFileExist(info)) {
                    pr001Service.importPR001(info);
                    files.add(info);

                    // Copy and Delete
                    FileUtils fileUtils = new FileUtils();
                    fileUtils.moveFile(info.getFullfbkpath(), fbkConfig.getFbkPathBackup(), info.getFbkname());
                }
            }
        }

        if (files.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(files, HttpStatus.OK);
    }

    /**
     * anhtn 20190910 get VRL001 Virtual account Create
     *
     * @return
     */
    @Scheduled(cron = "0 15 12,19 * * *")
    @GetMapping(value = "/impvlr001")
    public ResponseEntity<?> imporVLR001() throws IOException {
        List<FbkFilesInfo> files = new ArrayList<>();
        String directory = fbkConfig.getFbkPath();
        List<Map<String, FbkFilesInfo>> fbkFiles = fbkFilesService.getFbkFiles(directory);

        for (int i = 0; i < fbkFiles.size(); i++) {
            Map<String, FbkFilesInfo> filesInfoMap = fbkFiles.get(i);
            FbkFilesInfo info = filesInfoMap.get(FileType.VIR001);
            if (Validator.validateObject(info)) {
                // check exist
                if (!fbkFilesService.isFbkFileExist(info)) {
                    vlr001Service.importVLR001(info);
                    files.add(info);

                    // Copy and Delete
                    FileUtils fileUtils = new FileUtils();
                    fileUtils.moveFile(info.getFullfbkpath(), fbkConfig.getFbkPathBackup(), info.getFbkname());
                }
            }
        }

        if (files.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(files, HttpStatus.OK);
    }

    /**
     * anhtn 20190911 get RA001 Customer Withdrawal Request File
     *
     * @return
     */

    @Scheduled(cron = "0 10 12,19 * * *")
    @GetMapping(value = "/impra001")
    public ResponseEntity<?> importRA001() throws IOException {
        List<FbkFilesInfo> ra001Files = new ArrayList<>();
        String directory = fbkConfig.getFbkPath();
        List<Map<String, FbkFilesInfo>> fbkFiles = fbkFilesService.getFbkFiles(directory);

        for (int i = 0; i < fbkFiles.size(); i++) {
            Map<String, FbkFilesInfo> filesInfoMap = fbkFiles.get(i);
            FbkFilesInfo info = filesInfoMap.get(FileType.RA001);
            if (Validator.validateObject(info) && !fbkFilesService.isFbkFileExist(info)) {
                ra001Service.importRA001(info); // add FBK file to DB and RA001info to DB
                ra001Files.add(info);

                // Copy and Delete
                FileUtils fileUtils = new FileUtils();
                fileUtils.moveFile(info.getFullfbkpath(), fbkConfig.getFbkPathBackup(), info.getFbkname());
            }
        }

        if (ra001Files.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(ra001Files, HttpStatus.OK);
    }

    @Scheduled(cron = "0 5 12,19 * * *")
    @GetMapping(value = "/imprb001")
    public ResponseEntity<?> importRB001() throws IOException {
        List<FbkFilesInfo> rb001Files = new ArrayList<>();
        String directory = fbkConfig.getFbkPath();
        List<Map<String, FbkFilesInfo>> fbkFiles = fbkFilesService.getFbkFiles(directory);

        for (int i = 0; i < fbkFiles.size(); i++) {
            Map<String, FbkFilesInfo> filesInfoMap = fbkFiles.get(i);
            FbkFilesInfo info = filesInfoMap.get(FileType.RB001);
            if (Validator.validateObject(info) && !fbkFilesService.isFbkFileExist(info)) {
                rb001Service.importRB001(info); // add FBK file to DB and RA001info to DB
                rb001Files.add(info);

                // Copy and Delete
                FileUtils fileUtils = new FileUtils();
                fileUtils.moveFile(info.getFullfbkpath(), fbkConfig.getFbkPathBackup(), info.getFbkname());
            }
        }

        if (rb001Files.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(rb001Files, HttpStatus.OK);
    }

    /**
     * anhtn 20190911 post RA001 Customer Withdrawal Request File
     *
     * @param model: List<RA001Model>
     * @return
     */
    @PostMapping(value = "/ra001", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> postRA001(@RequestBody RA001Model model) throws IOException {
        String sndDir = fbkConfig.getFbkSend();
        if (model != null) {
            //create request file
            ra001Service.createRA001Req(sndDir, model);
        }
        return new ResponseEntity<>(DateUtils.dateYYYMMDDHHMMSS(), HttpStatus.OK);
    }

    /**
     * cuongtm 20191021 post RB001 Request auto transfer
     *
     * @param model: RB001Model
     * @return
     */
    @PostMapping(value = "/rb001")
    public ResponseEntity<?> postRB001(@RequestBody RB001Model model) {
        String sndDir = fbkConfig.getFbkSend();
        String trxId = null;
        boolean isCreated = false;
        if (model != null) {
            //create request file
            trxId = rb001Service.createRB001Req(sndDir, model);
        }
        if (Validator.validateString(trxId)) {
            return new ResponseEntity<>(trxId, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Not Accept to Post Duplicate Data", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    // Crawl FBK file from Woori server
    @Scheduled(cron = "0 0 12,19 * * *")
    public void scheduleFbkFiles() throws IOException {
        // get Files from SFTP
        SftpUtils sftpUtils = new SftpUtils();
        String SFTPHOST = fbkConfig.getSftphost();
        String SFTPPORT = fbkConfig.getSftpport();
        String SFTPUSER = fbkConfig.getSftuser();
        String SFTPPASS = fbkConfig.getSftpassword();
        String SFTPWORKINGDIR = fbkConfig.getFbkPath();
        String SFTPREMOTE = fbkConfig.getFbktowrb();
        sftpUtils.getFilesSftp(SFTPHOST, SFTPPORT, SFTPUSER, SFTPPASS, SFTPWORKINGDIR, SFTPREMOTE);
    }

    // Put file to Woori Serverw
    @Scheduled(cron = "0 30 12,19 * * *")
    public void schedulePutFbkFiles() throws IOException {
        SftpUtils sftpUtils = new SftpUtils();
        String fbkSend = fbkConfig.getFbkSend();
        List<String> fbkFiles = fbkFilesService.getSendFbkFiles(fbkSend);
        for (String fbkfile : fbkFiles) {
            System.out.println(fbkfile);
            File file = new File(fbkfile);
            sftpUtils.putFileSftp(fbkConfig.getSftphost(), fbkConfig.getSftpport(), fbkConfig.getSftuser(), fbkConfig.getSftpassword(), file, fbkConfig.getFbkfromwrb());

            // Copy and Delete
            FileUtils fileUtils = new FileUtils();
            String fileName = fbkfile.substring(fbkfile.lastIndexOf('/') + 1);
            fileUtils.moveFile(fbkfile, fbkConfig.getFbkSendBk(), fileName);
        }
    }
}

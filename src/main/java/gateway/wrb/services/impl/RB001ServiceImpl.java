package gateway.wrb.services.impl;

import com.google.common.base.Strings;
import gateway.wrb.cache.SeqCache;
import gateway.wrb.config.BankConfig;
import gateway.wrb.config.FbkConfig;
import gateway.wrb.config.RB001Config;
import gateway.wrb.constant.FileType;
import gateway.wrb.domain.FbkFilesInfo;
import gateway.wrb.domain.RB001Info;
import gateway.wrb.domain.RB001SInfo;
import gateway.wrb.model.*;
import gateway.wrb.repositories.FbkFilesRepo;
import gateway.wrb.repositories.RB001Repo;
import gateway.wrb.services.RB001Service;
import gateway.wrb.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Service
public class RB001ServiceImpl implements RB001Service {
    public static final Logger logger = LoggerFactory.getLogger(RB001ServiceImpl.class);

    @Autowired
    FbkConfig fbkConfig;

    @Autowired
    BankConfig bankConfig;

    @Autowired
    RB001Config rb001Config;

    @Autowired
    RB001Repo rb001Repo;

    @Autowired
    FbkFilesRepo fbkFilesRepo;

    @Autowired
    SeqCache seqCache;


    @Override
    public List<RB001DTO> getRB001(String orgCd, String bankCd, String bankCoNo, String trnxId) {
        String bankCode = bankConfig.getBankCode();
        List<RB001DTO> rb001DTOS = new ArrayList<>();
        if (!bankCode.equals(bankCd)) {
            return rb001DTOS;
        } else {
            rb001DTOS = rb001Repo.filterRB001(orgCd, bankCd, bankCoNo, trnxId);
        }
        return rb001DTOS;
    }

    @Override
    public void importRB001(FbkFilesInfo fbkFilesInfo) {
        Integer S_msgDscd_1Length = rb001Config.getS_msgDscd_1();
        Integer S_coNoLength = rb001Config.getS_coNo();
        Integer S_reqDtLength = rb001Config.getS_reqDt();
        Integer S_trnDtLength = rb001Config.getS_trnDt();
        Integer S_inActNoLength = rb001Config.getS_inActNo();
        Integer S_trnDscdLength = rb001Config.getS_trnDscd();
        Integer S_msgDscd_2Length = rb001Config.getS_msgDscd_2();
        Integer S_rqDscdLength = rb001Config.getS_rqDscd();
        Integer S_multiTrnCd = rb001Config.getS_multiTrnCd();
        Integer S_feePreOcc = rb001Config.getS_feePreOcc();
        Integer S_feeInclYn = rb001Config.getS_feeInclYn();
        Integer S_firmSvrSec = rb001Config.getS_firmSvrSec();
        Integer S_firmSvrSec2 = rb001Config.getS_firmSvrSec2();
        Integer S_confirmDupYn = rb001Config.getS_comfirmDupYn();
        Integer S_filler = rb001Config.getS_filler();

        Integer D_msgDscdLength = rb001Config.getMsgDscd();
        Integer D_seqLength = rb001Config.getSeq();
        Integer D_outActNoLength = rb001Config.getOutActNo();
        Integer D_curCdLength = rb001Config.getCurCd();
        Integer D_trnAmLength = rb001Config.getTrnAm();
        Integer D_tobkDscd = rb001Config.getTobkDscd();
        Integer D_istDscdLength = rb001Config.getIstDscd();
        Integer D_inCdAccGbLength = rb001Config.getInCdAccGb();
        Integer D_rcvbk1CdLength = rb001Config.getRcvbk1Cd();
        Integer D_rcvbk2CdLength = rb001Config.getRcvbk2Cd();
        Integer D_rcvbkNmLength = rb001Config.getRcvbkNm();
        Integer D_sndNameLength = rb001Config.getSndName();
        Integer D_rcvacDppeNmLength = rb001Config.getRcvacDppeNm();
        Integer D_depRmkLength = rb001Config.getDepRmk();
        Integer D_wdrRmkLength = rb001Config.getWdrRmk();
        Integer D_trnSrnoLength = rb001Config.getTrnSrno();
        Integer D_statusLength = rb001Config.getStatus();
        Integer D_prcCdLength = rb001Config.getPrcCd();
        Integer D_errCdLength = rb001Config.getErrCd();
        Integer D_refNoLength = rb001Config.getRefNo();
        Integer D_fillerLength = rb001Config.getFiller();

        Integer E_msgDscd = rb001Config.getE_msgDscd();
        Integer E_totCnt = rb001Config.getE_totCnt();
        Integer E_totReqCnt = rb001Config.getE_totReqCnt();
        Integer E_totReqAmt = rb001Config.getE_totReqAmt();
        Integer E_totSucCnt = rb001Config.getE_totSucCnt();
        Integer E_totSucAmt = rb001Config.getE_totSucAmt();
        Integer E_failCnt = rb001Config.getE_failCnt();
        Integer E_failAmt = rb001Config.getE_failAmt();
        Integer E_inSucCnt = rb001Config.getE_inSucCnt();
        Integer E_inSucAmt = rb001Config.getE_inSucAmt();
        Integer E_outSucCnt = rb001Config.getE_outSucCnt();
        Integer E_outSucAmt = rb001Config.getE_outSucAmt();
        Integer E_filler = rb001Config.getE_filler();

        try (Stream<String> stream = Files.lines(Paths.get(fbkFilesInfo.getFullfbkpath()))) {
            stream.forEach(line -> {
                try {
                    if (line.startsWith(FileType.PREFIX_START)) {
                        line = line.substring(S_msgDscd_1Length);

                        String coNo = line.substring(0, S_coNoLength);
                        line = line.substring(S_coNoLength);
                        String reqDt = line.substring(0, S_reqDtLength);
                        line = line.substring(S_reqDtLength);
                        String trnDt = line.substring(0, S_trnDtLength);
                        line = line.substring(S_trnDtLength);
                        String inActNo = line.substring(0, S_inActNoLength);
                        line = line.substring(S_inActNoLength);
                        String trnDscd = line.substring(0, S_trnDscdLength);
                        line = line.substring(S_trnDscdLength);
                        String msgDscd2 = line.substring(0, S_msgDscd_2Length);
                        line = line.substring(S_msgDscd_2Length);
                        String rqDscd = line.substring(0, S_rqDscdLength);
                        line = line.substring(S_rqDscdLength);
                        String multiTrnCd = line.substring(0, S_multiTrnCd);
                        line = line.substring(S_multiTrnCd);
                        String feePreOcc = line.substring(0, S_feePreOcc);
                        line = line.substring(S_feePreOcc);
                        String feeIncYn = line.substring(0, S_feeInclYn);
                        line = line.substring(S_feeInclYn);
                        String firmSvrSec = line.substring(0, S_firmSvrSec);
                        line = line.substring(S_firmSvrSec);
                        String firmSvrSec2 = line.substring(0, S_firmSvrSec2);
                        line = line.substring(S_firmSvrSec2);
                        String confirmDupYn = line.substring(0, S_confirmDupYn);
                        line = line.substring(S_confirmDupYn);

                        RB001SInfo rb001SInfo = new RB001SInfo();
                        rb001SInfo.setMsgDscd("S");
                        rb001SInfo.setCoNo(coNo);
                        rb001SInfo.setReqDt(reqDt);
                        rb001SInfo.setTrnDt(trnDt);
                        rb001SInfo.setInActNo(inActNo);
                        rb001SInfo.setTrnDscd(trnDscd);
                        rb001SInfo.setRqDscd(rqDscd);
                        rb001SInfo.setMultiTrnCd(multiTrnCd);
                        rb001SInfo.setFeePreOcc(feePreOcc);
                        rb001SInfo.setFeeInclYn(feeIncYn);
                        rb001SInfo.setFirmSvrSec(firmSvrSec);
                        rb001SInfo.setComfirmDupYn(confirmDupYn);
                        rb001SInfo.setFbkname(fbkFilesInfo.getFbkname());

                        fbkFilesInfo.setTmsdts(trnDt);
                        fbkFilesInfo.setTmstms("");
                        fbkFilesInfo.setConos(coNo);

                        rb001Repo.save_header(rb001SInfo);

                    } else if (line.startsWith(FileType.PREFIX_CONTENT)) {
                        String msgDscD = line.substring(0, D_msgDscdLength);
                        line = line.substring(D_msgDscdLength);

                        String seq = line.substring(0, D_seqLength);
                        line = line.substring(D_seqLength);
                        String outActNo = line.substring(0, D_outActNoLength);
                        line = line.substring(D_outActNoLength);
                        String curCd = line.substring(0, D_curCdLength);
                        line = line.substring(D_curCdLength);
                        String trnAm = line.substring(0, D_trnAmLength);
                        line = line.substring(D_trnAmLength);
                        String tobkDscd = line.substring(0, D_tobkDscd);
                        line = line.substring(D_tobkDscd);
                        String istDscd = line.substring(0, D_istDscdLength);
                        line = line.substring(D_istDscdLength);
                        String inCdAccGb = line.substring(0, D_inCdAccGbLength);
                        line = line.substring(D_inCdAccGbLength);
                        String rcvbk1Cd = line.substring(0, D_rcvbk1CdLength);
                        line = line.substring(D_rcvbk1CdLength);
                        String rcvbk2Cd = line.substring(0, D_rcvbk2CdLength);
                        line = line.substring(D_rcvbk2CdLength);
                        String rcvbkNm = line.substring(0, D_rcvbkNmLength);
                        line = line.substring(D_rcvbkNmLength);
                        String sndName = line.substring(0, D_sndNameLength);
                        line = line.substring(D_sndNameLength);
                        String rcvacDppeNm = line.substring(0, D_rcvacDppeNmLength);
                        line = line.substring(D_rcvacDppeNmLength);
                        String depRmk = line.substring(0, D_depRmkLength);
                        line = line.substring(D_depRmkLength);
                        String wdrRmk = line.substring(0, D_wdrRmkLength);
                        line = line.substring(D_wdrRmkLength);
                        String trnSrno = line.substring(0, D_trnSrnoLength);
                        line = line.substring(D_trnSrnoLength);
                        String status = line.substring(0, D_statusLength);
                        line = line.substring(D_statusLength);
                        String prcCd = line.substring(0, D_prcCdLength);
                        line = line.substring(D_prcCdLength);
                        String errCd = line.substring(0, D_errCdLength);
                        line = line.substring(D_errCdLength);
                        String refNo = line.substring(0, D_refNoLength);
                        line = line.substring(D_refNoLength);
                        String filler = line.substring(0, D_fillerLength);
                        line = line.substring(D_fillerLength);

                        logger.info("rb001Path : [" + fbkFilesInfo.getFullfbkpath()
                                + ", msgDscd :" + msgDscD + ", seq:" + seq
                                + ", outActNo :" + outActNo + ", curCd :" + curCd
                                + ", trnAm:" + trnAm + ", tobkDscd:" + tobkDscd
                                + ", istDscd:" + istDscd + ", inCdAccGb:" + inCdAccGb
                                + ", inCdAccGb:" + inCdAccGb + ", rcvbk1Cd:" + rcvbk1Cd
                                + ", rcvbk2Cd:" + rcvbk2Cd + ", rcvbkNm:" + rcvbkNm
                                + ", sndName:" + sndName + ", rcvacDppeNm:" + rcvacDppeNm
                                + ", depRmk:" + depRmk + ", wdrRmk:" + wdrRmk
                                + ", trnSrno:" + trnSrno + ", status:" + status
                                + ", prcCd:" + prcCd + ", errCd:" + errCd
                                + ", refNo:" + refNo + ", filler:" + filler
                                + "]");

                        // save to DB
                        RB001Info info = new RB001Info();
                        info.setFbkname(fbkFilesInfo.getFbkname());
                        info.setMsgDscd(msgDscD);
                        info.setSeq(seq);
                        info.setOutActNo(outActNo);
                        info.setCurCd(curCd);
                        info.setTrnAm(trnAm);
                        info.setTobkDscd(tobkDscd);
                        info.setIstDscd(istDscd);
                        info.setInCdAccGb(inCdAccGb);
                        info.setRcvbk1Cd(rcvbk1Cd);
                        info.setRcvbk2Cd(rcvbk2Cd);
                        info.setRcvbkNm(rcvbkNm);
                        info.setSndName(sndName);
                        info.setRcvacDppeNm(rcvacDppeNm);
                        info.setDepRmk(depRmk);
                        info.setWdrRmk(wdrRmk);
                        info.setTrnSrno(trnSrno);
                        info.setStatus(status);
                        info.setPrcCd(prcCd);
                        info.setErrCd(errCd);
                        info.setRefNo(refNo);
                        info.setFiller(filler);

                        //if (!isRB001exist(info))
                        rb001Repo.save(info);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error(e.getMessage());
                }
            });
            fbkFilesRepo.addFbkFile(fbkFilesInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isRB001exist(RB001Info info) {
        try {
            Integer countEntity = rb001Repo.isRB001Exist(info.getMsgDscd(), info.getSeq()
                    , info.getOutActNo(), info.getCurCd()
                    , info.getTrnAm(), info.getTobkDscd()
                    , info.getIstDscd(), info.getInCdAccGb()
                    , info.getRcvbk1Cd(), info.getRcvbk2Cd(), info.getStatus(), info.getRefNo());
            logger.info("RB001 " + info.getMsgDscd() + "," + info.getSeq() + " detail has count = " + countEntity);

            if (countEntity < 1)
                return false;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return true;
    }

    public boolean saveRB001(String fbkname, String msgDscD, String seq, String outActNo, String curCd, String trnAm, String tobkDscd
            , String istDscd, String inCdAccGb, String rcvbk1Cd, String rcvbk2Cd, String rcvbkNm, String sndName, String rcvacDppeNm, String depRmk
            , String wdrRmk, String trnSrno, String status, String prcCd, String errCd, String refNo, String filler) {
        try {
            // save to DB
            RB001Info info = new RB001Info();
            info.setFbkname(fbkname);
            info.setMsgDscd(msgDscD);
            info.setSeq(seq);
            info.setOutActNo(outActNo);
            info.setCurCd(curCd);
            info.setTrnAm(trnAm);
            info.setTobkDscd(tobkDscd);
            info.setIstDscd(istDscd);
            info.setInCdAccGb(inCdAccGb);
            info.setRcvbk1Cd(rcvbk1Cd);
            info.setRcvbk2Cd(rcvbk2Cd);
            info.setRcvbkNm(rcvbkNm);
            info.setSndName(sndName);
            info.setRcvacDppeNm(rcvacDppeNm);
            info.setDepRmk(depRmk);
            info.setWdrRmk(wdrRmk);
            info.setTrnSrno(trnSrno);
            info.setStatus(status);
            info.setPrcCd(prcCd);
            info.setErrCd(errCd);
            info.setRefNo(refNo);
            info.setFiller(filler);

            if (!isRB001exist(info)) {
                rb001Repo.save(info);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String createRB001Req(String dir, RB001Model model) {
        int totReqCnt = 0;
        int totReqAmt = 0;
        FileUtils utils = new FileUtils();
        //Request: fbk_ccr_099_yyyyMMdd(8 length)_FirmbankingCustomerCode(9 length)_sequenceNumber(3 length).dat(Customer->WooriBank)
        //ex: fbk_ccr_099_20190820_000000098_001.dat
        String sndFileName = createSndFileName(AppConst.TYPE_RA001_REQ, model.getBankCoNo());
        String path = dir + sndFileName;
        //parse model to string
        Charset utf8 = StandardCharsets.UTF_8;
        List<String> contents = new ArrayList<String>();

        contents.add(buildStartContent(model));
        List<RB001AccModel> rb001AccModels = model.getTrnList();
        for (int i = 0; i < rb001AccModels.size(); i++) {
            boolean isSave = saveRB001(sndFileName, "D", rb001AccModels.get(i).getSeq(), rb001AccModels.get(i).getOutActNo(), rb001AccModels.get(i).getCurCd(), rb001AccModels.get(i).getTrnAm(),
                    rb001AccModels.get(i).getTobkDscd(), rb001AccModels.get(i).getIstDscd(), rb001AccModels.get(i).getInCdAccGb(), rb001AccModels.get(i).getRcvbk1Cd(), rb001AccModels.get(i).getRcvbk2Cd(),
                    rb001AccModels.get(i).getRcvbkNm(), rb001AccModels.get(i).getSndName(), rb001AccModels.get(i).getRcvacDppeNm(),
                    rb001AccModels.get(i).getDepRmk(), rb001AccModels.get(i).getWdrRmk(), "", rb001AccModels.get(i).getStatus(), "", "", rb001AccModels.get(i).getRefNo(), "");
            if (isSave) {
                contents.add(buildDataContent(rb001AccModels.get(i)));
                totReqCnt++;
                int ident = rb001AccModels.get(i).getTrnAm().indexOf(".");
                totReqAmt = totReqAmt + Integer.parseInt(rb001AccModels.get(i).getTrnAm().substring(0, ident));
            }
        }

        if (contents.size() > 1) {
            contents.add(buildEndContent(model, totReqCnt, totReqAmt));
            utils.createFile(path, contents);
            String trxId = DateUtils.dateYYYMMDDHHMMSS();
            updateRB001(sndFileName, trxId);
            return trxId;
        } else {
            return null;
        }
    }

    private void updateRB001(String sndFileName, String trxId) {
        rb001Repo.update(sndFileName, trxId);
    }

    @Override
    public List<RB001SDTO> getRB001S(String orgCd, String bankCd, String bankCoNo, String trnxId) {
        String bankCode = bankConfig.getBankCode();
        String orgCode = bankConfig.getOrgCode();
        List<RB001SDTO> rb001SDTOS = new ArrayList<>();
        if (!bankCode.equals(bankCd) || !orgCode.equals(orgCd)) {
            return rb001SDTOS;
        } else {
            rb001SDTOS = rb001Repo.filterRB001S(orgCd, bankCd, bankCoNo, trnxId);
        }
        return rb001SDTOS;
    }

    private String buildStartContent(RB001Model model) {
        Integer S_coNoLength = rb001Config.getS_coNo();
        Integer S_reqDtLength = rb001Config.getS_reqDt();
        Integer S_trnDtLength = rb001Config.getS_trnDt();
        Integer S_inActNoLength = rb001Config.getS_inActNo();
        Integer S_trnDscdLength = rb001Config.getS_trnDscd();
        Integer S_msgDscd_2Length = rb001Config.getS_msgDscd_2();
        Integer S_rqDscdLength = rb001Config.getS_rqDscd();
        Integer S_multiTrnCd = rb001Config.getS_multiTrnCd();
        Integer S_feePreOcc = rb001Config.getS_feePreOcc();
        Integer S_feeInclYn = rb001Config.getS_feeInclYn();
        Integer S_firmSvrSec = rb001Config.getS_firmSvrSec();
        Integer S_firmSvrSec2 = rb001Config.getS_firmSvrSec2();
        Integer S_confirmDupYn = rb001Config.getS_comfirmDupYn();
        Integer S_filler = rb001Config.getS_filler();

        StringBuilder builder = new StringBuilder();
        builder.append("S");
        builder.append(Strings.padEnd(model.getBankCoNo(), S_coNoLength, ' '));
        // Request Date : D+1
        builder.append(Strings.padStart(DateUtils.nextDate(0), S_reqDtLength, '0'));
        // Transaction Date : D+2
        builder.append(Strings.padStart(DateUtils.nextDate(2), S_trnDtLength, '0'));
        builder.append(Strings.padEnd(model.getInActNo(), S_inActNoLength, ' '));
        builder.append(Strings.padEnd(model.getTrnDscd(), S_trnDscdLength, ' '));
        builder.append(Strings.padEnd("RB001", S_msgDscd_2Length, ' '));
        builder.append(Strings.padEnd(model.getRqDscd(), S_rqDscdLength, ' '));
        builder.append(Strings.padEnd(model.getMultiTrnCd(), S_multiTrnCd, ' '));
        builder.append(Strings.padEnd(model.getFeePreOcc(), S_feePreOcc, ' '));
        builder.append(Strings.padEnd(model.getFeeInclYn(), S_feeInclYn, ' '));
        builder.append(Strings.padEnd("0", S_firmSvrSec, ' '));
        builder.append(Strings.padEnd("0", S_firmSvrSec2, ' '));
        builder.append(Strings.padStart("0", S_confirmDupYn, ' '));
        builder.append(Strings.padEnd("0", S_filler, ' '));
        return builder.toString();
    }

    private String buildDataContent(RB001AccModel model) {
        Integer D_msgDscdLength = rb001Config.getMsgDscd();
        Integer D_seqLength = rb001Config.getSeq();
        Integer D_outActNoLength = rb001Config.getOutActNo();
        Integer D_curCdLength = rb001Config.getCurCd();
        Integer D_trnAmLength = rb001Config.getTrnAm();
        Integer D_tobkDscd = rb001Config.getTobkDscd();
        Integer D_istDscdLength = rb001Config.getIstDscd();
        Integer D_inCdAccGbLength = rb001Config.getInCdAccGb();
        Integer D_rcvbk1CdLength = rb001Config.getRcvbk1Cd();
        Integer D_rcvbk2CdLength = rb001Config.getRcvbk2Cd();
        Integer D_rcvbkNmLength = rb001Config.getRcvbkNm();
        Integer D_sndNameLength = rb001Config.getSndName();
        Integer D_rcvacDppeNmLength = rb001Config.getRcvacDppeNm();
        Integer D_depRmkLength = rb001Config.getDepRmk();
        Integer D_wdrRmkLength = rb001Config.getWdrRmk();
        Integer D_trnSrnoLength = rb001Config.getTrnSrno();
        Integer D_statusLength = rb001Config.getStatus();
        Integer D_prcCdLength = rb001Config.getPrcCd();
        Integer D_errCdLength = rb001Config.getErrCd();
        Integer D_refNoLength = rb001Config.getRefNo();
        Integer D_fillerLength = rb001Config.getFiller();

        StringBuilder builder = new StringBuilder();
        builder.append("D");

        // Gen Seq
        String seq = "1";
        SeqModel seqModel = seqCache.getItem("rb001seq");
        if (Validator.validate(seqModel)) {
            Integer nextVal = seqModel.getSeqValue();
            nextVal++;
            seqCache.updateItem(new SeqModel("rb001seq", nextVal));
        } else {
            seqCache.addItem(new SeqModel("rb001seq", 1));
        }
        SeqModel nextSeq = seqCache.getItem("rb001seq");
        if (Validator.validate(nextSeq)) {
            seq = Strings.padStart(String.valueOf(nextSeq.getSeqValue()), 10, '0');
        }

        builder.append(seq);
        builder.append(Strings.padEnd(model.getOutActNo(), D_outActNoLength, ' '));
        builder.append(Strings.padEnd(model.getCurCd(), D_curCdLength, ' '));
        builder.append(Strings.padStart(model.getTrnAm(), D_trnAmLength, '0'));
        builder.append(Strings.padEnd(model.getTobkDscd(), D_tobkDscd, ' '));
        builder.append(Strings.padEnd(model.getIstDscd(), D_istDscdLength, ' '));
        builder.append(Strings.padEnd(model.getInCdAccGb(), D_inCdAccGbLength, ' '));
        builder.append(Strings.padEnd(model.getRcvbk1Cd(), D_rcvbk1CdLength, ' '));
        builder.append(Strings.padEnd(model.getRcvbk2Cd(), D_rcvbk2CdLength, ' '));
        builder.append(Strings.padEnd(model.getRcvbkNm(), D_rcvbkNmLength, ' '));
        builder.append(Strings.padEnd(model.getSndName(), D_sndNameLength, ' '));
        builder.append(Strings.padEnd(model.getRcvacDppeNm(), D_rcvacDppeNmLength, ' '));
        builder.append(Strings.padEnd(model.getDepRmk(), D_depRmkLength, ' '));
        builder.append(Strings.padEnd(model.getWdrRmk(), D_wdrRmkLength, ' '));
        builder.append(Strings.padEnd("", D_trnSrnoLength, ' '));
        builder.append(Strings.padEnd("REG01", D_statusLength, ' '));
        builder.append(Strings.padEnd("00", D_prcCdLength, ' '));
        builder.append(Strings.padEnd("", D_errCdLength, ' '));
        builder.append(Strings.padEnd(model.getRefNo(), D_refNoLength, ' '));
        builder.append(Strings.padEnd("", D_fillerLength, ' '));
        return builder.toString();
    }

    private String buildEndContent(RB001Model model, int totReqCnt, int totReqAmt) {
        Integer E_totCnt = rb001Config.getE_totCnt();
        Integer E_totReqCnt = rb001Config.getE_totReqCnt();
        Integer E_totReqAmt = rb001Config.getE_totReqAmt();
        Integer E_totSucCnt = rb001Config.getE_totSucCnt();
        Integer E_totSucAmt = rb001Config.getE_totSucAmt();
        Integer E_failCnt = rb001Config.getE_failCnt();
        Integer E_failAmt = rb001Config.getE_failAmt();
        Integer E_inSucCnt = rb001Config.getE_inSucCnt();
        Integer E_inSucAmt = rb001Config.getE_inSucAmt();
        Integer E_outSucCnt = rb001Config.getE_outSucCnt();
        Integer E_outSucAmt = rb001Config.getE_outSucAmt();
        Integer E_filler = rb001Config.getE_filler();

        StringBuilder builder = new StringBuilder();
        builder.append("E");
        builder.append(Strings.padStart(String.valueOf(totReqCnt + 2), E_totCnt, '0'));
        builder.append(Strings.padStart(String.valueOf(totReqCnt), E_totReqCnt, '0'));
        builder.append(Strings.padStart(String.valueOf(totReqAmt), E_totReqAmt, '0'));
        builder.append(Strings.padStart("0", E_totSucCnt, '0'));
        builder.append(Strings.padStart("0", E_totSucAmt, '0'));
        builder.append(Strings.padStart("0", E_failCnt, '0'));
        builder.append(Strings.padStart("0", E_failAmt, '0'));
        builder.append(Strings.padStart("0", E_inSucCnt, '0'));
        builder.append(Strings.padStart("0", E_inSucAmt, '0'));
        builder.append(Strings.padStart("0", E_outSucCnt, '0'));
        builder.append(Strings.padStart("0", E_outSucAmt, '0'));
        builder.append(Strings.padEnd("0", E_filler, ' '));
        return builder.toString();
    }

    private String createSndFileName(String fileType, String customerCode) {
        String strCurrDate = DateUtils.getDateFormat(new Date(), "yyyyMMdd");
        String seq = "1";
        SeqModel seqModel = seqCache.getItem("fbk_ccr_099_" + strCurrDate);

        if (Validator.validate(seqModel)) {
            Integer nextVal = seqModel.getSeqValue();
            nextVal++;
            seqCache.updateItem(new SeqModel("fbk_ccr_099_" + strCurrDate, nextVal));
        } else {
            seqCache.addItem(new SeqModel("fbk_ccr_099_" + strCurrDate, 1));
        }
        SeqModel nextSeq = seqCache.getItem("fbk_ccr_099_" + strCurrDate);
        if (Validator.validate(nextSeq)) {
            seq = StringUtils.padLeftZeros(String.valueOf(nextSeq.getSeqValue()), 3);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("fbk_ccr_099_");
        sb.append(strCurrDate + "_");
        sb.append(customerCode + "_");
        sb.append(seq);
        sb.append(".dat");

        return sb.toString();
    }
}

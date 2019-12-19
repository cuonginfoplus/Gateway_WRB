package gateway.wrb.services.impl;

import com.google.common.base.Strings;
import gateway.wrb.cache.SeqCache;
import gateway.wrb.config.BankConfig;
import gateway.wrb.config.FbkConfig;
import gateway.wrb.config.RA001Config;
import gateway.wrb.constant.FileType;
import gateway.wrb.domain.FbkFilesInfo;
import gateway.wrb.domain.RA001Info;
import gateway.wrb.domain.RA001SInfo;
import gateway.wrb.model.RA001AccModel;
import gateway.wrb.model.RA001DTO;
import gateway.wrb.model.RA001Model;
import gateway.wrb.model.SeqModel;
import gateway.wrb.repositories.FbkFilesRepo;
import gateway.wrb.repositories.RA001Repo;
import gateway.wrb.services.RA001Service;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Service
public class RA001ServiceImpl implements RA001Service {
    public static final Logger logger = LoggerFactory.getLogger(RA001ServiceImpl.class);

    @Autowired
    BankConfig bankConfig;

    @Autowired
    FbkConfig fbkConfig;

    @Autowired
    RA001Config ra001Config;

    @Autowired
    RA001Repo ra001Repo;

    @Autowired
    FbkFilesRepo fbkFilesRepo;

    @Autowired
    SeqCache seqCache;

    @Override
    public List<RA001Info> getAllRA001() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<RA001DTO> getRA001(String orgCd, String bankCd, String bankCoNo, String outActNo, String bankRsvSdt, String bankRsvEdt) {
        String bankCode = bankConfig.getBankCode();
        String orgCode = bankConfig.getOrgCode();
        List<RA001DTO> ra001DTOS = new ArrayList<>();
        if (!bankCode.equals(bankCd)) {
            return ra001DTOS;
        } else {
            ra001DTOS = ra001Repo.filterRA001(orgCd, bankCd, bankCoNo, outActNo, bankRsvSdt, bankRsvEdt);
        }

        return ra001DTOS;
    }

    @Override
    public void importRA001(FbkFilesInfo fbkFilesInfo) {
        Integer msgDscdLength = ra001Config.getMsgDscdLength();

        Integer tmsDtLength = ra001Config.getTmsDtLength();
        Integer tmsTmLength = ra001Config.getTmsTmLength();
        Integer coNoLength = ra001Config.getCoNoLength();
        Integer actNoLength = ra001Config.getActNoLength();
        Integer dataCntLength = ra001Config.getDataCntLength();
        Integer etcArLength = ra001Config.getEtcLength();

        Integer wdrActNoLength = ra001Config.getWdrActNoLength();
        Integer aplDscdLength = ra001Config.getAplDscdLength();
        Integer msgTrnoLength = ra001Config.getMsgTrnoLength();
        Integer trnStDtLength = ra001Config.getTrnStDtLength();
        Integer trnClsDtLength = ra001Config.getTrnClsDtLength();
        Integer trnTypeLength = ra001Config.getTrnTypeLength();
        Integer statusLength = ra001Config.getStatusLength();
        Integer curCdLength = ra001Config.getCurCdLength();
        Integer rcpAmLength = ra001Config.getRcpAmLength();
        Integer rcpCntLength = ra001Config.getRcpCntLength();
        Integer outParticularLength = ra001Config.getOutParticularLength();
        Integer inParticularLength = ra001Config.getInParticularLength();
        Integer cusIdNoCdLength = ra001Config.getCusIdNoCdLength();
        Integer cusIdNoLength = ra001Config.getCusIdNoLength();
        Integer isuDtLength = ra001Config.getIsuDtLength();
        Integer vldEdtLength = ra001Config.getVldEdtLength();

        try (Stream<String> stream = Files.lines(Paths.get(fbkFilesInfo.getFullfbkpath()))) {
            stream.forEach(line -> {
                try {
                    if (line.startsWith(FileType.PREFIX_START)) {
                        line = line.substring(msgDscdLength);

                        String tmsDt = line.substring(0, tmsDtLength);
                        line = line.substring(tmsDtLength);
                        String tmsTm = line.substring(0, tmsTmLength);
                        line = line.substring(tmsTmLength);
                        String coNo = line.substring(0, coNoLength);
                        line = line.substring(coNoLength);
                        String outActNo = line.substring(0, actNoLength);
                        line = line.substring(actNoLength);
                        String dataCnt = line.substring(0, dataCntLength);
                        line = line.substring(dataCntLength);
                        String etcAr = line.substring(0, etcArLength);
                        line = line.substring(etcArLength);

                        fbkFilesInfo.setTmsdts(tmsDt);
                        fbkFilesInfo.setTmstms(tmsTm);
                        fbkFilesInfo.setConos(coNo);

                        RA001SInfo ra001SInfo = new RA001SInfo();
                        ra001SInfo.setMsgDscd("S");
                        ra001SInfo.setTmsDt(tmsDt);
                        ra001SInfo.setTmsTm(tmsDt);
                        ra001SInfo.setCoNo(coNo);
                        ra001SInfo.setOutActNo(outActNo);
                        ra001SInfo.setDataCnt(dataCnt);
                        ra001SInfo.setEtcAr(etcAr);
                        ra001SInfo.setFbkname(fbkFilesInfo.getFbkname());

                        ra001Repo.save_header(ra001SInfo);

                    } else if (line.startsWith(FileType.PREFIX_CONTENT)) {
                        String msgDscD = line.substring(0, msgDscdLength);
                        line = line.substring(msgDscdLength);

                        String wdrActNo = line.substring(0, wdrActNoLength);
                        line = line.substring(wdrActNoLength);
                        String aplDscd = line.substring(0, aplDscdLength);
                        line = line.substring(aplDscdLength);
                        String msgTrno = line.substring(0, msgTrnoLength);
                        line = line.substring(msgTrnoLength);
                        String trnStDt = line.substring(0, trnStDtLength);
                        line = line.substring(trnStDtLength);
                        String trnClsDt = line.substring(0, trnClsDtLength);
                        line = line.substring(trnClsDtLength);
                        String trnType = line.substring(0, trnTypeLength);
                        line = line.substring(trnTypeLength);
                        String status = line.substring(0, statusLength);
                        line = line.substring(statusLength);
                        String curCd = line.substring(0, curCdLength);
                        line = line.substring(curCdLength);
                        String rcpAm = line.substring(0, rcpAmLength);
                        line = line.substring(rcpAmLength);
                        String rcpCnt = line.substring(0, rcpCntLength);
                        line = line.substring(rcpCntLength);
                        String outParticular = line.substring(0, outParticularLength);
                        line = line.substring(outParticularLength);
                        String inParticular = line.substring(0, inParticularLength);
                        line = line.substring(inParticularLength);
                        String cusIdNoCd = line.substring(0, cusIdNoCdLength);
                        line = line.substring(cusIdNoCdLength);
                        String cusIdNo = line.substring(0, cusIdNoLength);
                        line = line.substring(cusIdNoLength);
                        String isuDt = line.substring(0, isuDtLength);
                        line = line.substring(isuDtLength);
                        String vldEdt = line.substring(0, vldEdtLength);
                        line = line.substring(vldEdtLength);

                        logger.info("ra001Path : [" + fbkFilesInfo.getFullfbkpath()
                                + ", msgDscd :" + msgDscD + ", wdrActNo:" + wdrActNo
                                + ", aplDscd :" + aplDscd + ", msgTrno :" + msgTrno
                                + ", trnStDt:" + trnStDt + ", trnClsDt:" + trnClsDt
                                + ", trnType:" + trnType + ", status:" + status
                                + ", curCd:" + curCd + ", rcpAm:" + rcpAm
                                + ", rcpCnt:" + rcpCnt + ", outParticular:" + outParticular
                                + ", inParticular:" + inParticular + ", cusIdNoCd:" + cusIdNoCd
                                + ", cusIdNo:" + cusIdNo + ", isuDt:" + isuDt
                                + ", vldEdt:" + vldEdt
                                + "]");

                        // save to DB
                        RA001Info info = new RA001Info();
                        info.setFbkname(fbkFilesInfo.getFbkname());
                        info.setMsgdscd(msgDscD);
                        info.setWdrActNo(wdrActNo);
                        info.setAplDscd(aplDscd);
                        info.setMsgTrno(msgTrno);
                        info.setTrnStDt(trnStDt);
                        info.setTrnClsDt(trnClsDt);
                        info.setTrnType(trnType);
                        info.setStatus(status);
                        info.setCurCd(curCd);
                        info.setRcpAm(rcpAm);
                        info.setRcpCnt(rcpCnt);
                        info.setOutParticular(outParticular);
                        info.setInParticular(inParticular);
                        info.setCusIdNoCd(cusIdNoCd);
                        info.setCusIdNo(cusIdNo);
                        info.setIsuDt(isuDt);
                        info.setVldEdt(vldEdt);

                        if (!isRA001exist(info)) {
                            ra001Repo.save(info); //save a RA001Info to DB
                            System.out.println("Saved a RA001info to DB");
                        } else {
                            ra001Repo.update(info);
                            System.out.println("Update RA001Info to DB");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error(e.getMessage());
                }
            });
            fbkFilesRepo.addFbkFile(fbkFilesInfo); //add FBKfile to DB
            System.out.println("Saved a R001_FBkFile to DB");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateRA001(RA001Info RA001Info) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteRA001(String viracno) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isRA001exist(RA001Info info) {
        try {
            Integer countEntity = ra001Repo.isRA001Exist(info.getMsgdscd(), info.getWdrActNo()
                    , info.getAplDscd(), info.getTrnStDt()
                    , info.getTrnType(), info.getStatus(), info.getCurCd()
                    , info.getCusIdNo());
            logger.info("VLR001 " + info.getMsgdscd() + "," + info.getWdrActNo() + " detail has count = " + countEntity);

            if (countEntity < 1)
                return false;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return true;
    }

    @Override
    public void createRA001Req(String dir, RA001Model model) {
        FileUtils utils = new FileUtils();
        //Request: fbk_awa_099_yyyyMMdd(8 length)_FirmbankingCustomerCode(9 length)_sequenceNumber(3 length).dat(Customer -> WooriBank)
        //ex: fbk_awa_099_20190822_000000098_001.dat
        String sndFileName = createSndFileName(AppConst.TYPE_RA001_REQ, model.getBankCoNo());
        String path = dir + sndFileName;
        //parse model to string
        Charset utf8 = StandardCharsets.UTF_8;
        List<String> contents = new ArrayList<String>();

        contents.add(createStartContent(model));
        List<RA001AccModel> ra001AccModels = model.getActList();
        ra001AccModels.forEach(accModel -> {
            boolean isSave = saveRA001(sndFileName, "D", accModel.getWdrActNo(), "RA001", accModel.getMsgTrno(), accModel.getTrnStDt(),
                    accModel.getTrnClsDt(), accModel.getTrnType(), "REG01", accModel.getCurCd(), accModel.getRcpAm(),
                    accModel.getRcpCnt(), accModel.getOutParticular(), accModel.getInParticular(),
                    accModel.getCus_id_no_cd(), accModel.getCus_id_no(), accModel.getIsuDt(), accModel.getVld_edt());
            if (isSave) {
                contents.add(getDataContent(accModel));
            }
        });
        if (contents.size() > 1) {
            utils.createFile(path, contents);

        }
    }

    private boolean saveRA001(String fbkName, String msgDscD, String wdrActNo, String aplDscd, String msgTrno, String trnStDt,
                              String trnClsDt, String trnType, String status, String curCd, String rcpAm, String rcpCnt, String outParticular, String inParticular,
                              String cusIdNoCd, String cusIdNo, String isuDt, String vldEdt
    ) {
        try {
            RA001Info info = new RA001Info();
            info.setFbkname(fbkName);
            info.setMsgdscd(msgDscD);
            info.setWdrActNo(wdrActNo);
            info.setAplDscd(aplDscd);
            info.setMsgTrno(msgTrno);
            info.setTrnStDt(trnStDt);
            info.setTrnClsDt(trnClsDt);
            info.setTrnType(trnType);
            info.setStatus(status);
            info.setCurCd(curCd);
            info.setRcpAm(rcpAm);
            info.setRcpCnt(rcpCnt);
            info.setOutParticular(outParticular);
            info.setInParticular(inParticular);
            info.setCusIdNoCd(cusIdNoCd);
            info.setCusIdNo(cusIdNo);
            info.setIsuDt(isuDt);
            info.setVldEdt(vldEdt);
            if (!isRA001exist(info)) {
                System.out.println("Saved a RA001info to DB");
                ra001Repo.save(info); //save a RA001Info to DB
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private String createStartContent(RA001Model model) {
        Integer tmsDtLength = ra001Config.getTmsDtLength();
        Integer tmsTmLength = ra001Config.getTmsTmLength();
        Integer coNoLength = ra001Config.getCoNoLength();
        Integer actNoLength = ra001Config.getActNoLength();
        Integer dataCntLength = ra001Config.getDataCntLength();
        Integer etcArLength = ra001Config.getEtcLength();

        String strCurrDate = DateUtils.getDateFormat(new Date(), "yyyyMMdd");
        DateFormat df2 = new SimpleDateFormat("HHmmss");
        String strCurrHour = df2.format(new Date());

        StringBuilder builder = new StringBuilder();
        builder.append("S");
        builder.append(Strings.padStart(strCurrDate, tmsDtLength, '0'));
        builder.append(Strings.padStart(strCurrHour, tmsTmLength, '0'));
        builder.append(Strings.padEnd(model.getBankCoNo(), coNoLength, ' '));
        builder.append(Strings.padEnd(model.getOutActNo(), actNoLength, ' '));
        builder.append(Strings.padStart("1", dataCntLength, '0'));
        builder.append(Strings.padStart("0", etcArLength, ' '));

        return builder.toString();
    }

    private String getDataContent(RA001AccModel model) {
        Integer wdrActNoLength = ra001Config.getWdrActNoLength();
        Integer aplDscdLength = ra001Config.getAplDscdLength();
        Integer msgTrnoLength = ra001Config.getMsgTrnoLength();
        Integer trnStDtLength = ra001Config.getTrnStDtLength();
        Integer trnClsDtLength = ra001Config.getTrnClsDtLength();
        Integer trnTypeLength = ra001Config.getTrnTypeLength();
        Integer statusLength = ra001Config.getStatusLength();
        Integer curCdLength = ra001Config.getCurCdLength();
        Integer rcpAmLength = ra001Config.getRcpAmLength();
        Integer rcpCntLength = ra001Config.getRcpCntLength();
        Integer outParticularLength = ra001Config.getOutParticularLength();
        Integer inParticularLength = ra001Config.getInParticularLength();
        Integer cusIdNoCdLength = ra001Config.getCusIdNoCdLength();
        Integer cusIdNoLength = ra001Config.getCusIdNoLength();
        Integer isuDtLength = ra001Config.getIsuDtLength();
        Integer vldEdtLength = ra001Config.getVldEdtLength();

        StringBuilder builder = new StringBuilder();
        builder.append("D");

        builder.append(Strings.padEnd(model.getWdrActNo(), wdrActNoLength, ' '));
        builder.append(Strings.padEnd("RA001", aplDscdLength, ' '));
        builder.append(Strings.padEnd(model.getMsgTrno(), msgTrnoLength, ' '));
        builder.append(Strings.padEnd(model.getTrnStDt(), trnStDtLength, ' '));
        builder.append(Strings.padEnd(model.getTrnClsDt(), trnClsDtLength, ' '));
        builder.append(Strings.padEnd(model.getTrnType(), trnTypeLength, ' '));
        builder.append(Strings.padEnd("", statusLength, ' '));
        builder.append(Strings.padEnd(model.getCurCd(), curCdLength, ' '));
        builder.append(Strings.padStart(model.getRcpAm() + "000", rcpAmLength, '0'));
        builder.append(Strings.padEnd(model.getRcpCnt(), rcpCntLength, ' '));
        builder.append(Strings.padEnd(model.getOutParticular(), outParticularLength, ' '));
        builder.append(Strings.padEnd(model.getInParticular(), inParticularLength, ' '));
        builder.append(Strings.padEnd(model.getCus_id_no_cd(), cusIdNoCdLength, ' '));
        builder.append(Strings.padEnd(model.getCus_id_no(), cusIdNoLength, ' '));
        builder.append(Strings.padEnd(model.getIsuDt(), isuDtLength, ' '));
        builder.append(Strings.padEnd(model.getVld_edt(), vldEdtLength, ' '));
        return builder.toString();
    }

    private String createSndFileName(String fileType, String customerCode) {
        String strCurrDate = DateUtils.getDateFormat(new Date(), "yyyyMMdd");
        String seq = "1";
        SeqModel seqModel = seqCache.getItem("fbk_awa_099_" + strCurrDate);

        if (Validator.validate(seqModel)) {
            Integer nextVal = seqModel.getSeqValue();
            nextVal++;
            seqCache.updateItem(new SeqModel("fbk_awa_099_" + strCurrDate, nextVal));
        } else {
            seqCache.addItem(new SeqModel("fbk_awa_099_" + strCurrDate, 1));
        }
        SeqModel nextSeq = seqCache.getItem("fbk_awa_099_" + strCurrDate);
        if (Validator.validate(nextSeq)) {
            seq = StringUtils.padLeftZeros(String.valueOf(nextSeq.getSeqValue()), 3);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("fbk_awa_099_");
        sb.append(strCurrDate + "_");
        sb.append(customerCode + "_");
        sb.append(seq);
        sb.append(".dat");
        return sb.toString();
    }
}

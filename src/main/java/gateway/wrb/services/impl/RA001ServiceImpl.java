package gateway.wrb.services.impl;

import gateway.wrb.config.RA001Config;
import gateway.wrb.constant.FileType;
import gateway.wrb.domain.FbkFilesInfo;
import gateway.wrb.domain.RA001Info;
import gateway.wrb.model.RA001Model;
import gateway.wrb.repositories.FbkFilesRepo;
import gateway.wrb.repositories.RA001Repo;
import gateway.wrb.repositories.SysFileSeqRepo;
import gateway.wrb.services.RA001Service;
import gateway.wrb.util.AppConst;
import gateway.wrb.util.DateUtils;
import gateway.wrb.util.FileUtils;
import gateway.wrb.util.StringUtils;
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
public class RA001ServiceImpl implements RA001Service {
    public static final Logger logger = LoggerFactory.getLogger(RA001ServiceImpl.class);

    @Autowired
    RA001Config ra001Config;

    @Autowired
    RA001Repo ra001Repo;

    @Autowired
    FbkFilesRepo fbkFilesRepo;

    @Autowired
    SysFileSeqRepo sysFileSeqRepo;

    @Override
    public List<RA001Info> getAllRA001() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<RA001Info> getRA001(String viracno) {
        // TODO Auto-generated method stub
        return null;
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
                        //String outActNo = line.substring(0, outActNoLength);
                        line = line.substring(actNoLength);
                        //String dataCnt = line.substring(0, dataCntLength);
                        line = line.substring(dataCntLength);
                        //String etcAr = line.substring(0, etcArLength);
                        line = line.substring(etcArLength);

                        fbkFilesInfo.setTmsdts(tmsDt);
                        fbkFilesInfo.setTmstms(tmsTm);
                        fbkFilesInfo.setConos(coNo);

                    } else if (line.startsWith(FileType.PREFIX_CONTENT)) {
                        String msgDscD = line.substring(0, msgDscdLength);
                        line = line.substring(msgDscdLength);

                        String wdrActNo = line.substring(0, wdrActNoLength);
                        String aplDscd = line.substring(0, aplDscdLength);
                        String msgTrno = line.substring(0, msgTrnoLength);
                        String trnStDt = line.substring(0, trnStDtLength);
                        String trnClsDt = line.substring(0, trnClsDtLength);
                        String trnType = line.substring(0, trnTypeLength);
                        String status = line.substring(0, statusLength);
                        String curCd = line.substring(0, curCdLength);
                        String rcpAm = line.substring(0, rcpAmLength);
                        String rcpCnt = line.substring(0, rcpCntLength);
                        String outParticular = line.substring(0, outParticularLength);
                        String inParticular = line.substring(0, inParticularLength);
                        String cusIdNoCd = line.substring(0, cusIdNoCdLength);
                        String cusIdNo = line.substring(0, cusIdNoLength);
                        String isuDt = line.substring(0, isuDtLength);
                        String vldEdt = line.substring(0, vldEdtLength);


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

                        if (!isRA001exist(info))
                            ra001Repo.save(info);

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
            Integer countEntity = ra001Repo.countItem(info.getMsgdscd(), info.getWdrActNo()
                    , info.getAplDscd(), info.getTrnStDt()
                    , info.getTrnType(), info.getCurCd()
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
        //parse model to string
        Charset utf8 = StandardCharsets.UTF_8;
        List<String> contents = new ArrayList<String>();

        contents.add(createStartContent(model));
        contents.add(getDataContent(model));

        FileUtils utils = new FileUtils();
        //Request: fbk_awa_099_yyyyMMdd(8 length)_FirmbankingCustomerCode(9 length)_sequenceNumber(3 length).dat(Customer -> WooriBank)
        //ex: fbk_awa_099_20190822_000000098_001.dat
        String sndFileName = createSndFileName(AppConst.TYPE_RA001_REQ, model.getCoNo());
        String path = dir + sndFileName;
        utils.createFile(path, contents);

    }

    private String createStartContent(RA001Model model) {
        Integer tmsDtLength = ra001Config.getTmsDtLength();
        Integer tmsTmLength = ra001Config.getTmsTmLength();
        Integer coNoLength = ra001Config.getCoNoLength();
        Integer actNoLength = ra001Config.getActNoLength();
        Integer dataCntLength = ra001Config.getDataCntLength();
        Integer etcArLength = ra001Config.getEtcLength();

        StringBuilder builder = new StringBuilder();
        builder.append("S");
        builder.append(StringUtils.padLeftSpaces(model.getTmsDt(), tmsDtLength));
        builder.append(StringUtils.padLeftSpaces(model.getTmsTm(), tmsTmLength));
        builder.append(StringUtils.padLeftSpaces(model.getCoNo(), coNoLength));
        builder.append(StringUtils.padLeftSpaces(model.getActNo(), actNoLength));
        builder.append(StringUtils.padLeftSpaces(model.getDataCnt(), dataCntLength));
        builder.append(StringUtils.padLeftSpaces(model.getEtcAr(), etcArLength));

        return builder.toString();
    }

    private String getDataContent(RA001Model model) {
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

        builder.append(StringUtils.padLeftSpaces(model.getWdrActNo(), wdrActNoLength));
        builder.append(StringUtils.padLeftSpaces(model.getAplDscd(), aplDscdLength));
        builder.append(StringUtils.padLeftSpaces(model.getMsgTrno(), msgTrnoLength));
        builder.append(StringUtils.padLeftSpaces(model.getTrnStDt(), trnStDtLength));
        builder.append(StringUtils.padLeftSpaces(model.getTrnClsDt(), trnClsDtLength));
        builder.append(StringUtils.padLeftSpaces(model.getTrnType(), trnTypeLength));
        builder.append(StringUtils.padLeftSpaces(model.getStatus(), statusLength));
        builder.append(StringUtils.padLeftSpaces(model.getCurCd(), curCdLength));
        builder.append(StringUtils.padLeftSpaces(model.getRcpAm(), rcpAmLength));
        builder.append(StringUtils.padLeftSpaces(model.getRcpCnt(), rcpCntLength));
        builder.append(StringUtils.padLeftSpaces(model.getOutParticular(), outParticularLength));
        builder.append(StringUtils.padLeftSpaces(model.getInParticular(), inParticularLength));
        builder.append(StringUtils.padLeftSpaces(model.getCusIdNoCd(), cusIdNoCdLength));
        builder.append(StringUtils.padLeftSpaces(model.getCusIdNo(), cusIdNoLength));
        builder.append(StringUtils.padLeftSpaces(model.getIsuDt(), isuDtLength));
        builder.append(StringUtils.padLeftSpaces(model.getVldEdt(), vldEdtLength));
        return builder.toString();
    }

    private String createSndFileName(String fileType, String customerCode) {
        String strCurrDate = DateUtils.getDateFormat(new Date(), "yyyyMMdd");
        Integer curSeq = sysFileSeqRepo.getNextSeq(strCurrDate, fileType);
        String seq = "001";
        if (curSeq != null) {
            seq = StringUtils.padLeftZeros(curSeq.toString(), 3);
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

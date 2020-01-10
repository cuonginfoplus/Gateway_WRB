package gateway.wrb.services.impl;

import com.google.common.base.Strings;
import gateway.wrb.cache.SeqCache;
import gateway.wrb.config.FbkConfig;
import gateway.wrb.config.RV002Config;
import gateway.wrb.constant.FileType;
import gateway.wrb.domain.FbkFilesInfo;
import gateway.wrb.domain.RV002Info;
import gateway.wrb.model.RV002Model;
import gateway.wrb.model.SeqModel;
import gateway.wrb.repositories.FbkFilesRepo;
import gateway.wrb.repositories.RV002Repo;
import gateway.wrb.services.RV002Service;
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
public class RV002ServiceImpl implements RV002Service {
    public static final Logger logger = LoggerFactory.getLogger(RV002ServiceImpl.class);

    @Autowired
    FbkConfig fbkConfig;

    @Autowired
    RV002Config rv002Config;

    @Autowired
    RV002Repo rv002Repo;

    @Autowired
    FbkFilesRepo fbkFilesRepo;

    @Autowired
    SeqCache seqCache;

    @Override
    public List<RV002Info> getAllRV002() {
        return rv002Repo.getAllRV002();
    }

    @Override
    public RV002Info getRV002(long id) {
        return rv002Repo.getRV002byID(id);
    }

    @Override
    public void importRV002(FbkFilesInfo fbkFilesInfo) {
        Integer coNoLength = rv002Config.getCoNoLength();
        Integer aplDscdLength = rv002Config.getAplDscdLength();

        Integer outActNoLength = rv002Config.getOutActNoLength();
        Integer virActNoLength = rv002Config.getVirActNoLength();
        Integer virAcNmLength = rv002Config.getVirAcNmLength();
        Integer refNoLength = rv002Config.getRefNoLength();
        Integer recCodCdLength = rv002Config.getRecCodCdLength();
        Integer trnAmLength = rv002Config.getTrnAmLength();
        Integer trnAvlSdtLength = rv002Config.getTrnAvlSdtLength();
        Integer trnAvlEdtLength = rv002Config.getTrnAvlEdtLength();
        Integer trnAvlStmLength = rv002Config.getTrnAvlStmLength();
        Integer trnAvlEtmLength = rv002Config.getTrnAvlEtmLength();
        Integer trnAvlYnLength = rv002Config.getTrnAvlYnLength();
        Integer corpRecCompCodeLength = rv002Config.getCorpRecCompCodeLength();
        Integer fillerLength = rv002Config.getFillerLength();

        try (Stream<String> stream = Files.lines(Paths.get(fbkFilesInfo.getFullfbkpath()))) {
            stream.forEach(line -> {
                try {
                    if (line.startsWith(FileType.PREFIX_START)) {
                        String msgDscd = line.substring(0, 1);
                        line = line.substring(1);
                        String coNo = line.substring(0, coNoLength);
                        line = line.substring(coNoLength);
                        String aplDscd = line.substring(0, aplDscdLength);
                        line = line.substring(aplDscdLength);

                        fbkFilesInfo.setConos(coNo);
                        fbkFilesInfo.setApldscd(aplDscd);

                    } else if (line.startsWith(FileType.PREFIX_CONTENT)) {
                        String msgDscd = line.substring(0, 1);
                        line = line.substring(1);
                        String outActNo = line.substring(0, outActNoLength);
                        line = line.substring(outActNoLength);
                        String virActNo = line.substring(0, virActNoLength);
                        line = line.substring(virActNoLength);
                        String virAcNm = line.substring(0, virAcNmLength);
                        line = line.substring(virAcNmLength);
                        String refNo = line.substring(0, refNoLength);
                        line = line.substring(refNoLength);
                        String recCodCd = line.substring(0, recCodCdLength);
                        line = line.substring(recCodCdLength);
                        String trnAm = line.substring(0, trnAmLength);
                        line = line.substring(trnAmLength);
                        String trnAvlSdt = line.substring(0, trnAvlSdtLength);
                        line = line.substring(trnAvlSdtLength);
                        String trnAvlEdt = line.substring(0, trnAvlEdtLength);
                        line = line.substring(trnAvlEdtLength);
                        String trnAvlStm = line.substring(0, trnAvlStmLength);
                        line = line.substring(trnAvlStmLength);
                        String trnAvlEtm = line.substring(0, trnAvlEtmLength);
                        line = line.substring(trnAvlEtmLength);
                        String trnAvlYn = line.substring(0, trnAvlYnLength);
                        line = line.substring(trnAvlYnLength);
                        String corpRecCompCode = line.substring(0, corpRecCompCodeLength);
                        line = line.substring(corpRecCompCodeLength);
                        String filler = line.substring(0, fillerLength);

                        System.out.println("rv002Path : [" + fbkFilesInfo.getFullfbkpath() + ", outActNo :" + outActNo + ", virActNo:" + virActNo + ", virAcNm :" + virAcNm + ", refNo :" + refNo + ", recCodCd:" + recCodCd + ", trnAm:" + trnAm
                                + ", trnAvlSdt:" + trnAvlSdt + ", trnAvlEdt:" + trnAvlEdt + ", trnAvlStm:" + trnAvlStm + ", trnAvlEtm:" + trnAvlEtm + ", trnAvlYn:" + trnAvlYn + ", corpRecCompCode:" + corpRecCompCode + ", filter:" + filler + "]");

                        // save to DB
                        RV002Info rv002Info = new RV002Info();
                        rv002Info.setFbkname(fbkFilesInfo.getFbkname());
                        rv002Info.setOutactno(outActNo);
                        rv002Info.setViractno(virActNo);
                        rv002Info.setViracnm(virAcNm);
                        rv002Info.setRefno(refNo);
                        rv002Info.setReccodcd(recCodCd);
                        rv002Info.setTrnam(trnAm);
                        rv002Info.setTrnavlsdt(trnAvlSdt);
                        rv002Info.setTrnavledt(trnAvlEdt);
                        rv002Info.setTrnavlstm(trnAvlStm);
                        rv002Info.setTrnavletm(trnAvlEtm);
                        rv002Info.setTrnavlyn(trnAvlYn);
                        rv002Info.setCorpreccompcode(corpRecCompCode);
                        rv002Info.setFilter(filler);
                        if (!isRV002exist(rv002Info))
                            rv002Repo.addRV002(rv002Info);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error(e.getMessage());
                }
            });
            fbkFilesRepo.addFbkFile(fbkFilesInfo);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void updateRV002(RV002Info rv002Info) {
        rv002Repo.updateRV002(rv002Info);
    }

    @Override
    public void deleteRV002(long userId) {
        rv002Repo.deleteRV002(userId);
    }

    @Override
    public void createRV002Req(String sndDir, RV002Model model) {
        FileUtils utils = new FileUtils();
        //Request: fbk_vir_101_yyyMMdd_FirmbankingCustomerCode(9 length)_sequenceNumber(3 length).dat(Customer->WooriBank)
        //ex: fbk_vir_099_20190822_000000098_001.dat
        String sndFileName = createSndFileName(AppConst.TYPE_RV002_REQ, model.getBankCoNo());
        String path = sndDir + sndFileName;
        //parse model to string
        Charset utf8 = StandardCharsets.UTF_8;
        List<String> contents = new ArrayList<String>();

        boolean isSave = saveRV002(sndFileName, model.getOutActNo(), model.getVirActNo(), model.getVirAcNm(), model.getRefNo(),
                model.getRecCodCd(), model.getTrnAm(), model.getTrnAvlSdt(), model.getTrnAvlEtm(), model.getTrnAvlStm(), model.getTrnAvlEtm());
//        if (isSave) {
        contents.add(createStartContent(model));
        contents.add(getDataContent(model));
        contents.add(createEndContent(model));
        utils.createFile(path, contents);
//        }
    }

    private String createSndFileName(String typeRv002Req, String orgCd) {
        String strCurrDate = DateUtils.getDateFormat(new Date(), DateUtils.DATE_FORMAT);

        String seq = "1";
        SeqModel seqModel = seqCache.getItem(Constant.FBK_VIR_101 + strCurrDate);

        if (Validator.validate(seqModel)) {
            Integer nextVal = seqModel.getSeqValue();
            nextVal++;
            seqCache.updateItem(new SeqModel(Constant.FBK_VIR_101 + strCurrDate, nextVal));
        } else {
            seqCache.addItem(new SeqModel(Constant.FBK_VIR_101 + strCurrDate, 1));
        }
        SeqModel nextSeq = seqCache.getItem(Constant.FBK_VIR_101 + strCurrDate);
        if (Validator.validate(nextSeq)) {
            seq = StringUtils.padLeftZeros(String.valueOf(nextSeq.getSeqValue()), 3);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(Constant.FBK_VIR_101);
        sb.append(strCurrDate + "_");
        sb.append(orgCd + "_");
        sb.append(seq);
        sb.append(".dat");
        return sb.toString();
    }

    private String getDataContent(RV002Model model) {
        Integer outActNoLength = rv002Config.getOutActNoLength();
        Integer virActNoLength = rv002Config.getVirActNoLength();
        Integer virAcNmLength = rv002Config.getVirAcNmLength();
        Integer refNoLength = rv002Config.getRefNoLength();
        Integer recCodCdLength = rv002Config.getRecCodCdLength();
        Integer trnAmLength = rv002Config.getTrnAmLength();
        Integer trnAvlSdtLength = rv002Config.getTrnAvlSdtLength();
        Integer trnAvlEdtLength = rv002Config.getTrnAvlEdtLength();
        Integer trnAvlStmLength = rv002Config.getTrnAvlStmLength();
        Integer trnAvlEtmLength = rv002Config.getTrnAvlEtmLength();
        Integer trnAvlYnLength = rv002Config.getTrnAvlYnLength();
        Integer corpRecCompCodeLength = rv002Config.getCorpRecCompCodeLength();
        Integer fillerLength = rv002Config.getFillerLength();

        StringBuilder builder = new StringBuilder();
        builder.append("D");

        builder.append(Strings.padEnd(model.getOutActNo(), outActNoLength, ' '));
        builder.append(Strings.padEnd(model.getVirActNo(), virActNoLength, ' '));
        builder.append(Strings.padEnd(model.getVirAcNm(), virAcNmLength, ' '));
        builder.append(Strings.padEnd(model.getRefNo(), refNoLength, ' '));
        builder.append(Strings.padEnd(model.getRecCodCd(), recCodCdLength, ' '));
        builder.append(Strings.padStart(model.getTrnAm() + "000", trnAmLength, '0'));
        builder.append(Strings.padEnd(model.getTrnAvlSdt(), trnAvlSdtLength, ' '));
        builder.append(Strings.padEnd(model.getTrnAvlEdt(), trnAvlEdtLength, ' '));
        builder.append(Strings.padEnd(model.getTrnAvlStm(), trnAvlStmLength, ' '));
        builder.append(Strings.padEnd(model.getTrnAvlEtm(), trnAvlEtmLength, ' '));
        builder.append(Strings.padEnd("N", trnAvlYnLength, ' '));
        builder.append(Strings.padEnd("", corpRecCompCodeLength, ' '));
        builder.append(Strings.padEnd("", fillerLength, ' '));
        return builder.toString();
    }

    private String createStartContent(RV002Model model) {
        Integer coNoLength = rv002Config.getCoNoLength();
        Integer aplDscdLength = rv002Config.getAplDscdLength();
        Integer fillerLength = rv002Config.getSfiller();

        String strCurrDate = DateUtils.getDateFormat(new Date(), DateUtils.DATE_FORMAT);
        DateFormat df2 = new SimpleDateFormat("HHmmss");
        String strCurrHour = df2.format(new Date());

        StringBuilder builder = new StringBuilder();
        builder.append("S");
        builder.append(Strings.padEnd(model.getBankCoNo(), coNoLength, ' '));
        builder.append(Strings.padEnd("RV002", aplDscdLength, ' '));
        builder.append(Strings.padEnd("1", fillerLength, ' '));

        return builder.toString();
    }

    private String createEndContent(RV002Model model) {
        Integer etotCntLength = rv002Config.getEtotCntLength();
        Integer etotReqCntLength = rv002Config.getEtotReqCntLength();
        Integer etotReqAmtLength = rv002Config.getEtotReqAmtLength();
        Integer efillerLength = rv002Config.getEfillerLength();

        String strCurrDate = DateUtils.getDateFormat(new Date(), DateUtils.DATE_FORMAT);
        DateFormat df2 = new SimpleDateFormat("HHmmss");
        String strCurrHour = df2.format(new Date());

        StringBuilder builder = new StringBuilder();
        builder.append("E");
        builder.append(Strings.padEnd("3", etotCntLength, ' '));
        builder.append(Strings.padEnd("1", etotReqCntLength, ' '));
        builder.append(Strings.padEnd(model.getTrnAm(), etotReqAmtLength, ' '));
        builder.append(Strings.padStart("1", efillerLength, '0'));
        return builder.toString();
    }


    public boolean saveRV002(String fbkname, String outActNo, String virActNo, String virAcNm, String refNo
            , String recCodCd, String trnAm, String trnAvlSdt, String trnAvlEdt, String trnAvlStm, String trnAvlEtm) {
        try {
            // save to DB
            RV002Info info = new RV002Info();
            info.setFbkname(fbkname);
            info.setOutactno(outActNo);
            info.setViractno(virActNo);
            info.setViracnm(virAcNm);
            info.setRefno(refNo);
            info.setReccodcd(recCodCd);
            info.setTrnam(trnAm);
            info.setTrnavlsdt(trnAvlSdt);
            info.setTrnavledt(trnAvlEdt);
            info.setTrnavlstm(trnAvlStm);
            info.setTrnavletm(trnAvlEtm);

            if (!isRV002exist(info)) {
                rv002Repo.addRV002(info);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isRV002exist(RV002Info info) {
        return rv002Repo.isRV002Exist(info.getOutactno()
                , info.getViractno(), info.getReccodcd()
                , info.getTrnavlsdt(), info.getTrnavledt()
                , info.getTrnavlstm(), info.getTrnavletm());
    }
}

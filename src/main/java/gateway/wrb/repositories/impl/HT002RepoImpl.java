package gateway.wrb.repositories.impl;

import gateway.wrb.domain.HT002Info;
import gateway.wrb.model.HT002DTO;
import gateway.wrb.repositories.HT002Repo;
import gateway.wrb.util.Validator;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Repository
@Transactional
public class HT002RepoImpl implements HT002Repo {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<HT002Info> getAllHT002() {
        List<HT002Info> ht002InfoList = new ArrayList<>();
        try {
            String hql = "FROM HT002Info";
            Query query = entityManager.createQuery(hql);
            ht002InfoList = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ht002InfoList;
    }

    @Override
    public List<HT002DTO> filterHT002(String orgCd, String bankCd, String bankCoNo, String outActNo, String InqSdt, String InqEdt) {
        List<HT002DTO> ht002DTOS = new ArrayList<>();
        StringBuilder hql = new StringBuilder("FROM HT002Info as ht002 " +
                "INNER JOIN FbkFilesInfo AS fbkFiles ON ht002.fbkname = fbkFiles.fbkname " +
                "WHERE fbkFiles.conos = :bankCoNo " +
                "AND ht002.viractno = :outActNo");
        try {
            Map<String, String> mapParam = new LinkedHashMap<>();
            mapParam.put("bankCoNo", bankCoNo);
            mapParam.put("outActNo", outActNo);
            if (Validator.validateString(InqSdt)) {
                mapParam.put("InqSdt", InqSdt);
                hql.append(" AND STR_TO_DATE (fbkFiles.trndt, '%Y%m%d') >= STR_TO_DATE (:InqSdt, '%Y%m%d')");
            }
            if (Validator.validateString(InqEdt)) {
                mapParam.put("InqEdt", InqEdt);
                hql.append(" AND STR_TO_DATE (fbkFiles.trndt, '%Y%m%d') =< STR_TO_DATE (:InqEdt, '%Y%m%d')");
            }
            System.out.println(hql.toString());
            Query query = entityManager.createQuery(hql.toString());
            for (Map.Entry<String, String> entry : mapParam.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }

            List<?> rs = query.getResultList();
            for (int i = 0; i < rs.size(); ++i) {
                Object[] row = (Object[]) rs.get(i);
                HT002DTO ht002DTO = new HT002DTO();
                ht002DTO = convertToDTO((HT002Info) row[0]);
                ht002DTOS.add(ht002DTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ht002DTOS;
    }

    private HT002DTO convertToDTO(HT002Info ht002Info) {
        HT002DTO ht002DTO = new HT002DTO();
        ht002DTO.setActNo(ht002Info.getActno());
        ht002DTO.setTrnDt(ht002Info.getTrndt());
        ht002DTO.setTrnTm(ht002Info.getTrntm());
        ht002DTO.setTrnAmt(ht002Info.getTrnamt());
        ht002DTO.setTrnAfBl(ht002Info.getTrnafbl());
        ht002DTO.setTrnType(ht002Info.getTrntype());
        ht002DTO.setRefTxt(ht002Info.getReftxt());
        ht002DTO.setTrmPrcSrno(ht002Info.getTrmprcsrno());
        ht002DTO.setVirActNo(ht002Info.getViractno());
        return ht002DTO;
    }

    @Override
    public void addHT002(HT002Info ht002Info) {
        try {
            entityManager.persist(ht002Info);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateHT002(HT002Info ht002Info) {
        try {
            entityManager.merge(ht002Info);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteHT002(long id) {
        if (Validator.validateObject(id)) {
            String hql = " FROM HT002Info AS ht002  WHERE ht002.ht002id = :id";
            try {
                Query query = entityManager.createQuery(hql);
                query.setParameter("id", id);
                HT002Info ht002Info = (HT002Info) query.getSingleResult();//Get the HT002Info in DB that has ht002id = id
                if (Validator.validateObject(ht002Info)) {
                    entityManager.remove(ht002Info);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean isHT002Exist(String msgDscd, String actNo, String trnDt, String trnTm, String drCr, String trnAmt, String trnAfBl,
                                String brCd, String chkAmt, String trnType, String particular, String depSeq, String status, String channelType,
                                String trnSrno, String destAccount, String recieveName, String refTxt, String depRmk, String trmPrcSrno) {
        Map<String, String> mapParam = new LinkedHashMap<>();
        Long count = 0l;
        String hql = " SELECT COUNT(*) FROM HT002Info as ht002 WHERE";
        if (Validator.validateString(msgDscd)) {
            mapParam.put("msgDscd", msgDscd);
            hql = hql.concat(" ht002.msgdscd = :msgDscd AND");
        }
        if (Validator.validateString(actNo)) {
            mapParam.put("actNo", actNo);
            hql = hql.concat(" ht002.actno = :actNo AND");
        }
        if (Validator.validateString(trnDt)) {
            mapParam.put("trnDt", trnDt);
            hql = hql.concat(" ht002.trndt = :trnDt AND");
        }
        if (Validator.validateString(trnTm)) {
            mapParam.put("trnTm", trnTm);
            hql = hql.concat(" ht002.trntm = :trnTm AND");
        }
        if (Validator.validateString(drCr)) {
            mapParam.put("drCr", drCr);
            hql = hql.concat(" ht002.drcr = :drCr AND");
        }
        if (Validator.validateString(trnAmt)) {
            mapParam.put("trnAmt", trnAmt);
            hql = hql.concat(" ht002.trnamt = :trnAmt AND");
        }
        if (Validator.validateString(trnAfBl)) {
            mapParam.put("trnAfBl", trnAfBl);
            hql = hql.concat(" ht002.trnafBl = :trnAfBl AND");
        }
        if (Validator.validateString(brCd)) {
            mapParam.put("brCd", brCd);
            hql = hql.concat(" ht002.brcd = :brCd AND");
        }
        if (Validator.validateString(chkAmt)) {
            mapParam.put("chkAmt", chkAmt);
            hql = hql.concat(" ht002.chkamt = :chkAmt AND");
        }
        if (Validator.validateString(trnType)) {
            mapParam.put("trnType", trnType);
            hql = hql.concat(" ht002.trntype = :trnType AND");
        }
        if (Validator.validateString(particular)) {
            mapParam.put("particular", particular);
            hql = hql.concat(" ht002.particular = :particular AND");
        }
        if (Validator.validateString(depSeq)) {
            mapParam.put("depSeq", depSeq);
            hql = hql.concat(" ht002.depseq = :depSeq AND");
        }
        if (Validator.validateString(status)) {
            mapParam.put("status", status);
            hql = hql.concat(" ht002.status = :status AND");
        }
        if (Validator.validateString(channelType)) {
            mapParam.put("channelType", channelType);
            hql = hql.concat(" ht002.channeltype = :channelType AND");
        }
        if (Validator.validateString(trnSrno)) {
            mapParam.put("trnSrno", trnSrno);
            hql = hql.concat(" ht002.trnsrno = :trnSrno AND");
        }
        if (Validator.validateString(destAccount)) {
            mapParam.put("destAccount", destAccount);
            hql = hql.concat(" ht002.destaccount = :destAccount AND");
        }
        if (Validator.validateString(recieveName)) {
            mapParam.put("recieveName", recieveName);
            hql = hql.concat(" ht002.recievename = :recieveName AND");
        }
        if (Validator.validateString(refTxt)) {
            mapParam.put("refTxt", refTxt);
            hql = hql.concat(" ht002.reftxt = :refTxt AND");
        }
        if (Validator.validateString(depRmk)) {
            mapParam.put("depRmk", depRmk);
            hql = hql.concat(" ht002.deprmk = :depRmk AND");
        }
        if (Validator.validateString(trmPrcSrno)) {
            mapParam.put("trmPrcSrno", trmPrcSrno);
            hql = hql.concat(" ht002.trmprcsrno = :trmPrcSrno");
        }
        if (hql.endsWith("WHERE")) {
            hql = hql.replace("WHERE", "");
        }
        if (hql.endsWith("AND")) {
            hql = hql.substring(0, hql.lastIndexOf("AND") - 1);
        }
        try {
            Query query = entityManager.createQuery(hql);
            for (Map.Entry<String, String> param : mapParam.entrySet()) {
                query.setParameter(param.getKey(), param.getValue());
            }
            List<Long> rs = query.getResultList();
            count = rs.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count > 0 ? true : false;
    }
}

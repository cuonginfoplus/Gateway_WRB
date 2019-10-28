package gateway.wrb.repositories.impl;

import gateway.wrb.domain.HT002Info;
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
    public List<HT002Info> filterHT002(String orgCd, String bankCd, String bankCoNo, String outActNo, String bankRsvSdt, String bankRsvEdt) {
        List<HT002Info> ht002InfoList = new ArrayList<>();
        Map<String, String> mapParam = new LinkedHashMap<>();
        String hql = "FROM HT002Info as ht002";
        if (Validator.validateString(orgCd)) {
            mapParam.put("orgCd", orgCd);
            hql = hql.concat(" INNER JOIN FbkFilesInfo as fbkFiles ON ht002.fbkname = fbkFiles.fbkname WHERE fbkFiles.conos = :orgCd AND");
        }
        if (!Validator.validateString(orgCd)) {
            hql = hql.concat(" WHERE");
        }
        if (Validator.validateString(bankCd)) {
            //
        }
        if (Validator.validateString(bankCoNo)) {
            //
        }
        if (Validator.validateString(outActNo)) {
            mapParam.put("outActNo", outActNo);
            hql = hql.concat(" ht002.viractno = :outActNo AND");
        }
        if (Validator.validateString(bankRsvSdt)) {
            mapParam.put("bankRsvSdt", bankRsvSdt);
            hql = hql.concat(" ht002.trndt >= :bankRsvSdt AND");
        }
        if (Validator.validateString(bankRsvEdt)) {
            mapParam.put("bankRsvEdt", bankRsvEdt);
            hql = hql.concat(" ht002.trndt <= :bankRsvEdt");
        }
        if (hql.endsWith("END")) {
            hql = hql.substring(0, hql.lastIndexOf("AND") - 1);
        }
        try {
            System.out.println("HQL Query: " + hql);
            Query query = entityManager.createQuery(hql);
            for (Map.Entry<String, String> param : mapParam.entrySet()) {
                query.setParameter(param.getKey(), param.getValue());
            }
            List<?> rs = new ArrayList<>();
            rs = query.getResultList();
            for (Object item : rs) {
                if (!hql.contains("JOIN")) {
                    ht002InfoList.add((HT002Info) item);
                } else {
                    Object[] row = (Object[]) item;
                    ht002InfoList.add((HT002Info) row[0]);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ht002InfoList;
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

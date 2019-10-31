package gateway.wrb.repositories.impl;

import gateway.wrb.domain.FbkFilesInfo;
import gateway.wrb.domain.VLR001Info;
import gateway.wrb.model.RV001DTO;
import gateway.wrb.repositories.VLR001Repo;
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
public class VLRV001RepoImpl implements VLR001Repo {
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void addVLR001(VLR001Info vlr001Info) {
        entityManager.persist(vlr001Info);
    }

    @Override
    public List<RV001DTO> filterVLRV001(String orgCd, String bankCd, String bankCoNo, String outActNo, String bankRsvSdt, String bankRsvEdt) {
        List<RV001DTO> vlr001InfoList = new ArrayList<>();

        StringBuilder hql = new StringBuilder("FROM VLR001Info AS vlr001 INNER JOIN FbkFilesInfo AS fbkFiles " +
                " ON vlr001.fbkname = fbkFiles.fbkname WHERE fbkFiles.conos = :bankCoNo  AND vlr001.virActNo = :outActNo" +
                " AND STR_TO_DATE (fbkFiles.trndt, '%Y%m%d') >= STR_TO_DATE (:bankRsvSdt, '%Y%m%d')");
        System.out.println(hql);
        try {
            Map<String, String> mapParam = new LinkedHashMap<>();
            mapParam.put("bankCoNo", bankCoNo);
            mapParam.put("outActNo", outActNo);
            mapParam.put("bankRsvSdt", bankRsvSdt);
            if (Validator.validateString(bankRsvEdt)) {
                mapParam.put("bankRsvEdt", bankRsvEdt);
                hql.append(" AND STR_TO_DATE (fbkFiles.trndt, '%Y%m%d') <= STR_TO_DATE (:bankRsvEdt, '%Y%m%d')");
            }
            Query query = entityManager.createQuery(hql.toString());
            for (Map.Entry<String, String> entry : mapParam.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
            List<?> rs = query.getResultList();
            for (int i = 0; i < rs.size(); ++i) {
                Object[] row = (Object[]) rs.get(i);
                RV001DTO rv001DTO = new RV001DTO();
                rv001DTO = castToDTO((VLR001Info) row[0]);
                rv001DTO.setBankRcvDt(((FbkFilesInfo) row[1]).getTmsdts());
                rv001DTO.setBankRcvTm(((FbkFilesInfo) row[1]).getTmstms());
                vlr001InfoList.add(rv001DTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return vlr001InfoList;
    }

    private RV001DTO castToDTO(VLR001Info vlr001Info) {
        RV001DTO rv001DTO = new RV001DTO();
        rv001DTO.setVirActNo(vlr001Info.getVirActNo());
        rv001DTO.setVractCusNm(vlr001Info.getVractCusNm());
        rv001DTO.setTrnAvlSdt(vlr001Info.getTrnAvlSdt());
        rv001DTO.setTrnAvlEdt(vlr001Info.getTrnAvlEdt());
        rv001DTO.setTrnAvlStm(vlr001Info.getTrnAvlStm());
        rv001DTO.setTrnAvlEtm(vlr001Info.getTrnAvlEtm());
        rv001DTO.setRgsTrnDt(vlr001Info.getRgsTrnDt());
        rv001DTO.setBankRcvDt(vlr001Info.getTrnAvlSdt());
        return rv001DTO;
    }

    @Override
    public boolean isVLR001exist(String msgDscd, String virActNo, String aplDscd, String trnAvlSdt, String trnAvlEdt, String trnAvlStm, String trnAvlEtm, String rgsTrnDt, String stsDscd) {
        Map<String, String> mapParam = new LinkedHashMap<>();
        String hql = "SELECT COUNT (*) FROM VLR001Info AS vlr001 WHERE";
        Long count = 0l;
        if (Validator.validateString(msgDscd)) {
            mapParam.put("msgDscd", msgDscd);
            hql = hql.concat(" vlr001.msgdscd = :msgDscd AND");
        }
        if (Validator.validateString(virActNo)) {
            mapParam.put("virActNo", virActNo);
            hql = hql.concat(" vlr001.virActNo = :virActNo AND");
        }
        if (Validator.validateString(aplDscd)) {
            mapParam.put("aplDscd", aplDscd);
            hql = hql.concat(" vlr001.aplDscd = :aplDscd AND");
        }
        if (Validator.validateString(trnAvlSdt)) {
            mapParam.put("trnAvlSdt", trnAvlSdt);
            hql = hql.concat(" vlr001.trnAvlSdt = :trnAvlSdt AND");
        }
        if (Validator.validateString(trnAvlEdt)) {
            mapParam.put("trnAvlEdt", trnAvlEdt);
            hql = hql.concat(" vlr001.trnAvlEdt = :trnAvlEdt AND");
        }
        if (Validator.validateString(trnAvlStm)) {
            mapParam.put("trnAvlStm", trnAvlStm);
            hql = hql.concat(" vlr001.trnAvlStm = :trnAvlStm AND");
        }
        if (Validator.validateString(trnAvlEtm)) {
            mapParam.put("trnAvlEtm", trnAvlEtm);
            hql = hql.concat(" vlr001.trnAvlEtm = :trnAvlEtm AND");
        }
        if (Validator.validateString(rgsTrnDt)) {
            mapParam.put("rgsTrnDt", rgsTrnDt);
            hql = hql.concat(" vlr001.rgsTrnDt = :rgsTrnDt AND");
        }
        if (Validator.validateString(stsDscd)) {
            mapParam.put("stsDscd", stsDscd);
            hql = hql.concat(" vlr001.stsDscd = :stsDscd");
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

    @Override
    public void updateVLR001(VLR001Info vlr001Info) {
        try {
            entityManager.merge(vlr001Info);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteVLR001(Long id) {

        String hql = " FROM VLR001Info AS vlr001  WHERE vlr001.id = :id";
        try {
            Query query = entityManager.createQuery(hql);
            query.setParameter("id", id);
            VLR001Info vlr001 = (VLR001Info) query.getSingleResult();//Get the VLR001 in DB that has id = id
            if (Validator.validateObject(vlr001)) {
                entityManager.remove(vlr001);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

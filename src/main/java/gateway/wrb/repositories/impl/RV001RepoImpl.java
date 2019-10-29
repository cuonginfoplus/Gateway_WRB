package gateway.wrb.repositories.impl;

import gateway.wrb.domain.RV001Info;
import gateway.wrb.repositories.RV001Repo;
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
public class RV001RepoImpl implements RV001Repo {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<RV001Info> getAllRV001() {
        String hql = " FROM RV001Info as u ORDER BY u.id";
        return (List<RV001Info>) entityManager.createQuery(hql).getResultList();
    }

    @Override
    public List<RV001Info> filterRV001(String orgCd, String bankCd, String bankCoNo, String outActNo, String bankRsvSdt, String bankRsvEdt) {
        List<RV001Info> rv001InfoList = new ArrayList<>();
        Map<String, String> mapParam = new LinkedHashMap<>();
        StringBuilder hql = new StringBuilder("FROM RV001Info as rv001 ");
        if (Validator.validateString(bankCoNo)) {
            mapParam.put("bankCoNo", bankCoNo);
            hql.append(" INNER JOIN FbkFilesInfo AS fbkFile ON rv001.fbkname = fbkFile.fbkname WHERE fbkFile.conos = :bankCoNo ");
        }
        if (Validator.validateString(outActNo)) {
            mapParam.put("outActNo", outActNo);
            hql.append(" AND rv001.rcvviracno = :outActNo");
        }
        if (Validator.validateString(bankRsvSdt)) {
            mapParam.put("bankRsvSdt", bankRsvSdt);
            hql.append(" AND STR_TO_DATE(rv001.trndt,'%Y%m%d')  > STR_TO_DATE(:bankRsvSdt,'%Y%m%d') ");
        }
        if (Validator.validateString(bankRsvEdt)) {
            mapParam.put("bankRsvEdt", bankRsvEdt);
            hql.append(" AND STR_TO_DATE(rv001.trndt,'%Y%m%d')  < STR_TO_DATE(:bankRsvEdt,'%Y%m%d') ");
        }
        Query query = entityManager.createQuery(hql.toString());
        for (Map.Entry<String, String> entry : mapParam.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }

        List<?> rs = new ArrayList<>();
        rs = query.getResultList();
        for (int i = 0; i < rs.size(); ++i) {
            Object[] row = (Object[]) rs.get(i);
            rv001InfoList.add((RV001Info) row[0]);
        }
        return rv001InfoList;
    }

    @Override
    public void addRV001(RV001Info rv001Info) {
        entityManager.persist(rv001Info);
    }

    /*Đạt sửa*/
    @Override
    public void updateRV001(RV001Info rv001Info) {

        entityManager.merge(rv001Info);
    }

    @Override
    public void deleteRV001(RV001Info rv001Info) {
        entityManager.detach(rv001Info);
    }

    /* Đạt sửa lại isRV001Exist */
    @Override
    public boolean isRV001Exist(String msgDscd, String trnDt, String trnTm, String msgNo,
                                String wdracno, String rcvacNo, String wdram) {
        Map<String, String> mapParam = new LinkedHashMap<>();
        String hql = "FROM RV001Info as rv001  WHERE";
        if (Validator.validateString(msgDscd)) {
            mapParam.put("msgDscd", msgDscd);
            hql = hql.concat("  rv001.msgdscd = :msgDscd AND");
        }
        if (Validator.validateString(trnDt)) {
            mapParam.put("trnDt", trnDt);
            hql = hql.concat(" rv001.trndt = :trnDt AND");
        }
        if (Validator.validateString(trnTm)) {
            mapParam.put("trnTm", trnTm);
            hql = hql.concat(" rv001.trntm = :trnTm AND");
        }
        if (Validator.validateString(msgNo)) {
            mapParam.put("msgNo", msgNo);
            hql = hql.concat(" rv001.msgno = :msgNo AND");
        }
        if (Validator.validateString(wdracno)) {
            mapParam.put("wdracno", wdracno);
            hql = hql.concat(" rv001.wdracno = :wdracno AND");
        }
        if (Validator.validateString(rcvacNo)) {
            mapParam.put("rcvacNo", rcvacNo);
            hql = hql.concat(" rv001.rcvacno = :rcvacNo AND");
        }
        if (Validator.validateString(wdram)) {
            mapParam.put("wdram", wdram);
            hql = hql.concat(" rv001.wdram = :wdram");
        }
        if (hql.endsWith("WHERE")) {
            hql = hql.replace("WHERE", "");
        }
        if (hql.endsWith("AND")) {
            hql = hql.substring(0, hql.lastIndexOf("AND") - 1);
        }
        Query query = entityManager.createQuery(hql);
        for (Map.Entry<String, String> param : mapParam.entrySet()) {
            query.setParameter(param.getKey(), param.getValue());
        }
        Integer count = query.getResultList().size();
        return count > 0 ? true : false;
    }
}


package gateway.wrb.repositories.impl;

import gateway.wrb.domain.RV001Info;
import gateway.wrb.repositories.RV001Repo;
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


        if (!orgCd.isEmpty()) {
            mapParam.put("orgCd", orgCd);
            hql.append(" INNER JOIN FbkFilesInfo AS fbkFile ON rv001.fbkname = fbkFile.fbkname WHERE fbkFile.conos = :orgCd ");
        } else {
            hql.append(" WHERE 1 = 1 ");
        }
        if (!bankCd.isEmpty()) {
            //mapParam.put("bankCd", bankCd);
            //hql.append("AND rv001.")
        }
        if (!bankCoNo.isEmpty()) {
            //mapParam.put("bankCoNo", bankCoNo);
            //hql.append ...
        }

        if (!outActNo.isEmpty()) {
            mapParam.put("outActNo", outActNo);
            hql.append(" AND rv001.rcvacno = :outActNo");
        }
        if (!bankRsvSdt.isEmpty()) {
            mapParam.put("bankRsvSdt", bankRsvSdt);
            hql.append(" AND rv001.trndt >= :bankRsvSdt");
        }
        if (!bankRsvEdt.isEmpty()) {
            mapParam.put("bankRsvEdt", bankRsvEdt);
            hql.append(" AND rv001.trndt <= :bankRsvEdt");
        }
        System.out.println(hql.toString());
        Query query = entityManager.createQuery(hql.toString());
        for (Map.Entry<String, String> entry : mapParam.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
            System.out.println(entry.getKey() + "==========" + entry.getValue());
        }

        List<?> rs = new ArrayList<>();
        rs = query.getResultList();
        for (int i = 0; i < rs.size(); ++i) {
            //Object[] row = (Object[]) rs.get(i);
            //rv001InfoList.add((RV001Info) row[0]);
            if (!orgCd.isEmpty() && orgCd != null) {
                Object[] row = (Object[]) rs.get(i);
                rv001InfoList.add((RV001Info) row[0]);
            } else {
                rv001InfoList.add((RV001Info) rs.get(i));
            }
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
                                String wrdacNo, String rcvacNo, String wdram) {
        //String hql = "FROM RV001Info as u WHERE u.msgDscd=:msgDscd";
        String hql = "FROM RV001Info as u WHERE u.msgDscd =:msgDscd AND u.trnDt = :trnDt AND u.trnTm = :trnTm AND u.msgNo = :msgNo AND u.wrdacNo = :wrdacNo AND u.rcvacNo = :rcvacNo AND u.wdram = :wdram";
        //int count = entityManager.createQuery(hql).setParameter("msgDscd", msgDscd).getResultList().size();
        Query query = entityManager.createQuery(hql);
        query.setParameter("msgDscd", msgDscd);
        query.setParameter("trnDt", trnDt);
        query.setParameter("trnTm", trnTm);
        query.setParameter("msgNo", msgNo);
        query.setParameter("wrdacNo", wrdacNo);
        query.setParameter("rcvacNo", rcvacNo);
        query.setParameter("wdram", wdram);
        Integer count = query.getResultList().size();
        return count > 0 ? true : false;
    }
}


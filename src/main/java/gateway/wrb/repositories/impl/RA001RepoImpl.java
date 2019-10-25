package gateway.wrb.repositories.impl;

import gateway.wrb.domain.RA001Info;
import gateway.wrb.repositories.RA001Repo;
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
public class RA001RepoImpl implements RA001Repo {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(RA001Info ra001Info) {
        entityManager.persist(ra001Info);
    }

    /*Đạt sửa lại  isRA001Exist*/
    @Override
    public Integer isRA001Exist(String msgdscd, String wdrActNo, String aplDscd, String trnStDt, String trnType, String curCd, String cusIdNo) {
        String hql = "FROM RA001Info WHERE msgdscd=:msgdscd and wdrActNo=:wdrActNo and aplDscd=:aplDscd and trnStDt=:trnStDt and trnType=:trnType and curCd=:curCd and cusIdNo=:cusIdNo";
        List<RA001Info> ra001InfoList = entityManager.createQuery(hql)
                .setParameter("msgdscd", msgdscd)
                .setParameter("wdrActNo", wdrActNo)
                .setParameter("aplDscd", aplDscd)
                .setParameter("trnStDt", trnStDt)
                .setParameter("trnType", trnType)
                .setParameter("curCd", curCd)
                .setParameter("cusIdNo", cusIdNo)
                .getResultList();
        return ra001InfoList.size();
    }

    @Override
    public List<RA001Info> filterRA001_2(String orgCd, String bankCd, String bankCoNo, String bankRsvSdt, String bankRsvEdt) {
        List<RA001Info> ra001InfoList = new ArrayList<>();
        String hql = "FROM RA001Info ";
        ra001InfoList = entityManager.createQuery(hql).getResultList();
        //Criteria criteria = entityManager.
        return ra001InfoList;
    }

    @Override
    public List<RA001Info> filterRA001(String orgCd, String bankCd, String bankCoNo) {
        List<RA001Info> ra001InfoList = new ArrayList<>();
        //String hql = "FROM VLR001Info";
        /*if (Validator.validateObjects(orgCd, bankCd, bankCoNo)) {
            String hql = "FROM RA001Info ";
            System.out.println("SQl: "+hql);
            Query query = entityManager.createQuery(hql);
            System.out.println("QUERY thanh cong");
            ra001InfoList = query.getResultList();
        }*/
        Map<String, String> mapParam = new LinkedHashMap<>();
        StringBuilder hql = new StringBuilder("FROM RA001Info AS ra001 ");
        if (!orgCd.isEmpty() && orgCd != null) {
            mapParam.put("orgCd", orgCd);
            hql.append(" INNER JOIN FbkFilesInfo AS fbkFiles ON ra001.fbkname = fbkFiles.fbkname WHERE fbkFiles.conos = :orgCd ");
        } else {
            hql.append(" WHERE 1 = 1 ");
        }
        if (!bankCd.isEmpty() && bankCd != null) {
            // mapParam.put("bankCd",bankCd);
        }
        if (!bankCoNo.isEmpty() && bankCoNo != null) {
            // mapParam.put("bankCoNo",bankCoNo);
        }
        System.out.println(hql.toString());
        Query query = entityManager.createQuery(hql.toString());
        for (Map.Entry<String, String> param : mapParam.entrySet()) {
            query.setParameter(param.getKey(), param.getValue());
        }
        List<?> rs = new ArrayList<>();
        rs = query.getResultList();
        for (int i = 0; i < rs.size(); ++i) {
            //bject[] row = (Object[]) rs.get(i);
            //System.out.println(row[0]);
            //ra001InfoList.add((RA001Info) rs);
            //System.out.println(((RA001Info) rs.get(i)).getId());
            //ra001InfoList.add(((RA001Info) rs.get(i).get(0)));
            if (!orgCd.isEmpty() && orgCd != null) {
                Object[] row = (Object[]) rs.get(i);
                ra001InfoList.add((RA001Info) row[0]);
            } else {
                ra001InfoList.add((RA001Info) rs.get(i));
            }
        }

        return ra001InfoList;
    }
}


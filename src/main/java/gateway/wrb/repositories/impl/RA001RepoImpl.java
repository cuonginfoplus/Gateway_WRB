package gateway.wrb.repositories.impl;

import gateway.wrb.domain.FbkFilesInfo;
import gateway.wrb.domain.RA001Info;
import gateway.wrb.domain.RA001SInfo;
import gateway.wrb.model.RA001DTO;
import gateway.wrb.repositories.RA001Repo;
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
public class RA001RepoImpl implements RA001Repo {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(RA001Info ra001Info) {
        entityManager.persist(ra001Info);
    }

    @Override
    public void save_header(RA001SInfo ra001SInfo) {
        entityManager.persist(ra001SInfo);
    }

    @Override
    public void update(RA001Info info) {
        entityManager.merge(info);
    }

    @Override
    public Integer isRA001Exist(String msgdscd, String wdrActNo, String aplDscd, String trnStDt, String trnType, String status, String curCd, String cusIdNo) {
        String hql = "FROM RA001Info WHERE msgdscd=:msgdscd and wdrActNo=:wdrActNo and aplDscd=:aplDscd and trnStDt=:trnStDt and trnType=:trnType and status=:status and curCd=:curCd and cusIdNo=:cusIdNo";
        List<RA001Info> ra001InfoList = entityManager.createQuery(hql)
                .setParameter("msgdscd", msgdscd)
                .setParameter("wdrActNo", wdrActNo)
                .setParameter("aplDscd", aplDscd)
                .setParameter("trnStDt", trnStDt)
                .setParameter("trnType", trnType)
                .setParameter("status", status)
                .setParameter("curCd", curCd)
                .setParameter("cusIdNo", cusIdNo)
                .getResultList();
        return ra001InfoList.size();
    }

    @Override
    public List<RA001DTO> filterRA001(String orgCd, String bankCd, String bankCoNo, String outActNo, String bankRsvSdt, String bankRsvEdt) {
        List<RA001DTO> ra001DTOList = new ArrayList<>();
        StringBuilder hql = new StringBuilder("FROM RA001Info AS ra001 INNER JOIN FbkFilesInfo AS fbkFiles ON ra001.fbkname = fbkFiles.fbkname INNER JOIN RA001SInfo as ra001s ON ra001s.fbkname = fbkFiles.fbkname" +
                " WHERE ra001s.coNo = :bankCoNo" +
                " AND (ra001.status = 'SUC01' OR ra001.status = 'CAN01')");
        try {
            Map<String, String> mapParam = new LinkedHashMap<>();
            mapParam.put("bankCoNo", bankCoNo);
            if (Validator.validateString(bankRsvSdt)) {
                mapParam.put("bankRsvSdt", bankRsvSdt);
                hql.append(" AND STR_TO_DATE (fbkFiles.trndt, '%Y%m%d') >= STR_TO_DATE (:bankRsvSdt, '%Y%m%d')");
            }
            if (Validator.validateString(bankRsvEdt)) {
                mapParam.put("bankRsvEdt", bankRsvEdt);
                hql.append(" AND STR_TO_DATE (fbkFiles.trndt, '%Y%m%d') <= STR_TO_DATE (:bankRsvEdt, '%Y%m%d')");
            }
            if (Validator.validateString(outActNo)) {
                mapParam.put("outActNo", outActNo);
                hql.append(" AND ra001s.outActNo = :outActNo");
            }
            System.out.println(hql.toString());
            Query query = entityManager.createQuery(hql.toString());
            for (Map.Entry<String, String> entry : mapParam.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
            List<?> rs = query.getResultList();
            for (int i = 0; i < rs.size(); ++i) {
                Object[] row = (Object[]) rs.get(i);
                RA001DTO ra001DTO = new RA001DTO();
                ra001DTO.convertToDTO((RA001Info) row[0]);
                ra001DTO.setBankRcvDt(((FbkFilesInfo) row[1]).getTmsdts());
                ra001DTO.setBankRcvTm(((FbkFilesInfo) row[1]).getTmstms());
                ra001DTOList.add(ra001DTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ra001DTOList;
    }
}


package gateway.wrb.repositories.impl;

import gateway.wrb.domain.FbkFilesInfo;
import gateway.wrb.domain.RA001Info;
import gateway.wrb.model.RA001DTO;
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
    public List<RA001DTO> filterRA001(String orgCd, String bankCd, String bankCoNo, String bankRsvSdt, String bankRsvEdt) {
        List<?> rs = new ArrayList<>();
        List<RA001DTO> ra001DTOList = new ArrayList<>();
        String hql = "FROM RA001Info AS ra001 INNER JOIN FbkFilesInfo AS fbkFiles" +
                " ON ra001.fbkname = fbkFiles.fbkname WHERE fbkFiles.conos = :orgCd" +
                " AND STR_TO_DATE (fbkFiles.trndt, '%Y%m%d') >= STR_TO_DATE (:bankRsvSdt, '%Y%m%d')" +
                " AND STR_TO_DATE (fbkFiles.trndt, '%Y%m%d') <= STR_TO_DATE (:bankRsvEdt, '%Y%m%d')";
        try {
            Query query = entityManager.createQuery(hql);
            query.setParameter("orgCd",orgCd);
            query.setParameter("bankRsvSdt",bankRsvSdt);
            query.setParameter("bankRsvEdt",bankRsvEdt);
            rs = query.getResultList();
            for(int i=0;i<rs.size();++i){
                Object[] row = (Object[]) rs.get(i);
                RA001DTO ra001DTO = new RA001DTO();
                ra001DTO.convertToDTO((RA001Info) row[0]);
                ra001DTO.setBankRcvDt(((FbkFilesInfo )row[1]).getTmsdts());
                ra001DTO.setBankRcvTm(((FbkFilesInfo )row[1]).getTmstms());
                ra001DTOList.add(ra001DTO);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return ra001DTOList;
    }
}


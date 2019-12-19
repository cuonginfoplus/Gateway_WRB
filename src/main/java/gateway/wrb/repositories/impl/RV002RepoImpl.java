package gateway.wrb.repositories.impl;

import gateway.wrb.domain.RV002Info;
import gateway.wrb.repositories.RV002Repo;
import gateway.wrb.util.Validator;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Repository
@Transactional
public class RV002RepoImpl implements RV002Repo {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<RV002Info> getAllRV002() {
        String hql = "FROM RV001Info as u ORDER BY u.id";
        return (List<RV002Info>) entityManager.createQuery(hql).getResultList();
    }

    @Override
    public RV002Info getRV002byID(long id) {
        RV002Info obj = entityManager.find(RV002Info.class, id);
        return obj;
    }

    @Override
    public void addRV002(RV002Info rv002Info) {
        entityManager.persist(rv002Info);
    }

    @Override
    public void updateRV002(RV002Info rv002Info) {
        entityManager.merge(rv002Info);
    }

    @Override
    public void deleteRV002(long id) {
        entityManager.detach(getRV002byID(id));
    }

    @Override
    public boolean isRV002Exist(String outActNo, String virActno, String recCodCd,
                                String trnAvlSdt, String trnAvlEdt, String trnAvlStm, String trnAvlEtm) {
        Long count = 0l;
        String hql = "SELECT COUNT(*) FROM RV002Info as rv002 WHERE";
        Map<String, String> mapParam = new LinkedHashMap<>();
        if (Validator.validateString(outActNo)) {
            mapParam.put("outActNo", outActNo);
            hql = hql.concat(" rv002.outactno = :outActNo AND");
        }
        if (Validator.validateString(virActno)) {
            mapParam.put("virActno", virActno);
            hql = hql.concat(" rv002.viractno = :virActno AND");
        }
        if (Validator.validateString(recCodCd)) {
            mapParam.put("recCodCd", recCodCd);
            hql = hql.concat(" rv002.reccodcd = :recCodCd AND");
        }
        if (Validator.validateString(trnAvlSdt)) {
            mapParam.put("trnAvlSdt", trnAvlSdt);
            hql = hql.concat(" rv002.trnavlsdt = :trnAvlSdt AND");
        }
        if (Validator.validateString(trnAvlEdt)) {
            mapParam.put("trnAvlEdt", trnAvlEdt);
            hql = hql.concat(" rv002.trnavledt = :trnAvlEdt AND");
        }
        if (Validator.validateString(trnAvlStm)) {
            mapParam.put("trnAvlStm", trnAvlStm);
            hql = hql.concat(" rv002.trnavlstm = :trnAvlStm AND");
        }
        if (Validator.validateString(trnAvlEtm)) {
            mapParam.put("trnAvlEtm", trnAvlEtm);
            hql = hql.concat(" rv002.trnavletm = :trnAvlEtm AND");
        }
        if (hql.endsWith("WHERE")) {
            hql = hql.replace("WHERE", "");
        }
        if (hql.endsWith("AND")) {
            hql = hql.substring(0, hql.lastIndexOf("AND") - 1);
        }

        try {
            Query query = entityManager.createQuery(hql);
            for (Map.Entry<String, String> param : mapParam.entrySet())
                query.setParameter(param.getKey(), param.getValue());
            List<Long> rs = query.getResultList();
            count = rs.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count > 0 ? true : false;
    }


}


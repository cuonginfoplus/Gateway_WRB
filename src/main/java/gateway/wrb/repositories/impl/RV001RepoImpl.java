package gateway.wrb.repositories.impl;

import gateway.wrb.domain.RV001Info;
import gateway.wrb.repositories.RV001Repo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
@Transactional
public class RV001RepoImpl implements RV001Repo {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<RV001Info> getAllRV001() {
        String hql = "FROM RV001Info as u ORDER BY u.id";
        return (List<RV001Info>) entityManager.createQuery(hql).getResultList();
    }

    @Override
    public RV001Info getRV001byID(long id) {
        RV001Info obj = entityManager.find(RV001Info.class, id);
        return obj;
    }

    @Override
    public void addRV001(RV001Info rv001Info) {
        entityManager.persist(rv001Info);
    }

    @Override
    public void updateRV001(RV001Info rv001Info) {

    }

    @Override
    public void deleteRV001(long id) {
        entityManager.detach(getRV001byID(id));
    }

    @Override
    public boolean isRV001Exist(String msgDscd, String trnDt, String trnTm, String msgNo,
                                String wrdacNo, String rcvacNo, String amount) {
        String hql = "FROM RV001Info as u WHERE u.msgDscd=?";
        int count = entityManager.createQuery(hql).setParameter(1, msgDscd).getResultList().size();
        return count > 0 ? true : false;
    }
}


package gateway.wrb.repositories.impl;

import gateway.wrb.domain.HT002Info;
import gateway.wrb.repositories.HT002Repo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
@Transactional
public class HT002RepoImpl implements HT002Repo {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<HT002Info> getAllHT002() {
        return null;
    }

    @Override
    public HT002Info getHT002byID(long id) {
        return null;
    }

    @Override
    public void addHT002(HT002Info ht002Info) {
        entityManager.persist(ht002Info);
    }

    @Override
    public void updateHT002(HT002Info ht002Info) {

    }

    @Override
    public void deleteHT002(long id) {

    }

    @Override
    public boolean isHT002Exist(String msgDscd, String trnDt, String trnTm, String msgNo, String wrdacNo, String rcvacNo, String amount) {
        return false;
    }
}


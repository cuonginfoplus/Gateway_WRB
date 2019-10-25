package gateway.wrb.repositories.impl;

import gateway.wrb.domain.RB001Info;
import gateway.wrb.repositories.RB001Repo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;


@Repository
@Transactional
public class RB001RepoImpl implements RB001Repo {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(RB001Info rb001Info) {
        entityManager.persist(rb001Info);
    }

    @Override
    public Integer isRB001Exist(String msgdscd, String seq, String outActNo, String curCd, String trnAm, String tobkDscd, String istDscd, String inCdAccGb, String rcvbk1Cd,
                                String rcvbk2Cd, String rcvbkNm, String sndName, String rcvacDppeNm, String depRmk, String wdrRmk, String trnSrno, String status,
                                String prcCd, String errCd, String refNo, String filler) {
        String hql = "FROM RB001Info WHERE msgdscd=:msgdscd and seq=:seq and outActNo=:outActNo and curCd=:curCd and trnAm=:trnAm and tobkDscd=:tobkDscd " +
                " and istDscd=:istDscd and inCdAccGb=:inCdAccGb and rcvbk1Cd=:rcvbk1Cd" +
                " and rcvbk2Cd := rcvbk2Cd and rcvbkNm := rcvbkNm and sndName := sndName and rcvacDppeNm := rcvacDppeNm " +
                " and depRmk := depRmk and wdrRmk := wdrRmk and trnSrno :=trnSrno and status :=status " +
                " and prcCd :=prcCd and errCd :=errCd and refNo :=refNo and filler :=filler";
        List<RB001Info> rb001Infos = entityManager.createQuery(hql)
                .setParameter("msgdscd", msgdscd)
                .setParameter("seq", seq)
                .setParameter("outActNo", outActNo)
                .setParameter("curCd", curCd)
                .setParameter("trnAm", trnAm)
                .setParameter("tobkDscd", tobkDscd)
                .setParameter("istDscd", istDscd)
                .setParameter("inCdAccGb", inCdAccGb)
                .setParameter("rcvbk1Cd", rcvbk1Cd)
                .setParameter("rcvbk2Cd", rcvbk2Cd)
                .setParameter("rcvbkNm", rcvbkNm)
                .setParameter("sndName", sndName)
                .setParameter("rcvacDppeNm", rcvacDppeNm)
                .setParameter("depRmk", depRmk)
                .setParameter("wdrRmk", wdrRmk)
                .setParameter("trnSrno", trnSrno)
                .setParameter("status", status)
                .setParameter("prcCd", prcCd)
                .setParameter("errCd", errCd)
                .setParameter("refNo", refNo)
                .setParameter("filler", filler)
                .getResultList();
        return rb001Infos.size();
    }

    @Override
    public List<RB001Info> filterRB001(String orgCd, String bankCd, String bankCoNo, String trnxId) {
        List<RB001Info> rb001Infos = new ArrayList<>();
        String hql = "FROM RB001Info";
        rb001Infos = entityManager.createQuery(hql).getResultList();
        return rb001Infos;
    }
}


package gateway.wrb.repositories.impl;

import gateway.wrb.domain.FbkFilesInfo;
import gateway.wrb.domain.RB001Info;
import gateway.wrb.domain.RB001SInfo;
import gateway.wrb.model.RB001DTO;
import gateway.wrb.model.RB001SDTO;
import gateway.wrb.repositories.RB001Repo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
    public void save_header(RB001SInfo rb001SInfo) {
        entityManager.persist(rb001SInfo);
    }

    @Override
    public Integer isRB001Exist(String msgDscd, String seq, String outActNo, String curCd, String trnAm, String tobkDscd, String istDscd, String inCdAccGb, String rcvbk1Cd,
                                String rcvbk2Cd, String status, String refNo) {
        String hql = "FROM RB001Info WHERE msgDscd=:msgDscd and seq=:seq and outActNo=:outActNo and curCd=:curCd and trnAm=:trnAm and tobkDscd=:tobkDscd " +
                " and istDscd=:istDscd and inCdAccGb=:inCdAccGb and rcvbk1Cd=:rcvbk1Cd and rcvbk2Cd=:rcvbk2Cd and status=:status and refNo=:refNo";
        List<RB001Info> rb001Infos = entityManager.createQuery(hql)
                .setParameter("msgDscd", msgDscd)
                .setParameter("seq", seq)
                .setParameter("outActNo", outActNo)
                .setParameter("curCd", curCd)
                .setParameter("trnAm", trnAm)
                .setParameter("tobkDscd", tobkDscd)
                .setParameter("istDscd", istDscd)
                .setParameter("inCdAccGb", inCdAccGb)
                .setParameter("rcvbk1Cd", rcvbk1Cd)
                .setParameter("rcvbk2Cd", rcvbk2Cd)
                .setParameter("status", status)
                .setParameter("refNo", refNo)
                .getResultList();
        return rb001Infos.size();
    }

    @Override
    public List<RB001SDTO> filterRB001S(String orgCd, String bankCd, String bankCoNo, String trnxId) {
        List<RB001SDTO> rb001SDTOS = new ArrayList<>();
        String hql = "FROM RB001Info AS rb001 " +
                "INNER JOIN RB001SInfo AS rb001s ON rb001s.fbkname = rb001.fbkname " +
                "INNER JOIN FbkFilesInfo AS files ON files.fbkname = rb001.fbkname WHERE rb001s.coNo=:bankCoNo";
        Query query = entityManager.createQuery(hql);
        query.setParameter("bankCoNo", bankCoNo);
        List<?> rs = query.getResultList();
        for (int i = 0; i < rs.size(); ++i) {
            Object[] row = (Object[]) rs.get(i);
            RB001SDTO rb001SDTO = new RB001SDTO();
            RB001Info rb001Info = (RB001Info) row[0];
            RB001SInfo rb001SInfo = (RB001SInfo) row[1];

            rb001SDTO.setBankRcvDt(rb001SInfo.getTrnDt());
            rb001SDTO.setBankRcvTm("000000");
            if (rb001Info.getStatus().equals("REG01")) {
                rb001SDTO.setHeaderStatus("01");
            } else if (rb001Info.getStatus().equals("SUC01")) {
                rb001SDTO.setHeaderStatus("02");
            } else if (rb001Info.getStatus().equals("CAN01")) {
                rb001SDTO.setHeaderStatus("00");
            }
            rb001SDTOS.add(rb001SDTO);
        }
        return rb001SDTOS;
    }

    @Override
    public List<RB001DTO> filterRB001(String orgCd, String bankCd, String bankCoNo, String trnxId) {
        List<RB001DTO> rb001Infos = new ArrayList<>();
        String hql = "FROM RB001Info AS rb001 " +
                "INNER JOIN RB001SInfo AS rb001s ON rb001s.fbkname = rb001.fbkname " +
                "INNER JOIN FbkFilesInfo AS files ON files.fbkname = rb001.fbkname  WHERE rb001s.coNo=:bankCoNo ";
        Query query = entityManager.createQuery(hql);
        query.setParameter("bankCoNo", bankCoNo);
        List<?> rs = query.getResultList();
        for (int i = 0; i < rs.size(); ++i) {
            Object[] row = (Object[]) rs.get(i);
            RB001DTO rb001DTO = new RB001DTO();
            RB001Info rb001Info = (RB001Info) row[0];
            RB001SInfo rb001SInfo = (RB001SInfo) row[1];
            FbkFilesInfo fbkFilesInfo = (FbkFilesInfo) row[2];

            rb001DTO.setReqDt(rb001SInfo.getReqDt());
            rb001DTO.setTrnDt(rb001SInfo.getTrnDt());
            rb001DTO.setInActNo(rb001SInfo.getInActNo());
            rb001DTO.setTrnDscd(rb001SInfo.getTrnDscd());
            rb001DTO.setRqDscd(rb001SInfo.getRqDscd());
            rb001DTO.setMultiTrnCd(rb001SInfo.getMultiTrnCd());
            rb001DTO.setFeePreOcc(rb001SInfo.getFeePreOcc());
            rb001DTO.setFeeInclYn(rb001SInfo.getFeeInclYn());
            rb001DTO.setSeq(rb001Info.getSeq());
            rb001DTO.setOutActNo(rb001Info.getOutActNo());
            rb001DTO.setCurCd(rb001Info.getCurCd());
            rb001DTO.setTrnAm(rb001Info.getTrnAm());
            rb001DTO.setTobkDscd(rb001Info.getTobkDscd());
            rb001DTO.setIstDscd(rb001Info.getIstDscd());
            rb001DTO.setInCdAccGb(rb001Info.getInCdAccGb());
            rb001DTO.setRcvbk1Cd(rb001Info.getRcvbk1Cd());
            rb001DTO.setRcvbk2Cd(rb001Info.getRcvbk2Cd());
            rb001DTO.setRcvbkNm(rb001Info.getRcvbkNm());
            rb001DTO.setSndName(rb001Info.getSndName());
            rb001DTO.setRcvacDppeNm(rb001Info.getRcvacDppeNm());
            rb001DTO.setDepRmk(rb001Info.getDepRmk());
            rb001DTO.setWdrRmk(rb001Info.getWdrRmk());
            rb001DTO.setStatus(rb001Info.getStatus());
            rb001DTO.setRefNo(rb001Info.getRefNo());

            rb001Infos.add(rb001DTO);
        }
        return rb001Infos;
    }
}


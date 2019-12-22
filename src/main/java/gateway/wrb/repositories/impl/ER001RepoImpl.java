package gateway.wrb.repositories.impl;

import gateway.wrb.domain.ER001Info;
import gateway.wrb.domain.FbkFilesInfo;
import gateway.wrb.model.ER001DTO;
import gateway.wrb.repositories.ER001Repo;
import gateway.wrb.util.Validator;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Repository
@Transactional
public class ER001RepoImpl implements ER001Repo {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(ER001Info er001Info) {
        entityManager.persist(er001Info);
    }

    @Override
    public Integer isER001Exist(String msgdscd, String noticeDt, Integer noticeCnt, String fromCcy, String toCcy
            , BigDecimal baseRate, BigDecimal cashBuying, BigDecimal cashSelling, BigDecimal ttBuying
            , BigDecimal ttSelling, String orderDscd, String status, String filler) {
        String hql = "FROM ER001Info a where a.msgdscd = :msgdscd " +
                "and a.noticeDt = :noticeDt and a.noticeCnt = :noticeCnt " +
                "and a.fromCcy = :fromCcy and a.toCcy = :toCcy " +
                "and a.baseRate = :baseRate and a.cashBuying = :cashBuying " +
                "and a.cashSelling = :cashSelling and a.ttBuying = :ttBuying " +
                "and a.ttSelling = :ttSelling and a.orderDscd = :orderDscd " +
                "and a.status = :status and a.filler = :filler";
        List<ER001Info> er001Infos = entityManager.createQuery(hql)
                .setParameter("msgdscd", msgdscd)
                .setParameter("noticeDt", noticeDt)
                .setParameter("noticeCnt", noticeCnt)
                .setParameter("fromCcy", fromCcy)
                .setParameter("toCcy", toCcy)
                .setParameter("baseRate", baseRate)
                .setParameter("cashBuying", cashBuying)
                .setParameter("cashSelling", cashSelling)
                .setParameter("ttBuying", ttBuying)
                .setParameter("ttSelling", ttSelling)
                .setParameter("orderDscd", orderDscd)
                .setParameter("status", status)
                .setParameter("filler", filler)
                .getResultList();
        return er001Infos.size();
    }

    @Override
    public List<ER001DTO> filterER001(String orgCd, String bankCd, String bankCoNo, String noticeSdt, String noticeEdt) {
        List<ER001DTO> er001DTOS = new ArrayList<>();
        try {
            StringBuilder hql = new StringBuilder("FROM ER001Info as er001 " +
                    "INNER JOIN FbkFilesInfo AS fbkFiles ON er001.fbkname = fbkFiles.fbkname ");
            Map<String, String> mapParam = new LinkedHashMap<>();
            if (Validator.validateString(noticeSdt)) {
                mapParam.put("noticeSdt", noticeSdt);
                hql.append(" AND STR_TO_DATE(fbkFiles.trndt, '%Y%m%d') >= STR_TO_DATE(:noticeSdt, '%Y%m%d')");
            }
            if (Validator.validateString(noticeEdt)) {
                mapParam.put("noticeEdt", noticeEdt);
                hql.append(" AND STR_TO_DATE(fbkFiles.trndt, '%Y%m%d') <= STR_TO_DATE(:noticeEdt, '%Y%m%d')");
            }
            Query query = entityManager.createQuery(hql.toString());
            for (Map.Entry<String, String> entry : mapParam.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
            List<?> rs = query.getResultList();
            for (int i = 0; i < rs.size(); ++i) {
                Object[] row = (Object[]) rs.get(i);
                ER001DTO er001DTO = convertDTO((ER001Info) row[0]);
                er001DTO.setBankRcvDt(((FbkFilesInfo) row[1]).getTmsdts());
                er001DTO.setBankRcvTm(((FbkFilesInfo) row[1]).getTmstms());
                er001DTOS.add(er001DTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return er001DTOS;
    }

    private ER001DTO convertDTO(ER001Info er001Info) {
        ER001DTO er001DTO = new ER001DTO();
        er001DTO.setNoticeDt(er001Info.getNoticeDt());
        er001DTO.setNoticeCnt(er001Info.getNoticeCnt().toString());
        er001DTO.setFromCcy(er001Info.getFromCcy());
        er001DTO.setToCcy(er001Info.getToCcy());
        if (Validator.validate(er001Info.getBaseRate())) {
            er001DTO.setBaseRate(er001Info.getBaseRate().toString());
        } else {
            er001DTO.setBaseRate("0.00");
        }
        if (Validator.validate(er001Info.getCashBuying())) {
            er001DTO.setCashBuying(er001Info.getCashBuying().toString());
        } else {
            er001DTO.setCashBuying("0.00");
        }
        if (Validator.validate(er001Info.getCashSelling())) {
            er001DTO.setCashSelling(er001Info.getCashSelling().toString());
        } else {
            er001DTO.setCashSelling("0.00");
        }
        if (Validator.validate(er001Info.getTtBuying())) {
            er001DTO.setTtBuying(er001Info.getTtBuying().toString());
        } else {
            er001DTO.setTtBuying("0.00");
        }
        if (Validator.validate(er001Info.getTtSelling())) {
            er001DTO.setTtSelling(er001Info.getTtSelling().toString());
        } else {
            er001DTO.setTtSelling("0.00");
        }
        er001DTO.setOrderDscd(er001Info.getOrderDscd());
        er001DTO.setStatus(er001Info.getStatus());
        return er001DTO;
    }
}


package gateway.wrb.repositories;

import gateway.wrb.domain.ER001Info;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Transactional
public interface ER001Repo extends JpaRepository<ER001Info, Long> {

    @Query("SELECT count(a) FROM ER001Info a where a.msgdscd = :msgdscd and a.noticeDt = :noticeDt"
            + " and a.noticeCnt = :noticeCnt and a.fromCcy = :fromCcy and a.toCcy = :toCcy"
            + " and a.baseRate = :baseRate and a.cashBuying = :cashBuying and a.cashSelling = :cashSelling"
            + " and a.ttBuying = :ttBuying and a.ttSelling = :ttSelling and a.orderDscd = :orderDscd"
            + " and a.status = :status and a.filler = :filler")
    Integer countEr001(String msgdscd, String noticeDt, Integer noticeCnt, String fromCcy, String toCcy
            , BigDecimal baseRate, BigDecimal cashBuying, BigDecimal cashSelling, BigDecimal ttBuying
            , BigDecimal ttSelling, String orderDscd, String status, String filler);
}

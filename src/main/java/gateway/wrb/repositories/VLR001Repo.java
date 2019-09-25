package gateway.wrb.repositories;

import gateway.wrb.domain.VLR001Info;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

@Transactional
public interface VLR001Repo extends JpaRepository<VLR001Info, Long> {
    @Query("SELECT count(a) FROM VLR001Info a where "
            + " a.msgdscd = :msgdscd and a.virActNo = :virActNo"
            + " and a.aplDscd = :aplDscd and a.trnAvlSdt = :trnAvlSdt "
            + " and a.trnAvlEdt = :trnAvlEdt and a.trnAvlStm = :trnAvlStm"
            + " and a.trnAvlEtm = :trnAvlEtm and a.rgsTrnDt = :rgsTrnDt"
            + " and a.stsDscd = :stsDscd")
    Integer countItem(String msgdscd, String virActNo
            , String aplDscd, String trnAvlSdt
            , String trnAvlEdt, String trnAvlStm
            , String trnAvlEtm, String rgsTrnDt
            , String stsDscd);
}

package gateway.wrb.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gateway.wrb.domain.RA001Info;

@Transactional
public interface RA001Repo extends JpaRepository<RA001Info, Long>{
	@Query("SELECT count(a) FROM RA001Info a where "
			+ " a.msgdscd = :msgdscd and a.wdrActNo = :wdrActNo"
			+ " and a.aplDscd = :aplDscd and a.trnStDt = :trnStDt "
			+ " and a.trnType = :trnType and a.curCd = :curCd"
			+ " and a.cusIdNo = :cusIdNo ")
	Integer countItem(String msgdscd, String wdrActNo
			, String aplDscd, String trnStDt
			, String trnType, String curCd
			, String cusIdNo);
}

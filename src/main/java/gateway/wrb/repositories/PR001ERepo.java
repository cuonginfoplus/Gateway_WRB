package gateway.wrb.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import gateway.wrb.domain.PR001EInfo;

@Transactional
public interface PR001ERepo extends JpaRepository<PR001EInfo, Long>{

}

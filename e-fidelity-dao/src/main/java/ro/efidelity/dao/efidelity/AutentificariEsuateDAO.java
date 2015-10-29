package ro.efidelity.dao.efidelity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ro.efidelity.model.domain.efidelity.AutentificariEsuate;

@Repository
public interface AutentificariEsuateDAO extends
		JpaRepository<AutentificariEsuate, String> {

}

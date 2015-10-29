package ro.efidelity.dao.efidelity;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import ro.efidelity.dao.DAOConstants;
import ro.efidelity.model.domain.efidelity.Rol;

@Repository
public interface RolDAO extends JpaRepository<Rol, Long> {

	@QueryHints(value = { @QueryHint(name = DAOConstants.SQL_QUERY_HINT, value = "true") })
	public Rol findByDenumire(String string);

	@QueryHints(value = { @QueryHint(name = DAOConstants.SQL_QUERY_HINT, value = "true") })
	public List<Rol> findByDenumireIn(List<String> denumiri);

	@Query("SELECT t FROM Rol t where upper(denumire) = ?1")
	public Rol findByRole(String denumire);

	public Rol getByDenumire(String string);

}

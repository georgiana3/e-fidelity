package ro.efidelity.dao.efidelity;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import ro.efidelity.dao.DAOConstants;
import ro.efidelity.model.domain.efidelity.PersoanaJuridica;
import ro.efidelity.model.domain.efidelity.Rol;
import ro.efidelity.model.domain.efidelity.Utilizator;

@Repository
public interface UtilizatorDAO extends JpaRepository<Utilizator, Long>
	 {

	public Utilizator findByConfirmat(String cod);

	@QueryHints(value = { @QueryHint(name = DAOConstants.SQL_QUERY_HINT, value = "true") })
	@Query("SELECT DISTINCT t FROM Utilizator t LEFT OUTER JOIN FETCH t.attributes WHERE t.username = ?1")
	public Utilizator findByUsername(String username);

	@QueryHints(value = { @QueryHint(name = DAOConstants.SQL_QUERY_HINT, value = "true") })
	@Query("SELECT DISTINCT t FROM Utilizator t LEFT OUTER JOIN FETCH t.attributes WHERE t.email = ?1")
	public Utilizator findByEmail(String email);

	@Query("SELECT DISTINCT t FROM Utilizator t LEFT OUTER JOIN FETCH t.attributes WHERE t.email = ?1 and t != ?2")
	public Utilizator findByEmailAndNotUtilizator(String email,
			Utilizator utilizator);

	@QueryHints(value = { @QueryHint(name = DAOConstants.SQL_QUERY_HINT, value = "true") })
	@Query("SELECT DISTINCT t FROM PersoanaJuridica t LEFT OUTER JOIN FETCH t.attributes WHERE t.rol in ?1 order by t.denumire")
	public List<PersoanaJuridica> findPersoanaJuridicaByRolInOrderByDenumire(
			List<Rol> r);

	@QueryHints(value = { @QueryHint(name = DAOConstants.SQL_QUERY_HINT, value = "true") })
	@Query("SELECT DISTINCT t FROM Utilizator t LEFT OUTER JOIN FETCH t.attributes WHERE t.rol in ?1")
	public List<Utilizator> findByRolIn(List<Rol> r);

	@QueryHints(value = { @QueryHint(name = DAOConstants.SQL_QUERY_HINT, value = "true") })
	@Query("SELECT DISTINCT t FROM Utilizator t LEFT OUTER JOIN FETCH t.attributes WHERE t.username = ?1 AND t.valid = true")
	public Utilizator findByUsernameActiv(String username);

	@QueryHints(value = { @QueryHint(name = DAOConstants.SQL_QUERY_HINT, value = "true") })
	@Query("SELECT t FROM Utilizator t INNER JOIN FETCH t.attributes at WHERE trimupper(at.attribute) = trimupper(?1)")
	public List<Utilizator> findByAttribute(String attribute);

	@QueryHints(value = { @QueryHint(name = DAOConstants.SQL_QUERY_HINT, value = "true") })
	@Query("SELECT t FROM Utilizator t INNER JOIN FETCH t.attributes at WHERE trimupper(at.attribute) = trimupper(?1) AND trimupper(at.value) = trimupper(?2)")
	public List<Utilizator> findByAttributeAndValue(String attribute,
			String value);

	@Query("SELECT  t FROM Utilizator t where rol = ?1")
	public List<Utilizator> findByRole(Rol rol);

//	@QueryHints(value = { @QueryHint(name = DAOConstants.SQL_QUERY_HINT, value = "true") })
//	@Query(value = "SELECT DISTINCT pj FROM PersoanaJuridica pj "
//			+ "INNER JOIN FETCH pj.obiecteContract oc WHERE oc.scanConfirmari = true")
//	public List<Utilizator> findPersoanaJuridicaByContractCuConfirmariScanate();

}

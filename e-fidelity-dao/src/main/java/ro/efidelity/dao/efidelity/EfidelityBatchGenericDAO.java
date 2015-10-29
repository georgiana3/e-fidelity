package ro.efidelity.dao.efidelity;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class EfidelityBatchGenericDAO {

	public static final Logger logger = LoggerFactory
			.getLogger(EfidelityBatchGenericDAO.class);

	public static final int SQL_BATCH_SIZE = 20;

	@PersistenceContext(unitName = "efidelity")
	@Qualifier("efidelityEMF")
	private EntityManager em;

	/**
	 * Salveaza eficient obiecte multiple (bulk).
	 * 
	 * @param objects
	 */
	@Transactional
	public <T> void saveBatch(List<T> objects) {

		/* Hibernate prepare batch session */
		Session session = em.unwrap(Session.class);
		session.setFlushMode(FlushMode.COMMIT);
		session.setCacheMode(CacheMode.IGNORE);

		for (int i = 0; i < objects.size(); i++) {
			session.persist(objects.get(i));
			if ((i + 1) % SQL_BATCH_SIZE == 0) {
				session.flush();
				session.clear();
				logger.debug("Insert force flushed " + SQL_BATCH_SIZE
						+ " objects.");
			}
		}
	}

	/**
	 * Actualizeaza eficient obiecte multiple (bulk).
	 * 
	 * @param objects
	 */
	@Transactional
	public <T> void updateBatch(List<T> objects) {
		throw new RuntimeException("Not Yet Implemented.");
	}

}
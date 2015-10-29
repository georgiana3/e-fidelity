package ro.efidelity.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Cache;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import ro.efidelity.config.persistence.EFidelityPersistenceConfig;

/**
 * @author Georgiana
 * 
 */
@Component
public class CacheEvictManager {

	@PersistenceContext(unitName = EFidelityPersistenceConfig.PERSISTENCE_UNIT_NAME)
	@Qualifier(EFidelityPersistenceConfig.ENTITY_MANAGER_FACTORY)
	private EntityManager em;

	private static final Logger logger = LoggerFactory
			.getLogger(CacheEvictManager.class);

	/**
	 * Evicts all second level cache hibernate entites. This is generally only
	 * needed when an external application modifies the game databaase.
	 */
	public void clearHibernateCache() {
		EntityManager applicationEm = em.getEntityManagerFactory()
				.createEntityManager();
		try {
			logger.info("Evicting Hibernate level 2 cache...");

			Session session = applicationEm.unwrap(Session.class);
			SessionFactory sessionFactory = session.getSessionFactory();

			if (session != null) {
				session.clear(); // internal cache clear
			}

			Cache cache = sessionFactory.getCache();

			if (cache != null) {
				cache.evictAllRegions(); // Evict data from all query regions.
			}

			logger.info("Hibernate level 2 cache successfully evicted.");
		} catch (Exception e) {
			logger.error("Error evicting Hibernate level 2 cache. Check logs.",
					e);
			throw e;
		} finally {
			applicationEm.close();
		}
	}

	/**
	 * Evict entitati cache-uite, tabela AWB.
	 */
	public void clearHibernateAwbCache() {
		EntityManager applicationEm = em.getEntityManagerFactory()
				.createEntityManager();
		try {
			logger.info("Evicting Awb Hibernate level 2 cache...");

			Session session = applicationEm.unwrap(Session.class);
//			SessionFactory sessionFactory = session.getSessionFactory();

			if (session != null) {
				session.clear(); // internal cache clear
			}

//			Cache cache = sessionFactory.getCache();

//			if (cache != null) {
////				cache.evictEntityRegion(Awb.class); // Evict data from all query
//													// regions.
//			}

			logger.info("Hibernate Awb level 2 cache successfully evicted.");
		} catch (Exception e) {
			logger.error(
					"Error evicting Hibernate Awb level 2 cache. Check logs.",
					e);
			throw e;
		} finally {
			applicationEm.close();
		}
	}

}

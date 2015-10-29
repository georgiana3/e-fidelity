package ro.efidelity.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
public class ProgrammerService {

	private static final Logger logger = LoggerFactory
			.getLogger(ProgrammerService.class);

	@Autowired
	private CacheEvictManager cem;

	@CacheEvict(value = "mainCache", allEntries = true)
	public void clearCache() {
		cem.clearHibernateCache();
		logger.info("Hibernate Level 2 Cache and Ehcache cleared.");
	}

	public void clearAwbCache() {
		cem.clearHibernateAwbCache();
		logger.info("Hibernate Awb Level 2 Cache and Ehcache cleared.");
	}

}

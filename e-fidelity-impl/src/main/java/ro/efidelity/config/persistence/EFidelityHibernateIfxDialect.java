package ro.efidelity.config.persistence;

import org.hibernate.dialect.InformixDialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.DateType;
import org.hibernate.type.DoubleType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;

/**
 * @author Andrei Pietrusel
 */
public class EFidelityHibernateIfxDialect extends InformixDialect {
	public EFidelityHibernateIfxDialect() {
		super();
		// Date-Time functions
		registerFunction("month", new StandardSQLFunction("month", IntegerType.INSTANCE));
		registerFunction("year", new StandardSQLFunction("year", IntegerType.INSTANCE));
		registerFunction("datetime2date", new StandardSQLFunction("datetime2date", DateType.INSTANCE));

		// String manipulation functions
		registerFunction("substr", new StandardSQLFunction("substr", StringType.INSTANCE));

		// Math functions
		registerFunction("round", new StandardSQLFunction("round", DoubleType.INSTANCE));
		registerFunction("floor", new StandardSQLFunction("floor", DoubleType.INSTANCE));
		registerFunction("ceil", new StandardSQLFunction("ceil", DoubleType.INSTANCE));

		// Random function
		registerFunction("rand", new StandardSQLFunction("cnprapp:rand"));
	}
}

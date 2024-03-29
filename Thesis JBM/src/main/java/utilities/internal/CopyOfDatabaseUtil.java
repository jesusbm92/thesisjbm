/* DatabaseUtilities.java
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package utilities.internal;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.metamodel.EmbeddableType;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.Dialect;
import org.hibernate.internal.util.ReflectHelper;
import org.hibernate.jdbc.Work;
import org.hibernate.jpa.HibernatePersistenceProvider;

import utilities.DatabaseConfig;
import domain.DomainEntity;

public class CopyOfDatabaseUtil {

	public CopyOfDatabaseUtil() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		// Due to a bug in Hibernate 4.3.0.Final, the old Hibernate persistence
		// provider's selected
		// by default, which causes a deprecation warning to be output to the
		// console. That means that
		// we shouldn't use Persistence to create the entity manager factory.
		// entityManagerFactory =
		// Persistence.createEntityManagerFactory(PersistenceUnit);

		persistenceProvider = new HibernatePersistenceProvider();
		entityManagerFactory = persistenceProvider.createEntityManagerFactory(
				DatabaseConfig.PersistenceUnit, null);
		entityManager = entityManagerFactory.createEntityManager();

		properties = entityManagerFactory.getProperties();

		databaseUrl = findProperty("javax.persistence.jdbc.url");
		databaseName = StringUtils.substringAfterLast(databaseUrl, "/");
		databaseDialectName = findProperty("hibernate.dialect");
		databaseDialect = (Dialect) ReflectHelper.classForName(
				databaseDialectName).newInstance();

		configuration = buildConfiguration();

		entityTransaction = entityManager.getTransaction();
	}

	private HibernatePersistenceProvider persistenceProvider;
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	private Map<String, Object> properties;
	private String databaseUrl;
	private String databaseName;
	private String databaseDialectName;
	private Dialect databaseDialect;
	private Configuration configuration;
	private EntityTransaction entityTransaction;

	public HibernatePersistenceProvider getPersistenceProvider() {
		return persistenceProvider;
	}

	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public String getDatabaseUrl() {
		return databaseUrl;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public String getDatabaseDialectName() {
		return databaseDialectName;
	}

	public Dialect getDatabaseDialect() {
		return databaseDialect;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public EntityTransaction getEntityTransaction() {
		return entityTransaction;
	}

	public void recreateDatabase() throws Throwable {
		List<String> databaseScript;
		List<String> schemaScript;
		String[] statements;

		databaseScript = new ArrayList<String>();
		databaseScript.add(String.format("drop database `%s`", databaseName));
		databaseScript.add(String.format("create database `%s`", databaseName));
		if (this.executeSelect("Select e from Exam e") == null) {
			executeScript(databaseScript);
		}
		schemaScript = new ArrayList<String>();
		schemaScript.add(String.format("use `%s`", databaseName));
		statements = configuration
				.generateSchemaCreationScript(databaseDialect);
		schemaScript.addAll(Arrays.asList(statements));
		if (this.executeSelect("Select e from Exam e") == null) {
			executeScript(schemaScript);
		}
	}

	private void executeScript(final List<String> script) {
		Session session;
		session = entityManager.unwrap(Session.class);
		session.doWork(new Work() {
			@Override
			public void execute(Connection connection) throws SQLException {
				Statement statement;

				statement = connection.createStatement();
				for (String line : script) {
					statement.execute(line);
				}
				connection.commit();
			}
		});
	}

	public void openTransaction() {
		entityTransaction.begin();
	}

	public void commitTransaction() {
		entityTransaction.commit();
	}

	public void rollbackTransaction() {
		entityTransaction.rollback();
	}

	public void persist(DomainEntity entity) {
		entityManager.persist(entity);
		entityManager.flush();
	}

	public void close() {
		if (entityTransaction.isActive())
			entityTransaction.rollback();
		if (entityManager.isOpen())
			entityManager.close();
		if (entityManagerFactory.isOpen())
			entityManagerFactory.close();
	}

	private Configuration buildConfiguration() {
		Configuration result;
		Metamodel metamodel;
		Collection<EntityType<?>> entities;
		Collection<EmbeddableType<?>> embeddables;

		result = new Configuration();
		metamodel = entityManagerFactory.getMetamodel();

		entities = metamodel.getEntities();
		for (EntityType<?> entity : entities)
			result.addAnnotatedClass(entity.getJavaType());

		embeddables = metamodel.getEmbeddables();
		for (EmbeddableType<?> embeddable : embeddables)
			result.addAnnotatedClass(embeddable.getJavaType());

		return result;
	}

	private String findProperty(String property) {
		String result;
		Object value;

		value = properties.get(property);
		if (value == null)
			throw new RuntimeException(String.format("Property `%s' not found",
					property));
		if (!(value instanceof String))
			throw new RuntimeException(String.format(
					"Property `%s' is not a string", property));
		result = (String) value;
		if (StringUtils.isBlank(result))
			throw new RuntimeException(String.format("Property `%s' is blank",
					property));

		return result;
	}

	@SuppressWarnings("unused")
	private void printProperties(Map<String, Object> properties) {
		for (Entry<String, Object> entry : properties.entrySet())
			System.out.println(String.format("%s=`%s'", entry.getKey(),
					entry.getValue()));
	}

	public int executeUpdate(String line) {
		int result;
		Query query;

		query = entityManager.createQuery(line);
		result = query.executeUpdate();

		return result;
	}

	public List<?> executeSelect(String line) {
		List<?> result;
		Query query;

		query = entityManager.createQuery(line);
		result = (List<?>) query.getResultList();

		return result;
	}

}

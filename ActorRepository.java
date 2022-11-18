package com.hibernet.test;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class ActorRepository {

	  private final EntityManager entityManager;

	  public ActorRepository(final EntityManager entityManager) {
	    this.entityManager = entityManager;
	  }
	  
	  //7.	Saving objects of type Actor to the database
	  public Actor save(final Actor actor) {
	    EntityTransaction transaction = null;
	    try {
	      transaction = entityManager.getTransaction();
	      if (!transaction.isActive()) {
	        transaction.begin();
	      }

	      entityManager.persist(actor);
	      transaction.commit();
	      return actor;
	    } catch (final Exception e) {
	      if (transaction != null) {
	        transaction.rollback();
	      }
	      return null;
	    }
	  }

	  //8.	look for objects in the database of type Actor by id
	  public Optional<Actor> findById(final Long id) {
	    return Optional.ofNullable(entityManager.find(Actor.class, id));
	  }

	  //9.	search for objects in the Actor type database that were born after a certain year (i.e. the year is a method parameter)
	  public List<Actor> findAllBornAfter(final int lowerBoundExclusive) {
	    return entityManager.createQuery("SELECT a FROM Actor a WHERE a.yearOfBirth > :year", Actor.class)
	        .setParameter("year", lowerBoundExclusive)
	        .getResultList();
	  }

	  //10.	look for objects in the database of the Actor type, the names of which end with the specified value of the String type object
	  public List<Actor> findAllWithLastNameEndsWith(final String surnameEndsWith) {
	    return entityManager.createQuery("SELECT a FROM Actor a WHERE a.lastName LIKE :lastName", Actor.class)
	        .setParameter("lastName", "%" + surnameEndsWith)
	        .getResultList();
	  }
	  
	 
	}
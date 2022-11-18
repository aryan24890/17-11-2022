package com.hibernet.test;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class MovieRepository {

	  private final EntityManager entityManager;

	  public MovieRepository(final EntityManager entityManager) {
	    this.entityManager = entityManager;
	  }

	  //14.	returning all Movie records
	  public List<Movie> findAll() {
	    return entityManager.createQuery("FROM Movie ", Movie.class).getResultList();
	  }

	  //13.	searching for Movie record by id
	  public Optional<Movie> findById(final long id) {
	    return Optional.ofNullable(entityManager.find(Movie.class, id));
	  }

	  
	  //12.	searching Movie record by title
	  public List<Movie> findAllByName(String title) {
	    return entityManager.createQuery("SELECT m FROM Movie m WHERE m.title = :title", Movie.class)
	        .setParameter("title", title)
	        .getResultList();
	  }
	  
	  public Movie findMovieByName(String title) {
		    return entityManager.createQuery("SELECT m FROM Movie m WHERE m.title = :title", Movie.class)
		        .setParameter("title", title)
		        .getSingleResult();
		  }

	  public Movie save(final Movie movie) {
	    EntityTransaction transaction = null;
	    try {
	      transaction = entityManager.getTransaction();
	      if (!transaction.isActive()) {
	        transaction.begin();
	      }

	      entityManager.persist(movie);
	      transaction.commit();
	      return movie;
	    } catch (final Exception e) {
	      if (transaction != null) {
	        transaction.rollback();
	      }
	      return null;
	    }
	  }

	  //15.	removing one Movie record from the database.
	  public void remove(final Movie movie) {
	    EntityTransaction transaction = null;
	    try {
	      transaction = entityManager.getTransaction();
	      if (!transaction.isActive()) {
	        transaction.begin();
	      }

	      entityManager.remove(movie);
	      transaction.commit();
	    } catch (final Exception e) {
	      if (transaction != null) {
	        transaction.rollback();
	      }
	    }
	  }
	  
	 // 16.	removing all Movie records from the database
	  public void removeAll() {
		  	List<Movie> movies = findAll();
		  	
		  	for(Movie movie:movies) {
			    EntityTransaction transaction = null;
			    try {
			      transaction = entityManager.getTransaction();
			      if (!transaction.isActive()) {
			        transaction.begin();
			      }
	
			      entityManager.remove(movie);
			      transaction.commit();
			    } catch (final Exception e) {
			      if (transaction != null) {
			        transaction.rollback();
			      }
			    }
		  	}
		  }

	}

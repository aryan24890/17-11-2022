/*
 1.	Create the following tables by using Hibernate JPA. (Don’t create tables by your own, let hibernate to do it.)
a.	actors
id – int
name – varchar(255)
last_name – varchar(255)
year_of_birth – int
b.	genres
id – int
name – varchar(255)
c.	movies
id – int
title – varchar(255)
year_of_release – int
genre_id int
d.	actors_to_movies (There is no class by this name)
actor_id – int
movie_id – int
2.	Create entity classes like Actor, Genre and Movie
3.	The identifier for each class should be automatically generated.
4.	Implement @OneToMany between Genre and Movie
5.	Implememt @ManyToMany between the Actor and Movie entities
6.	Implememt @ManyToOne between Movie and Genre
7.	Saving objects of type Actor to the database
8.	look for objects in the database of type Actor by id
9.	search for objects in the Actor type database that were born after a certain year (i.e. the year is a method parameter)
10.	look for objects in the database of the Actor type, the names of which end with the specified value of the String type object
11.	adding Movie records to the database
12.	searching Movie record by title
13.	searching for Movie record by id
14.	returning all Movie records
15.	removing one Movie record from the database.
16.	removing all Movie records from the database
 
 */

package com.hibernet.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.hibernate.HibernateException;

public class Main {
	private static EntityManagerFactory factory = null;
	private static EntityManager entityManager = null;

	
	public static void main(String[] args) {
		try {
			factory = Persistence.createEntityManagerFactory("HibernateDemo");
			
			entityManager = factory.createEntityManager();
			
			Actor a1 = new Actor("Ajay", "Devgon", 1969, null);
			Actor a2 = new Actor("sunil", "shetthy", 1965, null);
			
			Actor a3 = new Actor("Akshay", "Kumar", 1983, null);
			Actor a4 = new Actor("Tigor", "Shraff", 1981, null);
			
			// 7.	Saving objects of type Actor to the database
			ActorRepository actorRepositoty = new ActorRepository(entityManager);
			
			actorRepositoty.save(a1);
			actorRepositoty.save(a2);
			
			actorRepositoty.save(a3);
			actorRepositoty.save(a4);
			
			
			Genre g1 = new Genre("Comedy",null);
			
			Genre g2 = new Genre("Action",null);
			
			
			
			GenreRepository genreRepository = new GenreRepository(entityManager);
			
			genreRepository.save(g1);
			
			genreRepository.save(g2);
			
			
		
			List<Actor> actors1 = new ArrayList<>();
			actors1.add(a1);
			actors1.add(a2);
			
			List<Actor> actors2 = new ArrayList<>();
			actors2.add(a3);
			actors2.add(a4);
			
			Movie m1 = new Movie("Dilwale", 2018, actors1, g1);
			
			Movie m2 = new Movie("Hiropanti", 2016, actors2, g2);
			
			// 11.	adding Movie records to the database
			MovieRepository movieRepository = new MovieRepository(entityManager);
			
			movieRepository.save(m1);
			movieRepository.save(m2);
			
		
			// 8. look for objects in the database of type Actor by id
			
			Optional<Actor> a = actorRepositoty.findById(1L);
			
			System.out.println("Actor: " + a);
			
			
			// 9. search for objects in the Actor type database that 
			// were born after a certain year (i.e. the year is a method parameter)
			
			List<Actor> actors = actorRepositoty.findAllBornAfter(1980);
			
			actors.forEach((ac) -> System.out.println(ac));
			
			
			// 10.	look for objects in the database of the Actor type, the names 
			// of which end with the specified value of the String type object
			
			actors = actorRepositoty.findAllWithLastNameEndsWith("ey");
			actors.forEach(ac -> System.out.println(ac));
			
			
			
			//12.	searching Movie record by title
			
			Movie movie = movieRepository.findMovieByName("Dilwale");
			System.out.println(movie);
			
			
			//13.	searching for Movie record by id
			Optional<Movie>	m = movieRepository.findById(2L);
			System.out.println(m);
			
			
			//14.	returning all Movie records
			List<Movie> movies = movieRepository.findAll();
			movies.forEach(mv -> System.out.println(mv));
			
			
			//15.	removing one Movie record from the database
			
			/*
			m = movieRepository.findById(2L);
			
			movieRepository.remove(m.get());
			
			movies = movieRepository.findAll();
			movies.forEach(mv -> System.out.println(mv));
			*/
			
			// 16.	removing all Movie records from the database
			
			//movieRepository.removeAll();
			
			
			
		}
		catch(HibernateException e) {
			
			e.printStackTrace();
		}
		catch(Exception e) {
			
			
			e.printStackTrace();
		}
		finally {
			
			
			if(entityManager != null)
				entityManager.close();
			
			if(factory != null)
				factory.close();
		}


	}

	
}
package facades;

import entities.Movie;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class MovieFacade {

  private static MovieFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private MovieFacade() {}
    
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static MovieFacade getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new MovieFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public void deleteAllMovies(){
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Movie.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
    public long getMovieCount(){
        EntityManager em = emf.createEntityManager();
        try{
            long movieCount = (long)em.createQuery("SELECT COUNT(m) FROM Movie m").getSingleResult();
            return movieCount;
        }finally{  
            em.close();
        }
    }

    public Movie getMovieById(long id){
        EntityManager em = emf.createEntityManager();
        try {
              Query query = em.createNamedQuery("Movie.getById");
              query.setParameter("id", id);
              Movie movie = (Movie) query.getSingleResult();
              return movie;
        }         
        finally {
            em.close();
        }
    }
    
    public List<Movie> getMovieByTitle(String title){
        EntityManager em = emf.createEntityManager();
        try {
              Query query = em.createNamedQuery("Movie.getByTitle");
              query.setParameter("title", title);
              List<Movie> movieList = query.getResultList();
              return movieList;
        }         
        finally {
            em.close();
        }  
    }
    
    public List<Movie> getAllMovies() {
                
        EntityManager em = emf.createEntityManager();
        try {
              Query query = em.createNamedQuery("Movie.getAll");
              List<Movie> movies = query.getResultList();
              return movies;
        }         
        finally {
            em.close();
        }
    }
    
    public void populateDB(){
            EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(new Movie(1986, "Topgun", new String[]{"Tom Cruise", "Val Kilmer","Kelly McGills"}));
            em.persist(new Movie(2003, "Kill Bill", new String[]{"Uma Thurman", "Daryl Hannah"}));
            em.persist(new Movie(1991, "Point Break", new String[]{"Patrick Swayze", "Keanu Reeves"}));
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

}

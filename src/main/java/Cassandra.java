import com.datastax.driver.core.*;
import java.util.UUID;


public class Cassandra {
  
  
  public void insertMovie(Session session, String title, int releaseYear) {
    PreparedStatement insertSmt = session.prepare("INSERT INTO movies (movie_id, title, release_year) VALUES (?,?,?);");
    BoundStatement bs = insertSmt.bind(UUID.randomUUID(), title, releaseYear);
    session.execute(bs);
  }

  public static void main(String args[]) {
    Cassandra c = new Cassandra();
    Cluster cluster;
    Session session;
    cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
    session = cluster.connect("essentials");

    // session.execute("INSERT INTO movies (movie_id, title, release_year) VALUES (uuid(), 'Blade Runner 2049', 2017)");

    c.insertMovie(session, "Real Genius", 1985);
    
    ResultSet results = session.execute("SELECT * FROM movies");

    for (Row row : results) {
       System.out.format("%s %s %s\n", row.getUUID("movie_id"), row.getString("title"),row.getInt("release_year"));
    }

  }
}

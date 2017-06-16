import com.datastax.driver.core.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.UUID;


public class Cassandra {
  
  public static void main(String args[]) {
    Cluster cluster;
    Session session;
    cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
    session = cluster.connect("essentials");

    session.execute("INSERT INTO movies (movie_id, title, release_year) VALUES (uuid(), 'Blade Runner 2049', 2017)");

    ResultSet results = session.execute("SELECT * FROM movies");

    for (Row row : results) {
       System.out.format("%s %s %s\n", row.getUUID("movie_id"), row.getString("title"),row.getInt("release_year"));
    }

  }
}

import com.example.config.DbConfig;
import com.example.flyway.MyFileWayCallback;
import org.flywaydb.core.Flyway;

public class MigrationMain {
    public static void main(String[] args) {
        DbConfig dbConfig = new DbConfig();

        Flyway flyway = Flyway.configure()
                .dataSource(dbConfig.getDbUrl(), dbConfig.getDbUser(), dbConfig.getDbPassword())
                .locations("db/migration")
                .baselineOnMigrate(true)
                .callbacks(new MyFileWayCallback())
                .load();

        flyway.migrate();
    }
}

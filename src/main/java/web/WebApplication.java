package web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import query.tbql.executor.TBQLQueryExecutor;
import query.tbql.executor.dbadaptor.Neo4jDBProperties;
import query.tbql.executor.dbadaptor.PostgresDBProperties;
import query.tbql.executor.handler.WebResultHandler;
import sograph.SecurityOperationGraph;
import st2g.ST2G;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@RestController
public class WebApplication {
    private static boolean running = false;
    private static String dotFilePath;

    /**
     * Start the HTTP server at http://localhost:8080
     */
    public static void init() {
        if (!running) {
            SpringApplication.run(WebApplication.class);
            running = true;
        }
    }

    /**
    * Construct graphs from the description of a query.
    * @param description The query in natural language
    * @return JSON object { ".svg": string, ".dp.svg": string, err?: string }
    */
    @GetMapping("/st2g")
    public Map<String, String> st2g(@RequestParam("des") String description) {
        HashMap<String, String> response = new HashMap<>();

        String inputPath = "output/tmp/st2g_temp.des";
        Path file = Paths.get(inputPath);
        try {
            Files.write(file, Collections.singleton(description), StandardCharsets.UTF_8);
        } catch (IOException err) {
            response.put("err", "Error writing the description");
            return response;
        }

        ST2G st2g = ST2G.getInstance();
        try {
            dotFilePath = st2g.process(inputPath);
        } catch (Exception err) {
            response.put("err", "Error constructing the graph");
            return response;
        }

        String[] SVGs = {".svg", ".dp.svg"};
        for (String svg : SVGs) {
            try {
                Path svgFile = Paths.get(dotFilePath + svg);
                List<String> lines = Files.readAllLines(svgFile);
                response.put(svg, String.join("", lines));
            } catch (IOException err) {
                response.put("err", "Error reading svg files");
                return response;
            }
        }

        return response;
    }

    /**
     * Get the TBQL query from the generated graph.
     * @return JSON object { tbql: string, err?: string }
     */
    @GetMapping("/tbql")
    public Map<String, String> tbql() {
        HashMap<String, String> response = new HashMap<>();
        if (dotFilePath != null) {
            SecurityOperationGraph sog = new SecurityOperationGraph(dotFilePath);
            response.put("tbql", sog.generateTBQL());
        } else {
            response.put("err", "Please generate a graph first");
        }
        return response;
    }

    /**
     * Execute the TBQL query edited by the user.
     * @param tbql The TBQL query to execute
     * @return JSON object { headers: string[], rows: string[], executionTime: number, err?: string }
     */
    @GetMapping("/execute")
    public WebResultHandler execute(@RequestParam("tbql") String tbql) {
        WebResultHandler response = new WebResultHandler();
        long start = System.currentTimeMillis();

        PostgresDBProperties postgresDbProperty = new PostgresDBProperties("cfg/database/postgres_db.properties");
        Neo4jDBProperties neo4jDbProperty = new Neo4jDBProperties("cfg/database/neo4j_db.properties");

        TBQLQueryExecutor tbqlQueryExecutor = new TBQLQueryExecutor();
        tbqlQueryExecutor.setResultHandler(response);
        tbqlQueryExecutor.setErrorHandler(response);
        tbqlQueryExecutor.setPostgresDbProperty(postgresDbProperty);
        tbqlQueryExecutor.setNeo4jDbProperty(neo4jDbProperty);
        tbqlQueryExecutor.parseExpression(tbql);
        tbqlQueryExecutor.run();

        long end = System.currentTimeMillis();
        response.setExecutionTime(end - start);
        return response;
    }
}

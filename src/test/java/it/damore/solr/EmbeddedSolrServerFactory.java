package it.damore.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.core.NodeConfig;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class EmbeddedSolrServerFactory {

    static private final String DEFAULT_CORE = "_default";
    static private EmbeddedSolrServer server;

    private static final String SOLR_DATA = "target/solr-data/";
    private static final String SOLR_HOME = "/solr-home/";

    public static void createDir(String path) {
        File dir = Path.of(path).toFile();
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    public static void init() throws IOException, SolrServerException {

        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("*:*");
        solrQuery.setRows(0);
        solrQuery.setFields("id");
        if (server == null) {
            server = getEmbeddedSolrServerByHomeAndName(DEFAULT_CORE);
            QueryResponse queryResponse = getTestSolrClient().query(solrQuery);
//            assert queryResponse.getResults().getNumFound() != 0;
        }
    }

    private static EmbeddedSolrServer getEmbeddedSolrServerByHomeAndName(String coreName) throws IOException {
        String cwd = Paths.get("").toAbsolutePath().toString();
        final String solrHome = Path.of(cwd, SOLR_HOME).toAbsolutePath().toString();
        final String solrDataHome = Path.of(cwd, SOLR_DATA).toAbsolutePath().toString();
        createDir(solrDataHome);
        System.setProperty("solr.solr.home", solrHome);
        final NodeConfig config = new NodeConfig.NodeConfigBuilder(coreName,Path.of(cwd, SOLR_HOME, coreName))
                .setSolrDataHome(solrDataHome)
                .setConfigSetBaseDirectory(solrHome)
                .build();

        return new EmbeddedSolrServer(config, coreName);
    }

    public static SolrClient getTestSolrClient() {
        return server;
    }

}

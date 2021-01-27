package it.damore.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.request.CoreAdminRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.core.NodeConfig;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class EmbeddedSolrServerFactory {

    private static final String SOLR_HOME = "target/solr-home/";
    private static final String SOLR_CONFIGSETS = "configsets/";

    public static void createDir(Path path) {
        File dir = path.toFile();
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    private static String getRandomSolrData(String basePath) {
        Long v = (long)(Math.random() * Long.MAX_VALUE);
        return Path.of(basePath, v.toString()).toString();
    }

    public static EmbeddedSolrServer create(String coreName, String coreConfig) throws IOException, SolrServerException {
        String cwd = Paths.get("").toAbsolutePath().toString();
        final Path solrHome = Path.of(cwd, getRandomSolrData(SOLR_HOME)).toAbsolutePath();
        final Path solrConfigsets = Path.of(cwd, SOLR_CONFIGSETS).toAbsolutePath();
        createDir(solrHome);

        final NodeConfig config = new NodeConfig.NodeConfigBuilder(coreConfig, solrHome)
                .setConfigSetBaseDirectory(solrConfigsets.toString())
                .build();
        final EmbeddedSolrServer embeddedSolrServer = new EmbeddedSolrServer(config, coreName);
        CoreAdminRequest.Create createRequest = new CoreAdminRequest.Create();
        createRequest.setCoreName(coreName);
        createRequest.setConfigSet(coreConfig);
        embeddedSolrServer.request(createRequest);
        return embeddedSolrServer;
    }

}

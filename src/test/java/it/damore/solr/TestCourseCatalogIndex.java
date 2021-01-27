package it.damore.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestCourseCatalogIndex extends TestBase {

    private static final Logger logger = LoggerFactory.getLogger(TestCourseCatalogIndex.class);
    static private final String CORE_CONFIG = "_default";
    static private final String CORE_NAME = "core1";

    @BeforeAll
    public void init() throws IOException, SolrServerException {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("*:*");
        solrQuery.setRows(0);
        solrQuery.setFields("id");
        if (server == null) {
            server = EmbeddedSolrServerFactory.create(CORE_NAME, CORE_CONFIG);
            QueryResponse queryResponse = getTestSolrClient().query(solrQuery);
        }
    }

    @DisplayName("Check presence all documents")
    @Test
    public void testSample() throws IOException, SolrServerException {
        SolrClient solrClient = getTestSolrClient();
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("*:*");
        final QueryResponse response = solrClient.query(solrQuery);
        logger.info("document found {}", response.getResults().getNumFound());
        assertTrue(response.getResults().getNumFound() == 0, "not all documents has been loaded");
    }
}

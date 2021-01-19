package it.damore.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestCourseCatalogIndex {

    private static final Logger logger = LoggerFactory.getLogger(TestCourseCatalogIndex.class);

    @BeforeAll
    public static void init() throws IOException, SolrServerException {
        EmbeddedSolrServerFactory.init();
        loadData();
    }

    @AfterAll
    public static void end() {
        EmbeddedSolrServerFactory.deleteSolrData();
    }

    public static void loadData() throws IOException, SolrServerException {

    }

    @DisplayName("Check presence all documents")
    @Test
    public void testSample() throws IOException, SolrServerException {
        SolrClient solrClient = EmbeddedSolrServerFactory.getTestSolrClient();
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("*:*");
        final QueryResponse response = solrClient.query(solrQuery);
        logger.info("document found {}", response.getResults().getNumFound());
        assertTrue(response.getResults().getNumFound() == 0, "not all documents has been loaded");
    }
}

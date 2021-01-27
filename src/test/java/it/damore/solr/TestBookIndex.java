package it.damore.solr;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.SolrInputField;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestBookIndex extends TestBase {

    private static final Logger logger = LoggerFactory.getLogger(TestBookIndex.class);
    static private final String CORE_CONFIG = "techproducts";
    static private final String CORE_NAME = "techproducts1";

    @BeforeAll
    public void init() throws IOException, SolrServerException {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("*:*");
        solrQuery.setRows(0);
        solrQuery.setFields("id");
        if (server == null) {
            server = EmbeddedSolrServerFactory.create(CORE_NAME, CORE_CONFIG);
            TypeReference<List<Map<String, Object>>> reference = new TypeReference<>() {};
            var documents = new ObjectMapper().readValue(Paths.get("exampledocs/books.json").toFile(), reference);
            var solrInputDocuments = documents.stream().map(d -> new SolrInputDocument(convertMap(d)))
                                                             .collect(Collectors.toList());
            getTestSolrClient().add(solrInputDocuments);
            getTestSolrClient().commit();
        }
    }

    public Map<String, SolrInputField> convertMap(Map<String, Object> map) {
        return map.entrySet()
           .stream()
           .map(e -> {
               final SolrInputField solrInputField = new SolrInputField(e.getKey());
               solrInputField.setValue(e.getValue());
               return Map.entry(e.getKey(), solrInputField);
           }).collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @DisplayName("Check presence all documents")
    @Test
    public void testSample() throws IOException, SolrServerException {
        SolrClient solrClient = getTestSolrClient();
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("*:*");
        final QueryResponse response = solrClient.query(solrQuery);
        logger.info("document found {}", response.getResults().getNumFound());
        assertTrue(response.getResults().getNumFound() == 4, "not all documents has been loaded");
    }

    @DisplayName("Find document by Id")
    @Test
    public void testFindById() throws IOException, SolrServerException {
        SolrClient solrClient = getTestSolrClient();
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("id:978-0641723445");
        final QueryResponse response = solrClient.query(solrQuery);
        logger.info("document found {}", response.getResults().getNumFound());
        assertTrue(response.getResults().getNumFound() == 1, "not all documents has been loaded");
    }
}

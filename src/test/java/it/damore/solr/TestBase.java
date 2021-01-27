package it.damore.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;

public class TestBase {

    protected EmbeddedSolrServer server = null;

    protected SolrClient getTestSolrClient() {
        return server;
    }

}

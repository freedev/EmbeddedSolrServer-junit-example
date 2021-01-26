# EmbeddedSolrServer Example

This little project will help you to create junit tests in order to test your Solr schema and configuration, and very important, load data in a controlled enviroment and test your Solr queries.

This task can be accomplished using an embedded version of Solr [(`EmbeddedSolrServer`)](https://lucene.apache.org/solr/guide/7_0/using-solrj.html#embeddedsolrserver)

> The EmbeddedSolrServer class provides an implementation of the SolrClient client API talking directly to an micro-instance of Solr running directly in your Java application. This embedded approach is not recommended in most cases and fairly limited in the set of features it supports – in particular it can not be used with SolrCloud or Index Replication. `EmbeddedSolrServer` exists primarily to help facilitate testing.

> For information on how to use EmbeddedSolrServer please review the SolrJ JUnit tests in the [`org.apache.solr.client.solrj.embedded`](https://github.com/apache/lucene-solr/tree/master/solr/core/src/test/org/apache/solr/client/solrj/embedded) package of the Solr source release.

## Issues
Please report issues at https://github.com/freedev/EmbeddedSolrServer-junit-example/issues

## Building
### What you need ###
* [Install Java 11 SDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html).
* Make sure that your JAVA\_HOME environment variable is set to the newly installed JDK location, and that your PATH includes ```%JAVA_HOME%\bin``` (Windows) or ```$JAVA_HOME/bin``` (\*NIX).
* [Install Maven 3.1.0 \(or later\)](http://maven.apache.org/download.html). Make sure that your PATH includes the MVN\_HOME/bin directory.
* run ```git clone https://github.com/freedev/EmbeddedSolrServer-junit-example```
* run ```mvn clean package```

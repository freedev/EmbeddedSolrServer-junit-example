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

    public static void initPlaylistTags() throws IOException, SolrServerException {
//        final SolrClient client = getTestSolrClientPlaylistTags();
//        client.add(getPlaylistTagsSolrInputDocument("On the way",
//                List.of("TAG_Mood_Personality_Casual",
//                        "TAG_Mood_For_the_Occasion_Travel_City",
//                        "TAG_Segments_Evergreen",
//                        "TAG_Features_Shape_Round")));
//        client.commit("playlist_tags");
    }

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
//                .setSharedLibDirectory(solrSharedLib)
                .build();

        return new EmbeddedSolrServer(config, coreName);
    }

    private static SolrInputDocument getPlaylistTagsSolrInputDocument(String title, List<String> tags) {
        SolrInputDocument solrInputDocument = new SolrInputDocument("title", title,
                "id", title);
        solrInputDocument.addField("tags", tags);
        return solrInputDocument;
    }

//    private static void initVideoConf() throws IOException, SolrServerException {
//        final SolrClient client = getTestSolrClientVideoConf();
//        final List<String> fieldNames = List
//                .of("eyeBags", "ethnicity", "gender", "videoId", "faceLength", "eyeColor", "pd", "eyebrowsThickness", "id", "hairColor", "ni", "faceShape", "age", "mood", "skinUndertones");
//        client.add("video_conf", getVideoConfSolrInputDocument(fieldNames,
//                List.of("no", "afro-american", "female", "afro-american-oval-female-1", "long", "brown", "66", "thick", "afro-american-oval-female-1_central", "black", "1", "oval_angled", "adult", "harmony", "warm")));
//        client.add("video_conf", getVideoConfSolrInputDocument(fieldNames,
//                List.of("no", "afro-american", "male", "afro-american-oval-male-1", "long", "brown", "64", "thick", "afro-american-oval-male-1_central", "black", "3", "oval_angled", "adult", "harmony", "warm")));
//        client.add("video_conf", getVideoConfSolrInputDocument(fieldNames,
//                List.of("no", "chinese", "male", "chinese-oval-male-1", "short", "brown", "66", "thin", "chinese-oval-male-1_central", "black", "3", "oval_angled", "adult", "harmony", "warm")));
//        client.add("video_conf", getVideoConfSolrInputDocument(fieldNames,
//                List.of("no", "chinese", "female", "chinese-round-female-1", "short", "brown", "64", "thin", "chinese-round-female-1_central", "black", "1", "round", "adult", "harmony", "warm")));
//        client.add("video_conf", getVideoConfSolrInputDocument(fieldNames,
//                List.of("no", "chinese", "female", "chinese-square-female-1", "short", "brown", "64", "thick", "chinese-square-female-1_central", "black", "1", "square", "adult", "harmony", "warm")));
//        client.add("video_conf", getVideoConfSolrInputDocument(fieldNames,
//                List.of("no", "chinese", "male", "chinese-square-male-1", "short", "brown", "61", "thin", "chinese-square-male-1_central", "black", "3", "square", "adult", "harmony", "warm")));
//        client.add("video_conf", getVideoConfSolrInputDocument(fieldNames,
//                List.of("yes", "indian", "female", "indian-oval-female-1", "short", "brown", "63", "thick", "indian-oval-female-1_central", "black", "1", "oval_round", "adult", "harmony", "warm")));
//        client.add("video_conf", getVideoConfSolrInputDocument(fieldNames,
//                List.of("no", "indian", "male", "indian-square-male-1", "long", "brown", "66", "thick", "indian-square-male-1_central", "black", "2", "square", "adult", "harmony", "warm")));
//        client.add("video_conf", getVideoConfSolrInputDocument(fieldNames,
//                List.of("no", "japanese", "male", "japanese-square-male-1", "short", "brown", "64", "thick", "japanese-square-male-1_central", "black", "5", "square", "adult", "harmony", "warm")));
//        client.add("video_conf", getVideoConfSolrInputDocument(fieldNames,
//                List.of("no", "japanese", "female", "japanese-triangle-female-1", "short", "brown", "63", "thin", "japanese-triangle-female-1_central", "brown", "1", "triangle", "adult", "harmony", "warm")));
//        client.add("video_conf", getVideoConfSolrInputDocument(fieldNames,
//                List.of("no", "japanese", "female", "japanese-triangle-female-2", "short", "brown", "62", "thin", "japanese-triangle-female-2_central", "brown", "0", "triangle", "adult", "harmony", "warm")));
//        client.add("video_conf", getVideoConfSolrInputDocument(fieldNames,
//                List.of("no", "western-north", "male", "western-north-oval-male-1", "long", "green", "65", "thick", "western-north-oval-male-1_central", "blonde", "5", "oval_round", "adult", "harmony", "warm"
//                )));
//        client.add("video_conf", getVideoConfSolrInputDocument(fieldNames,
//                List.of("no", "western-north", "female", "western-north-square-female-1", "short", "gray", "62", "thin", "western-north-square-female-1_central", "blonde", "1", "square", "adult", "harmony", "warm"
//                )));
//        client.add("video_conf", getVideoConfSolrInputDocument(fieldNames,
//                List.of("no", "western-south", "female", "western-south-oval-female-1", "long", "brown", "65", "thin", "western-south-oval-female-1_central", "brown", "1", "oval_round", "adult", "harmony", "warm"
//                )));
//        client.add("video_conf", getVideoConfSolrInputDocument(fieldNames,
//                List.of("no", "western-south", "male", "western-south-oval-male-1", "long", "green", "64", "thick", "western-south-oval-male-1_central", "black", "2", "oval_angled", "adult", "harmony", "warm"
//                )));
//        client.add("video_conf", getVideoConfSolrInputDocument(fieldNames,
//                List.of("no", "western-south", "female", "western-south-round-female-1", "shot", "gray", "63", "thin", "western-south-round-female-1_central", "black", "1", "round", "adult", "harmony", "cool"
//                )));
//        client.add("video_conf", getVideoConfSolrInputDocument(fieldNames,
//                List.of("no", "western-south", "male", "western-south-round-male-1", "long", "brown", "63", "thin", "western-south-round-male-1_central", "brown", "3", "round", "adult", "harmony", "warm"
//                )));
//        client.add("video_conf", getVideoConfSolrInputDocument(fieldNames,
//                List.of("no", "western-south", "female", "western-south-square-female-1", "short", "blue", "68", "thick", "western-south-square-female-1_central", "brown", "1", "square", "adult", "harmony", "warm"
//                )));
//        client.add("video_conf", getVideoConfSolrInputDocument(fieldNames,
//                List.of("no", "western-south", "male", "western-south-square-male-1", "long", "brown", "64", "thick", "western-south-square-male-1_central", "brown", "2", "square", "adult", "harmony", "warm"
//                )));
//        client.add("video_conf", getVideoConfSolrInputDocument(fieldNames,
//                List.of("yes", "western-south", "female", "western-south-triangle-female-1", "short", "brown", "63", "thick", "western-south-triangle-female-1_central", "black", "2", "triangle", "adult", "harmony", "warm"
//                )));
//        client.add("video_conf", getVideoConfSolrInputDocument(fieldNames,
//                List.of("yes", "western-south", "male", "western-south-triangle-male-1", "long", "brown", "68", "thick", "western-south-triangle-male-1_central", "black", "3", "triangle", "adult", "harmony", "cool"
//                )));
//        client.commit("video_conf");
//    }


    public static SolrClient getTestSolrClient() {
        return server;
    }

    public static void deleteSolrData() {
        String cwd = Paths.get("").toAbsolutePath().toString();
        final String solrDataHome = cwd + SOLR_DATA;
    }

}

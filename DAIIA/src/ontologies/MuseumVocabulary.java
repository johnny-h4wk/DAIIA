package ontologies;

/**
 *
 * @author nickstanogias, Ioannis Kerkinos
 */
public interface MuseumVocabulary {

    //-------> Basic vocabulary
    public static final int GET_RECOMMENDATIONS = 1;
    public static final int SEND_ARTIFACTS_LIST = 2;
    
    public static final String PROFILER_AGENT = "Profiler agent";
    public static final String TOUR_GUIDE_AGENT = "Tour Guide agent";
    public static final String CURATOR_AGENT = "Curator agent";
	
    //-------> Ontology vocabulary
    // Concepts
    public static final String ARTIFACT = "Artifact";
    public static final String ARTIFACT_ID = "id";
    public static final String ARTIFACT_NAME = "name";
    public static final String ARTIFACT_GENRE = "genre";
    public static final String ARTIFACT_CREATOR = "creator";
    public static final String ARTIFACT_DESCRIPTION = "description";
    public static final String ARTIFACT_IMG = "img";
    public static final String ARTIFACT_YEAR = "year";
    

    // Actions
    public static final String CREATE_PREFERENCES = "Create_preferences";
    public static final String CREATE_PREFERENCES_GENRE = "genre";
    public static final String CREATE_PREFERENCES_CREATOR = "creator";
    
    public static final String MAKE_OPERATION = "MakeOperation";
    public static final String MAKE_OPERATION_TYPE = "type";
}
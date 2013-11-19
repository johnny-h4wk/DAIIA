package ontologies;

import jade.content.onto.*;
import jade.content.schema.*;
import java.util.*;

/**
 *
 * @author nickstanogias Ioannis Kerkinos
 */
public class MuseumOntology extends Ontology implements MuseumVocabulary{
    
    // A symbolic constant, containing the name of this ontology.
    public static final String ONTOLOGY_NAME = "museum-ontology";

    // The singleton instance of this ontology
    private static Ontology instance = new MuseumOntology();

    // Method to access the singleton ontology object
    public static Ontology getInstance() { return instance; }
  
    // Private constructor
    private MuseumOntology() {

        super(ONTOLOGY_NAME, new Ontology[]{BasicOntology.getInstance(), SerializableOntology.getInstance()}, new CFReflectiveIntrospector());

	try {
            
            // Add HashMap class in the Ontology
            ObjectSchema serializableSchema = getSchema(SerializableOntology.SERIALIZABLE);
            SerializableOntology.getInstance().add(serializableSchema, java.util.HashMap.class);
            SerializableOntology.getInstance().add(serializableSchema, java.util.ArrayList.class);
            
            // ------- Add Concepts

            // Artifact
            ConceptSchema cs = new ConceptSchema(ARTIFACT);
            add(cs, Artifact.class);
            cs.add(ARTIFACT_ID, (PrimitiveSchema) getSchema(BasicOntology.INTEGER), ObjectSchema.MANDATORY);
            cs.add(ARTIFACT_NAME, (PrimitiveSchema) getSchema(BasicOntology.STRING), ObjectSchema.MANDATORY);
            cs.add(ARTIFACT_GENRE, (PrimitiveSchema) getSchema(BasicOntology.STRING), ObjectSchema.MANDATORY);
            cs.add(ARTIFACT_CREATOR, (PrimitiveSchema) getSchema(BasicOntology.STRING), ObjectSchema.MANDATORY);
            cs.add(ARTIFACT_DESCRIPTION, (PrimitiveSchema) getSchema(BasicOntology.STRING), ObjectSchema.MANDATORY);
            cs.add(ARTIFACT_IMG, (PrimitiveSchema) getSchema(BasicOntology.STRING), ObjectSchema.MANDATORY);
            cs.add(ARTIFACT_YEAR, (PrimitiveSchema) getSchema(BasicOntology.STRING), ObjectSchema.MANDATORY);
            

            // ------- Add AgentActions

            // CreatePreferences
            AgentActionSchema as = new AgentActionSchema(CREATE_PREFERENCES);
            add(as, CreatePreferences.class);
            as.add(CREATE_PREFERENCES_GENRE, (PrimitiveSchema) getSchema(BasicOntology.STRING), ObjectSchema.MANDATORY);
            as.add(CREATE_PREFERENCES_CREATOR, (PrimitiveSchema) getSchema(BasicOntology.STRING), ObjectSchema.MANDATORY);
            
            // MakeOperation
            add(as = new AgentActionSchema(MAKE_OPERATION), MakeOperation.class);
            as.add(MAKE_OPERATION_TYPE, (PrimitiveSchema) getSchema(BasicOntology.INTEGER), ObjectSchema.MANDATORY);
            
        }
        catch (OntologyException oe) {
            oe.printStackTrace();
        }
    }
    
}

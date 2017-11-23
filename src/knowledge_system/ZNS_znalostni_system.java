package knowledge_system;

import asking.AskModule;
import inference_engine.EuclideanSimilarity;
import inference_engine.FastInferenceEngine;
import inference_engine.HalfInferenceMechanism;
import inference_engine.InferenceEngine;
import inference_engine.MinimumP;
import inference_engine.ProbabilityP;
import inference_engine.Uncertainty;
import knowledge_base.DataSave2DMatrix;
import knowledge_base.DataSave;

/**
 *
 * @author Ladislav Martinek
 */
public class ZNS_znalostni_system {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DataSave data = new DataSave2DMatrix();
        data.load_data("moje_data.txt");
        //InferenceEngine inference_engine = new FastInferenceEngine();
        InferenceEngine inference_engine = new HalfInferenceMechanism();
        data.print_to_file("Data_write.txt");
        AskModule start_ask = new AskModule();
        Uncertainty probabilityComputing = new MinimumP();
        //Uncertainty probabilityComputing = new ProbabilityP();
        start_ask.start_ask(inference_engine, data, probabilityComputing, new EuclideanSimilarity());        
    }
    
}

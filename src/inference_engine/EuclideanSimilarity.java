/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inference_engine;

/**
 *
 * @author Ladislav Martinek
 */
public class EuclideanSimilarity implements SimilarityEngine{

    @Override
    public Double get_similarity(Double[] a, Double[] b) {
        double diff_square_sum = 0.0;
        for (int i = 0; i < a.length; i++) {
            diff_square_sum += (a[i] - b[i]) * (a[i] - b[i]);
        }
        return 1/(1+Math.sqrt(diff_square_sum));
    }
    
}

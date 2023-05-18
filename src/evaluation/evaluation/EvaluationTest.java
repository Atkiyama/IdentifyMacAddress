package evaluation.evaluation;

import java.io.IOException;
public class EvaluationTest{
    public static void main(String args[]) throws IOException{
        Evaluation eval = new Evaluation(args[0]);
        eval.evaluation();
        System.out.println(eval.getScore());

    }

}

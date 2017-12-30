package cz.cvut.fit.martilad.zns.zns_knowledge_system_car.exceptions;

/**
 * Exception for runtime logic fail.
 * @author Ladislav Martínek
 * @since 30. 11. 2017
 */
public class ErrorException extends Exception {
    String problem;

    public ErrorException(String problem) {
        this.problem = problem;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }
}

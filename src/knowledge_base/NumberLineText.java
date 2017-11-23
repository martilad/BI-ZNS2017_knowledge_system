package knowledge_base;

/**
 * Data sets to return from parse to data matrix save.
 * @author Ladislav Martinek
 */
public class NumberLineText {
    private int number;
    private String text;
    private Double probability = 1.;

    public NumberLineText(int number, String text) {
        this.number = number;
        this.text = text;
    }

    public int getNumber() {
        return number;
    }

    public NumberLineText(int number, String text, Double probability) {
        this.number = number;
        this.text = text;
        this.probability = probability;
    }

    public Double getProbability() {
        return probability;
    }

    public void setProbability(Double probability) {
        this.probability = probability;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    
}

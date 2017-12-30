package cz.cvut.fit.martilad.zns.zns_knowledge_system_car;

import at.downdrown.vaadinaddons.highchartsapi.HighChart;
import javax.servlet.annotation.WebServlet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import cz.cvut.fit.martilad.zns.zns_knowledge_system_car.Appservice.Asking;
import cz.cvut.fit.martilad.zns.zns_knowledge_system_car.exceptions.ErrorException;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 * 
 *
 * @author Ladislav Martínek
 * @since 30. 11. 2017
 */
 
@Theme("mytheme")
public class MyUI extends UI {
    private HighChart chart = null;
    private Asking asking_module;
    private Label what = new Label("Znalostní systém pro diagnostiku poruchy na vozidle.");
    private Label question = new Label("Tady by měla být otázečka?");
    private TextField answer = new TextField();
    private Label front = new Label("0 pro to ze definitivně ne!");
    private Label back = new Label("1 pro definitivně ano!");
    private HorizontalLayout answer_layout = new HorizontalLayout();
    private Label answer_want = new Label("Prosím napiště odpověď v rozsahu [0 , 1].");
    private Label error_input = new Label("Zadejte prosím desetiné číslo s tečkou mezi 0-1");
    private Button start = new Button("Začít");
    private Button next = new Button("Další");
    private Button repeat = new Button("Znovu");
    private String actual_question = "";
    private Label explain = new Label("",ContentMode.PREFORMATTED);
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        try {
            asking_module = new Asking();
        } catch (ErrorException ex) {
            System.out.println(ex.getProblem());
        }
        final VerticalLayout layout = new VerticalLayout();
        layout.addComponent(what); 
        layout.setComponentAlignment(what, Alignment.MIDDLE_CENTER);
        what.addStyleName(ValoTheme.LABEL_H1);
        what.addStyleName(ValoTheme.LABEL_COLORED);
        layout.addComponent(start);
        layout.setComponentAlignment(start, Alignment.MIDDLE_CENTER);
        start.addClickListener(e -> {
            if (chart != null){
                layout.removeComponent(chart);
            }
           actual_question = asking_module.get_next_question();
           question.setValue(actual_question);
           layout.removeComponent(start);
           layout.addComponent(question);
           question.addStyleName(ValoTheme.LABEL_H2);
           question.addStyleName(ValoTheme.LABEL_BOLD);
           layout.addComponent(answer_want);
           answer_want.addStyleName(ValoTheme.LABEL_H3);
           answer_layout.addComponent(front);
           answer_layout.addComponent(answer);
           answer_layout.addComponent(back);
           layout.addComponent(answer_layout);
           layout.addComponent(error_input);
           layout.addComponent(next);
           error_input.setVisible(false);
           error_input.addStyleName(ValoTheme.LABEL_FAILURE);
           layout.setComponentAlignment(error_input, Alignment.MIDDLE_CENTER);
           layout.setComponentAlignment(question, Alignment.MIDDLE_CENTER);
           layout.setComponentAlignment(answer_want, Alignment.MIDDLE_CENTER);
           layout.setComponentAlignment(answer_layout, Alignment.MIDDLE_CENTER);
           layout.setComponentAlignment(next, Alignment.MIDDLE_CENTER);
        });
        
        next.addClickListener(e -> {
            String answer_get = answer.getValue();
            answer.setValue(" ");
            Double value_answer = 1.0;
            try {
                value_answer = Double.parseDouble(answer_get);
                if (value_answer < 0 || value_answer > 1){
                    error_input.setVisible(true);
                    return;
                }
            }catch (NumberFormatException es){
                error_input.setVisible(true);
                return;
            }
            error_input.setVisible(false);
            asking_module.anwer_question(actual_question, value_answer);
            if (asking_module.is_only_one_conclusion()){
                layout.addComponent(repeat);
                layout.setComponentAlignment(repeat, Alignment.MIDDLE_CENTER);
                if (chart != null){
                layout.removeComponent(chart);
                }
                chart = CreateBarChart.put_chart_to_layout(layout, asking_module.get_posibly_conclusion());
                   
                question.setValue(asking_module.return_final_conclusion().getKey());
                explain.setValue(asking_module.return_final_conclusion().getValue());
                layout.replaceComponent(chart, explain);
                layout.removeComponent(answer);
                layout.removeComponent(answer_layout);
                layout.removeComponent(answer_want);
                layout.removeComponent(next);
            }else{
                if (chart != null){
                layout.removeComponent(chart);
                }
                chart = CreateBarChart.put_chart_to_layout(layout, asking_module.get_posibly_conclusion());
                actual_question = asking_module.get_next_question();
                question.setValue(actual_question);
            }
            
        });
   
        repeat.addClickListener(e -> {
           layout.removeAllComponents();
           layout.addComponent(what);
            layout.setComponentAlignment(what, Alignment.MIDDLE_CENTER);
            what.addStyleName(ValoTheme.LABEL_H1);
            what.addStyleName(ValoTheme.LABEL_COLORED);
            layout.addComponent(start);
            layout.setComponentAlignment(start, Alignment.MIDDLE_CENTER);
            try {
                asking_module = new Asking();
            } catch (ErrorException ex) {
                System.out.println(ex.getProblem());
            }
        });
        setContent(layout);
    }
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false, widgetset = "cz.cvut.fit.martilad.zns.zns_knowledge_system_car.AppWidgetSet")
    public static class MyUIServlet extends VaadinServlet {
    }
}

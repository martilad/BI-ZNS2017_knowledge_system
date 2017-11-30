package cz.cvut.fit.martilad.zns.zns_knowledge_system_car;

import cz.cvut.fit.martilad.zns.zns_knowledge_system_car.Appservice.ConclusionFuzzy;
import at.downdrown.vaadinaddons.highchartsapi.HighChart;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {
private HighChart chart = null;
    private Asking asking_module;
    private Label what = new Label("Knowledge system to help you with diagnostig problem with the car.");
    private Label question = new Label("There must be question if you not see it you have problem?");
    private TextField answer = new TextField();
    private Label front = new Label("0 for definitely not have this problem!");
    private Label back = new Label("1 for definitely have this problem!");
    private HorizontalLayout answer_layout = new HorizontalLayout();
    private Label answer_want = new Label("Please type you answer as number in [0 , 1].");
    private Button start = new Button("Start");
    private Button next = new Button("Next");
    private Button repeat = new Button("Repeat");
    
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        try {
            asking_module = new Asking();
        } catch (ErrorException ex) {
            System.err.println(ex.getProblem());
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
           layout.addComponent(next);
           layout.setComponentAlignment(question, Alignment.MIDDLE_CENTER);
           layout.setComponentAlignment(answer_want, Alignment.MIDDLE_CENTER);
           layout.setComponentAlignment(answer_layout, Alignment.MIDDLE_CENTER);
           layout.setComponentAlignment(next, Alignment.MIDDLE_CENTER);
        });
        
        next.addClickListener(e -> {
            ConclusionFuzzy test = new ConclusionFuzzy(new ArrayList<String>());
            chart = CreateBarChart.put_chart_to_layout(layout, test);
            layout.addComponent(repeat);
        });
   
        repeat.addClickListener(e -> {
           layout.removeAllComponents();
           layout.addComponent(what); 
            layout.setComponentAlignment(what, Alignment.MIDDLE_CENTER);
            what.addStyleName(ValoTheme.LABEL_H1);
            what.addStyleName(ValoTheme.LABEL_COLORED);
            layout.addComponent(start);
            layout.setComponentAlignment(start, Alignment.MIDDLE_CENTER);
        });
        
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false, widgetset = "cz.cvut.fit.martilad.zns.zns_knowledge_system_car.AppWidgetSet")
    public static class MyUIServlet extends VaadinServlet {
    }
}

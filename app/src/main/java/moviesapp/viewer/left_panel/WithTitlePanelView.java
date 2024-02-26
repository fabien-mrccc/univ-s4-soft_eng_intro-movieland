package moviesapp.viewer.left_panel;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import static moviesapp.model.api.UrlRequestBuilder.maxAcceptableYearValue;
import static moviesapp.model.api.UrlRequestBuilder.minAcceptableYearValue;

public class WithTitlePanelView {
    private final Pane leftPane;
    private final Label appTitle;
    private final Pane titleAndSearchPane;
    private final Label title;
    private final TextField searchBar;
    private final Pane yearPane;
    private final Label year;
    private final TextField yearField;

    public WithTitlePanelView(Pane leftPane, Label appTitle, Pane titleAndSearchPane, Label title,
                              TextField searchBar, Pane yearPane, Label year, TextField yearField) {
        this.leftPane = leftPane;
        this.appTitle = appTitle;
        this.titleAndSearchPane = titleAndSearchPane;
        this.title = title;
        this.searchBar = searchBar;
        this.yearPane = yearPane;
        this.year = year;
        this.yearField = yearField;

        setupView();
    }

    private void setupView() {
        setTitleAndSearchPane();
        setTitle();
        setSearchBar();
        setYearPane();
        setYear();
    }

    private void setTitleAndSearchPane(){
        titleAndSearchPane.layoutXProperty().bind(leftPane.widthProperty().divide(2).subtract(titleAndSearchPane.widthProperty().divide(2)));
        titleAndSearchPane.layoutYProperty().bind(appTitle.layoutYProperty().add(170));
        titleAndSearchPane.prefWidthProperty().bind(leftPane.widthProperty().multiply(0.9));
    }

    private void setTitle(){
        title.layoutYProperty().bind(titleAndSearchPane.heightProperty().divide(2).subtract(title.heightProperty().divide(2)));
    }

    private void setSearchBar(){
        searchBar.layoutXProperty().bind(title.layoutXProperty().add(60));
        searchBar.layoutYProperty().bind(title.layoutYProperty().subtract(7));
        searchBar.prefWidthProperty().bind(titleAndSearchPane.widthProperty().subtract(70));
    }

    private void setYearPane(){
        yearPane.layoutXProperty().bind(titleAndSearchPane.layoutXProperty());
        yearPane.layoutYProperty().bind(titleAndSearchPane.layoutYProperty().add(80));
        yearPane.prefWidthProperty().bind(titleAndSearchPane.prefWidthProperty());
    }

    private void setYear(){
        year.layoutYProperty().bind(yearPane.heightProperty().divide(2).subtract(year.heightProperty().divide(2)));

        yearField.setPrefWidth(190);
        yearField.layoutXProperty().bind(searchBar.layoutXProperty());
        yearField.layoutYProperty().bind(year.layoutYProperty().subtract(7));
        yearField.setPromptText("from " + minAcceptableYearValue + " to " + maxAcceptableYearValue);
    }

}

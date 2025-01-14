module org.stock.stock_view {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires retrofit2;
    requires retrofit2.converter.gson;
    requires gson;
    requires java.sql;
    requires okhttp3;
    requires java.desktop;

    exports org.kobeU.stock_view.model to gson;
    opens org.kobeU.stock_view.model to gson;
    exports org.kobeU.stock_view to javafx.graphics;
    exports org.kobeU.stock_view.view to javafx.graphics;

}

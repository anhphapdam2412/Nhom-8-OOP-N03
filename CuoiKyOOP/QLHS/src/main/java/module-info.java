module com.qlhs.qlhs {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires java.sql;
    requires java.desktop;

    opens com.qlhs.qlhs to javafx.fxml;
    exports com.qlhs.qlhs;
    exports com.qlhs.qlhs.Controller;
    opens com.qlhs.qlhs.Controller to javafx.fxml;
    exports com.qlhs.qlhs.Model;
    opens com.qlhs.qlhs.Model to javafx.fxml;
}
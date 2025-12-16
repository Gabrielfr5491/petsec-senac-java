module petsec_senac {
    requires transitive java.sql;

    requires transitive javafx.base;
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
    requires transitive javafx.graphics;

    opens petsec_senac to javafx.fxml;
    exports petsec_senac;
    exports dao;
    exports jdbc;
}

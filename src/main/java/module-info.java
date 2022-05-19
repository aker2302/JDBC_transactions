module com.project.database_project {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
	requires java.sql;

    opens com.project.database_project to javafx.fxml;
    exports com.project.database_project;
    opens com.project.database_project.domain to javafx.fxml;
    exports com.project.database_project.domain;
}
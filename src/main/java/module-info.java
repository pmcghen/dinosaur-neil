module com.example.dinosaurneil {
    requires javafx.controls;
    requires javafx.fxml;
            
                        requires org.kordamp.bootstrapfx.core;
            
    opens com.example.dinosaurneil to javafx.fxml;
    exports com.example.dinosaurneil;
}
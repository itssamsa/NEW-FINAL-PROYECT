module co.edu.uniquindio.proyectofinal.sameday {
    requires javafx.controls;
    requires javafx.fxml;

    exports co.edu.uniquindio.proyectofinal.sameday;
    opens co.edu.uniquindio.proyectofinal.sameday.viewController to javafx.fxml;
}

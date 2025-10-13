module co.edu.uniquindio.proyectofinal.plataformadeenvios {
    requires javafx.controls;
    requires javafx.fxml;


    opens co.edu.uniquindio.proyectofinal.plataformadeenvios to javafx.fxml;
    exports co.edu.uniquindio.proyectofinal.plataformadeenvios;
    exports co.edu.uniquindio.proyectofinal.plataformadeenvios.modelo;
    opens co.edu.uniquindio.proyectofinal.plataformadeenvios.modelo to javafx.fxml;
    exports co.edu.uniquindio.proyectofinal.plataformadeenvios.service;
    opens co.edu.uniquindio.proyectofinal.plataformadeenvios.service to javafx.fxml;
}
javac --module-path ./lib --add-modules javafx.controls,javafx.fxml -cp lib/*.jar; -d compile -encoding utf-8 com/db/*.java

javac --module-path ./lib --add-modules javafx.controls,javafx.fxml -cp lib/*.jar; -d compile -encoding utf-8 com/db/controller/*.java

javac --module-path ./lib --add-modules javafx.controls,javafx.fxml -cp lib/*.jar; -d compile -encoding utf-8 com/db/model/*.java

javac --module-path ./lib --add-modules javafx.controls,javafx.fxml -cp lib/*.jar; -d compile -encoding utf-8 com/db/view/*.java

xcopy com\db\css\*.* compile\com\db\css\ /s /h /e /d /y

xcopy com\db\xml\*.* compile\com\db\xml\ /s /h /e /d /y
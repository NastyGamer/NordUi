package nord.ui

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

fun main(vararg args: String) {
    Application.launch(App::class.java, *args)
}

class App : Application() {

    override fun start(stage: Stage) {
        val root = FXMLLoader.load<Parent>(this.javaClass.classLoader.getResource("MainView.fxml"));
        val scene = Scene(root, 500.0, 600.0)
        stage.title = "FXML Welcome"
        stage.scene = scene
        stage.show()
    }
}

fun String.sanitize(): String {
    var out = String()
    this.toCharArray().forEach { c -> run { if (c.toInt() in 65..122) out = out.plus(c) } }
    return out
}
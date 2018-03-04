import javafx.application.Application
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import tornadofx.*

class MyApp: App(MyView::class)

class MyView: View() {

        override var root = vbox {
            hbox {
                button("Press Me") {
                    action {
                        println("pressed!")
                    }
                }
                button("Press Me Too") {
                    action {
                        println("pressed too!")
                    }
                }

            }
        label("Waiting")
    }
}

fun main(args: Array<String>) {
    Application.launch(MyApp::class.java, *args)
}



import javafx.application.Application
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.VBox
import tornadofx.*
import java.util.*

class MyApp: App(MyView::class)

class MyView: View() {
        private val stack = Stack<String>()
        private val numbers = listOf("0","1","2", "3","4","5","6","7","8","9","0");
        private val operators = mapOf("+" to 1, "-" to 2, "*" to 2, "/" to 2 );
        private val lastPriority: Int get () {
            val result = stack.lastOrNull { operators.containsKey(it) }
            if(result != null)
            {
                return  operators.getValue(result)
            }
            return 0
        }
    private val lastValue: Int get () {
        val result = stack.lastOrNull { it.toIntOrNull() != null }
        if(result != null)
        {
            return  result.toInt();
        }
        return 0
    }

        fun calculate(first: Float, second: Float, op: String): Float
        {
            return when (op) {
                "+" -> first + second
                "-" -> second - first
                "/" -> second / first
                "*" -> second * first
                else -> {
                    throw IllegalArgumentException("Uknown operator")
                }
            }

        }

        fun resolve(buttonPressed : String)
        {
            if(numbers.contains(buttonPressed))
            {
                stack.push(buttonPressed)
            }
            else if (operators.containsKey(buttonPressed))
            {
                if(lastPriority == 0)
                {
                    stack.push(buttonPressed)
                }
                else if (operators.getValue(buttonPressed) <= lastPriority)
                {
                    recalculate()
                    stack.push(buttonPressed)
                }

                else {
                    stack.push(buttonPressed);
                }

            }
            else if(buttonPressed == "=")
            {
                recalculate()
            }
            else if(buttonPressed == ".")
            {
                stack.push(buttonPressed)
            }


            (root.children[0] as TextField).text = stack.toString()
        }

    private fun recalculate() {
        while (stack.size != 1) {
            var firstStr = "";
            while (!stack.empty() && !operators.containsKey(stack.peek())) {
                firstStr += stack.pop()
            }
            val op = stack.pop()
            var secondStr = "";
            while(!stack.empty() && !operators.containsKey(stack.peek()))
            {
                secondStr += stack.pop()
            }
            stack.push(calculate(firstStr.toFloat(), secondStr.toFloat(), op).toString())
        }
    }

    override var root = vbox {
            textfield {  }
            hbox {
                button("1") {
                    action {
                        resolve("1")
                    }
                }
                button("2") {
                    action {
                        resolve("2")
                    }
                }
                button("3") {
                    action {
                        resolve("3")
                    }
                }
                button("+") {
                    action {
                        resolve("+")
                    }
                }
            }
            hbox {
                button("4") {
                    action {
                        resolve("4")
                    }
                }
                button("5") {
                    action {
                        resolve("5")
                    }
                }
                button("6") {
                    action {
                        resolve("6")
                    }
                }
                button("*") {
                    action {
                        resolve("*")
                    }
                }
            }
            hbox {
                button("7") {
                    action {
                        resolve("7")
                    }
                }
                button("8") {
                    action {
                        resolve("8")
                    }
                }
                button("9") {
                    action {
                        resolve("9")
                    }
                }
                button("/") {
                    action {
                        resolve("/")
                    }
                }
            }
            hbox {
                button("0") {
                    action {
                        resolve("0")

                    }
                }
                button("-") {
                    action {
                        resolve("-")
                    }
                }
                button("=") {
                    action {
                        resolve("=")
                    }
                }
                button(".") {
                    action {
                        resolve(".")
                    }
                }

            }
    }
}

fun main(args: Array<String>) {
    Application.launch(MyApp::class.java, *args)
}



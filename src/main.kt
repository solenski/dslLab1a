import javafx.application.Application
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.VBox
import tornadofx.*
import java.util.*

class MyApp : App(MyView::class)

class MyView : View() {
    private var stack = Stack<String>()
    private val numbers = listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", ".")
    private val operators = mapOf("+" to 1, "-" to 1, "*" to 2, "/" to 2)

    private val lastPriority: Int
        get () {
            val result = stack.lastOrNull { operators.containsKey(it) }
            if (result != null) {
                return operators.getValue(result)
            }
            return 0
        }

    private val noNumber: Boolean get() {
        return !stack.any { value -> numbers.contains(value) }
    }

    private val noOp: Boolean get() {
        return !stack.any { value -> operators.contains(value) }
    }


    private val lastValue: Int
        get () {
            val result = stack.lastOrNull { it.toIntOrNull() != null }
            if (result != null) {
                return result.toInt()
            }
            return 0
        }

    fun calculate(first: Float, second: Float, op: String): Float {
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

    fun resolve(buttonPressed: String) {

        try {
            when {
                numbers.contains(buttonPressed) -> stack.push(buttonPressed)
                operators.containsKey(buttonPressed) -> when {
                    lastPriority == 0 -> stack.push(buttonPressed)
                    operators.getValue(buttonPressed) <= lastPriority -> {
                        recalculate()
                        stack.push(buttonPressed)
                    }
                    else -> stack.push(buttonPressed)
                }
                buttonPressed == "=" -> recalculate()
                buttonPressed == "(" -> stack.push(buttonPressed)
                buttonPressed == ")" -> recalculate()
            }
            updateUI()
        }
        catch (e : Exception) {

            (root.children[0] as TextField).text = "Error"
            stack.clear();
        }


    }

    private fun updateUI() {

        (root.children[0] as TextField).text = stack.toString().filter { item -> item != ',' && item != '[' && item != ']' && !item.isWhitespace() }
    }

    private fun recalculate() {
        var stack_copy = this.stack.clone() as Stack<String>
        while (stack.size != 1 && stack.peek() != "(") {
            var firstStr = ""
            while (!stack.empty() && !operators.containsKey(stack.peek()) && stack.peek() != "(") {
                firstStr += stack.pop()
            }
            if(!stack.empty() && stack.peek() == "-")
                firstStr += stack.pop()

            firstStr = firstStr.reversed()

            val op = stack.pop()
            if (!operators.containsKey(op)) {
                this.stack = stack_copy
                break
            }
            var secondStr = ""
            while (!stack.empty() && !operators.containsKey(stack.peek()) && stack.peek() != "(") {
                secondStr += stack.pop()
            }

            if(!stack.empty() && stack.peek() == "-")
                secondStr += stack.pop()

            secondStr = secondStr.reversed()

            if (!stack.empty() && stack.peek() == "(")
                stack.pop()

            if (stack.empty()) {
                calculate(firstStr.toFloat(), secondStr.toFloat(), op)
                        .toString()
                        .forEach { value -> stack.push(value.toString()) }
                break
            } else {
                calculate(firstStr.toFloat(), secondStr.toFloat(), op)
                        .toString()
                        .forEach { value -> stack.push(value.toString()) }
            }


        }


    }

    private fun clearStack() {
        stack.clear()
        updateUI()
    }

    override var root = vbox {
        textfield { isEditable = false }
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
            button("CR") {
                action {
                    clearStack()
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
            button("(") {
                action {
                    resolve("(")
                }
            }
            button(")") {
                action {
                    resolve(")")
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



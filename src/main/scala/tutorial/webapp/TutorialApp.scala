package tutorial.webapp
// import com.thoughtworks.binding.Binding
// import com.thoughtworks.binding.dom
// import scala.scalajs.js.timers.setInterval
// import java.util.Date

// import com.thoughtworks.binding.Binding._

// import com.thoughtworks.binding.Binding.Var
// import com.thoughtworks.binding.Binding.Vars
// import org.scalajs.dom.document
// import org.scalajs.dom.raw.Event
// import org.scalajs.dom._
import com.thoughtworks.binding.dom
import java.util.Date
import com.thoughtworks.binding.Binding.Var
import com.thoughtworks.binding.Binding.Vars
import com.thoughtworks.binding.Binding
import scala.scalajs.js.timers.setInterval
import scala.scalajs.js.timers.setTimeout
import org.scalajs.dom.raw.HTMLButtonElement
import org.scalajs.dom.raw.HTMLCanvasElement
import org.scalajs.dom.document
import org.scalajs.dom.raw.Event
import org.scalajs.dom.html
import scala.scalajs.js
import scala.scalajs.js.Dynamic.global
import scala.scalajs.js.annotation._


import tutorial.webapp.css.Button
@js.native
@JSGlobal("window.paper.Point")
class Point(val x: Int, val y: Int) extends js.Object {
  def add(r: Point): Point = js.native
}
@js.native
@JSGlobal("window.paper.Tool")
class Tool() extends js.Object {
  var onMouseDown: js.Function1[ToolEvent, Unit] = js.native  
  var onMouseDrag: js.Function1[ToolEvent, Unit] = js.native  

}

@js.native
trait ToolEvent extends js.Object {
  val `type`: String = js.native
  val point: Point = js.native
  val lastPoint: Point = js.native
  val downPoint: Point = js.native
  val middlePoint: Point = js.native
  val delta: Point = js.native
  val count: Int = js.native
  override def toString(): String = js.native
  val modifiers: js.Dynamic = js.native
}

@js.native
@JSGlobal("window.paper.Path")
class Path extends js.Object {
  var strokeColor: String = js.native
  def moveTo(p: Point): Unit = js.native
  def lineTo(p: Point): Unit = js.native
  def add(p: Point): Unit = js.native
}

class PaperData   {
  var path: Path = null
}

object TutorialApp {

  case class Contact(name: Var[String], email: Var[String])

  val data = Vars.empty[Contact]
  val now = Var(new Date )
  setInterval(1000) { now := new Date }
  val paperobj = new PaperData()
  @dom def randomParagraph = {
    <p>生成一个随机数： { math.random.toString }</p>
  }
  @dom def comment = {
    <!-- 你看不见我 -->
  }

  @dom def canvas = {
    var mycanvas = <canvas class="w-50 h-50"></canvas>
    setTimeout(1000) { makepaper(mycanvas) }
    mycanvas
  }
  @dom def drawbutton = {
    <button onclick={event: Event => makedraw()}>add</button>
  }

  @dom def table = {
    <div>
     现在时间：{ now.bind.toString } 哈哈
     { randomParagraph.bind }
      { comment.bind }
      {canvas.bind}
      {drawbutton.bind}
      <button
        class = {Button.rBtn}
        onclick={ event: Event =>
          
          data.get += Contact(Var("Yang Bo"), Var("yang.bo@rea-group.com"))
        }
      >
        Add a contact
      </button>
    </div>
    <table border="1" cellPadding="5">
      <thead>
        <tr>
          <th>Name</th>
          <th>E-mail</th>
          <th>Operation</th>
        </tr>
      </thead>
      <tbody>
        {
          for (contact <- data) yield {
            <tr>
              <td>
                {contact.name.each}
              </td>
              <td>
                {contact.email.each}
              </td>
              <td>
                <button
                  onclick={ event: Event =>
                    contact.name := "Modified Name"
                  }
                >
                  Modify the name
                </button>
              </td>
            </tr>
          }
        }
      </tbody>
    </table>
  }

  def main(args: Array[String]): Unit = {
    dom.render(document.body, table)
  }

  def mouseDown(event:ToolEvent): Unit = {
      paperobj.path = new Path()
			paperobj.path.strokeColor = "black"
			paperobj.path.add(event.point)
  }
  def mouseDrag(event:ToolEvent): Unit = {
    paperobj.path.add(event.point)
  }

  def makedraw(): Unit = {
    var tool = new Tool()

		// Define a mousedown and mousedrag handler
		tool.onMouseDown = mouseDown

		tool.onMouseDrag = mouseDrag
    var h = println("hello")
  }

  def makepaper(ca: html.Canvas): Unit = {
    global.window.paper.setup(ca)

    var path = new Path()
    paperobj.path = path
    // Give the stroke a color
    path.strokeColor = "black"
    var start = new Point(10, 10)
    // Move to start and draw a line from there
    path.moveTo(start)
    // Note that the plus operator on Point objects does not work
    // in JavaScript. Instead, we need to call the add() function:
    path.lineTo(start.add(new Point(100, 50)))
    // Draw the view now:
    global.window.paper.view.draw()

  }

}

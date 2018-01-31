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
import org.scalajs.dom.raw.HTMLButtonElement
import org.scalajs.dom.document
import org.scalajs.dom.raw.Event


import org.scalajs.dom.raw.HTMLButtonElement




object TutorialApp {
  
  case class Contact(name: Var[String], email: Var[String])

  val data = Vars.empty[Contact]
  val now = Var(new Date)
  setInterval(1000) { now := new Date }
  @dom def randomParagraph = {
    <p>生成一个随机数： { math.random.toString }</p>
  }
  @dom def comment = {
    <!-- 你看不见我 -->
  }
   
  @dom def table = {
    <div>
     现在时间：{ now.bind.toString } 哈哈
     { randomParagraph.bind }
      { comment.bind }
      <button
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
    println("Hello world!")
    dom.render(document.body, table)
  }
}

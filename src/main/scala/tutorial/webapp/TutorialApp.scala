package tutorial.webapp
import com.thoughtworks.binding.Binding
import com.thoughtworks.binding.dom

import com.thoughtworks.binding.Binding._

import com.thoughtworks.binding.Binding.Var
import com.thoughtworks.binding.Binding.Vars
import com.thoughtworks.binding.Binding.BindingSeq
import org.scalajs.dom.document
import org.scalajs.dom.raw.Event

object TutorialApp {
  
  case class Contact(name: Var[String], email: Var[String])

  val data = Vars.empty[Contact]

  @dom
  def table = {
    <div>
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

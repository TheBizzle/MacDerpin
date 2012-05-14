package derpin.boring

/**
 * Created by IntelliJ IDEA.
 * User: Jason
 * Date: 5/13/12
 * Time: 8:39 PM
 */

case class A(one: Int, two: Int, three: String) extends Boring

trait Boring {
  override def toString = "I'm boring!"
}

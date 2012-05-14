package derpin.mixerinner

import derpin.boring.A


/**
 * Created by IntelliJ IDEA.
 * User: Jason
 * Date: 5/13/12
 * Time: 9:37 PM
 */

object MixerInnerGenerator {
  def apply[T](a: A, typeHint: String) : A = {
    import MixerInnerMacros._
    typeHint match {
      case "B" => toB(a)
      case "C" => toC(a)
      case _   => toLonely(a)
    }
  }
}

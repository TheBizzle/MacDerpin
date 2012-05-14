package derpin.mixerinner

/**
 * Created by IntelliJ IDEA.
 * User: Jason
 * Date: 5/13/12
 * Time: 8:38 PM
 */

trait MixerInner {
  override def toString : String
}

trait Lonely extends MixerInner {
  override def toString = "I'm lonely!"
}

trait B extends MixerInner {
  override def toString = "I'm a B!"
}

trait C extends MixerInner {
  override def toString = "I'm a C!"
}

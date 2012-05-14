package derpin

import boring.A
import mixerinner.MixerInnerGenerator

/**
 * Created by IntelliJ IDEA.
 * User: Jason
 * Date: 5/13/12
 * Time: 8:21 PM
 */

/*

  Compiling is a mess right now...

  First pass: bin/scalac src/main/derpin/boring/Boring.scala src/main/derpin/mixerinner/MixerInner.scala src/main/derpin/mixerinner/MixerInnerMacros.scala
  --This builds the macros, but not any of the macro usages; including macro-using source will cause this to fail

  Second pass: find src/main | grep ".*\.scala" | xargs bin/scalac -Ymacro-debug-lite -Xmacro-fallback-classpath derpin
  --This builds all of the macro-using code; the `-Ymacro-debug-lite` flag is optional (since it just shows debugging output for macro expansion);
    the `-Xmacro-fallback-classpath` tells the compiler where to look for the output of our first pass

  Running it: bin/scala derpin.MyDerpin

  Runs:

  A
  I'm boring!
  I'm a B!
  I'm a C!
  I'm lonely!
  I'm lonely!

  B
  I'm boring!
  I'm a B!
  I'm a C!
  I'm lonely!
  I'm a B!

  C
  I'm boring!
  I'm a B!
  I'm a C!
  I'm lonely!
  I'm a C!

 */
object MyDerpin extends App {

  Console.print("Input, plox: ")
  val in = Console.readLine()

  // OMG, so customized!  WHAT WILL WE DO TO DYNAMICALLY MIX IN?!
  val a = A(1, 2, "three")
  println(a)

  // Whew!  Macros to the rescue!
  val awb = MixerInnerGenerator(a, "B")
  println(awb)

  val awc = MixerInnerGenerator(a, "C")
  println(awc)

  val awl = MixerInnerGenerator(a, "kjashfdkjahsf")
  println(awl)

  val awi = MixerInnerGenerator(a, in)
  println(awi)

}

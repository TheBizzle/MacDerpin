package derpin.mixerinner

import language.experimental.macros
import scala.reflect.makro.Context
import scala.reflect.api.Modifier._
import derpin.boring.A

/**
 * Created by IntelliJ IDEA.
 * User: Jason
 * Date: 5/13/12
 * Time: 8:34 PM
 */

object MixerInnerMacros {
  
  def toB(a: A) : A = macro mixerInnerImpl

  def toC(a: A) : A = macro mixerInnerImpl

  def toLonely(a: A) : A = macro mixerInnerImpl

  // See http://scalamacros.org/documentation.html for more info
  def mixerInnerImpl(context: Context)(a: context.Expr[A]) : context.Expr[A] = {
    
    import context.mirror._

    // HAX!
    val traitName = context.toString drop "macroContext(to".size takeWhile (_ != '@') mkString
    val mixerInnerPackageName = "derpin.mixerinner"
    val aVarName = "a"

    val aTag = context.typeTag[A]
    val aSymbol = aTag.tpe.typeSymbol
    if (!(aSymbol.modifiers contains `case`))
      context.abort(context.enclosingPosition, "to%s only accepts case classes; you provided a %s".format(traitName, aTag.tpe))

    val fields = aSymbol.typeSignature.members.filter { case m => (m.owner == aSymbol) && (!m.isMethod) }.toList.reverse
    val fieldNames = fields map { case field => nme.dropLocalSuffix(field.name) }

    val aParam = ValDef(Modifiers(Set(parameter, paramAccessor)), newTermName(aVarName), TypeTree(aSymbol.asType), EmptyTree)

    val superArgs = fieldNames map (Select(Ident(aParam.name), _))
    val body = Apply(Select(Super(This(newTypeName("")), newTypeName("")), nme.CONSTRUCTOR), superArgs)
    val constructor = DefDef(NoMods, nme.CONSTRUCTOR, Nil, List(List(aParam)), TypeTree(), Block(List(body), Literal(Constant(()))))
    val template = Template(List(Ident(aSymbol), Ident(staticClass("%s.%s".format(mixerInnerPackageName, traitName)))), emptyValDef, List(constructor))
    val classDef = ClassDef(NoMods, newTypeName(context.fresh("%s$%s".format(aSymbol.name, traitName))), Nil, template)

    val init = New(Ident(classDef.name), List(List(a.tree)))
    Expr(Block(classDef, init))

  }
  
}

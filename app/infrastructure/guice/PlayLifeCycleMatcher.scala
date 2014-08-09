package infrastructure.guice

import com.google.inject.matcher.{Matchers, AbstractMatcher}
import com.google.inject.TypeLiteral

/**
 *
 *
 * @author Lukasz Olczak
 */
object PlayLifeCycleMatcher extends AbstractMatcher[TypeLiteral[_]] {
  def matches(t: TypeLiteral[_]): Boolean = Matchers.subclassesOf(classOf[PlayLifeCycleListener]).matches(t.getRawType)
}

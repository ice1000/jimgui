module ice1000.jimgui.dsl {
  requires static org.jetbrains.annotations;

  requires kotlin.stdlib;
  requires static kotlin.stdlib.jdk7;
  requires static kotlin.stdlib.jdk8;

  requires ice1000.jimgui.dsl;

  exports org.ice1000.jimgui.dsl;
}

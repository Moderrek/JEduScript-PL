package pl.moderr.impactscript.interpreter.statements;

import pl.moderr.impactscript.interpreter.type.Value;

public interface Expression {
  Value evaluate() throws Exception;
}

package pl.moderr.eduscript.interpreter.statements;

import pl.moderr.eduscript.interpreter.ImpactEnvironment;
import pl.moderr.eduscript.interpreter.type.Value;

import java.io.Serializable;

public interface Expression extends Serializable {
  Value evaluate(ImpactEnvironment environment) throws Exception;
}

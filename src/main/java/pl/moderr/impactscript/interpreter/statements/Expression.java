package pl.moderr.impactscript.interpreter.statements;

import pl.moderr.impactscript.interpreter.ImpactEnvironment;
import pl.moderr.impactscript.interpreter.type.Value;

import java.io.Serializable;

public interface Expression extends Serializable {
  Value evaluate(ImpactEnvironment environment) throws Exception;
}

package pl.moderr.impactscript.interpreter;

import org.jetbrains.annotations.NotNull;
import pl.moderr.impactscript.interpreter.function.Function;
import pl.moderr.impactscript.interpreter.type.*;

import java.util.HashMap;

public class ImpactEnvironment {

  public final HashMap<String, Function<?>> functions;
  public final HashMap<String, Variable> variables;


  public ImpactEnvironment() {
    variables = new HashMap<>();
    functions = new HashMap<>();
  }

  public void defineFunction(String identifier, Function<?> function) throws Exception {
    if (functions.containsKey(identifier)) throw new Exception("Function " + identifier + " override");
    functions.put(identifier, function);
  }

  public void defineFunction(Function<?> function) throws Exception {
    defineFunction(function.getName(), function);
  }

  public boolean hasDefinedFunction(String identifier) {
    return functions.containsKey(identifier);
  }

  public Function<?> getFunction(String identifier) {
    return functions.get(identifier);
  }

  public void defineVariable(String identifier, boolean mutable) throws Exception {
    if (variables.containsKey(identifier)) throw new Exception("variable override");
    variables.put(identifier, new Variable(identifier, UnitType.empty(), mutable));
  }

  public void defineVariable(String identifier, boolean mutable, Value value) throws Exception {
    if (variables.containsKey(identifier)) throw new Exception("variable override");
    variables.put(identifier, new Variable(identifier, value, mutable));
  }

  public boolean hasDefinedVariable(String identifier) {
    return variables.containsKey(identifier);
  }

  public boolean hasDefinedVariable(@NotNull VariableIdentifier identifier) {
    return variables.containsKey(identifier.identifier());
  }

  public Variable getVariable(String identifier) {
    return variables.get(identifier);
  }

  public Variable getVariable(@NotNull VariableIdentifier identifier) {
    return variables.get(identifier.identifier());
  }


}

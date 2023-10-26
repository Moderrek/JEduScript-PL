package pl.moderr.eduscript.interpreter;

import org.jetbrains.annotations.NotNull;
import pl.moderr.eduscript.interpreter.function.Function;
import pl.moderr.eduscript.interpreter.type.UnitType;
import pl.moderr.eduscript.interpreter.type.Value;
import pl.moderr.eduscript.interpreter.type.VariableIdentifier;

import java.util.HashMap;
import java.util.Stack;

public final class ImpactEnvironment {

  private final HashMap<String, Function<?>> functions;
  private final HashMap<String, Variable> variables;
  private final Stack<String> heap;
  private String fileName;

  public ImpactEnvironment() {
    variables = new HashMap<>();
    functions = new HashMap<>();
    heap = new Stack<>();
    fileName = "<stdin>";
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

  public Stack<String> getHeap() {
    return heap;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
}

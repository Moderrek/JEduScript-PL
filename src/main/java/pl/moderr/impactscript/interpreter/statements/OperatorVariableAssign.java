package pl.moderr.impactscript.interpreter.statements;

import org.jetbrains.annotations.NotNull;
import pl.moderr.impactscript.interpreter.ImpactEnvironment;
import pl.moderr.impactscript.interpreter.Variable;
import pl.moderr.impactscript.interpreter.type.*;

public class OperatorVariableAssign implements Expression {

  private final VariableIdentifier id;
  private final TokenType op;
  private final Expression expr_right;

  public OperatorVariableAssign(VariableIdentifier id, TokenType op, Expression expr_right) {
    this.id = id;
    this.op = op;
    this.expr_right = expr_right;
  }

  @Override
  public Value evaluate(@NotNull ImpactEnvironment scope) throws Exception {
    if(!scope.hasDefinedVariable(id)) throw new Exception("Not defined variable " + id.identifier());
    Variable var = scope.getVariable(id);
    if(!var.isMutable()) throw new Exception("Variable " + id.identifier() + " is not mutable!");
    Value right = expr_right.evaluate(scope);
    if (var.getValue() instanceof IntegerType var_value) {
      if (right instanceof IntegerType change) {
        switch (op) {
          case ASSIGN_PLUS -> var.setValue(IntegerType.add(var_value, change));
        }
      }
    }
    return UnitType.empty();
  }
}

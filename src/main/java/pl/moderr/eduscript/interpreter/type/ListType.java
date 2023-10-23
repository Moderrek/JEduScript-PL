package pl.moderr.eduscript.interpreter.type;

import pl.moderr.eduscript.interpreter.ImpactEnvironment;
import pl.moderr.eduscript.interpreter.statements.Expression;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ListType extends Value {

  private final ValueType TYPE = ValueType.LIST;
  private final ArrayList<Expression> expressions;
  private final ArrayList<Value> value;

  public ListType() {
    expressions = new ArrayList<>();
    value = new ArrayList<>();
  }

  public ListType(List<Expression> values) {
    expressions = new ArrayList<>(values);
    value = new ArrayList<>();
  }

  public ListType(Expression... values) {
    expressions = new ArrayList<>(Arrays.asList(values));
    value = new ArrayList<>();
  }

  @Override
  public Value evaluate(ImpactEnvironment environment) throws Exception {
    value.clear();
    for (Expression expression : expressions) {
      value.add(expression.evaluate(environment));
    }
    return this;
  }

  public int getLength() {
    return value.size();
  }

  public void addElement(Value val) {
    expressions.add(val);
  }

  @Override
  public Value operatorPlus(Value right, ImpactEnvironment scope) throws Exception {
    List<Expression> newExpr = new ArrayList<>(expressions);
    newExpr.add(right);
    return new ListType(newExpr).evaluate(scope);
  }

  @Override
  public IntegerType operatorLength() {
    return IntegerType.of(value.size());
  }

  @Override
  public BoolType operatorContains(Value right, ImpactEnvironment scope) {
    boolean found = false;
    for (Value v : value) {
      try {
        if (v.operatorEqual(right, scope).toBool()) {
          found = true;
          break;
        }
      } catch (Exception ignored) {}
    }
    return BoolType.of(found);
  }

  @Override
  public ValueType getType() {
    return TYPE;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append('[');
    for (int i = 0; i < value.size(); i += 1) {
      Value item = value.get(i);
      if (i > 0) builder.append(", ");
      builder.append(item);
    }
    builder.append(']');
    return builder.toString();
  }

}

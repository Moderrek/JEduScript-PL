package pl.moderr.impactscript.interpreter.statements;

import pl.moderr.impactscript.interpreter.exception.SyntaxErr;
import pl.moderr.impactscript.interpreter.exception.TypeErr;
import pl.moderr.impactscript.interpreter.type.*;

import java.math.BigDecimal;

public class Operator implements Expression {

  private final Expression expr_left;
  private final Expression expr_right;
  private final char op;

  public Operator(Expression left, char op, Expression right) {
    this.expr_left = left;
    this.op = op;
    this.expr_right = right;
  }

  @Override
  public Value evaluate() throws Exception {

    Value e_left = expr_left.evaluate();
    Value e_right = expr_right.evaluate();

    if(e_left instanceof IntegerValue left) {
      // expected right value number
      if(e_right instanceof IntegerValue right) {
        return switch (op) {
          case '+' -> IntegerType.of(left.toLong() + right.toLong());
          case '-' -> IntegerType.of(left.toLong() - right.toLong());
          case '*' -> IntegerType.of(left.toLong() * right.toLong());
          case '/' -> IntegerType.of(left.toLong() / right.toLong());
          case '^' -> IntegerType.of((long)Math.pow((double) left.toLong(), (double) right.toLong()));
          case '%' -> new DecimalType(left.toLong() % right.toLong());
          default -> throw new SyntaxErr("Unexpected operator '" + op + "'");
        };
      }
      if(e_right instanceof DecimalValue right) {
        BigDecimal ldecimal = BigDecimal.valueOf(left.toLong());
        BigDecimal rdecimal = right.toDecimal();
        return switch (op) {
          case '+' -> DecimalType.of(ldecimal.add(rdecimal));
          case '-' -> DecimalType.of(ldecimal.subtract(rdecimal));
          case '*' -> DecimalType.of(ldecimal.multiply(rdecimal));
          case '/' -> DecimalType.of(BigDecimal.valueOf(left.toLong()).divide(BigDecimal.valueOf(right.toLong())));
          case '^' -> DecimalType.of(Math.pow(ldecimal.doubleValue(), rdecimal.doubleValue()));
          default -> throw new SyntaxErr("Unexpected operator '" + op + "'");
        };
      }
      throw new TypeErr("cannot perform algebra on another type '" + e_right.getTypeName() + "'");
    }
    // float + X
    if(e_left instanceof DecimalValue left) {
      // expected right value number
      if(e_right instanceof IntegerValue right) {
        BigDecimal ldecimal = left.toDecimal();
        BigDecimal rdecimal = BigDecimal.valueOf(right.toLong());
        return switch (op) {
          case '+' -> DecimalType.of(ldecimal.add(rdecimal));
          case '-' -> DecimalType.of(ldecimal.subtract(rdecimal));
          case '*' -> DecimalType.of(ldecimal.multiply(rdecimal));
          case '/' -> DecimalType.of(BigDecimal.valueOf(left.toLong()).divide(BigDecimal.valueOf(right.toLong())));
          case '^' -> DecimalType.of(Math.pow(ldecimal.doubleValue(), rdecimal.doubleValue()));
          default -> throw new SyntaxErr("Unexpected operator '" + op + "'");
        };
      }
      if(e_right instanceof DecimalValue right) {
        BigDecimal ldecimal = left.toDecimal();
        BigDecimal rdecimal = right.toDecimal();
        return switch (op) {
          case '+' -> DecimalType.of(ldecimal.add(rdecimal));
          case '-' -> DecimalType.of(ldecimal.subtract(rdecimal));
          case '*' -> DecimalType.of(ldecimal.multiply(rdecimal));
          case '/' -> DecimalType.of(BigDecimal.valueOf(left.toLong()).divide(BigDecimal.valueOf(right.toLong())));
          case '^' -> DecimalType.of(Math.pow(ldecimal.doubleValue(), rdecimal.doubleValue()));
          default -> throw new SyntaxErr("Unexpected operator '" + op + "'");
        };
      }
      throw new TypeErr("cannot perform algebra on another type '" + e_right.getTypeName() + "'");
    }
    if(e_left instanceof StringValue lval) {
      // expected right value string
      if(!(e_right instanceof StringValue rval)) {
        throw new TypeErr("cannot perform concat on another type '" + e_right.getTypeName() + "'");
      }
      return switch (op) {
        case '+' -> StringType.of(lval.toString() + rval.toString());
        default -> throw new IllegalStateException("Unexpected value: " + op);
      };
    }
    throw new Exception("Unknown operation " + e_left.getTypeName());
  }
}

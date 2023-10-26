package pl.moderr.eduscript.interpreter.statements;

import pl.moderr.eduscript.interpreter.ImpactEnvironment;
import pl.moderr.eduscript.interpreter.Variable;
import pl.moderr.eduscript.interpreter.exception.SyntaxErr;
import pl.moderr.eduscript.interpreter.exception.TypeErr;
import pl.moderr.eduscript.interpreter.type.*;

import java.math.BigDecimal;
import java.util.Objects;

public final class Operator implements Expression {

  private final Expression expr_left;
  private final Expression expr_right;
  private final TokenType op;

  public Operator(Expression left, TokenType op, Expression right) {
    this.expr_left = left;
    this.op = op;
    this.expr_right = right;
  }

  @Override
  public Value evaluate(ImpactEnvironment scope) throws Exception {
    Value e_left = expr_left.evaluate(scope);
    Value e_right = expr_right.evaluate(scope);
    if (e_left instanceof Variable variable_left) {
      e_left = variable_left.getValue();
    }
    if (e_right instanceof Variable variable) {
      e_right = variable.getValue();
    }

    try {
      switch (op) {
        case PLUS -> {
          return e_left.operatorPlus(e_right, scope);
        }
        case MINUS -> {
          return e_left.operatorMinus(e_right, scope);
        }
        case MULTIPLY -> {
          return e_left.operatorMultiply(e_right, scope);
        }
        case DIVIDE -> {
          return e_left.operatorDivide(e_right, scope);
        }
        case EQUAL -> {
          return e_left.operatorEqual(e_right, scope);
        }
        case NOT_EQUAL -> {
          return BoolType.negate(e_left.operatorEqual(e_right, scope));
        }
        case AND -> {
          return e_left.operatorAnd(e_right, scope);
        }
        case OR -> {
          return e_left.operatorOr(e_right, scope);
        }
        case CONTAINS -> {
          return e_left.operatorContains(e_right, scope);
        }
      };
    } catch (Exception ignored) {}

    System.out.println("UNSAFE");

    if(e_left instanceof BoolType bool_left) {
      if(e_right instanceof BoolType bool_right) {
        return switch (op) {
          case OR -> BoolType.of(bool_left.toBool() || bool_right.toBool());
          case AND, EQUAL -> BoolType.of(bool_left.toBool() && bool_right.toBool());
          case NOT_EQUAL -> BoolType.of(bool_left.toBool() != bool_right.toBool());
          default -> throw new SyntaxErr("Unexpected operator '" + op.name() + "'");
        };
      };
    }

    if(e_left instanceof IntegerType left) {
      // expected right value number
      if(e_right instanceof IntegerType right) {
        return switch (op) {
          case PLUS -> IntegerType.of(left.toLong() + right.toLong());
          case MINUS -> IntegerType.of(left.toLong() - right.toLong());
          case MULTIPLY -> IntegerType.of(left.toLong() * right.toLong());
          case DIVIDE -> IntegerType.of(left.toLong() / right.toLong());
          case POWER -> IntegerType.of((long)Math.pow((double) left.toLong(), (double) right.toLong()));
          case MODULO -> new DecimalType(left.toLong() % right.toLong());
          case EQUAL -> BoolType.of(left.toLong() == right.toLong());
          case NOT_EQUAL -> BoolType.of(left.toLong() != right.toLong());
          case GREATER -> BoolType.of(left.toLong() > right.toLong());
          case GREATER_EQUAL -> BoolType.of(left.toLong() >= right.toLong());
          case LESS -> BoolType.of(left.toLong() < right.toLong());
          case LESS_EQUAL -> BoolType.of(left.toLong() <= right.toLong());
          case DIVIDED -> BoolType.of(left.toLong() % right.toLong() == 0);
          default -> throw new SyntaxErr("Unexpected operator '" + op + "'");
        };
      }
      if(e_right instanceof DecimalType right) {
        BigDecimal ldecimal = BigDecimal.valueOf(left.toLong());
        BigDecimal rdecimal = right.toDecimal();
        return switch (op) {
          case PLUS -> DecimalType.of(ldecimal.add(rdecimal));
          case MINUS -> DecimalType.of(ldecimal.subtract(rdecimal));
          case MULTIPLY -> DecimalType.of(ldecimal.multiply(rdecimal));
          case DIVIDE -> DecimalType.of(BigDecimal.valueOf(left.toLong()).divide(BigDecimal.valueOf(right.toLong())));
          case POWER -> DecimalType.of(Math.pow(ldecimal.doubleValue(), rdecimal.doubleValue()));
          default -> throw new SyntaxErr("Unexpected operator '" + op + "'");
        };
      }
      throw new TypeErr("cannot perform algebra on another type '" + e_right.getTypeName() + "'");
    }
    // float + X
    if(e_left instanceof DecimalType left) {
      // expected right value number
      if(e_right instanceof IntegerType right) {
        BigDecimal ldecimal = left.toDecimal();
        BigDecimal rdecimal = BigDecimal.valueOf(right.toLong());
        return switch (op) {
          case PLUS -> DecimalType.of(ldecimal.add(rdecimal));
          case MINUS -> DecimalType.of(ldecimal.subtract(rdecimal));
          case MULTIPLY -> DecimalType.of(ldecimal.multiply(rdecimal));
          case DIVIDE -> DecimalType.of(BigDecimal.valueOf(left.toLong()).divide(BigDecimal.valueOf(right.toLong())));
          case POWER -> DecimalType.of(Math.pow(ldecimal.doubleValue(), rdecimal.doubleValue()));
          case EQUAL -> BoolType.of(left.toLong() == right.toLong());
          case NOT_EQUAL -> BoolType.of(left.toLong() != right.toLong());
          case GREATER -> BoolType.of(left.toLong() > right.toLong());
          case GREATER_EQUAL -> BoolType.of(left.toLong() >= right.toLong());
          case LESS -> BoolType.of(left.toLong() < right.toLong());
          case LESS_EQUAL -> BoolType.of(left.toLong() <= right.toLong());
          default -> throw new SyntaxErr("Unexpected operator '" + op + "'");
        };
      }
      if(e_right instanceof DecimalType right) {
        BigDecimal ldecimal = left.toDecimal();
        BigDecimal rdecimal = right.toDecimal();
        return switch (op) {
          case PLUS -> DecimalType.of(ldecimal.add(rdecimal));
          case MINUS -> DecimalType.of(ldecimal.subtract(rdecimal));
          case MULTIPLY -> DecimalType.of(ldecimal.multiply(rdecimal));
          case DIVIDE -> DecimalType.of(BigDecimal.valueOf(left.toLong()).divide(BigDecimal.valueOf(right.toLong())));
          case POWER -> DecimalType.of(Math.pow(ldecimal.doubleValue(), rdecimal.doubleValue()));
          case EQUAL -> BoolType.of(left.toLong() == right.toLong());
          case NOT_EQUAL -> BoolType.of(left.toLong() != right.toLong());
          case GREATER -> BoolType.of(left.toLong() > right.toLong());
          case GREATER_EQUAL -> BoolType.of(left.toLong() >= right.toLong());
          case LESS -> BoolType.of(left.toLong() < right.toLong());
          case LESS_EQUAL -> BoolType.of(left.toLong() <= right.toLong());
          default -> throw new SyntaxErr("Unexpected operator '" + op + "'");
        };
      }
      throw new TypeErr("cannot perform algebra on another type '" + e_right.getTypeName() + "'");
    }
    if(e_left instanceof StringType lval) {
      // expected right value string
      if(e_right instanceof StringType rval) {
        return switch (op) {
          case PLUS -> StringType.of(lval.toString() + rval.toString());
          case EQUAL -> BoolType.of(Objects.equals(lval.toString(), rval.toString()));
          case NOT_EQUAL -> BoolType.of(!Objects.equals(lval.toString(), rval.toString()));
          default -> throw new IllegalStateException("Unexpected value: " + op);
        };
      }
      if(e_right instanceof IntegerType rval) {
        return switch (op) {
          case PLUS -> StringType.of(lval.toString() + rval.toString());
          default -> throw new IllegalStateException("Unexpected value: " + op);
        };
      }
      if(e_right instanceof DecimalType rval) {
        return switch (op) {
          case PLUS -> StringType.of(lval.toString() + rval.toString());
          default -> throw new IllegalStateException("Unexpected value: " + op);
        };
      }
      if(e_right instanceof BoolType rval) {
        return switch (op) {
          case PLUS -> StringType.of(lval.toString() + rval.toString());
          default -> throw new IllegalStateException("Unexpected value: " + op);
        };
      }
      throw new TypeErr("cannot perform concat on another type '" + e_right.getTypeName() + "'");
    }
    throw new Exception("Unknown operation " + op.name() + " on type '" + e_left.getTypeName() + "'");
  }
}

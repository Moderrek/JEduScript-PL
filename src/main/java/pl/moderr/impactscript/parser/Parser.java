package pl.moderr.impactscript.parser;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import pl.moderr.impactscript.interpreter.exception.SyntaxErr;
import pl.moderr.impactscript.interpreter.exception.TypeErr;
import pl.moderr.impactscript.interpreter.function.FunctionCall;
import pl.moderr.impactscript.interpreter.statements.*;
import pl.moderr.impactscript.interpreter.type.*;

import java.util.ArrayList;
import java.util.List;

public class Parser {

  private final ArrayList<Token> tokens;
  int pos = 0;

  public Parser(ArrayList<Token> tokens) {
    this.tokens = tokens;
  }

  private Token currentToken() {
    return tokens.get(pos);
  }

  private Token lookBack(int offset) {
    return tokens.get(pos - offset);
  }

  private Token lookForward(int offset) {
    return tokens.get(pos + offset);
  }

  private Token nextToken() {
    return lookForward(1);
  }

  private Token previousToken() {
    return lookBack(1);
  }

  private Token consume(TokenType token) throws Exception {
    if (currentToken().type() != token) throw new SyntaxErr("expected " + token.name() + ", got " + currentToken().type());
    return tokens.get(pos++);
  }

  private boolean match(TokenType type) {
    if (currentToken().type() != type) return false;
    pos += 1;
    return true;
  }

  private boolean match(TokenType type, TokenType nextType) {
    if (currentToken().type() != type || nextToken().type() != nextType) return false;
    pos += 2;
    return true;
  }

  private boolean matchOperator() {
    return match(TokenType.PLUS)
        || match(TokenType.MINUS)
        || match(TokenType.MULTIPLY)
        || match(TokenType.DIVIDE)
        || match(TokenType.MODULO)
        || match(TokenType.POWER)
        || match(TokenType.EQUAL)
        || match(TokenType.GREATER_EQUAL)
        || match(TokenType.LESS_EQUAL)
        || match(TokenType.GREATER)
        || match(TokenType.LESS)
        || match(TokenType.NOT_EQUAL)
        || match(TokenType.AND)
        || match(TokenType.OR)
        || match(TokenType.DIVIDED);
  }

  private boolean matchAssignOperator() {
    return match(TokenType.ASSIGN_PLUS)
        || match(TokenType.ASSIGN_MINUS)
        || match(TokenType.ASSIGN_MULTIPLY)
        || match(TokenType.ASSIGN_DIVIDE)
        || match(TokenType.ASSIGN_MODULO)
        || match(TokenType.ASSIGN_POWER);
  }


  private @NotNull Expression matchFunctionCall() throws Exception {
//    if (match(TokenType.LPARA)) consume(TokenType.LPARA);
    // Prepare function call
    Token name = lookBack(2);
    FunctionCall functionCall;
    // Set function exec
    functionCall = new FunctionCall(name);
    // Add arguments
    boolean haveArguments = false;
    while (!match(TokenType.RPARA)) {
      haveArguments = true;
      functionCall.addArgument(op());
      if(!match(TokenType.SEPARATOR)) break;
    }
    if(haveArguments) consume(TokenType.RPARA);
    // Call Function
    return functionCall;
  }

  @Contract(" -> new")
  private @NotNull Expression getValue() throws Exception {
    // is int or float
    if (match(TokenType.DECIMAL)) {
      return DecimalType.of(lookBack(1));
    }
    if(match(TokenType.INTEGER)) {
      return IntegerType.of(lookBack(1));
    }
    // is bool
    if(match(TokenType.TRUE)) {
      return BoolType.of(true);
    }
    if(match(TokenType.FALSE)) {
      return BoolType.of(false);
    }
    // is str
    if(match(TokenType.STRING)) {
      return StringType.of(lookBack(1).value());
    }
    // is parenthesis
    if (match(TokenType.LPARA)) {
      Expression expression = op();
      consume(TokenType.RPARA);
      return expression;
    }
    // is function
    if (match(TokenType.IDENTIFIER, TokenType.LPARA)) {
      return matchFunctionCall();
    }
    // is variable assign
    if (match(TokenType.IDENTIFIER, TokenType.ASSIGN)) {
      return new VariableAssign(new VariableIdentifier(lookBack(2).value()), op(), true);
    }
    // is variable assign operator
    if (match(TokenType.IDENTIFIER)) {
      if (matchAssignOperator())
        return new OperatorVariableAssign(new VariableIdentifier(lookBack(2).value()), lookBack(1).type(), op());
      pos -= 1;
    }
    // is variable
    if (match(TokenType.IDENTIFIER)) {
      return new VariableIdentifier(lookBack(1).value());
    }
    // is if
    if (match(TokenType.IF)) {
      Expression condition = op();
      if (!match(TokenType.LCURLY)) throw new Exception("1");
      ExpressionBlock block = block();
      if (!match(TokenType.RCURLY)) throw new Exception("1");
      return new IfStatement(condition, block);
    }
    // block
    if (match(TokenType.LCURLY)) {
      ExpressionBlock block = block();
      if (!match(TokenType.RCURLY)) throw new Exception("1");
      return block;
    }
    // unknown
    throw new TypeErr("expected value, got " + currentToken().type() + " '" + currentToken().value() + "' @ " + (currentToken().startPos().toString()));
  }

  private ExpressionBlock block() throws Exception {
    List<Expression> expressionList = new ArrayList<>();
    while(currentToken().type() != TokenType.RCURLY) {
      match(TokenType.END);
      expressionList.add(op());
      match(TokenType.END);
    }
    return new ExpressionBlock(expressionList);
  }

  private Expression op() throws Exception {
    Expression left;
    if (match(TokenType.MINUS) || match(TokenType.PLUS)) {
      pos -= 1;
      left = IntegerType.of(0);
      // -a -> 0-a
      // -50 (- & number) -> 0-50 (operator) -> -50 (number)
    } else {
      left = getValue();
    }
    while (matchOperator()) {
      TokenType op = previousToken().type();
      Expression right = getValue();
      left = new Operator(left, op, right);
    }
    return left;
  }

  public ArrayList<Expression> parseString() throws Exception {
    ArrayList<Expression> expressions = new ArrayList<>();
    if (currentToken().type() == TokenType.END || currentToken().type() == TokenType.FILE_END) return expressions;
    while (currentToken().type() != TokenType.FILE_END) {
      expressions.add(op());
      match(TokenType.END);
    }
    if(currentToken().type() != TokenType.END && currentToken().type() != TokenType.FILE_END) throw new SyntaxErr("cannot reach end, got " + currentToken().type());
    return expressions;
  }
  public Expression parseLine() throws Exception {
    if (currentToken().type() == TokenType.END || currentToken().type() == TokenType.FILE_END) return null;
    Expression expression = op();
    if(currentToken().type() != TokenType.END && currentToken().type() != TokenType.FILE_END ) throw new SyntaxErr("cannot reach end, got " + currentToken().type());
    return expression;
  }

}

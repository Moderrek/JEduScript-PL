package pl.moderr.impactscript.interpreter;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import pl.moderr.impactscript.interpreter.exception.NameErr;
import pl.moderr.impactscript.interpreter.exception.SyntaxErr;
import pl.moderr.impactscript.interpreter.exception.TypeErr;
import pl.moderr.impactscript.interpreter.function.Function;
import pl.moderr.impactscript.interpreter.function.FunctionCall;
import pl.moderr.impactscript.interpreter.statements.Expression;
import pl.moderr.impactscript.interpreter.statements.Operator;
import pl.moderr.impactscript.interpreter.type.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Parser {

  private final ArrayList<Token> tokens;
  public HashMap<String, Function<?>> functions = new HashMap<>();
  int pos = 0;
  private final boolean debugMode;

  public Parser(ArrayList<Token> tokens) {
    this.tokens = tokens;
    this.debugMode = false;
  }

  public Parser(ArrayList<Token> tokens, boolean debugMode) {
    this.tokens = tokens;
    this.debugMode = debugMode;
  }

  public void defineFunction(Function<?> function) {
    functions.put(function.getName(), function);
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
        || match(TokenType.POWER);
  }

  public Value callFunction(String name) throws Exception {
    return functions.get(name).preInvoke(this, new ArrayList<>());
  }
  public Value callFunction(String name, Expression... args) throws Exception {
    ArrayList<Expression> arguments = new ArrayList<>();
    Collections.addAll(arguments, args);
    return functions.get(name).preInvoke(this, arguments);
  }

  private @NotNull Expression matchFunctionCall() throws Exception {
    if (match(TokenType.LPARA)) consume(TokenType.LPARA);
    // Prepare function call
    String name = lookBack(2).value();
    FunctionCall functionCall;
    // Set function exec
    if (isFunctionDefined(name)) functionCall = new FunctionCall(this,  functions.get(name));
    else throw new NameErr("unknown function '" + name + "'");
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
    // unknown
    throw new TypeErr("expected value, got " + currentToken().type() + " '" + currentToken().value() + "' @ " + (currentToken().startPos()+1) + " col");
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
      char op = previousToken().value().charAt(0);
      Expression right = getValue();
      left = new Operator(left, op, right);
    }
    return left;
  }

  public String parse() throws Exception {
    if (currentToken().type() == TokenType.END) return "0";
    String result = op().evaluate().toString();
    if(currentToken().type() != TokenType.END) throw new SyntaxErr("cannot reach end");
    return result;
  }

  public boolean isFunctionDefined(String name) {
    return functions.containsKey(name);
  }

}

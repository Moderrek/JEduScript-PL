package pl.moderr.impactscript.interpreter;

import org.jetbrains.annotations.NotNull;
import pl.moderr.impactscript.interpreter.exception.SyntaxErr;
import pl.moderr.impactscript.interpreter.type.Token;
import pl.moderr.impactscript.interpreter.type.TokenType;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class Lexer {

  private final Set<Character> operators = Set.of('+', '-', '*', '/', '%', '^', '(', ')', ',');
  private final Map<Character, TokenType> operators_type = Map.of(
      '+', TokenType.PLUS,
      '-', TokenType.MINUS,
      '*', TokenType.MULTIPLY,
      '/', TokenType.DIVIDE,
      '%', TokenType.MODULO,
      '^', TokenType.POWER,
      '(', TokenType.LPARA,
      ')', TokenType.RPARA,
      ',', TokenType.SEPARATOR
  );
  private final Map<String, TokenType> keywords = Map.of(
      "true", TokenType.TRUE,
      "false", TokenType.FALSE
  );

  String input;
  ArrayList<Token> tokens;
  int pos;
  boolean debugMode;

  public ArrayList<Token> lex(@NotNull String input, boolean debugMode) throws Exception {
    this.input = input;
    tokens = new ArrayList<>();
    pos = 0;
    this.debugMode = debugMode;

    while (pos < input.length()) {
      // ignore white space
      if (isWhiteSymbol()) {
        nextSymbol();
        continue;
      }
      // detects int and float
      if (isDigit()) {
        StringBuilder buffer = new StringBuilder();
        int start = pos;
        boolean isDecimal = false; // is float?
        while (isNotEnd(input.length()) && isDigitOrDot()) {
          char decimal = currentSymbolNext();
          if (decimal == '.') {
            if (isDecimal) throw new SyntaxErr("Invalid decimal number!");
            isDecimal = true;
          }
          buffer.append(decimal);
        }
        registerNumber(buffer.toString(), isDecimal, start);
        continue;
      }
      // identifier or keyword
      if (isLetter()) {
        StringBuilder buffer = new StringBuilder();
        int start = pos;
        while (isNotEnd(input.length()) && isLetterOrDigit()) buffer.append(currentSymbolNext());
        String name = buffer.toString();
        // if is keyword, register
        if(isKeyword(name)) {
          registerKeyword(name, start);
          continue;
        }
        // is name, register identifier
        registerIdentifier(buffer.toString(), start);
        continue;
      }
      // string
      if (currentSymbol() == '"') {
        StringBuilder buffer = new StringBuilder();
        int start = pos;
        boolean endFound = false;
        while (isNotEnd(input.length())) {
          pos += 1;
          if(isEnd()) break;
          if(currentSymbol() == '"') {
            endFound = true;
            pos += 1;
            break;
          }
          buffer.append(currentSymbol());
        }
        if(!endFound) throw new SyntaxErr("cannot reach end of string");
        registerString(buffer.toString(), start);
        continue;
      }
      // operators
      if(isOperator()) {
        registerOperator();
        currentSymbolNext();
        continue;
      }
      throw new SyntaxErr("Unknown symbol '" + currentSymbol() + "' at " + (pos + 1) + " col");
    }
    end();
    return tokens;
  }

  private void debug(Object object) {
    if(!debugMode) return;
    System.out.println(object);
  }

  private boolean isKeyword(String name) {
    return keywords.containsKey(name);
  }

  private char currentSymbol() {
    return input.charAt(pos);
  }

  private char nextSymbol() {
    return input.charAt(++pos);
  }

  private char currentSymbolNext() {
    return input.charAt(pos++);
  }

  private boolean isOperator() {
    return operators.contains(currentSymbol());
  }

  private void registerOperator() throws Exception {
    char operator = currentSymbol();
    if (!isOperator()) throw new Exception("");
    TokenType operator_type = operators_type.get(operator);
    registerToken(new Token(operator_type, String.valueOf(operator), pos, 1));
  }

  private void registerToken(Token token) {
    debug(token);
    tokens.add(token);
  }

  private void registerNumber(String number, boolean isDecimal, int start) {
    if(isDecimal) {
      registerToken(new Token(TokenType.DECIMAL, number, start, number.length()));
      return;
    }
    registerToken(new Token(TokenType.INTEGER, number, start, number.length()));
  }

  private void registerIdentifier(String identifier, int start) {
    registerToken(new Token(TokenType.IDENTIFIER, identifier, start, identifier.length()));
  }

  private void registerString(String content, int start) {
    registerToken(new Token(TokenType.STRING, content, start, content.length()));
  }

  private void registerKeyword(String keyword, int start) {
    registerToken(new Token(keywords.get(keyword), keyword, start, keyword.length()));
  }

  private void end() {
    registerToken(new Token(TokenType.END, "\0", pos, 0));
  }

  private boolean isEnd() {
    return pos >= input.length();
  }

  private boolean isNextEnd() {
    return pos +1 >= input.length();
  }

  public boolean isWhiteSymbol() {
    char symbol = currentSymbol();
    return Character.isWhitespace(symbol);
  }

  public boolean isDigit() {
    return Character.isDigit(currentSymbol());
  }

  public boolean isLetter() {
    return Character.isLetter(currentSymbol());
  }

  public boolean isLetterOrDigit() {
    return Character.isLetterOrDigit(currentSymbol());
  }

  public boolean isNotEnd(int length) {
    return pos < length;
  }

  public boolean isDigitOrDot() {
    char symbol = currentSymbol();
    return Character.isDigit(symbol) || symbol == '.';
  }

}

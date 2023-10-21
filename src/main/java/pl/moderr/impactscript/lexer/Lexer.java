package pl.moderr.impactscript.lexer;

import org.jetbrains.annotations.NotNull;
import pl.moderr.impactscript.interpreter.exception.LexerErr;
import pl.moderr.impactscript.interpreter.exception.SyntaxErr;
import pl.moderr.impactscript.interpreter.type.Token;
import pl.moderr.impactscript.interpreter.type.TokenType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Lexer {

  private final Set<String> operators;
  private final Map<String, TokenType> operators_type;

  private final Map<Character, TokenType> short_operators = Map.of(
      '(', TokenType.LPARA,
      ')', TokenType.RPARA,
      '{', TokenType.LCURLY,
      '}', TokenType.RCURLY,
      ',', TokenType.SEPARATOR
  );

  public Lexer(String fileName) {
    this.fileName = fileName;
    operators_type = new HashMap<>();
    operators_type.put("+", TokenType.PLUS);
    operators_type.put("+=", TokenType.ASSIGN_PLUS);
    operators_type.put("-", TokenType.MINUS);
    operators_type.put("-=", TokenType.ASSIGN_MINUS);
    operators_type.put("*", TokenType.MULTIPLY);
    operators_type.put("*=", TokenType.ASSIGN_MULTIPLY);
    operators_type.put("/", TokenType.DIVIDE);
    operators_type.put("/=", TokenType.ASSIGN_DIVIDE);
    operators_type.put("%", TokenType.MODULO);
    operators_type.put("%=", TokenType.ASSIGN_MODULO);
    operators_type.put("^", TokenType.POWER);
    operators_type.put("^=", TokenType.ASSIGN_POWER);
    operators_type.put("=", TokenType.ASSIGN);
    operators_type.put("==", TokenType.EQUAL);
    operators_type.put("rowne", TokenType.EQUAL);
    operators_type.put("!=", TokenType.NOT_EQUAL);
    operators_type.put("rozne", TokenType.NOT_EQUAL);
    operators_type.put("~=", TokenType.NOT_EQUAL);
    operators_type.put("~", TokenType.NEGATE);
    operators_type.put("!", TokenType.NEGATE);
    operators_type.put(">", TokenType.GREATER);
    operators_type.put("wieksze", TokenType.GREATER);
    operators_type.put(">=", TokenType.GREATER_EQUAL);
    operators_type.put("<", TokenType.LESS);
    operators_type.put("mniejsze", TokenType.LESS);
    operators_type.put("<=", TokenType.LESS_EQUAL);
    operators = operators_type.keySet();
  }
  private final Map<String, TokenType> keywords = Map.of(
      "prawda", TokenType.TRUE,
      "falsz", TokenType.FALSE,
      "rowne", TokenType.EQUAL,
      "rozne", TokenType.NOT_EQUAL,
      "wieksze", TokenType.GREATER,
      "mniejsze", TokenType.LESS,
      "jezeli", TokenType.IF,
      "i", TokenType.AND,
      "lub", TokenType.OR,
      "podzielne", TokenType.DIVIDED
  );

  String input;
  ArrayList<Token> tokens;
//  int pos;
  Position pos;
  boolean debugMode;
  String fileName;

  public ArrayList<Token> lex(@NotNull String input, boolean debugMode) throws Exception {
    this.input = input;
    tokens = new ArrayList<>();
    pos = new Position();
    this.debugMode = debugMode;
    ArrayList<String> lines = input.lines().collect(Collectors.toCollection(ArrayList::new));

    while (pos.getIndex() < input.length()) {
      if(currentSymbol() == '\n' || currentSymbol() == ';') {
        if (!tokens.isEmpty() && tokens.get(tokens.size()-1).type() != TokenType.END) registerToken(new Token(TokenType.END, "end of line", pos, 0));
        pos.advance(currentSymbol());
        continue;
      }
      if(currentSymbol() == '#') {
        while (pos.getIndex() < input.length() && currentSymbol() != '\n') {
          pos.advance(currentSymbol());
        }
        continue;
      }
      // ignore white space
      if (isWhiteSymbol()) {
        pos.advance(currentSymbol());
        continue;
      }
      // detects int and float
      if (isDigit()) {
        StringBuilder buffer = new StringBuilder();
        Position start = pos.clone();
        boolean isDecimal = false; // is float?
        while (isNotEnd(input.length()) && isDigitOrDot()) {
          char decimal = currentSymbolNext();
          if (decimal == '.') {
            if (isDecimal) throw new LexerErr(fileName, lines.get(start.getRow()), start, buffer.length(), "Invalid decimal number!");
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
        Position start = pos.clone();
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
        Position start = pos.clone();
        boolean endFound = false;
        while (isNotEnd(input.length())) {
          pos.advance(currentSymbol());
          if(isEnd()) break;
          if(currentSymbol() == '"') {
            endFound = true;
            pos.advance(currentSymbol());
            break;
          }
          buffer.append(currentSymbol());
        }
        if(!endFound) throw new LexerErr(fileName, lines.get(start.getRow()), start, buffer.length(), "Cannot reach end of string!");
        registerString(buffer.toString(), start);
        continue;
      }
      if(short_operators.containsKey(currentSymbol())) {
        char c = currentSymbol();
        TokenType type = short_operators.get(c);
        pos.advance(c);
        registerToken(new Token(type, String.valueOf(c), pos.clone(), 1));
        continue;
      }
      // others
      StringBuilder buffer = new StringBuilder();
      Position start = pos.clone();
      while (pos.getIndex() < input.length() && !isWhiteSymbol()) {
        char c = currentSymbol();
        pos.advance(c);
        buffer.append(c);
      }
      String symbol = buffer.toString();
      if (operators.contains(buffer.toString())) {
        TokenType type = operators_type.get(symbol);
        registerToken(new Token(type, symbol, start, symbol.length()));
        continue;
      }
      throw new LexerErr(fileName, lines.get(start.getRow()), start, buffer.length(), "Unknown symbol '" + symbol + "'");
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
    return input.charAt(pos.getIndex());
  }

  private char nextSymbol() {
    char c = input.charAt(pos.getIndex()+1);
    pos.advance(c);
    return c;
  }

  private char currentSymbolNext() {
    char c = input.charAt(pos.getIndex());
    pos.advance(c);
    return c;
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

  private void registerNumber(String number, boolean isDecimal, Position start) {
    if(isDecimal) {
      registerToken(new Token(TokenType.DECIMAL, number, start, number.length()));
      return;
    }
    registerToken(new Token(TokenType.INTEGER, number, start, number.length()));
  }

  private void registerIdentifier(String identifier, Position start) {
    registerToken(new Token(TokenType.IDENTIFIER, identifier, start, identifier.length()));
  }

  private void registerString(String content, Position start) {
    registerToken(new Token(TokenType.STRING, content, start, content.length()));
  }

  private void registerKeyword(String keyword, Position start) {
    registerToken(new Token(keywords.get(keyword), keyword, start, keyword.length()));
  }

  private void end() {
    registerToken(new Token(TokenType.FILE_END, "end of file", pos, 0));
  }

  private boolean isEnd() {
    return pos.getIndex() >= input.length();
  }

  private boolean isNextEnd() {
    return pos.getIndex()+1 >= input.length();
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
    return pos.getIndex() < length;
  }

  public boolean isDigitOrDot() {
    char symbol = currentSymbol();
    return Character.isDigit(symbol) || symbol == '.';
  }

}

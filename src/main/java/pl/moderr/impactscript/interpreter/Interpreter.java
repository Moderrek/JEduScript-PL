package pl.moderr.impactscript.interpreter;

import pl.moderr.impactscript.interpreter.function.ConstFunction;;
import pl.moderr.impactscript.interpreter.library.function.*;
import pl.moderr.impactscript.interpreter.type.Token;

import java.util.ArrayList;

public class Interpreter {

  public boolean debugMode = false;

  public String interpret(ArrayList<Token> tokens) throws Exception {
    Parser parser = new Parser(tokens, debugMode);
    // Function Native Library
    parser.defineFunction(new ConstFunction("pi", Math.PI));
    parser.defineFunction(new ConstFunction("e", Math.E));
    parser.defineFunction(new PowFunction());
    parser.defineFunction(new SqrtFunction());
    parser.defineFunction(new RoundFunction());
    parser.defineFunction(new SqrtFunction());
    parser.defineFunction(new Log2Function());
    parser.defineFunction(new LogFunction());
    parser.defineFunction(new RadFunction());
    parser.defineFunction(new AbsFunction());
    parser.defineFunction(new SinFunction());
    parser.defineFunction(new CosFunction());
    parser.defineFunction(new TanFunction());
    parser.defineFunction(new FibFunction());
    parser.defineFunction(new FacFunction());
    parser.defineFunction(new BoolFunction());
    parser.defineFunction(new AndFunction());
    parser.defineFunction(new OrFunction());
    parser.defineFunction(new TypeofFunction());
    parser.defineFunction(new StrFunction());
    parser.defineFunction(new LenFunction());
    parser.defineFunction(new NegateFunction());
    parser.defineFunction(new IntFunction());
    parser.defineFunction(new BinFunction());
    parser.defineFunction(new FloatFunction());

    return parser.parse();
  }

  public ArrayList<Token> tokenize(String statement) throws Exception {
    Lexer lexer = new Lexer();
    return lexer.lex(statement, debugMode);
  }
}

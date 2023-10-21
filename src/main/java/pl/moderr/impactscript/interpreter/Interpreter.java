package pl.moderr.impactscript.interpreter;

import org.jetbrains.annotations.NotNull;
import pl.moderr.impactscript.interpreter.function.ConstFunction;
import pl.moderr.impactscript.interpreter.statements.Expression;
import pl.moderr.impactscript.lib.function.*;

import java.util.List;

public class Interpreter {

  ImpactEnvironment environment;

  public Interpreter() throws Exception {
    environment = new ImpactEnvironment();
    environment.defineFunction(new ConstFunction("pi", Math.PI));
    environment.defineFunction(new ConstFunction("e", Math.E));
    environment.defineFunction(new PotegaFunction());
    environment.defineFunction(new PierwiastekFunction());
    environment.defineFunction(new ZaokrFunction());
    environment.defineFunction(new Log2Function());
    environment.defineFunction(new LogFunction());
    environment.defineFunction(new RadFunction());
    environment.defineFunction(new AbsFunction());
    environment.defineFunction(new SinFunction());
    environment.defineFunction(new CosFunction());
    environment.defineFunction(new TanFunction());
    environment.defineFunction(new FibFunction());
    environment.defineFunction(new FacFunction());
    environment.defineFunction(new LogiFunction());
    environment.defineFunction(new AndFunction());
    environment.defineFunction(new LubFunction());
    environment.defineFunction(new TypFunction());
    environment.defineFunction(new TekstFunction());
    environment.defineFunction(new LenFunction());
    environment.defineFunction(new ZaprzeczFunction());
    environment.defineFunction(new CalkFunction());
    environment.defineFunction(new BinFunction());
    environment.defineFunction(new FloatFunction());
    environment.defineFunction(new WypiszFunction());
    environment.defineFunction(new WpiszFunction());
  }

  public void interpret(@NotNull List<Expression> expressions) throws Exception {
    for (Expression expr : expressions) {
      expr.evaluate(environment);
    }
  }

}

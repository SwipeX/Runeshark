package bot.script;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ScriptManifest {

	String name();

	String[] authors();

	String category();

	String description() default "";

	double version() default 1.0;

}
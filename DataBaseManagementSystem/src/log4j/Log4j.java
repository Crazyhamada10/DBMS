package log4j;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;

public class Log4j {
	
	private static Log4j instance;
	private Logger log;
	private String path;
	
	private Log4j() {
		path = new String(System.getProperty("user.home"));
	}

	public static Log4j getInstance() {
		if (instance == null) {
			instance = new Log4j();
		}
		return instance;
	}

	public void setPath(String path) {
		this.path = new String(path);
	}
	
	public String getPath() {
		return path;
	}
	
	private LoggerContext define() {
		ConfigurationBuilder<BuiltConfiguration> builder = ConfigurationBuilderFactory.newConfigurationBuilder();
		builder.setStatusLevel(Level.INFO);
		builder.setConfigurationName("Builder");
		AppenderComponentBuilder appenderBuilder = builder.newAppender("File", "File").addAttribute(
				"fileName", path  + "/output.log");
		appenderBuilder.add(builder.newLayout("PatternLayout")
		    .addAttribute("pattern", "%d{yyyy-MM-dd HH:mm:ss.SSS} [%-5p] : %m%n%throwable"));
		builder.add(appenderBuilder);
		builder.add(builder.newLogger("org.apache.logging.log4j", Level.INFO)
		    .add(builder.newAppenderRef("File")).addAttribute("additivity", false));
		builder.add(builder.newRootLogger(Level.INFO).add(builder.newAppenderRef("File")));	
		return Configurator.initialize(builder.build());
	}
	
	private Logger getFileLogger() {
		if (log == null) {
			LoggerContext ctx = define();
			log = ctx.getLogger("org.apache.logging.log4j");
		}
		return log;
	}
	
	public void info(String string) {
		getFileLogger().info(string);
	}

	public void error(String string) {
		getFileLogger().error(string);
	}

}
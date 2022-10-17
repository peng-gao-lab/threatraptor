package st2g;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Utils {
	
	public static File createTempScript(String command) throws IOException {
		File tempScript = File.createTempFile("script", null);
		Writer streamWriter = new OutputStreamWriter(new FileOutputStream(tempScript));
		PrintWriter printWriter = new PrintWriter(streamWriter);
		printWriter.println(command);
		printWriter.close();
		return tempScript;
	}
	
	public static void executeCommands(List<String> commands) throws IOException, InterruptedException {
		ProcessBuilder pb = new ProcessBuilder(commands);
		pb.inheritIO(); // redirect the commands' stdout to the java process's stdout
		pb.redirectErrorStream(true);
		Process process = pb.start();
		process.waitFor();
	}
	
	public static void executeCommands(List<String> commands, OutputStream out)
			throws IOException, InterruptedException {
		ProcessBuilder pb = new ProcessBuilder(commands);	
		pb.redirectErrorStream(true);
		Process process = pb.start();
		process.waitFor();

		// Read the output from command
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line;
		while ((line = stdInput.readLine()) != null) {
			out.write((line + "\n").getBytes());
		}
		stdInput.close();
	}

	public static void executeCommands(List<String> commands, String commandDir) throws IOException, InterruptedException {
		ProcessBuilder pb = new ProcessBuilder(commands);
		pb.inheritIO(); // redirect the commands' stdout to the java process's stdout
		pb.directory(new File(commandDir));
		pb.redirectErrorStream(true);
		Process process = pb.start();
		process.waitFor();
	}

	public static void executeCommands(List<String> commands, String commandDir, OutputStream out)
			throws IOException, InterruptedException {
		ProcessBuilder pb = new ProcessBuilder(commands);
		pb.directory(new File(commandDir));
		pb.redirectErrorStream(true);
		Process process = pb.start();
		process.waitFor();

		// Read the output from command
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line;
		while ((line = stdInput.readLine()) != null) {
			out.write((line + "\n").getBytes());
		}
		stdInput.close();
	}

	public static String getAbsolutePath(String file, String relativePath) {
		return Paths.get(relativePath + file).toAbsolutePath().toString();
	}
	
	public static List<String> readCfg(String cfgPath) throws IOException {
		List<String> cfgList = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new FileReader(cfgPath));
		String line;
		while ((line = reader.readLine()) != null) {
			cfgList.add(line);
		}
		reader.close();
		return cfgList;
	}

	public static String getRandomID() {
		return "" + (UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
	}
}

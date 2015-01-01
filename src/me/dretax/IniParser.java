package me.dretax;


import org.apache.commons.cli.*;
import org.ini4j.Config;
import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by DreTaX on 2014.12.31 ..
 *
 */
public class IniParser {
	public final Logger log = Logger.getLogger(IniParser.class.getName());
	public String[] args = null;
	public Options options = new Options();
	public String dir;

	public IniParser(String[] args) {
		this.args = args;
		dir = System.getProperty("user.dir");
		options.addOption("h", "help", false, "Show help.");
		options.addOption("i", "id", true, "Write the given ID to the inifile");
		options.addOption("p", "path", true, "Direct path to the ini file");
		options.addOption("s", "sec", true, "Specify the Section if necessary");
		options.addOption("v", "val", true, "Specify the Value if necessary");
		options.addOption("f", "file", true, "Specify the File if necessary");
	}
	public void parse() {
		CommandLineParser parser = new BasicParser();
		CommandLine cmd;
		try {
			cmd = parser.parse(options, args);
			if (cmd.hasOption("h")) {
				help();
				return;
			}
			if (cmd.hasOption("i")) {
				String path = this.dir;
				String sec = "WhiteList";
				String val = "1";
				String fil = "Whitelist.ini";
				if (cmd.getOptionValue("p") != null) {
					path = cmd.getOptionValue("p");
				}
				if (cmd.getOptionValue("s") != null) {
					sec = cmd.getOptionValue("s");
				}
				if (cmd.getOptionValue("v") != null) {
					val = cmd.getOptionValue("v");
				}
				if (cmd.getOptionValue("f") != null) {
					fil = cmd.getOptionValue("f");
				}
				File n = new File(path);
				if (!n.exists()) {
					n.mkdir();
				}
				File newFile = new File(path + "\\" + fil);
				if (!newFile.exists()) {
					newFile.createNewFile();
				}
				// Config
				Config config = new Config();
				config.setStrictOperator(true);

				String id = cmd.getOptionValue("i");
				// Read
				Ini rini = new Ini(new File(path + "\\" + fil));
				if (rini.get(sec, id) != null) {
					return;
				}
				Wini ini = new Wini(new File(path + "\\" + fil));
				ini.setConfig(config);
				ini.add(sec, id, val);
				ini.store();
			} else {
				log.log(Level.SEVERE, "Missing id option");
				help();
			}

		} catch (ParseException e) {
			log.log(Level.SEVERE,"Failed to parse comand line properties", e);
			help();
		} catch (InvalidFileFormatException e) {
			log.log(Level.SEVERE, "Failed to work around with the ini", e);
			help();
		} catch (IOException e) {
			log.log(Level.SEVERE, "Failed to work around with the ini", e);
			help();
		}
		System.exit(0);
	}

	private void help() {
		// This prints out some help
		HelpFormatter formater = new HelpFormatter();
		log.log(Level.INFO, "IniParser 1.0 for Fougerite Created by DreTaX");
		formater.printHelp("Main", options);
		System.exit(0);
	}
}

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
	private final Logger log;
	private String[] args = null;
	private Options options;
	private String dir;
	private CommandLine cmd;
	private CommandLineParser parser;
	private Config config;
	private String fil;

	public IniParser(String[] args) {
		this.log = Logger.getLogger(IniParser.class.getName());
		this.options = new Options();
		this.config = new Config();
		this.config.setStrictOperator(true);
		this.args = args;
		this.parser = new BasicParser();
		this.dir = System.getProperty("user.dir");
		this.options.addOption("h", "help", false, "Show help.");
		this.options.addOption("m", "mode", true, "Specify mode: del/set/add");
		this.options.addOption("p", "path", true, "Direct path to the ini file");
		this.options.addOption("s", "sec", true, "Specify the Section if necessary");
		this.options.addOption("i", "id", true, "Write the given Key to the inifile");
		this.options.addOption("v", "val", true, "Specify the Value if necessary");
		this.options.addOption("f", "file", true, "Specify the File if necessary");
		try {
			this.cmd = parser.parse(options, args);
		} catch (ParseException e) {
			e.printStackTrace();
			return;
		}
		parse();
	}
	
	private void parse() {
		try {
			if (cmd.hasOption("h") || !cmd.hasOption("m")) {
				help();
				return;
			}
			if (cmd.hasOption("i")) {
				String sec = "WhiteList";
				String val = "1";
				fil = "Whitelist.ini";
				String id = cmd.getOptionValue("i");
				String mode = cmd.getOptionValue("m");
				if (cmd.getOptionValue("p") != null) {
					this.dir = cmd.getOptionValue("p");
				}
				if (cmd.getOptionValue("s") != null) {
					sec = cmd.getOptionValue("s");
				}
				if (cmd.getOptionValue("v") != null) {
					val = cmd.getOptionValue("v");
				}
				if (cmd.getOptionValue("f") != null) {
					if (!cmd.getOptionValue("f").contains(".ini")) fil = cmd.getOptionValue("f") + ".ini";
					else fil = cmd.getOptionValue("f");
				}
				File n = new File(this.dir);
				if (!n.exists()) {
					n.mkdir();
				}
				File newFile = new File(this.dir + "\\" + fil);
				if (!newFile.exists()) {
					newFile.createNewFile();
				}
				if (mode.equalsIgnoreCase("add")) {
					Add(sec, id, val);
				}
				else if (mode.equalsIgnoreCase("set")) {
					Set(sec, id, val);	
				}
				else if (mode.equalsIgnoreCase("del")) {
					DeleteKey(sec, id);
				}
			} else {
				log.log(Level.SEVERE, "Missing id option");
				help();
			}

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
		log.log(Level.INFO, "IniParser 1.1 for Fougerite Created by DreTaX");
		formater.printHelp("IniParser", options);
		System.exit(0);
	}

	private String getValue(String section, String key) throws IOException {
		Ini rini = new Ini(new File(this.dir + "\\" + this.fil));
		return rini.get(section, key);
	}

	private void DeleteKey(String section, String key) throws IOException {
		Wini ini = new Wini(new File(this.dir + "\\" + this.fil));
		ini.setConfig(config);	
		ini.remove(section, key);
		ini.store();
	}

	private void DeleteSection(String section) throws IOException {
		Wini ini = new Wini(new File(this.dir + "\\" + this.fil));
		ini.setConfig(config);
		ini.remove(section);
		ini.store();
	}

	private void Add(String section, String key, String value) throws IOException {
		Wini ini = new Wini(new File(this.dir + "\\" + this.fil));
		ini.setConfig(config);
		ini.add(section, key, value);
		ini.store();
	}

	private void Set(String section, String key, String value) throws IOException {
		Wini ini = new Wini(new File(this.dir + "\\" + this.fil));
		ini.setConfig(config);
		ini.put(section, key, value);
		ini.store();
	}
}

/**
 * 
 */
package com.analysis.manifest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * @author huge
 *
 * 2014年7月29日
 */
public class FormatParse {
	
//	public static void main(String[] args){
//	//	String[] testmain = {"-lt","java", "-ap","E:/work/Android静态检测/1 - 副本/22b0e67ee0c282db72af9cf4d4941334.apk" ,"-f","1","-rl","1-1-1-1", "1-1-1-2","-rf","1","-i","123456789","-t","100000640200001234","-n","0987654"};
//		String[] testmain = {"-f","E:\\work\\test\\zip","-p","dfa","dfese","-s","1234567890987654321","hfdfddddd","-n","hjghtd","ojfidg"};
//		getCommand(testmain);
//	}

	public  HashMap<String,List<String>> getCommand(String[] args){
	//	List<List<String>> mainTemp = new ArrayList<List<String>>();
		HashMap<String,List<String>> mainTemp = new HashMap<String,List<String>>();
		CommandLineParser parser = new BasicParser();
		CommandLine commandLine = null;
//		AppTransferResult appResult = new AppTransferResult();
		
		Options options = new Options();  
		options.addOption("f","file_path",true, "file_path");
		Option s = OptionBuilder.withArgName("args").withLongOpt("strings").
				hasArgs().withDescription("special_string").
				create("s");
		options.addOption(s);
		Option n = OptionBuilder.withArgName("args").withLongOpt("apknames").
				hasArgs().withDescription("apkname").
				create("n");
		options.addOption(n);
		Option p = OptionBuilder.withArgName("args").withLongOpt("packagenames").
				hasArgs().withDescription("package_name").
				create("p");
		options.addOption(p);
		options.addOption("h", "help", true, "help message");
		
		
		try {
			// Parse the program arguments  
			commandLine = parser.parse( options, args );
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println( "Incorrectly argument :" + " " + e + ", for more information please use -h or -help");
			e.printStackTrace();
		} 
		
		if(commandLine.hasOption("h")) {  
			System.out.println( "Help Message:");
			System.out.println( "-f or -file_path : file_path (not null)");
			System.out.println( "-s or -strings : special_strings (not null)");
			System.out.println( "-p or -packagenames : package_names (not null)");
			System.out.println( "-n or -apknames : apknames (not null)");
			System.out.println( "-h or -help : help Message");
			System.exit(0);
		}
		
		if(!commandLine.hasOption("f") || !commandLine.hasOption("s") || !commandLine.hasOption("p") || !commandLine.hasOption("n") ){
			System.out.println( "Incorrectly argument, for more information please use -h or -help");
			System.exit(0);
		}

		if(commandLine.hasOption("f")){
		//	System.out.println(commandLine.getOptionValue("f"));
			List<String> filepath = new ArrayList<String>();
			filepath.add(commandLine.getOptionValue("f"));
			mainTemp.put("filepath",filepath);
		}
		if(commandLine.hasOption("s")){
			List<String> special_string = new ArrayList<String>();
			for(String special : commandLine.getOptionValues("s")){
				special_string.add(special);
			}
			mainTemp.put("special", special_string);
		}
		if(commandLine.hasOption("p")){
			List<String> packagename = new ArrayList<String>();
			for(String pname : commandLine.getOptionValues("p")){
				packagename.add(pname);
			}
			mainTemp.put("packagename", packagename);
		}
		if(commandLine.hasOption("n")){
			List<String> apkname = new ArrayList<String>();
			for(String appname : commandLine.getOptionValues("n")){
				apkname.add(appname);
			}
			mainTemp.put("apkname", apkname);
		}
			
	//	System.out.println("22222222222222");
		return mainTemp;
	}
	
}

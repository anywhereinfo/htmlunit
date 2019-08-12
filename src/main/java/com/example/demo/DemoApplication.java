package com.example.demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	private static ExecutorService executorService;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		if  (args.length < 3) {
			System.out.println("\njava -jar target/demo-0.0.1-SNAPSHOT.jar {numberOfThreads} {startIndex} {secureAuthURL).\n Ensure username passwords exist in creds file");
			System.exit(-1);
		}
		
		int numberOfThreads = Integer.parseInt(args[0]);
		int startIndex = Integer.parseInt(args[1]);
		int endIndex = startIndex + numberOfThreads;
		String secureAuthURL = args[2];
		System.out.println("SecureAuth URL: " + secureAuthURL);
		
		if (numberOfThreads > 1001) {
			System.out.println("Number of threads cannot exceed 1001");
			System.exit(0);
		}
		List<UsernamePassword> userList = getCredentialList();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		System.out.println("Starting Test at: " + sdf.format(new Date()));
//		IntStream.range(1, 1001).forEach(i -> userList.add(new UsernamePassword(""+i, "")));
//		IntStream.range(startIndex, numberOfThreads).forEach(i -> userList.add(new UsernamePassword(""+i, "")));

		logger.info("Starting thread count: " +  numberOfThreads);
		executorService = Executors.newFixedThreadPool(numberOfThreads);

		IntStream.range(startIndex, endIndex).forEach(i -> {
			try {
				UsernamePassword credentials = userList.get(i);
				System.out.println("Starting user: " + credentials.userName);
				executorService.submit(TokenExtractor.getRunnable(credentials.userName, credentials.password, secureAuthURL));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		});

	}
	
	private static List<UsernamePassword> getCredentialList() throws FileNotFoundException {
		List<UsernamePassword> userList = new ArrayList<UsernamePassword>();
		File file = new File("creds");

			Scanner scanner = new Scanner(file);
			try {
			while (scanner.hasNextLine()) {
				String token = scanner.nextLine();
				String[] creds = token.split("/");
				if( (creds[0] != null) && (creds[0].length() > 0) && (creds[1] != null) && (creds[1].length() > 0)  )
					userList.add(new UsernamePassword(creds[0],creds[1]));
			}
			} finally {
				if ((scanner != null))
						scanner.close();
			}
			return userList;
	}
	
	public static class UsernamePassword {
		public String userName;
		public String password;
		
		public UsernamePassword(String username, String password) {
			this.userName = username;
			this.password = password;
		}
	}

}

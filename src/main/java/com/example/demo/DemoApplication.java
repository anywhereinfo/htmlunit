package com.example.demo;

import java.util.ArrayList;
import java.util.List;
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
		int numberOfThreads = Integer.parseInt(args[0]);
		int startIndex = Integer.parseInt(args[1]);
		int endIndex = startIndex + numberOfThreads;
		
		if (numberOfThreads > 1001) {
			System.out.println("Number of threads cannot exceed 1001");
			System.exit(0);
		}
		List<UsernamePassword> userList = new ArrayList<UsernamePassword>();
//		userList.add(new UsernamePassword("mbatth1", "Test@123"));
		IntStream.range(1, 1001).forEach(i -> userList.add(new UsernamePassword("uatptuser"+i, "Test@123")));
//		IntStream.range(2, 7).forEach(i -> userList.add(new UsernamePassword("prdptuser"+i, "Test@123")));

		logger.info("Starting thread count: " +  numberOfThreads);
		executorService = Executors.newFixedThreadPool(numberOfThreads);
	
		IntStream.range(startIndex, endIndex).forEach(i -> {
			try {
				UsernamePassword credentials = userList.get(i);
				System.out.println("Starting user: " + credentials.userName);
				executorService.submit(TokenExtractor.getRunnable(credentials.userName, credentials.password));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
	}
	
	public class UsernamePassword {
		public String userName;
		public String password;
		
		public UsernamePassword(String username, String password) {
			this.userName = username;
			this.password = password;
		}
	}

}

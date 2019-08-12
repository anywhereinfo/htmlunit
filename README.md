##HTML Unit For SecureAuth Testing

#Instructions

1. Unzip the folder
2. In the root of the folder, populate a file called creds. Each line should be a new user password with the format  usermame/password
3. Run the program by giving following arguments
	1. Number of concurrent connections
	2. Start line number within creds file.
	3. SecureAuth URL

For example, in order to run against SecureAuth31, with one thread and first username/password combination from the file, execute the command

java -jar target/demo-0.0.1-SNAPSHOT.jar 1 0 'https://auth.toolkitsonline.com/SecureAuth31/SecureAuth.aspx?client_id=d0bbeab5dcce4901a1d5b5dcaebe0781&redirect_uri=https://dot.prod.prd.local-os/dot-ui/oidc-callback&scope=openid+profile+email+address+phone&response_type=id_token+token&state=f5a3e12b480e9d442c4396d42e97ea0fcc8830fb&nonce=FYxhrC8KcIfmKryTkUSBO8Ao75ZLqDNjv3OdpgrX&masterCssURL=MFAStyleSheetWithPWReset.css'

You can run multiple copies of this program by supplying different start index in the creds file. For example if you have 20 enteries in creds file , you can run 2 instances of the program as follows

java -jar target/demo-0.0.1-SNAPSHOT.jar 10 0 {secureAuthURL} //This will start 10 threads, with each thread getting a unique user starting from line 0 till line 9

java -jar target/demo-0.0.1-SNAPSHOT.jar 10 10 {secureAuthURL} //This will start 10 threads, with each thread getting a unique user starting from line 10 till line 19

Each thread will create a file, which will contain metrics for that thread.

package com.example.demo;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class TokenExtractor {

//	private Logger logger = LoggerFactory.getLogger(this.getClass());
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

	public static Runnable getRunnable(final String username, final String password) throws Exception {
		return new Runnable() {		
			@Override
			public void run() {
				FileWriter fileWriter = null;
				PrintWriter writer = null;
				Set<String> tokens = new HashSet<String>(50000);
				
				try {
					fileWriter = new FileWriter(UUID.randomUUID().toString());
					writer = new PrintWriter(fileWriter);
					writer.println("Start time,IFrame Retrieval(ms),Credential Check(ms),Token Retrieval(ms),Total Duration(ms)");
			
				while(true)
				{

						writer.print(sdf.format(new Date())+",");
						Instant start = Instant.now();
						getToken(username, password, writer, tokens);
						Instant end = Instant.now();
						writer.println( Duration.between(start, end).toMillis()+","+ tokens.size());
						writer.flush();

				}

				} catch (Exception ex) {
					ex.printStackTrace();
				}
				finally {
					writer.close();
				}

				
			}
		};
			
	//	String tokenURL = tokenHref.getHrefAttribute();
	//	return tokenURL.substring(tokenURL.indexOf("=")+1);		
	}
	
	private static void getToken(final String username, final String password, final PrintWriter writer, final Set<String> tokens)  {
		WebClient webClient = new WebClient();
		try {
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setRedirectEnabled(false);
		//webClient.getOptions().setPrintContentOnFailingStatusCode(true);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		Instant start = Instant.now();
//		HtmlPage page = webClient.getPage("https://auth.uat.toolkitsonline.com/SecureAuth45/SecureAuth.aspx?client_id=a219bc091b584ae5b828d2447ee2e1f7&redirect_uri=https://uat.dentalofficetoolkit.com/dot-ui/oidc-callback&scope=openid+profile+email+address+phone&response_type=id_token+token&state=f5a3e12b480e9d442c4396d42e97ea0fcc8830fb&nonce=f5a3e12b480e9d442c4396d42e97ea0fcc8830gg&masterCssURL=MFAStyleSheetWithPWReset.css");
		//		HtmlPage page = webClient.getPage("https://auth.toolkitsonline.com/SecureAuth18/SecureAuth.aspx?client_id=d0bbeab5dcce4901a1d5b5dcaebe0781&redirect_uri=https://prd.dentalofficetoolkit.com/dot-ui/oidc-callback&scope=openid+profile+email+address+phone&response_type=id_token+token&state=f5a3e12b480e9d442c4396d42e97ea0fcc8830fb&nonce=f5a3e12b480e9d442c4396d42e97ea0fcc8830gg&masterCssURL=MFAStyleSheetWithPWReset.css");
		HtmlPage page = webClient.getPage("https://auth.toolkitsonline.com/SecureAuth28/SecureAuth.aspx?client_id=d0bbeab5dcce4901a1d5b5dcaebe0781&redirect_uri=https://prd.dentalofficetoolkit.com/dot-ui/oidc-callback&scope=openid+profile+email+address+phone&response_type=id_token+token&state=f5a3e12b480e9d442c4396d42e97ea0fcc8830fb&nonce=f5a3e12b480e9d442c4396d42e97ea0fcc8830gg&masterCssURL=MFAStyleSheetWithPWReset.css");

		//		HtmlPage page = webClient.getPage("https://auth.uat.toolkitsonline.com/SecureAuth88/SecureAuth.aspx?client_id=a219bc091b584ae5b828d2447ee2e1f7&redirect_uri=https://uat.dentalofficetoolkit.com/dot-ui/oidc-callback&scope=openid+profile+email+address+phone&response_type=id_token+token&state=f5a3e12b480e9d442c4396d42e97ea0fcc8830fb&nonce=f5a3e12b480e9d442c4396d42e97ea0fcc8830gg&masterCssURL=MFAStyleSheetWithPWReset.css");
		Instant end = Instant.now();
		writer.print(Duration.between(start, end).toMillis()+",");
		
		HtmlForm form = (HtmlForm) page.getElementById("aspnetForm");
		
		HtmlTextInput userNameTextBox = form.getInputByName("ctl00$ContentPlaceHolder1$MFALoginControl1$UserIDView$ctl00$ContentPlaceHolder1_MFALoginControl1_UserIDView_txtUserid");
		HtmlPasswordInput passwordTextBox = form.getInputByName("ctl00$ContentPlaceHolder1$MFALoginControl1$UserIDView$ctl00$ContentPlaceHolder1_MFALoginControl1_UserIDView_tbxPassword");
		userNameTextBox.setText(username);
		passwordTextBox.setText(password);
		

		HtmlSubmitInput submitButton = form.getInputByName("ctl00$ContentPlaceHolder1$MFALoginControl1$UserIDView$ctl00$ContentPlaceHolder1_MFALoginControl1_UserIDView_btnSubmit");
		start = Instant.now();
		HtmlPage page2 = submitButton.click();
		end = Instant.now();
		writer.print(Duration.between(start, end).toMillis()+",");
		
		page2.initialize();

		HtmlAnchor ahref = page2.getAnchorByText("here");
		
		start = Instant.now();
		HtmlPage page3 = ahref.click();
		end = Instant.now();
		writer.print(Duration.between(start, end).toMillis()+",");
		
		HtmlAnchor tokenHref =  page3.getAnchorByText("here");		
		String tokenURL = tokenHref.getHrefAttribute();
		if (tokenURL == null)
			throw new Exception("Token is null");
		else
			{
				String token = tokenURL.substring(tokenURL.indexOf("=")+1);
				if (token == null)
					throw new Exception("token is null");
				if (!tokens.add(token))
					throw new Exception("token already exists");
				
			}
		//return tokenURL.substring(tokenURL.indexOf("=")+1);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally
		{
			webClient.close();
		}
		
		//HtmlAnchor tokenHref =  page3.getAnchorByText("here");		
		//String tokenURL = tokenHref.getHrefAttribute();
		
		//return tokenURL.substring(tokenURL.indexOf("=")+1);	

	}
}

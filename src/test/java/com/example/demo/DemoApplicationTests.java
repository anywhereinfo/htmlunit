package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
public class DemoApplicationTests {

	
	@Test
	public void dummyTest() {

		//https://auth.toolkitsonline.com/SecureAuth28/SecureAuth.aspx?client_id=d0bbeab5dcce4901a1d5b5dcaebe0781&redirect_uri=https://prd.dentalofficetoolkit.com/dot-ui/oidc-callback&scope=openid+profile+email+address+phone&response_type=id_token+token&state=f5a3e12b480e9d442c4396d42e97ea0fcc8830fb&nonce=f5a3e12b480e9d442c4396d42e97ea0fcc8830gg&masterCssURL=MFAStyleSheetWithPWReset.css
	}
	
	/**	
	@Autowired
	ApplicationContext ctx;
	
	private WebClient webClient;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private ExecutorService executorService;

	@Before
	public void init() throws Exception {
		webClient = new WebClient();
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setRedirectEnabled(false);
		//webClient.getOptions().setPrintContentOnFailingStatusCode(true);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		
		executorService = Executors.newFixedThreadPool(8);
	}
	
	@After
	public void close() throws Exception {
		webClient.close();
	}

	
	@Test
	public void loadIFrame() throws Exception {
		Instant start = Instant.now();
		String token = getToken("mbatth1", "Test@123");
		Instant finish = Instant.now();
		logger.info("Time: " + (Duration.between(start, finish)));
	}
	
	private String getToken(final String username, final String password) throws Exception {
		HtmlPage page = webClient.getPage("https://auth.uat.toolkitsonline.com/SecureAuth45/SecureAuth.aspx?client_id=a219bc091b584ae5b828d2447ee2e1f7&redirect_uri=https://uat.dentalofficetoolkit.com/dot-ui/oidc-callback&scope=openid+profile+email+address+phone&response_type=id_token+token&state=f5a3e12b480e9d442c4396d42e97ea0fcc8830fb&nonce=f5a3e12b480e9d442c4396d42e97ea0fcc8830gg&masterCssURL=MFAStyleSheetWithPWReset.css");
		HtmlForm form = (HtmlForm) page.getElementById("aspnetForm");
		
		HtmlTextInput userNameTextBox = form.getInputByName("ctl00$ContentPlaceHolder1$MFALoginControl1$UserIDView$ctl00$ContentPlaceHolder1_MFALoginControl1_UserIDView_txtUserid");
		HtmlPasswordInput passwordTextBox = form.getInputByName("ctl00$ContentPlaceHolder1$MFALoginControl1$UserIDView$ctl00$ContentPlaceHolder1_MFALoginControl1_UserIDView_tbxPassword");
		userNameTextBox.setText(username);
		passwordTextBox.setText(password);
		

		HtmlSubmitInput submitButton = form.getInputByName("ctl00$ContentPlaceHolder1$MFALoginControl1$UserIDView$ctl00$ContentPlaceHolder1_MFALoginControl1_UserIDView_btnSubmit");
		HtmlPage page2 = submitButton.click();
		page2.initialize();

		HtmlAnchor ahref = page2.getAnchorByText("here");
		HtmlPage page3 = ahref.click();

		HtmlAnchor tokenHref =  page3.getAnchorByText("here");
		String tokenURL = tokenHref.getHrefAttribute();
		return tokenURL.substring(tokenURL.indexOf("=")+1);		
	}
**/
}

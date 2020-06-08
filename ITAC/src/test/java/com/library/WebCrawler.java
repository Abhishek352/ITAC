package com.library;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import utilities.Screenshots;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class WebCrawler {
	private static final int MAX_DEPTH = 10000;
	public static ArrayList<String> BrokenLinks_CompleteList = new ArrayList<>();
	public static ArrayList<String> BrokenLinks_UniqueList = new ArrayList<>();
	public static ArrayList<String> BrokenLinks_InternalList = new ArrayList<>();
	public static ArrayList<String> BrokenLinks_ExternalList = new ArrayList<>();
	public static ArrayList<String> BrokenLinks_AssetsList = new ArrayList<>();
	public static ArrayList<String> BrokenLinks_ExceptionList = new ArrayList<>();
	public static ArrayList<String> Assets_Childlinks = new ArrayList<>();
	public static ArrayList<String> Assets_InvalidList = new ArrayList<>();
	public static HashMap<String, String> AssetsList = new HashMap<>();
	static Response response;
	static int Status;
	public String SitePath = null;
	static String FilePath;


	public ArrayList<String> GetChildPages(String domain) throws Exception {

		try {
			//Handling Domains before initiating the execution

			if(!domain.startsWith("http")) {
				domain = "http://" + domain;
			}

			//To handle domains which have redirections
			Response resp = Jsoup.connect(domain).execute();
			System.out.println("Completed establishing connection to the website");
			int status = GetStatus(domain);
			if((status == 301) || (status == 302)) {
				System.out.println("Given domain has a redirection, taking the redirected url for execution");
				domain = resp.url().toString();
				System.out.println("Redirected url: " + domain);
			}else if(status == 404) {
				System.out.println(domain + " - This is not a valid domain, kindly re-check");
			}

			//To capture and save the SitePath for domains
			if(domain.endsWith(".html")) {
				SitePath = domain.replace(".html", "");
			}else if(domain.endsWith(".ashx")) {
				SitePath = domain.replace(".ashx", "");
			}else if(domain.endsWith(".php")) {
				SitePath = domain.replace(".php", "");
			}else {
				SitePath = domain;
			}

			System.out.println("Started Crawling website for domain: " + domain);
			System.out.println("Status of this domain is: " + GetStatus(domain));
			if(GetStatus(domain) == 200) {
				BrokenLinks_ChildPages(domain, 0);
				System.out.println("Completed crawling the domain and getting the childpages");

				System.out.println("==============================================================================================");
				System.out.println("Size of the list before removing empty values:");
				System.out.println("Number of links in completefile (duplicate): " + BrokenLinks_CompleteList.size());
				System.out.println("Number of links in Unique lis: " + BrokenLinks_UniqueList.size());
				System.out.println("Number of links in Internal: " + BrokenLinks_InternalList.size());
				System.out.println("Number of links in External: " + BrokenLinks_ExternalList.size());
				System.out.println("Number of links in Assets: " + BrokenLinks_AssetsList.size());
				System.out.println("Number of links in exception: " + BrokenLinks_ExceptionList.size());
				System.out.println("==============================================================================================");
				System.out.println("Removing empty values from the list");
				BrokenLinks_CompleteList = UtilClass.RemoveBlankValues(BrokenLinks_CompleteList);
				BrokenLinks_UniqueList = UtilClass.RemoveBlankValues(BrokenLinks_UniqueList);
				BrokenLinks_InternalList = UtilClass.RemoveBlankValues(BrokenLinks_InternalList);
				BrokenLinks_ExternalList = UtilClass.RemoveBlankValues(BrokenLinks_ExternalList);
				BrokenLinks_AssetsList = UtilClass.RemoveBlankValues(BrokenLinks_AssetsList);
				BrokenLinks_ExceptionList = UtilClass.RemoveBlankValues(BrokenLinks_ExceptionList);
				System.out.println("Size of the list after removing empty values: " + BrokenLinks_CompleteList.size());

				System.out.println("==============================================================================================");

				FilePath = Screenshots.CreateFolder(System.getProperty("user.dir")+"\\Results\\WebCrawler\\BrokenLinks");

				String BL_CompleteList_Path = FilePath + "\\CompleteList" + ".txt";
				String BL_UniqueList_Path = FilePath + "\\UniqueList" + ".txt";
				String BL_InternalList_Path = FilePath + "\\InternalList" + ".txt";
				String BL_ExternalList_Path = FilePath + "\\ExternalList" + ".txt";
				String BL_AssetsList_Path = FilePath + "\\AssetsList" + ".txt";
				String BL_ExceptionLinks_Path = FilePath + "\\ExceptionList" + ".txt";

				System.out.println("Started Writting the list of URL's and Docs to a Text file");
				System.out.println("==============================================================================================");

				createFile(BL_CompleteList_Path, BrokenLinks_CompleteList, BrokenLinks_CompleteList.size());
				createFile(BL_UniqueList_Path, BrokenLinks_UniqueList, BrokenLinks_UniqueList.size());
				createFile(BL_InternalList_Path, BrokenLinks_InternalList, BrokenLinks_InternalList.size());
				createFile(BL_ExternalList_Path, BrokenLinks_ExternalList, BrokenLinks_ExternalList.size());
				createFile(BL_AssetsList_Path, BrokenLinks_AssetsList, BrokenLinks_AssetsList.size());
				createFile(BL_ExceptionLinks_Path, BrokenLinks_ExceptionList, BrokenLinks_ExceptionList.size());


				System.out.println("Completed Writting the list of URL's and Docs to a Text file");
				System.out.println("==============================================================================================");
				System.out.println("Number of links in completefile (duplicate): " + BrokenLinks_CompleteList.size());
				System.out.println("Number of links in Unique lis: " + BrokenLinks_UniqueList.size());
				System.out.println("Number of links in Internal: " + BrokenLinks_InternalList.size());
				System.out.println("Number of links in External: " + BrokenLinks_ExternalList.size());
				System.out.println("Number of links in Assets: " + BrokenLinks_AssetsList.size());
				System.out.println("Number of links in exception: " + BrokenLinks_ExceptionList.size());

				System.out.println("==============================================================================================");

			}
		}catch (Exception e){
			System.out.println("Got exception in between " + e.getMessage());
		}
		return BrokenLinks_UniqueList;
	}

	public List<String> GetAssetsList(String domain) {

		try {
			//Handling Domains before initiating the execution

			if(!domain.startsWith("http")) {
				domain = "http://" + domain;
			}

			//To handle domains which have redirections
			Response resp = Jsoup.connect(domain).execute();
			System.out.println("Completed establishing connection to the website");
			int status = GetStatus(domain);
			if((status == 301) || (status == 302)) {
				System.out.println("Given domain has a redirection, taking the redirected url for execution");
				domain = resp.url().toString();
				System.out.println("Redirected url: " + domain);
			}else if(status == 404) {
				System.out.println(domain + " - This is not a valid domain, kindly re-check");
			}

			//To capture and save the SitePath for domains
			if(domain.endsWith(".html")) {
				SitePath = domain.replace(".html", "");
			}else if(domain.endsWith(".ashx")) {
				SitePath = domain.replace(".ashx", "");
			}else {
				SitePath = domain;
			}

			System.out.println("Started Crawling website for domain: " + domain);
			System.out.println("Status of this domain is: " + GetStatus(domain));
			if(GetStatus(domain) == 200) {
				Assets_ChildPages(domain, 0);
				System.out.println("Completed crawling the domain and getting the childpages");

				SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
				Date dt1 = new Date();
				String enddate = sdf1.format(dt1);
				System.out.println("==============================================================================================");

				String Assets_ChildLinks_Path = System.getProperty("user.dir")+"\\InputData\\WebCrawler\\Final_URLs_List-" + "-" + MAX_DEPTH + ".txt";
				String Assets_Invalid_List_Path = System.getProperty("user.dir")+"\\InputData\\WebCrawler\\CompleteList-" + "-" + MAX_DEPTH + ".txt";


				System.out.println("Started Writting the list of URL's and Docs to a Text file");
				System.out.println("==============================================================================================");

				createFile(Assets_ChildLinks_Path, Assets_Childlinks, Assets_Childlinks.size());
				createFile(Assets_Invalid_List_Path, Assets_InvalidList, Assets_InvalidList.size());


				System.out.println("Completed Writting the list of URL's and Docs to a Text file");
				System.out.println("==============================================================================================");

				System.out.println("Below are the final list of Child Pages:");
				System.out.println("Number of Child Links Captured: " + Assets_Childlinks.size());
				System.out.println("Number of Invalid Links captured: " + Assets_InvalidList.size());

				System.out.println("Test Execution Completed At : "+ enddate);
				System.out.println("==============================================================================================");
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		System.out.println("Completed crawling the website, retrning the complete set of pages");
		return Assets_Childlinks;
	}

	private void BrokenLinks_ChildPages(String URL, int depth) throws Exception {


		BrokenLinks_CompleteList.add(URL);

		if ((!BrokenLinks_UniqueList.contains(URL)) && (depth < MAX_DEPTH)) {
			//	System.out.println(">> Depth: " + depth + " [" + URL + "]");

			try {
				BrokenLinks_UniqueList.add(URL);
				BrokenLinks_InternalList.add(URL);
				//System.out.println("Entered the first Try Catch Loop: " + URL);

				Document document = Jsoup.connect(URL).followRedirects(true).ignoreHttpErrors(true).ignoreContentType(true).userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36").timeout(90000).get();
				//System.out.println("Could establish connection to url: " + URL);
				Elements linksOnPage = document.select("a[href]");
				//	System.out.println("Got list of all links for page: " + URL);

				depth++;

				for(int i = 0; i<linksOnPage.size();i++) {

					String childlink = linksOnPage.get(i).attr("abs:href");

					if(URL.startsWith("https://") && childlink.startsWith("http://")) {
						childlink = childlink.replace("http://", "https://");
					}else {
						//System.out.println("no need to replace");
					}

					try {
						if(childlink.startsWith(SitePath)) {

							//to identify and move documents to documents list		
							if ((childlink.endsWith(".pdf") || childlink.endsWith(".PDF") || childlink.endsWith(".doc") || childlink.endsWith(".xlsx") || childlink.endsWith(".docx") || childlink.endsWith(".txt"))) {

								BrokenLinks_CompleteList.add(childlink);
								if(!BrokenLinks_AssetsList.contains(childlink)) {
									BrokenLinks_AssetsList.add(childlink);
									BrokenLinks_UniqueList.add(childlink);
								}else {
									//System.out.println("its not a problem");
								}
							}
							//to remove the links which have target within the page		
							/*							if(childlink.contains(".html#") || childlink.contains( SitePath +"#") || childlink.contains(SitePath + "/#")) {
								childlink = childlink.split("#")[0];
							}*/
							BrokenLinks_ChildPages(childlink, depth);

						}else if ((childlink.endsWith(".pdf") || childlink.endsWith(".PDF") || childlink.endsWith(".doc") || childlink.endsWith(".xlsx") || childlink.endsWith(".docx") || childlink.endsWith(".txt"))) {

							BrokenLinks_CompleteList.add(childlink);
							if(!BrokenLinks_AssetsList.contains(childlink)) {
								BrokenLinks_AssetsList.add(childlink);
								BrokenLinks_UniqueList.add(childlink);
							}
						}else {
							BrokenLinks_CompleteList.add(childlink);
							if(!BrokenLinks_ExternalList.contains(childlink)) {
								BrokenLinks_ExternalList.add(childlink);
								BrokenLinks_UniqueList.add(childlink);
							}else {
								//	System.out.println("This link already exists in the list");
							}
						}

					}catch(Exception e) {
						if(!BrokenLinks_ExceptionList.contains(childlink)) {
							BrokenLinks_ExceptionList.add(childlink);
						}
					}
				}
			} catch (IOException e) {
				if(!BrokenLinks_ExceptionList.contains(URL)) {
					BrokenLinks_ExceptionList.add(URL);
				}
			}
		}
		//return BrokenLinks_UniqueList;
	}

	private ArrayList<String> Assets_ChildPages(String URL, int depth) throws Exception {

		if ((!Assets_Childlinks.contains(URL)) && (depth < MAX_DEPTH)) {
			//	System.out.println(">> Depth: " + depth + " [" + URL + "]");
			try {
				Assets_Childlinks.add(URL);
				//System.out.println("Entered the first Try Catch Loop: " + URL);

				Document document = Jsoup.connect(URL).followRedirects(true).ignoreHttpErrors(true).ignoreContentType(true).userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36").timeout(90000).get();
				//System.out.println("Could establish connection to url: " + URL);
				Elements linksOnPage = document.select("a[href]");
				//	System.out.println("Got list of all links for page: " + URL);

				depth++;

				for(int i = 0; i<linksOnPage.size();i++) {

					String childlink = linksOnPage.get(i).attr("abs:href");

					if(URL.startsWith("https://") && childlink.startsWith("http://")) {
						childlink = childlink.replace("http://", "https://");
					}else {
						//System.out.println("no need to replace");
					}

					try {
						if(childlink.startsWith(SitePath) && childlink.contains(".html")) {

							//((!childlink.endsWith(".pdf") || !childlink.endsWith(".PDF") || !childlink.endsWith(".doc") || !childlink.endsWith(".xlsx") || !childlink.endsWith(".docx") || !childlink.endsWith(".txt")))

							//to remove the links which have target within the page		
							if(childlink.contains(".html#") || childlink.contains(SitePath+"#") || childlink.contains(SitePath+"/#")) {
								childlink = childlink.split("#")[0];
							}
							Assets_ChildPages(childlink, depth);

						}else {
							if(!Assets_InvalidList.contains(childlink)) {
								Assets_InvalidList.add(childlink);
							}else {
								//	System.out.println("This link already exists in the list");
							}
						}
					}
					catch(Exception e) {
						if(!Assets_Childlinks.contains(childlink)){
							Assets_Childlinks.add(childlink);
							Assets_InvalidList.add(childlink);
						}
					}
				}

			} catch (IOException e) {

				if(!Assets_Childlinks.contains(URL)) {
					Assets_Childlinks.add(URL);
					Assets_InvalidList.add(URL);
				}
			}
		}
		
		return Assets_Childlinks;
	}

	private static int GetStatus (String url) {
		int Status = 0;
		try {
			response = Jsoup.connect(url).followRedirects(false).ignoreHttpErrors(true).ignoreContentType(true).execute();
			Status = response.statusCode();
		} catch (IOException e) {
			try {
				response = Jsoup.connect(url).followRedirects(false).ignoreHttpErrors(true).ignoreContentType(true).execute();
				Status = response.statusCode();
			}catch (Exception ee) {
				System.out.println("Unable to get status for link: " + url);
				Status = 404;
			}

		}
		return Status;
	}

	private static void createFile(String path , List<String> URLsList, int Size) throws IOException {

		FileWriter writer = new FileWriter(path);

		for (int i=0; i<Size; i++) {
			String str = URLsList.get(i).toString();
			writer.write(str);
			//This prevent creating a blank like at the end of the file**
			if(i < Size-1) {
				writer.write("\n");
			}
		}
		writer.close();
	}

}

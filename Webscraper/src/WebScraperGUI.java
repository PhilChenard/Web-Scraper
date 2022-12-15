//phil c
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JTextArea;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class WebScraperGUI extends JFrame implements ActionListener{
	//Declare
	private JTextField urlTextField;
	private JButton scrapeButton;
	private JTextArea resultsTextArea;
	
	public WebScraperGUI() {
		//setting the title and layout
		setTitle("Web Scraper");
		setLayout(new BorderLayout());
		

		//Create panel for when user inputs URL & scrape button
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new FlowLayout());
		
		//COntent pane color
		getContentPane().setBackground(Color.BLUE);
		
		//Create a label 
		JLabel urlLabel = new JLabel("Enter URL: ");
		urlLabel.setPreferredSize(new Dimension(100, 20));
		
	    // Set the font and text color of the label
        urlLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        urlLabel.setForeground(Color.BLUE);
        
		inputPanel.add(urlLabel);
		
		//Create a text field for url
		urlTextField = new JTextField();
		urlTextField.setPreferredSize(new Dimension(300, 20));
		urlTextField.setText("https://www.");
		
		//Set background color, text color, and font of the text field
		urlTextField.setBackground(Color.WHITE);
        urlTextField.setForeground(Color.BLACK);
        urlTextField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        
		
		//Listener
		urlTextField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//calling the actionPerformed method of the WebScraperGUI class on enter
				WebScraperGUI.this.actionPerformed(e);
			}
		
			
			
		});
		
		inputPanel.add(urlTextField);
		
		//Create a button to execute the web scraper
		scrapeButton = new JButton("Scrape");
		scrapeButton.setPreferredSize(new Dimension(100, 20));
		scrapeButton.addActionListener(this);
		inputPanel.add(scrapeButton);
		
		//add the input panel
		add(inputPanel, BorderLayout.NORTH);
		
		//Text field for area scraping results
		resultsTextArea = new JTextArea();
		resultsTextArea.setEditable(false);
		
		//add scroll pane
		JScrollPane scrollPane = new JScrollPane(resultsTextArea);
		add(scrollPane, BorderLayout.CENTER);
		
		//window dimensions and such
		setSize(500, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	try {	
		//grabbing url from user input
		String url = urlTextField.getText();
		
		Document doc = Jsoup.connect(url).get();
		
		//Extracting the page body and title
		resultsTextArea.setText("Page title: " + doc.title() + "\n\n");
		resultsTextArea.append("Page body: \n");
		
		
		//Dividing the page body
		String pageBody = doc.body().text();
		int chunkSize = 100;
		for(int i = 0; i < pageBody.length(); i += chunkSize) {
			int endIndex = Math.min(i + chunkSize, pageBody.length());
			String chunk = pageBody.substring(i, endIndex);
			resultsTextArea.append(chunk + "\n");
		}
		
	} catch(IOException ex) { 
		//print
		ex.printStackTrace();
	}
			
		
		
		
	}
	
	public static void main(String[] args) {
		new WebScraperGUI();
	}
}

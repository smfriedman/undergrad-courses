package kwic;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.Set;

import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.border.MatteBorder;

import java.awt.Color;

import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTextPane;

/**
 * 
 * @author stevenfriedman
 *
 */
public class GUIButterBar extends JFrame {

	private JPanel contentPane;
	private KWIC kwic;
	private PropertyChangeSupport pcs;
	private JTextField txtPhrase;
	private JTextField txtWord;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIButterBar frame = new GUIButterBar();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUIButterBar() {
		this.kwic = new KWIC();
		DefaultListModel<Word> wordModel = new DefaultListModel<Word>();
		kwic.getPCS().addPropertyChangeListener("Phrase Added", new PropertyChangeListener(){

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				wordModel.removeAllElements();
				Set<Word> words = kwic.getWords();
				for(Word w: words){
					wordModel.addElement(w);
				}
			}});
		kwic.addPhrases(new File("datafiles/kwic/fortunes.txt"));
		
		DefaultListModel<Phrase> phraseModel = new DefaultListModel<Phrase>();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 474, 514);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane words = new JScrollPane();
		words.setBounds(6, 6, 119, 292);
		contentPane.add(words);
		
		JList wordList = new JList();
		wordList.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		wordList.setModel(wordModel);
		wordList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				//called when someone clicks a word
				if(e.getValueIsAdjusting()){
					//someone is still fiddling with clicking
				}else if(wordList.getSelectedValue() != null){
					//^^if() allows for "delete word" button to function properly
					//now find out what was clicked
					int selected = wordList.getSelectedIndex();
					Word w = wordModel.get(selected);
					
					//clear all elements in phrase list(or else they pile up) and add ones associated with word
					phraseModel.removeAllElements();
					Set<Phrase> hasW = kwic.getPhrases(w);
					for(Phrase p: hasW){
						phraseModel.addElement(p);
					}
				}
			}
		});
		wordList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		words.setViewportView(wordList);
		
		JScrollPane phrases = new JScrollPane();
		phrases.setBounds(137, 6, 331, 292);
		contentPane.add(phrases);
		
		JList phraseList = new JList();
		phraseList.setModel(phraseModel);
		phraseList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				//called when someone clicks a phrase
				if(e.getValueIsAdjusting()){
					//someone is still fiddling with clicking
				}else{
					//now find out what was clicked
					int selected = phraseList.getSelectedIndex();
				}
			}
		});
		
		phraseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		phrases.setViewportView(phraseList);
		
		txtPhrase = new JTextField();
		txtPhrase.setText("phrase");
		txtPhrase.setBounds(186, 339, 282, 28);
		contentPane.add(txtPhrase);
		txtPhrase.setColumns(10);
		
		txtWord = new JTextField();
		txtWord.setText("word");
		txtWord.setBounds(6, 339, 168, 28);
		contentPane.add(txtWord);
		txtWord.setColumns(10);
		
		JButton btnAddAssociation = new JButton("Add Association");
		btnAddAssociation.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		btnAddAssociation.setBounds(6, 302, 119, 29);
		btnAddAssociation.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						kwic.forceAssoc(new Word(txtWord.getText()), new Phrase(txtPhrase.getText()));
					}
			
		});
		contentPane.add(btnAddAssociation);
		
		JButton btnDropAssociation = new JButton("Drop Association");	
		btnDropAssociation.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		btnDropAssociation.setBounds(122, 302, 137, 29);
		btnDropAssociation.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						if(wordList.getSelectedValue() != null &&
								phraseList.getSelectedValue() != null){
							kwic.dropAssoc(wordModel.getElementAt(wordList.getSelectedIndex()), 
							phraseModel.getElementAt(phraseList.getSelectedIndex()));
						}

					}
			
		});
		contentPane.add(btnDropAssociation);
		
		JButton btnDeletePhrase = new JButton("Delete Phrase");
		btnDeletePhrase.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		btnDeletePhrase.setBounds(254, 302, 111, 29);
		btnDeletePhrase.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						if(phraseList.getSelectedValue() != null){
							phraseModel.remove(phraseList.getSelectedIndex());
						}	
					}
			
		});
		contentPane.add(btnDeletePhrase);
		
		JButton btnDeleteWord = new JButton("Delete Word");	
		btnDeleteWord.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		btnDeleteWord.setBounds(363, 302, 105, 29);
		btnDeleteWord.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						if(wordList.getSelectedValue() != null){
							wordModel.remove(wordList.getSelectedIndex());
						}
					}
			
		});
		contentPane.add(btnDeleteWord);
		
		JTextPane txtpnToDeletePhrase = new JTextPane();
		txtpnToDeletePhrase.setBackground(Color.DARK_GRAY);
		txtpnToDeletePhrase.setForeground(Color.WHITE);
		txtpnToDeletePhrase.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		txtpnToDeletePhrase.setText("To drop association (and see result), hit button, then deselect word and reselect word.");
		txtpnToDeletePhrase.setBounds(6, 391, 462, 16);
		contentPane.add(txtpnToDeletePhrase);
		
		JButton btnAddWord = new JButton("Add Word");
		btnAddWord.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		btnAddWord.setBounds(86, 418, 117, 29);
		btnAddWord.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						kwic.addPhrase(new Phrase(txtWord.getText()));	
						kwic.dropAssoc(new Word(txtWord.getText()), new Phrase(txtWord.getText()));
					}
			
		});
		contentPane.add(btnAddWord);
		
		JButton btnAddPhrase = new JButton("Add Phrase");
		btnAddPhrase.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		btnAddPhrase.setBounds(226, 418, 117, 29);
		btnAddPhrase.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						kwic.addPhrase(new Phrase(txtPhrase.getText()));
					}
			
		});
		contentPane.add(btnAddPhrase);
		
		JTextPane txtpnWord = new JTextPane();
		txtpnWord.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		txtpnWord.setBackground(Color.DARK_GRAY);
		txtpnWord.setForeground(Color.WHITE);
		txtpnWord.setText("Word");
		txtpnWord.setBounds(16, 368, 77, 16);
		contentPane.add(txtpnWord);
		
		JTextPane txtpnPhrase = new JTextPane();
		txtpnPhrase.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		txtpnPhrase.setBackground(Color.DARK_GRAY);
		txtpnPhrase.setForeground(Color.WHITE);
		txtpnPhrase.setText("Phrase");
		txtpnPhrase.setBounds(196, 368, 63, 16);
		contentPane.add(txtpnPhrase);
		
		
	}
}

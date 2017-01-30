package test.java;

import java.io.File;

import main.bean.CasEssaiOpengoBean;
import main.constantes.Constantes;
import moteurs.FirefoxImpl;
import moteurs.GenericDriver;

import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxProfile;

import outils.SeleniumOutils;
import beans.ObjectifBean;
import exceptions.SeleniumException;

public class SC01Test extends SC00Test {

	@Test
	public void initialisationTest() throws SeleniumException {
		
		//Description du sc�nario
		CasEssaiOpengoBean scenario0 = new CasEssaiOpengoBean();
		//Configuration du driver
		FirefoxBinary ffBinary = new FirefoxBinary(new File(Constantes.EMPLACEMENT_FIREFOX));
		FirefoxProfile profile = configurerProfilNatixis();
		//Cr�ation et configuration du repertoire de t�l�chargement
		//File repertoireTelechargement = new File(".\\" + scenario0.getNomCasEssai());
		//repertoireTelechargement.mkdir();
		//profile.setPreference(Constantes.PREF_FIREFOX_REPERTOIRE_TELECHARGEMENT, repertoireTelechargement.getAbsolutePath());
		String repertoire = creerRepertoireTelechargement(scenario0, profile);
		scenario0.setRepertoireTelechargement(repertoire);
		// Initialisation du driver
		FirefoxImpl driver = new FirefoxImpl(ffBinary, profile);
		// LISTE DES OBJECTIFS DU CAS DE TEST
		scenario0.ajouterObjectif(new ObjectifBean("Test arriv� � terme", scenario0.getNomCasEssai() + scenario0.getTime()));
	   
	    SeleniumOutils outil = new SeleniumOutils(driver, GenericDriver.FIREFOX_IMPL);
	    outil.setRepertoireRacine(scenario0.getRepertoireTelechargement());
	    
	    try {
			//CT01 - Initialisation et acc�s � Izigate
			//CT02 - Consultation d'un dossier
	    	accesSynthese(outil);
			//scenario0.getTests().add();
			
		} catch (SeleniumException ex) {
			// Finalisation en erreur du cas de test.
			finaliserTestEnErreur(outil, scenario0, ex, scenario0.getNomCasEssai() + scenario0.getDateCreation().getTime());
			throw ex;
		}
		// Finalisation normale du cas de test.
	    finaliserTest(outil, scenario0, scenario0.getNomCasEssai() + scenario0.getDateCreation().getTime());
	}
}

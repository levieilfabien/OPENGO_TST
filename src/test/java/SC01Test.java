package test.java;

import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxProfile;

import beans.ObjectifBean;
import exceptions.SeleniumException;
import main.bean.CasEssaiOpengoBean;
import main.constantes.Constantes;
import moteurs.FirefoxImpl;
import moteurs.GenericDriver;
import outils.SeleniumOutils;

public class SC01Test extends SC00Test {

	@Test
	public void initialisationTest() throws SeleniumException {
		
		//Description du sc�nario
		CasEssaiOpengoBean scenario0 = new CasEssaiOpengoBean();
		scenario0.setAlm(true);
		scenario0.setDescriptif("SC02 - Acc�s synth�se et v�rification des zooms pour un dossier au recouvrement");
		scenario0.setNomTestLab("SC02 - Acc�s synth�se et v�rification des zooms pour un dossier au recouvrement");
		scenario0.setNomCasEssai("SC02-" + getTime());
		scenario0.setIdUniqueTestLab(55891);
		//Configuration du driver
		System.setProperty("webdriver.gecko.driver", Constantes.EMPLACEMENT_GECKO);
		//FirefoxBinary ffBinary = new FirefoxBinary(new File(Constantes.EMPLACEMENT_FIREFOX));
		FirefoxProfile profile = configurerProfilNatixis();
		//Cr�ation et configuration du repertoire de t�l�chargement
		//File repertoireTelechargement = new File(".\\" + scenario0.getNomCasEssai());
		//repertoireTelechargement.mkdir();
		//profile.setPreference(Constantes.PREF_FIREFOX_REPERTOIRE_TELECHARGEMENT, repertoireTelechargement.getAbsolutePath());
		
		String repertoire = creerRepertoireTelechargement(scenario0, profile);
		scenario0.setRepertoireTelechargement(repertoire);
		// Initialisation du driver
		//FirefoxImpl driver = new FirefoxImpl(ffBinary, profile);
		FirefoxImpl driver = new FirefoxImpl(profile);
		// LISTE DES OBJECTIFS DU CAS DE TEST
		scenario0.ajouterObjectif(new ObjectifBean("Test arriv� � terme", scenario0.getNomCasEssai() + scenario0.getTime()));
	   
	    SeleniumOutils outil = new SeleniumOutils(driver, GenericDriver.FIREFOX_IMPL);
	    outil.setRepertoireRacine(scenario0.getRepertoireTelechargement());
	    
	    try {
	    	accesSynthese(outil, scenario0);
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

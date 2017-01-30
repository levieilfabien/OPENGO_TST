package test.java;

import java.io.File;

import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;

import beans.CasEssaiBean;
import exceptions.SeleniumException;
import main.constantes.Cibles;
import main.constantes.Constantes;
import outils.ALMOutils;
import outils.PropertiesOutil;
import outils.SeleniumOutils;
import outils.XLSOutils;

/**
 * Classe contenant les fonctions communes entre les diff�rents scenario.
 * @author levieilfa
 *
 */
public class SC00Test extends CasEssaiBean {
	/**
	 * Ide de s�rialisation.
	 */
	private static final long serialVersionUID = -3998430627069819668L;

	/**
	 * Cr�er le repertoire de t�l�chargement pour le cas d'essai.
	 * @param profile le profil firefox utilis�
	 * @param casEssai le cas d'essai
	 * @return le chemin du repertoire.
	 */
	public String creerRepertoireTelechargement(CasEssaiBean casEssai, FirefoxProfile profile) {
		File repertoireTelechargement = new File(".\\" + casEssai.getNomCasEssai());
		repertoireTelechargement.mkdir();
		profile.setPreference(Constantes.PREF_FIREFOX_REPERTOIRE_TELECHARGEMENT, repertoireTelechargement.getAbsolutePath());
		return repertoireTelechargement.getAbsolutePath();
	}
	
	/**
	 * Configuration du profil pour test.
	 * @return Le profil utiliser sur le mod�le du profil "Automate"
	 */
	public static FirefoxProfile configurerProfilNatixis() {
		
		// Initialisation du profil avec le profil automate requis
		ProfilesIni profileIni = new ProfilesIni();
		//FirefoxProfile profile = profileIni.getProfile("Automate");
		FirefoxProfile profile = new FirefoxProfile(new File(Constantes.EMPLACEMENT_PROFIL));
		
		profile.setPreference("app.update.enabled", Boolean.FALSE);
		profile.setPreference("network.negotiate-auth.trusted-uris", "https://open-workplace.intranatixis.com/nfi/front-middle/wiki-izivente/Rfrentiel/Liens%20Izivente.aspx");
		profile.setPreference("network.automatic-ntlm-auth.trusted-uris", "https://open-workplace.intranatixis.com/nfi/front-middle/wiki-izivente/Rfrentiel/Liens%20Izivente.aspx");
		
		profile.setPreference("browser.download.pluginOverrideTypes", "");
		profile.setPreference("plugin.disable_full_page_plugin_for_types", "application/pdf,application/vnd.fdf,application/vnd.adobe.xfdf,application/vnd.adobe.xdp+xml");
		
		profile.setPreference("pdfjs.disabled", Boolean.TRUE);
		profile.setPreference("pdfjs.firstRun", Boolean.FALSE);
		profile.setPreference("pdfjs.previousHandler.alwaysAskBeforeHandling", Boolean.FALSE);
		profile.setPreference("pdfjs.previousHandler.preferredAction", 4);
		profile.setPreference("pdfjs.disabled", Boolean.TRUE);
		
		profile.setPreference("browser.download.useDownloadDir", Boolean.TRUE);
		profile.setPreference("browser.download.manager.focusWhenStarting", Boolean.FALSE);
		profile.setPreference("browser.download.folderList", 2);
        profile.setPreference("browser.download.dir", new File(".\\").getAbsolutePath());
        
        //browser.download.panel.shown
        profile.setPreference("browser.helperApps.alwaysAsk.force", Boolean.FALSE);
        profile.setPreference("browser.download.manager.showWhenStarting", Boolean.FALSE);
        profile.setPreference("browser.download.manager.useWindow", Boolean.FALSE);
		profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf,text/pdf,application/octet-stream,application/x-pdf,text/plain,text/xml");

		return profile;
	}
	/**
	 * Permet de finaliser le cas d'essai en erreur.
	 * @param outil la boite � outil
	 * @param casEssai le cas d'essai
	 * @param ex l'exception g�n�rant l'erreur
	 * @throws SeleniumException en cas d'erreur.
	 */
	public final void finaliserTestEnErreur(final SeleniumOutils outil, final CasEssaiBean casEssai, final SeleniumException ex, final String idObjectif) throws SeleniumException {
		ex.printStackTrace();
		casEssai.setCommentaire(ex.toString());
		logger(ex.toString());
		finaliserTest(outil, casEssai, idObjectif, false);
	}

	/**
	 * Finalise l'execution d'un test en renseignant les log du cas d'essai et du test � partir des
	 * logs du driver.
	 * 
	 * @param outils le driver.
	 * @param casEssai le cas d'essai concern� par le test.
	 * @throws SeleniumException en cas d'erreur lors de la g�n�ration du fichier excel de rapport.
	 */
	private void finaliserTest(SeleniumOutils outils, CasEssaiBean casEssai, final String idObjectif, boolean succes) throws SeleniumException {
		// On finalise aussi les sous cas.
		for(CasEssaiBean sousCas : casEssai.getTests()) {
			finaliserTest(outils, sousCas, casEssai.getNomCasEssai() + casEssai.getTime(), sousCas.getEtatFinal());
		}
		// Si le driver n'est pas nul on effectue des capture d'�cran et on r�cup�re les logs.
		if (outils != null) {
			casEssai.setRegistreExecution(outils.getDriver());
			logger(casEssai.getRegistreExecution() + "\n" + casEssai.toString());
			if (casEssai.getRepertoireTelechargement() != null) {
				outils.captureEcran("captureFinale" + casEssai.getNomCasEssai(), casEssai.getRepertoireTelechargement());
			} else {
				outils.captureEcran("captureFinale" + casEssai.getNomCasEssai(), casEssai.getNomCasEssai());
			}
		}
		// On valide l'objectif en fonction du succ�s du cas de test.
		casEssai.validerObjectif(outils.getDriver(), idObjectif, succes);
		//setCasEssai(casEssai);

		logger(casEssai.toString());

		//TODO A remettre
//		if (outils != null) {
//			outils.getDriver().quit();
//		}

		// On renseigne le rapport d'execution avec les donn�es du cas de test.
		XLSOutils.renseignerExcel(casEssai);
		
		// On tente de mettre � jour ALM
		if (casEssai.getAlm()) {
			ALMOutils.miseAJourTestSet(casEssai, succes);
			System.out.println("Mise � jour dans ALM");
		}
	}
	
	/**
	 * Fonction de finalisation de test.
	 * @param outil la boite � outil.
	 * @param casEssai le cas d'essai.
	 * @param idObjectif l'id de l'objectif final.
	 * @throws SeleniumException en cas d'erreur.
	 */
	public final void finaliserTest(SeleniumOutils outil, CasEssaiBean casEssai, final String idObjectif) throws SeleniumException {
		finaliserTest(outil, casEssai, idObjectif, true);
	}
	
	/**
	 * Permet d'ajouter une ligne dans le registre d'execution pour apporter plus d'informations.
	 * 
	 * @param append la chaine de caract�re � ajouter dans le registre d'execution.
	 */
	public final void logger(final String append) {
		if (getLogs() != null) {
			setLogs(getLogs() + "\n" + append);
		} else {
			setLogs(append);
		}
	}
	/**
	 * Permet de r�cup�rer les n derniers caract�res d'une cha�ne
	 * @param chaine
	 * @param nombre
	 * @return
	 */

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// FONCTION COMMUNES :
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public String accesIzigate(SeleniumOutils outil) throws SeleniumException {
		String login = PropertiesOutil.getInfoConstante("IZIGATE.login");
		String password = PropertiesOutil.getInfoConstante("IZIGATE.password");
		String url = Constantes.URL_IZIGATE;
		outil.chargerUrl(url);
		// Attente de l'affichage du titre de la page
		outil.attendreChargementPage(Constantes.TITRE_PAGE_IZIGATE);
		// G�n�ration de la chaine du Jeton	
		outil.viderEtSaisir(login, Cibles.SAISIE_LOGIN);
		outil.viderEtSaisir(password, Cibles.SAISIE_MDP);
		// Chargement d'IZIVENTE
		outil.cliquer(Cibles.BOUTON_VALIDATION_LOGIN);
		String retour = "Acc�s � Izigate OK";
		return retour;
	}
	

}

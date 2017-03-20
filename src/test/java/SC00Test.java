package test.java;

import java.io.File;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;

import beans.CasEssaiBean;
import beans.ObjectifBean;
import constantes.Erreurs;
import exceptions.SeleniumException;
import extensions.SeleniumALMRESTWrapper;
import main.bean.CasEssaiOpengoBean;
import main.constantes.Cibles;
import main.constantes.Constantes;
import outils.ALMOutils;
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
	private void finaliserTest(SeleniumOutils outils, CasEssaiBean casEssai, final String idObjectif, Boolean succes) throws SeleniumException {
		// On finalise aussi les sous cas.
//		for(CasEssaiBean sousCas : casEssai.getTests()) {
//			finaliserTest(outils, sousCas, casEssai.getNomCasEssai() + casEssai.getTime(), sousCas.getEtatFinal());
//		}
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
		
//		if (succes == null) {
//			succes = true;
//			for (ObjectifBean step : casEssai.getObjectifs().values()) {
//				if (step.isStep()) {
//					if (null == step.getEtat()) {
//						succes = null;
//					} else if (step.getEtat() == false) {
//						succes = false;
//						break;
//					}
//				}
//			}
//		}
		
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
			SeleniumALMRESTWrapper.miseAJourTestSet(casEssai, succes);
			System.out.println("Mise � jour dans ALM termin�e");
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
		// Ce n'est pas car il n'y a pas d'erreur que le test se finalise en �tant ok.
		//TODO � voir les cons�quences
		finaliserTest(outil, casEssai, idObjectif, null);
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
	public void accesSynthese(SeleniumOutils outil, CasEssaiOpengoBean reference) throws SeleniumException {
		
		CasEssaiOpengoBean CT01 = new CasEssaiOpengoBean(reference, "CT01-", 78945);
		CT01.ajouterStep("Acc�s � la synth�se via l'url d'acc�s direct", "ACCES", "Affichage de la synth�se pour le client demand�");
		CasEssaiOpengoBean CT02 = new CasEssaiOpengoBean(reference, "CT02-", 78946);
		CT02.ajouterStep("On r�cup�re les donn�es clients essentielles (Nom, pr�nom, IUN, �tat des dossiers)", "DONNEES", "");
		CasEssaiOpengoBean CT03 = new CasEssaiOpengoBean(reference, "CT03-", 78947);
		CT03.ajouterStep("Acc�s au zoom adresse", "ACCES", "Le zoom adresse s'affiche");
		CT03.ajouterStep("V�rifier la pr�sence de l'adresse dans le zoom adresse", "VERIFICATION", "L'adresse est bien pr�sente");
		CT03.ajouterStep("Quitter le zoom", "QUITTER", "On revient sur la synth�se");
		CasEssaiOpengoBean CT04 = new CasEssaiOpengoBean(reference, "CT04-", 78948);
		CT04.ajouterStep("Acc�s au zoom adresse", "ACCES", "Le zoom adresse s'affiche");
		CT04.ajouterStep("V�rifier la pr�sence de l'adresse dans le zoom adresse", "VERIFICATION", "L'adresse est bien pr�sente");
		CT04.ajouterStep("Quitter le zoom", "QUITTER", "On revient sur la synth�se");
		CasEssaiOpengoBean CT05 = new CasEssaiOpengoBean(reference, "CT05-", 78949);
		CT05.ajouterStep("Acc�s au zoom t�l�phone", "ACCES", "Le zoom t�l�phone s'affiche");
		CT05.ajouterStep("V�rifier la pr�sence du t�l�phone dans le zoom t�l�phone", "VERIFICATION", "Le t�l�phone est bien pr�sent");
		CT05.ajouterStep("Quitter le zoom", "QUITTER", "On revient sur la synth�se");
		CasEssaiOpengoBean CT06 = new CasEssaiOpengoBean(reference, "CT06-", 78950);
		CT06.ajouterStep("Acc�s au zoom cr�dit", "ACCES", "Le zoom cr�dit s'affiche");
		CT06.ajouterStep("Quitter le zoom", "QUITTER", "On revient sur la synth�se");
		CasEssaiOpengoBean CT07 = new CasEssaiOpengoBean(reference, "CT07-", 78951);
		CT07.ajouterStep("Acc�s au zoom contrat pour chaque dossier du client", "ACCES", "Le zoom contrat s'affiche correctement pour les dossier au recouvrement, pas de zoom contrat pour les dossiers sains.");
		CT07.ajouterStep("Quitter le zoom", "QUITTER", "On revient sur la synth�se");
		CT07.ajouterStep("Acc�s au zoom info RET pour chaque dossier du client", "ACCES2", "Le zoom infos RET s'affiche correctement pour les dossier au recouvrement.");
		CT07.ajouterStep("V�rifier la pr�sence du SREC du dossier au recouvrement dans le zoom", "VERIFICATION2", "Le SREC est bien pr�sent");
		CT07.ajouterStep("Quitter le zoom", "QUITTER2", "On revient sur la synth�se");
		CasEssaiOpengoBean CT08 = new CasEssaiOpengoBean(reference, "CT08-", 78952);
		CT08.ajouterStep("Acc�s au zoom r�le", "ACCES", "Le zoom r�le s'affiche");
		CT08.ajouterStep("V�rifier la pr�sence de l'emprunteur dans le zoom t�l�phone", "VERIFICATION", "L'emprunteur est bien pr�sent");
		CT08.ajouterStep("Quitter le zoom", "QUITTER", "On revient sur la synth�se");
		CasEssaiOpengoBean CT09 = new CasEssaiOpengoBean(reference, "CT09-", 78953);
		CT09.ajouterStep("Acc�s au zoom mens", "ACCES", "Le zoom mens s'affiche");
		CT09.ajouterStep("Quitter le zoom", "QUITTER", "On revient sur la synth�se");
		CasEssaiOpengoBean CT10 = new CasEssaiOpengoBean(reference, "CT10-", 78954);
		CT10.ajouterStep("Acc�s au zoom de relance commerciale pour un dossier disposant d'une relance commerciale", "ACCES", "Le zoom de relance commerciale s'affiche");
		CT10.ajouterStep("Quitter le zoom", "QUITTER", "On revient sur la synth�se");
		CasEssaiOpengoBean CT11 = new CasEssaiOpengoBean(reference, "CT11-", 78955);
		CT11.ajouterStep("V�rifier la pr�sence du bloc actualit� GC si le client dispose d'un dossier au recouvrement", "VERIFICATION", "Le bloc s'affiche bien si le client dispose d'un dossier au recouvrement.");
		CasEssaiOpengoBean CT12 = new CasEssaiOpengoBean(reference, "CT12-", 78956);
		CT12.ajouterStep("V�rifier la pr�sence des informations sur le motif du retard si il y en a un", "ACCES", "Les informations sont bien pr�sente si ce dossier dispose d'un retard");
		CasEssaiOpengoBean CT13 = new CasEssaiOpengoBean(reference, "CT13-", 78957);
		CT13.ajouterStep("Acc�s � la MAJ Client depuis le bouton d�di�", "ACCES", "La MAJ Client s'affiche sur l'onglet client");
		CT13.ajouterStep("V�rifier la pr�sence des blocs relatif � la nature du dossier dans la MAJ Client", "VERIFICATION", "Les blocs sont conformes");
		CasEssaiOpengoBean CT14 = new CasEssaiOpengoBean(reference, "CT14-", 78958);
		CT14.ajouterStep("Quitter la MAJ Client", "QUITTER", "La synth�se s'affiche pour le client");
		CasEssaiOpengoBean CT15 = new CasEssaiOpengoBean(reference, "CT15-", 78959);
		CT15.ajouterStep("Acc�s � la MAJ Client depuis le bouton de modification d'adresse", "ACCES", "La MAJ Client s'affiche sur l'onglet moyens de contact");
		CT15.ajouterStep("Quitter la MAJ Client", "QUITTER", "La synth�se s'affiche pour le client");
		CasEssaiOpengoBean CT16 = new CasEssaiOpengoBean(reference, "CT16-", 78960);
		CT16.ajouterStep("Acc�der au journal des modification par le lien sur la synth�se", "ACCES", "Une invite de connexion apparait");
		CT16.ajouterStep("S'identifier sur l'invite et valider", "IDENTIFICATION", "On acc�de au JDM");
		
		String login = Constantes.LOGIN_UNITED;
		String password = Constantes.PASSWORD_UNITED;
		String profil = Constantes.PROFIL_UTILISATEUR;
		String url = Constantes.URL_SYNTHESE;
		//url = url.replace("[IUN]", "5333383");
		//url = url.replace("[DISTRIBUTEUR]", "BP");
		url = url.replace("[IUN]", "4358233");
		url = url.replace("[DISTRIBUTEUR]", "CE");
		url = url.replace("[LOGIN UNITED]", login);
		url = url.replace("[MDP UNITED]", password);
		url = url.replace("[SAVCCO_DOM_M]", profil);
		
		System.out.println(url);
		outil.chargerUrl(url);
		
		// La pr�sence d'une erreur g�n�rale indique qu'il est impossible d'acc�der � l'IHM
		//if (outil.testerPresenceTexte("Erreur g�n�rale", true)) {
			
		if (outil.testerPresenceElementDiffere(Cibles.BLOC_MESSAGE_ERREUR)) {	
			CT01.invalider(outil, "ACCES");
		} else {
			CT01.valider(outil, "ACCES");
		}
		
		CT01.validerObjectif(outil.getDriver(), "ACCES", true);
		////////////////////////////////////////////////////
		// VERIFICATIONS RELATIVE AU ZOOM ADRESSE
		////////////////////////////////////////////////////
		String adresseLigne1 = outil.obtenirValeur(Cibles.ZOOM_ADRESSE1);
		String adresseLigne2 = outil.obtenirValeur(Cibles.ZOOM_ADRESSE2);
		
		CT02.validerObjectif(outil.getDriver(), "DONNEES", true);
		
		outil.cliquer(Cibles.ZOOM_ADRESSE1);
		outil.attendreChargementElement(Cibles.ZOOM_POPUP_ADRESSE_CORRESPONDANCE);
		CT03.validerObjectif(outil.getDriver(), "ACCES", true);
		if (!outil.obtenirValeur(Cibles.ZOOM_POPUP_ADRESSE_CORRESPONDANCE).contains(adresseLigne1)) {
			CT03.validerObjectif(outil.getDriver(), "VERIFICATION", false);
			throw new SeleniumException(Erreurs.E034, "Le zoom ne contient pas l'adresse : " + adresseLigne1);
		}
		CT03.validerObjectif(outil.getDriver(), "VERIFICATION", true);
		
		outil.cliquer(Cibles.ZOOM_ADRESSE1);
		if (outil.testerPresenceElement(Cibles.ZOOM_POPUP_ADRESSE_CORRESPONDANCE)) {
			CT03.validerObjectif(outil.getDriver(), "QUITTER", false);
			throw new SeleniumException(Erreurs.E035, "Le zoom n'as pas disparu lors d'un clic � l'ext�rieur de celui-ci.");
		}
		CT03.validerObjectif(outil.getDriver(), "QUITTER", true);
		
		outil.cliquer(Cibles.ZOOM_ADRESSE2);
		CT04.validerObjectif(outil.getDriver(), "ACCES", true);
		if (!outil.obtenirValeur(Cibles.ZOOM_POPUP_ADRESSE_CORRESPONDANCE).contains(adresseLigne2)) {
			CT04.validerObjectif(outil.getDriver(), "VERIFICATION", false);
			throw new SeleniumException(Erreurs.E034, "Le zoom ne contient pas l'adresse : " + adresseLigne2);
		}
		CT04.validerObjectif(outil.getDriver(), "VERIFICATION", true);
		outil.cliquer(Cibles.ZOOM_ADRESSE2);
		if (outil.testerPresenceElement(Cibles.ZOOM_POPUP_ADRESSE_CORRESPONDANCE)) {
			CT04.validerObjectif(outil.getDriver(), "QUITTER", false);
			throw new SeleniumException(Erreurs.E035, "Le zoom n'as pas disparu lors d'un clic � l'ext�rieur de celui-ci.");
		}
		CT04.validerObjectif(outil.getDriver(), "QUITTER", true);
		////////////////////////////////////////////////////
		// VERIFICATIONS RELATIVE AU ZOOM TELEPHONE
		////////////////////////////////////////////////////
		String telephone = outil.obtenirValeur(Cibles.ZOOM_TELEPHONE);
		// On ne teste que le client dispose d'un num�ro de t�l�phone.
		if (telephone != null && !"".equals(telephone)) {
			outil.cliquer(Cibles.ZOOM_TELEPHONE);
			CT05.validerObjectif(outil.getDriver(), "ACCES", true);
			if (!outil.obtenirValeur(Cibles.ZOOM_POPUP_TELEPHONE).contains(telephone)) {
				CT05.validerObjectif(outil.getDriver(), "VERIFICATION", false);
				throw new SeleniumException(Erreurs.E034, "Le zoom ne contient pas le num�ro de t�l�phone : " + telephone);
			}
			CT05.validerObjectif(outil.getDriver(), "VERIFICATION", true);
			outil.cliquer(Cibles.ZOOM_TELEPHONE);
			CT05.validerObjectif(outil.getDriver(), "QUITTER", true);
		}
		////////////////////////////////////////////////////
		// VERIFICATIONS RELATIVE AU ZOOM CREDIT
		////////////////////////////////////////////////////
		outil.attendreEtCliquer(Cibles.ZOOM_CREDIT);
		outil.attendreChargementElement(Cibles.ZOOM_POPUP_CREDIT, true, true);
		if (!outil.testerPresenceElement(Cibles.ZOOM_POPUP_CREDIT)) {
			CT06.validerObjectif(outil.getDriver(), "ACCES", false);
			throw new SeleniumException(Erreurs.E009, "Le zoom cr�dit n'apparais pas : " + Cibles.ZOOM_POPUP_CREDIT);
		}
		CT06.validerObjectif(outil.getDriver(), "ACCES", true);
		outil.cliquer(Cibles.ZOOM_CREDIT);
		CT06.validerObjectif(outil.getDriver(), "QUITTER", true);
		////////////////////////////////////////////////////
		// VERIFICATIONS RELATIVE AU ZOOM CONTRAT (cette fois pour tous les contrats du client)
		////////////////////////////////////////////////////
		// Pour avoir un zoom contrat, le dossier doit �tre au recouvrement !
		// Ici on renvoie le premier �tat de dossier rencontrer, il doit �tre RET.
		List<WebElement> elements = outil.obtenirElements(Cibles.ETAT_DOSSIER);
		Boolean recouvrement = false;
		for (WebElement element : elements) {
			String etatDossier = outil.obtenirValeur(element);
			if (etatDossier != null && "RET".equals(etatDossier)) {
				recouvrement = true;
				// On remonte depuis l'�l�ment, d'abord la DIV m�re, puis le td p�re, puis le tr et enfin on va � la deuxi�me cellule de ce TR qui est le zoom dossier.
				WebElement dossier = outil.obtenirElement(element, "../../../td[2]"); // element.findElement(By.xpath("../../../td[2]"));
				String numeroDossier = outil.obtenirValeur(dossier);
				// On ouvre le zoom contrat
				dossier.click();
				outil.attendreChargementElement(Cibles.ZOOM_POPUP_CONTRAT, true, true);
				if (!outil.testerPresenceElement(Cibles.ZOOM_POPUP_CONTRAT)) {
					CT07.validerObjectif(outil.getDriver(), "ACCES", false);
					throw new SeleniumException(Erreurs.E009, "Le zoom contrat n'apparais pas alors que le dossier " + numeroDossier + " est au recouvrement.");
				}
				CT07.validerObjectif(outil.getDriver(), "ACCES", true);
				dossier.click();
				CT07.validerObjectif(outil.getDriver(), "QUITTER", true);
				////////////////////////////////////////////////////
				// VERIFICATIONS RELATIVE A L'INFO RET
				////////////////////////////////////////////////////
				WebElement infoRet = outil.obtenirElement(element, "../../../td[12]"); //.findElement(By.xpath("../../../td[12]"));
				String valeurSRECSynthese = "";
				infoRet.click();
				CT07.valider(outil, "ACCES2");
				WebElement valeurSRECInfoRet = outil.obtenirElement(Cibles.BLOC_INFO_RET_SITUATION_ACTUELLE, "./tr[2]/td[2]/span");
				// On v�rifie que le SREC qui s'affiche dans l'info RET est conforme au SREC affich� dans la synth�se
				if (!valeurSRECSynthese.equals(outil.obtenirValeur(valeurSRECInfoRet))) {
					CT07.invalider(outil, "VERIFICATION2");
					throw new SeleniumException(Erreurs.E034, "L'info RET ne contient pas le SREC attendu : " + valeurSRECSynthese);
				}
				CT07.valider(outil, "VERIFICATION2");
				////////////////////////////////////////////////////
				// VERIFICATIONS RELATIVE A L'ACTUALITE GC
				////////////////////////////////////////////////////
				// Pour un dossier au recouvrement on s'attend � trouver le bloc d'actualit� GC 
				if (!outil.testerPresenceElement(Cibles.BLOC_ACTUALITE_GC)) {
					CT11.invalider(outil, "VERIFICATION");
					throw new SeleniumException(Erreurs.E009, "Le bloc actualit� GC n'apparais pas alors que le dossier " + numeroDossier + " est au recouvrement.");
				}
				CT11.valider(outil, "VERIFICATION");
				outil.attendreEtCliquer(Cibles.QUITTER_INFO_RET);
				CT07.valider(outil, "QUITTER2");
			}
		}
		
		////////////////////////////////////////////////////
		// VERIFICATIONS RELATIVE AU ZOOM ROLE
		////////////////////////////////////////////////////
		String nomEmprunteur = outil.obtenirValeur(Cibles.NOM_EMPRUNTEUR);
		outil.cliquer(Cibles.ZOOM_ROLE);
		outil.attendreChargementElement(Cibles.ZOOM_POPUP_ROLE, true, true);
		CT08.valider(outil, "ACCES");
		if (!outil.obtenirValeur(Cibles.ZOOM_POPUP_ROLE).contains(nomEmprunteur)) {
			CT08.invalider(outil, "VERIFICATION");
			throw new SeleniumException(Erreurs.E034, "Le zoom role ne contient pas le nom de l'emprunteur : " + nomEmprunteur);
		}
		CT08.valider(outil, "VERIFICATION");
		outil.cliquer(Cibles.ZOOM_ROLE);
		CT08.valider(outil, "QUITTER");
		////////////////////////////////////////////////////
		// VERIFICATIONS RELATIVE AU ZOOM MENS
		////////////////////////////////////////////////////
		outil.cliquer(Cibles.ZOOM_MENS);
		outil.attendreChargementElement(Cibles.ZOOM_POPUP_MENS, true, true);
		CT09.valider(outil, "ACCES");
		if (!outil.obtenirValeur(Cibles.ZOOM_POPUP_MENS).contains(nomEmprunteur)) {
			throw new SeleniumException(Erreurs.E034, "Le zoom mens ne contient pas le nom de l'emprunteur : " + nomEmprunteur);
		}
		outil.cliquer(Cibles.ZOOM_MENS);
		CT09.valider(outil, "QUITTER");
		////////////////////////////////////////////////////
		// ACCES A LA MAJ CLIENT = Onglet Client
		////////////////////////////////////////////////////
		outil.cliquer(Cibles.BOUTON_MAJ_CLIENT);
		// Une fois la MAJ Client affich�e, on v�rifie la pr�sence des blocs attendus.
		outil.attendreChargementElement(Cibles.BOUTON_RETOUR_MAJ_CLIENT_CLIENT);
		CT13.valider(outil, "ACCES");
		if (!outil.testerPresenceElement(Cibles.BLOC_ETAT_CIVIL_CLIENT)) {
			CT13.invalider(outil, "VERIFICATION");
			throw new SeleniumException(Erreurs.E009, "Le bloc �tat CIVIL n'apparais pas alors que l'on est sur l'onglet client de la MAJ Client.");
		}
		if (recouvrement && !outil.testerPresenceElement(Cibles.BLOC_EVENEMENT_CLIENT)) {
			CT13.invalider(outil, "VERIFICATION");
			throw new SeleniumException(Erreurs.E009, "Le bloc �v�nement client n'apparais pas alors que le client dispose d'un dossier au recouvrement.");
		}
		CT13.valider(outil, "VERIFICATION");
		try {
			outil.cliquerEtAttendre(Cibles.BOUTON_RETOUR_MAJ_CLIENT_CLIENT, Cibles.PICTO_MODIFICATION_ADRESSE);
			CT14.valider(outil, "QUITTER");
		} catch (SeleniumException ex) {
			CT14.invalider(outil, "QUITTER");
			throw ex;
		}
		
		////////////////////////////////////////////////////
		// ACCES A LA MAJ CLIENT = Onglet Moyens de contact
		////////////////////////////////////////////////////
		outil.cliquer(Cibles.PICTO_MODIFICATION_ADRESSE);
		// Une fois la MAJ Client affich�e, on v�rifie la pr�sence des blocs attendus.
		outil.attendreChargementElement(Cibles.BOUTON_RETOUR_MAJ_CLIENT_CONTACT);
		if (!outil.testerPresenceElement(Cibles.BLOC_MODIFICATION_ADRESSE)) {
			CT15.invalider(outil, "ACCES");
			throw new SeleniumException(Erreurs.E009, "Le bloc des coordonn�es n'apparais pas alors que l'on est sur l'onglet Moyens de contact de la MAJ Client.");
		}
		CT15.valider(outil, "ACCES");
		try {
			outil.cliquerEtAttendre(Cibles.BOUTON_RETOUR_MAJ_CLIENT_CONTACT, Cibles.BOUTON_MAJ_CLIENT);
			CT15.valider(outil, "QUITTER");
		} catch (SeleniumException ex) {
			CT15.invalider(outil, "QUITTER");
			throw ex;
		}
		////////////////////////////////////////////////////
		// ACCES AU JDM
		////////////////////////////////////////////////////
		outil.cliquer(Cibles.LIEN_JOURNAL_MODIF);
		
		//TODO Le JDM demande en recette une nouvelle identification, pour se faire il faut utiliser le user windows.
		if (outil.testerPresenceElementDiffere(Cibles.LOGIN_JDM)) {
			CT16.valider(outil, "ACCES");
			outil.saisir(Constantes.LOGIN_ALM, Cibles.LOGIN_JDM);
			outil.saisir(Constantes.PASSWORD_ALM, Cibles.PASSWORD_JDM);
			outil.cliquer(Cibles.VALIDER_LOGIN_JDM);
		}
		
		if(!outil.testerPresenceElementDiffere(Cibles.BLOC_PRINCIPAL_JDM)) {
			CT16.invalider(outil, "IDENTIFICATION");
			throw new SeleniumException(Erreurs.E009, "Le JDM n'apparais pas.");
		}
		CT16.valider(outil, "ACCES");
		CT16.valider(outil, "IDENTIFICATION");
	}
	

}

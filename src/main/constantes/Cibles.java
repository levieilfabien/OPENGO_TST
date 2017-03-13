package main.constantes;

import beans.CibleBean;
import constantes.Clefs;

public class Cibles {
	//////////////////////////////////////////////////////CIBLE A USAGE UNIQUE ///////////////////////////////////////////////////////////////
	/**
	 * Zone de saisie de l'identifiant de l'utilisateur
	 */
	public static final CibleBean SAISIE_LOGIN =  new CibleBean(Clefs.NAME, "user");
	
	/**
	 * Zone de saisie du mot de passe de l'utilisateur
	 */
	public static final CibleBean SAISIE_MDP =  new CibleBean(Clefs.NAME, "mdp");
	
	/**
	 * Bouton de validation des identifiants d'acc�s � Izigate
	 */
	public static final CibleBean BOUTON_VALIDATION_LOGIN =  new CibleBean(Clefs.XPATH, "html/body/table[4]/tbody/tr/td[1]/input");

	/**
	 * Zone de saisie de la date d'instructio d'un dossier pour la recherche d'un dossier
	 */
	public static final CibleBean SAISIE_DATE_MURISSEMENT = new CibleBean(Clefs.NAME, "s2_date");
	
	/**
	 * Bouton d'envoi de recherche de dossier
	 */
	public static final CibleBean BOUTON_ENVOI_RECHERCHE = new CibleBean(Clefs.VALEUR, "Envoi");
	
	/**
	 * Lien pour le zoom de l'Adresse (premi�re ligne)
	 */
	public static final CibleBean ZOOM_ADRESSE1 = new CibleBean(Clefs.VALEUR, "syntheseForm:adresseLabel:adresseLink");
	
	/**
	 * Lien pour le zoom de l'Adresse (deuxi�me ligne)
	 */
	public static final CibleBean ZOOM_ADRESSE2 = new CibleBean(Clefs.VALEUR, "syntheseForm:adresseLabel:adresse2Link");
	
	/**
	 * Ligne repr�sentant l'adresse de correspondance dans le zoom adresse.
	 */
	public static final CibleBean ZOOM_POPUP_ADRESSE_CORRESPONDANCE = new CibleBean("correspondance:correspondanceText");
	
	/**
	 * Bouton permettant l'acc�s � l'�cran de MAJ des donn�es client par l'adresse.
	 */
	public static final CibleBean BOUTON_MAJ_ADRESSE = new CibleBean("syntheseForm:adresseLabel:adressePictoMaj");
	
	/**
	 * Lien permettant l'acc�s � l'�cran au ZOOM des donn�es client pour le t�l�phone.
	 */
	public static final CibleBean ZOOM_TELEPHONE = new CibleBean("syntheseForm:telephoneMobileLabel:telephoneMobileLink");
	
	/**
	 * Bloc contenant les informations sur les num�ro de t�l�phone, c'est le contenu du zoom.
	 */
	public static final CibleBean ZOOM_POPUP_TELEPHONE = new CibleBean("zoomAutresNumTel_content");
	
	/**
	 * Lien pour l'acc�s au ZOOM cr�dit (pour le premier dossier du client).
	 */
	public static final CibleBean ZOOM_CREDIT = new CibleBean("syntheseForm:equipementTable:0:nomCreditLink");
	
	/**
	 * Bloc contenant les informations relative au produit, c'est le contenu du zoom cr�dit.
	 */
	public static final CibleBean ZOOM_POPUP_CREDIT = new CibleBean("zoomCredit_content");
	
	/**
	 * Bloc contenant le nom de l'emprunteur
	 */
	public static final CibleBean NOM_EMPRUNTEUR = new CibleBean("syntheseForm:nomEtPrenomLabel:nomText");
	
	/**
	 * Lien pour l'acc�s au ZOOM contrat (pour le premier dossier du client).
	 */
	public static final CibleBean ZOOM_CONTRAT = new CibleBean("syntheseForm:equipementTable:0:numContratLink");
	
	/**
	 * Bloc contenant les informations relative au contrat du client.
	 */
	public static final CibleBean ZOOM_POPUP_CONTRAT = new CibleBean("zoomNumContrat_content");
	
	/**
	 * Lien pour l'acc�s au ZOOM role (pour le premier dossier du client).
	 */
	public static final CibleBean ZOOM_ROLE = new CibleBean("syntheseForm:equipementTable:0:roleClientLink");
	
	/**
	 * Bloc contenant les informations relative aux r�les des clients.
	 */
	public static final CibleBean ZOOM_POPUP_ROLE = new CibleBean("zoomRole_content");
	
	/**
	 * Blocs contenant les �tats des diff�rents dossiers.
	 */
	public static final CibleBean ETAT_DOSSIER = new CibleBean("etat");

	/**
	 * Lien pour l'acc�s au ZOOM mensualit�.
	 */
	public static final CibleBean ZOOM_MENS = new CibleBean("syntheseForm:equipementTable:0:mensMiniLink");
	
	/**
	 * Bloc contenant les informations relative aux informations de pr�l�vement du contrat.
	 */
	public static final CibleBean ZOOM_POPUP_MENS = new CibleBean("zoomMensualite_content");
	
	/**
	 * Bloc contenant les atcualit� GC.
	 */
	public static final CibleBean BLOC_ACTUALITE_GC = new CibleBean("syntheseForm:actualitesGC");
	
	/**
	 * Bloc de l'info RET contenant les informations sur la situation actuelle.
	 */
	public static final CibleBean BLOC_INFO_RET_SITUATION_ACTUELLE = new CibleBean("zoomInfoRecouvrementForm:tableauSituationActuelle:tb");
	
	/**
	 * Shade de l'info RET permettant de fermer la popup.
	 */
	public static final CibleBean QUITTER_INFO_RET = new CibleBean("zoomInfoRecouvrement_shade");
	
	/**
	 * Bouton principal d'acc�s � la MAJ CLIENT.
	 */
	public static final CibleBean BOUTON_MAJ_CLIENT = new CibleBean("majClient");
	
	/**
	 * Picto "crayon" permettant l'acc�s � la MAJ CLIENT � partir de l'adresse. Am�ne � "Moyens de contact"
	 */
	public static final CibleBean PICTO_MODIFICATION_ADRESSE = new CibleBean("syntheseForm:adresseLabel:adressePictoMaj");
	
	/**
	 * Partie de la MAJ CLIENT permettant l'acc�s aux coordonn�es du client.
	 */
	public static final CibleBean BLOC_MODIFICATION_ADRESSE = new CibleBean("majClientForm:contactCoorPanel_header");
	
	/**
	 * Bloc r�serv� � la saisie et la consultation des �v�nement client (r�serv� aux clients au recouvrement?)
	 */
	public static final CibleBean BLOC_EVENEMENT_CLIENT = new CibleBean("majClientForm:evenementPanel");
	
	/**
	 * Bloc r�serv� � la consultation de l'�tat civil du client
	 */
	public static final CibleBean BLOC_ETAT_CIVIL_CLIENT = new CibleBean("majClientForm:etatCivilBlock");
	
	/**
	 * Bouton permettant le retour � la synth�se depuis la MAJ CLIENT, onglet moyens de contact.
	 */
	public static final CibleBean BOUTON_RETOUR_MAJ_CLIENT_CONTACT = new CibleBean("majClientForm:contactRetour");
	
	/**
	 * Bouton permettant le retour � la synth�se depuis la MAJ CLIENT, onglet client.
	 */
	public static final CibleBean BOUTON_RETOUR_MAJ_CLIENT_CLIENT = new CibleBean("majClientForm:clientRetour");
	
	/**
	 * Lien d'acc�s au journal des modifications.
	 */
	public static final CibleBean LIEN_JOURNAL_MODIF = new CibleBean("accesJournalModifsLabel");
	
	/**
	 * Champ de saisie pour le login de l'utilisation du JDM.
	 */
	public static final CibleBean LOGIN_JDM = new CibleBean(Clefs.NAME, "j_username");
	
	/**
	 * Champ de saisie pour le password de l'utilisateur du JDM.
	 */
	public static final CibleBean PASSWORD_JDM = new CibleBean(Clefs.NAME, "j_password");
	
	/**
	 * Bouton de validation de la page de login du JDM.
	 */
	public static final CibleBean VALIDER_LOGIN_JDM = new CibleBean(Clefs.VALEUR, "Valider");
	
	/**
	 * Bloc principal du JDM.
	 */
	public static final CibleBean BLOC_PRINCIPAL_JDM = new CibleBean("journalForm:journalPanel");
}

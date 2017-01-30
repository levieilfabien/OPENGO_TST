package main.bean;

import beans.CasEssaiBean;

/**
 * Classe d'extension des cas d'essai classique pour IZIGATE
 * @author levieilfa
 *
 */
public class CasEssaiOpengoBean extends CasEssaiBean {

	/**
	 * Id de s�rialisation par d�faut.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Le num�ro IUN du dossier saisi
	 */
	private String numeroIUN = "";
	
	/**
	 * distributeur
	 */
	private int distributeur;

	
	public int getDistributeur() {
		return distributeur;
	}

	public void setDistributeur(int distributeur) {
		this.distributeur = distributeur;
	}

	public CasEssaiOpengoBean() {
		super();
	}

	public String getNumeroIUN() {
		return numeroIUN;
	}

	public void setNumeroIUN(String numeroIUN) {
		this.numeroIUN = numeroIUN;
	}

}


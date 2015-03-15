package utilitaire;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Fonction {
	public static boolean verificationNumRegex(String chaine, int num) {
		boolean retour = false;
		Pattern pattern = Pattern.compile("^[0-9]{" + num + "}$");
		Matcher matcher = pattern.matcher(chaine);
		while (matcher.find()) {
			retour = true;
		}
		return retour;
	}

	public static boolean verificationChaineRegex(String chaine, int num) {
		boolean retour = false;
		Pattern pattern = Pattern.compile("^[A-z0-9]{" + num + "}$");
		Matcher matcher = pattern.matcher(chaine);
		while (matcher.find()) {
			retour = true;
		}
		return retour;
	}

	public static String generateString(int num, String base) {
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuffer pass = new StringBuffer();
		for (int x = 0; x < num; x++) {
			int i = (int) Math.floor(Math.random() * (chars.length() - 1));
			pass.append(chars.charAt(i));
		}
		String retour = base + "" + pass;
		return retour;
	}

	public static String hashMotDePasse(String motDePasse)
			throws NoSuchAlgorithmException {

		String salt = "48@!alsd";
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		String motDePasseSalt = motDePasse + "" + salt;
		md.update(motDePasseSalt.getBytes());

		byte byteData[] = md.digest();

		// convert the byte to hex format method 1
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
					.substring(1));
		}

		return sb.toString();
	}

}

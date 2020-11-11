package models;

public enum PotionsEnum {

	YELLOW_POTION("/img/remedyRed.png"), GREEN_POTION("/img/remedyGreen.png");

	private final String pathImg;
	
	private PotionsEnum(String pathImg) {
		this.pathImg = pathImg;
	}

	public String getPathImg() {
		return pathImg;
	}
}
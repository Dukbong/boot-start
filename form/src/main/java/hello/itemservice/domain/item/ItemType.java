package hello.itemservice.domain.item;

public enum ItemType {

	BOOK("도서"),
	FOOD("음식"),
	ETC("기타");
	
	private final String description; // Enum의 키(BOOk이나 FOOD, ETC)가 담긴다 
	
	ItemType(String description){
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	
}

package hello.core.singleton;

public class StatefulService {
	private int price;
	
	public void order(String name, int price) {
		System.out.println("name = " + name + ", price = " + price);
		this.price = price; // 여기가 문제.
	}
	
	 public int getPrice() {
		 return price;
	 }
	 
	 // 위에서 생길 수 있는 싱글톤 문제점 공유 필드를 고친 코드
	 public int order2(String name, int price) {
		 System.out.println("name = " + name + ", price = " + price);
		 return price;
	 }
}

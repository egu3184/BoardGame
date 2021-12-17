package com.egu.boot.BoardGame.handler;

public enum Fruit {

	Apple("사과", "red"), Banana("바나나","red"), peach("복숭아", "어..?");
	
	private String name;
	
	private String color;
	
	private Fruit(String name, String color) {
		this.name = name;
		this.color = color;
	}

	public static void main(String[] args) {
		
		System.out.println(Fruit.Apple.name);
		
	}


}

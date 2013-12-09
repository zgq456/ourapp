package com.example.ourapp;

public class Picture {
	private int picture[] = new int[] { R.drawable.img1, R.drawable.img2,
			R.drawable.img3, R.drawable.img4, R.drawable.img5, R.drawable.img6,
			R.drawable.img7, R.drawable.img8, R.drawable.img9,
			R.drawable.img10, R.drawable.img11 };

	public Picture() {
	}

	public int getPicture(int num) {
		return picture[num];

	}

}

package com.example.goodhabits;

public class ProfilePicture {
    int pic;

    public ProfilePicture(int picture) {
        switch (picture){
            case 1:
                pic = R.drawable.profile_image_foreground;
                break;
            case 2:
                pic = R.drawable.player1;
                break;
            case 3:
                pic = R.drawable.player2a;
                break;
            case 4:
                pic = R.drawable.player3a;
                break;
            case 5:
                pic = R.drawable.player4a;
                break;
        }

    }
}

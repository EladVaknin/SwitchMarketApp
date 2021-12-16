package com.example.swichmarketapp.utlities;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

public class Utilitie {

    public static boolean isEmailAndPasswordValid(Context context, String email, String password) {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(context, "Empty password or email", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.length() < 8) {
            Toast.makeText(context, "password length must be bigger than 8", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }
}

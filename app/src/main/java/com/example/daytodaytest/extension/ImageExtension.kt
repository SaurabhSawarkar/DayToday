package com.example.daytodaytest.extension

import android.widget.ImageView
import com.example.daytodaytest.R
import com.squareup.picasso.Picasso


fun ImageView.loadUrl(picasso: Picasso, url: String) =
    picasso.load(url).fit().error(R.drawable.bean).into(this)
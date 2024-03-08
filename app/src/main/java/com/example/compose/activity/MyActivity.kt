package com.example.compose.activity

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.compose.R
import com.example.compose.fragment.AddressBookFragment
import com.example.compose.fragment.MyFragment

class MyActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       setContentView(R.layout.activity_my)
       supportFragmentManager
            .beginTransaction()
           // .add(R.id.container, MyFragment())
            .add(R.id.container, AddressBookFragment())
            .commit()
    }
}



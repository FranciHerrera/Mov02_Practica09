package com.example.mov2_practica09

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.barraMenu)
        setSupportActionBar(toolbar)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent: Intent?
        when(item.itemId){
            R.id.itmFormParques -> {
                intent = Intent(applicationContext, ParquesActivity::class.java)
                startActivity(intent)
            }
            R.id.itmFormGuardabosques -> {
                intent = Intent(applicationContext, RangerActivity::class.java)
                startActivity(intent)
            }
            R.id.itmListParques -> {
                intent = Intent(applicationContext, ParquesListActivity::class.java)
                startActivity(intent)
            }
            R.id.itmListGuardaBosques -> {
                intent = Intent(applicationContext, RangerListActivity::class.java)
                startActivity(intent)
            }
            R.id.itmExit -> {
                intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
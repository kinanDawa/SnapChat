package kenan.com.snapchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth

class SnapActivity : AppCompatActivity() {

    val auth=FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snap)
    }

    override fun onCreateOptionsMenu(menu: Menu?):Boolean {
        val inflater=menuInflater
        inflater.inflate(R.menu.snaps,menu)
       return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item?.itemId==R.id.createSnap){
            val intent=Intent(this,CreateSnapActivity::class.java)
            startActivity(intent)
        }else  if (item?.itemId==R.id.logout ){
            auth.signOut()
            finish() // finish the activity and  back to login Screen
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        auth.signOut()
        super.onBackPressed()
    }
}
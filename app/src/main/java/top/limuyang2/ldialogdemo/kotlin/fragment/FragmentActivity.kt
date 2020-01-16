package top.limuyang2.ldialogdemo.kotlin.fragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import top.limuyang2.ldialogdemo.R

/**
 *
 * Date 2018/7/3
 * @author limuyang
 */
class FragmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentLayout, MyFragment())
                .commit()
    }

}
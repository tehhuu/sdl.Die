package jp.ac.titech.itpro.sdl.die

import android.opengl.GLSurfaceView
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity(), OnSeekBarChangeListener {
    private var glView: GLSurfaceView? = null
    private var renderer: SimpleRenderer? = null
    private var cube: Cube? = null
    private var pyramid: Pyramid? = null
    private var angle = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        setContentView(R.layout.activity_main)
        glView = findViewById(R.id.gl_view)
        val seekBarX = findViewById<SeekBar>(R.id.seekbar_x)
        val seekBarY = findViewById<SeekBar>(R.id.seekbar_y)
        val seekBarZ = findViewById<SeekBar>(R.id.seekbar_z)
        seekBarX.max = 360
        seekBarY.max = 360
        seekBarZ.max = 360
        seekBarX.setOnSeekBarChangeListener(this)
        seekBarY.setOnSeekBarChangeListener(this)
        seekBarZ.setOnSeekBarChangeListener(this)
        renderer = SimpleRenderer()
        cube = Cube()
        pyramid = Pyramid()
        renderer!!.setObj(cube)
        glView!!.setRenderer(renderer)

        //X, Y方向に一定間隔で一定角度ずつ回転させる。
        val timer = Timer(false)
        val task: TimerTask = object : TimerTask() {
            override fun run() {
                angle += 10
                angle %= 360
                seekBarX.progress = angle
                seekBarY.progress = angle
            }
        }
        timer.schedule(task, 0, 100)
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
        glView!!.onResume()
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
        glView!!.onPause()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        Log.d(TAG, "onCreateOptionsMenu")
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(TAG, "onOptionsItemSelected")
        when (item.itemId) {
            R.id.menu_cube -> renderer!!.setObj(cube)
            R.id.menu_pyramid -> renderer!!.setObj(pyramid)
        }
        return true
    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        when (seekBar.id) {
            R.id.seekbar_x -> renderer!!.rotateObjX(progress.toFloat())
            R.id.seekbar_y -> renderer!!.rotateObjY(progress.toFloat())
            R.id.seekbar_z -> renderer!!.rotateObjZ(progress.toFloat())
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {}
    override fun onStopTrackingTouch(seekBar: SeekBar) {}

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}
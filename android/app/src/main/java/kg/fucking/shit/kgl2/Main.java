/*          Copyright © 2014 Stanislav Petiakov
// Distributed under the Boost Software License, Version 1.0.
//    (See accompanying file LICENSE_1_0.txt or copy at
//          http://www.boost.org/LICENSE_1_0.txt)
*/
package kg.fucking.shit.kgl2;

/**
 * Created by 4eRT on 28.12.2014.
 */
import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.WindowManager;

public class Main extends Activity {
    private GLSurfaceView openGLContent;
    OpenGLRenderer openGL;
    float lastX = -1;   // last touch screen x coord
    float lastY = -1;   // last touch screen y coord
    short counter = 0;  // counter for multiclick

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        openGLContent = new GLSurfaceView(this);
        openGL = new OpenGLRenderer(this);
        openGLContent.setRenderer(openGL);
        this.setContentView(openGLContent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        openGLContent.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        openGLContent.onResume();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                    // get current coords
                    lastX = event.getX();
                    lastY = event.getY();

                    counter++;  // increase counter

                    if (counter == 1) { // reset it after 500ms
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                counter = 0;
                            }
                        }, 500);
                    }

                    if (counter > 2) {  // we have multiclick (>=3) in 500ms
                        counter = 0;    // reset
                        openGL.changeScene();    // change scene
                        openGLContent.requestRender();  // redraw
                    }
                    return true;
            case MotionEvent.ACTION_MOVE:
                boolean thresholdX = Math.abs(lastX - event.getX()) > 10;   // threshold by x
                boolean thresholdY = Math.abs(lastY - event.getY()) > 30;   // by y

                boolean left = lastX - event.getX() > 0;    // is it left or right?
                boolean up = lastY - event.getY() > 0;      // is it up or down?
                openGL.setDegree(thresholdX, left, thresholdY, up); // update our scene
                openGLContent.requestRender();

                if (thresholdX) // if event occurred set new last x
                    lastX = event.getX();

                if (thresholdY) // and y
                    lastY = event.getY();
                return true;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();

        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:   // zoom in on vol. up
                if (action == KeyEvent.ACTION_DOWN) {
                    openGL.setZoom(true);
                    openGLContent.requestRender();
                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:   // zoom out on vol. down
                if (action == KeyEvent.ACTION_DOWN) {
                    openGL.setZoom(false);
                    openGLContent.requestRender();
                }
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }
}
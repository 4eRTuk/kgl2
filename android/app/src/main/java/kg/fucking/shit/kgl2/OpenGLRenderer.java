/*          Copyright © 2014 Stanislav Petiakov
// Distributed under the Boost Software License, Version 1.0.
//    (See accompanying file LICENSE_1_0.txt or copy at
//          http://www.boost.org/LICENSE_1_0.txt)
*/
package kg.fucking.shit.kgl2;

/**
 * Created by 4eRT on 28.12.2014.
 */
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.widget.Toast;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

// simple OpenGL renderer
public class OpenGLRenderer implements GLSurfaceView.Renderer {
    Context context;

    private float degreeX = 30; // basic degree to rotate around x-axis
    private float degreeY = 45; // basic degree to rotate around y-axis
    private float zoom = -50.0f;    // basic zoom-out by z-axis

    private boolean insideScene = false;   // show true=inside or false=outside
    private boolean light = true;   // enable/disable light
    int houseW, houseH, houseD, groundW, groundD;   // default house and ground dims

    Group scene;    // our scene

    // data and buffers for lighting
    private float[] lightAmbient = {0.8f, 0.8f, 0.8f, 1.0f};
    private float[] lightDiffuse = {1.0f, 1.0f, 1.0f, 1.0f};
    private float[] lightPosition = {8.0f, 4.0f, 4.0f, 1.0f};

    public FloatBuffer lightAmbientBuffer;
    public FloatBuffer lightDiffuseBuffer;
    public FloatBuffer lightPositionBuffer;

    public OpenGLRenderer(Context context) {
        this.context = context;
        scene = new Group();

        houseH = houseD = 10;
        houseW = 20;
        groundD = groundW = 50;

        if (insideScene)
            loadInside();
        else
            loadOutside();
    }

    // loads inside scene
    private void loadInside() {
        scene.clear();

        // init light
        ByteBuffer byteBuf = ByteBuffer.allocateDirect(lightAmbient.length * 4);
        byteBuf.order(ByteOrder.nativeOrder());
        lightAmbientBuffer = byteBuf.asFloatBuffer();
        lightAmbientBuffer.put(lightAmbient);
        lightAmbientBuffer.position(0);

        byteBuf = ByteBuffer.allocateDirect(lightDiffuse.length * 4);
        byteBuf.order(ByteOrder.nativeOrder());
        lightDiffuseBuffer = byteBuf.asFloatBuffer();
        lightDiffuseBuffer.put(lightDiffuse);
        lightDiffuseBuffer.position(0);

        byteBuf = ByteBuffer.allocateDirect(lightPosition.length * 4);
        byteBuf.order(ByteOrder.nativeOrder());
        lightPositionBuffer = byteBuf.asFloatBuffer();
        lightPositionBuffer.put(lightPosition);
        lightPositionBuffer.position(0);

        // load and initialize meshes
        InDaHouse inside = new InDaHouse(houseW, houseH, houseD);
        Bitmap wall = BitmapFactory.decodeResource(context.getResources(), R.drawable.wallpaper_37);
        inside.loadBitmap(wall);

        Plane floor = new Plane(houseW, houseD);
        Bitmap floor_b = BitmapFactory.decodeResource(context.getResources(), R.drawable.bark_6);
        floor.loadBitmap(floor_b);

        Plane ceil = new Plane(houseW, houseD);
        ceil.setColor(200/255.0f, 215/255.0f, 1.0f, 0);
        ceil.y = houseH;

        Plane window_1 = new Plane(houseW / 6, houseH / 3);
        Bitmap window_b = BitmapFactory.decodeResource(context.getResources(), R.drawable.window_1);
        window_1.setWidthFilter(GL10.GL_CLAMP_TO_EDGE);
        window_1.setHeightFilter(GL10.GL_CLAMP_TO_EDGE);
        window_1.loadBitmap(window_b);
        window_1.rx = 90;
        window_1.x = -5;
        window_1.y = -houseH / 2;
        window_1.z = houseD / 2 - 0.1f;

        Plane window_2 = new Plane(houseW / 6, houseH / 3);
        window_2.loadBitmap(window_b);
        window_2.x = 10;

        Plane window_3 = new Plane(houseW / 6, houseH / 3);
        window_3.loadBitmap(window_b);
        window_3.y = -houseH + 0.2f;

        Plane window_4 = new Plane(houseW / 6, houseH / 3);
        window_4.loadBitmap(window_b);
        window_4.x = -5;

        Plane window_5 = new Plane(houseW / 6, houseH / 3);
        window_5.loadBitmap(window_b);
        window_5.x = -5;

        Plane door = new Plane(houseW / 5, houseH / 2 + houseH / 4);
        Bitmap door_b = BitmapFactory.decodeResource(context.getResources(), R.drawable.door_1);
        door.loadBitmap(door_b);
        door.y = houseH - 0.2f;
        door.x = 5;
        door.z = houseD / 4 - 0.4f;

        Plane map = new Plane(2.5f, 5);
        Bitmap map_b = BitmapFactory.decodeResource(context.getResources(), R.drawable.map_1);
        map.loadBitmap(map_b);
        map.rx = 90;
        map.rz = 90;
        map.x = -houseW /2 + 0.2f;
        map.y = -houseH /2;
        map.z = -houseD /4;

        Plane rug = new Plane(4, 6);
        Bitmap table_b = BitmapFactory.decodeResource(context.getResources(), R.drawable.rug_17);
        rug.loadBitmap(table_b);
        rug.rz = 90;
        rug.rx = 90;
        rug.x = houseH /2 + 0.2f;
        rug.y = -houseW /2;
        rug.z = -houseD /2;

        Sofa sofa = new Sofa(houseW, houseH, houseD);
        Bitmap sofa_b = BitmapFactory.decodeResource(context.getResources(), R.drawable.fabric_8);
        sofa.loadBitmap(sofa_b);
        sofa.ry = 270;
        sofa.x = houseD-1;
        sofa.z = -houseW/2;

        // add to scene
        scene.add(inside);
        scene.add(floor);
        scene.add(ceil);
        scene.add(window_1);
        scene.add(window_2);
        scene.add(window_3);
        scene.add(window_4);
        scene.add(window_5);
        scene.add(door);
        scene.add(map);
        scene.add(rug);
        scene.add(sofa);
    }

    // loads outside scene
    private void loadOutside() {
        scene.clear();

        // load and initialize meshes
        Plane ground = new Plane(groundW, groundD);
        Bitmap grass = BitmapFactory.decodeResource(context.getResources(), R.drawable.ground_17);
        ground.loadBitmap(grass);
        ground.setTextureZoom(4.0f);

        HouseBlock house = new HouseBlock(houseW, houseH, houseD);
        Bitmap bricks = BitmapFactory.decodeResource(context.getResources(), R.drawable.brick_4);
        house.loadBitmap(bricks);

        Roof roof = new Roof(houseW, houseH, houseD);
        Bitmap roof_b = BitmapFactory.decodeResource(context.getResources(), R.drawable.roof_1);
        roof.loadBitmap(roof_b);

        Plane window_1 = new Plane(houseW / 6, houseH / 3);
        Bitmap window_b = BitmapFactory.decodeResource(context.getResources(), R.drawable.window_1);
        window_1.setWidthFilter(GL10.GL_CLAMP_TO_EDGE);
        window_1.setHeightFilter(GL10.GL_CLAMP_TO_EDGE);
        window_1.loadBitmap(window_b);
        window_1.rx = 90;
        window_1.x = -5;
        window_1.y = houseH / 2;
        window_1.z = houseD / 2 + 0.1f;

        Plane window_2 = new Plane(houseW / 6, houseH / 3);
        window_2.loadBitmap(window_b);
        window_2.x = 10;

        Plane window_3 = new Plane(houseW / 6, houseH / 3);
        window_3.loadBitmap(window_b);
        window_3.y = -houseH - 0.2f;

        Plane window_4 = new Plane(houseW / 6, houseH / 3);
        window_4.loadBitmap(window_b);
        window_4.x = -5;

        Plane window_5 = new Plane(houseW / 6, houseH / 3);
        window_5.loadBitmap(window_b);
        window_5.x = -5;

        Plane door = new Plane(houseW / 5, houseH / 2 + houseH / 4);
        Bitmap door_b = BitmapFactory.decodeResource(context.getResources(), R.drawable.door_1);
        door.loadBitmap(door_b);
        door.y = houseH + 0.2f;
        door.x = 5;
        door.z = houseD / 4 - 0.4f;

        Plane road = new Plane(houseW / 2, groundD / 2 - houseD / 2);
        Bitmap road_b = BitmapFactory.decodeResource(context.getResources(), R.drawable.road_brick_1);
        road.loadBitmap(road_b);
        road.setTextureZoom(5);
        road.rx = 90;
        road.y = houseD;
        road.z = houseD / 4 + houseD / 8 + 0.3f;

        // add to scene
        scene.add(ground);
        scene.add(house);
        scene.add(roof);
        scene.add(window_1);
        scene.add(window_2);
        scene.add(window_3);
        scene.add(window_4);
        scene.add(window_5);
        scene.add(door);
        scene.add(road);
    }

    // Call back when the surface is first created or re-created
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);  // Set color's clear-value to black
        gl.glClearDepthf(1.0f);            // Set depth's clear-value to farthest
        gl.glEnable(GL10.GL_DEPTH_TEST);   // Enables depth-buffer for hidden surface removal
        gl.glDepthFunc(GL10.GL_LEQUAL);    // The type of depth testing to do
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);  // nice perspective view
        gl.glShadeModel(GL10.GL_SMOOTH);   // Enable smooth shading of color
        gl.glDisable(GL10.GL_DITHER);      // Disable dithering for better performance
    }

    // Call back after onSurfaceCreated() or whenever the window's size changes
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        if (height == 0) height = 1;   // To prevent divide by zero
        float aspect = (float)width / height;

        // Set the viewport (display area) to cover the entire window
        gl.glViewport(0, 0, width, height);
        // Setup perspective projection, with aspect ratio matches viewport
        gl.glMatrixMode(GL10.GL_PROJECTION); // Select projection matrix
        gl.glLoadIdentity();                 // Reset projection matrix
        GLU.gluPerspective(gl, 45, aspect, 0.1f, 100.f);    // Use perspective projection
        gl.glMatrixMode(GL10.GL_MODELVIEW);  // Select model-view matrix
        gl.glLoadIdentity();                 // Reset
    }

    // Call back to draw the current frame.
    @Override
    public void onDrawFrame(GL10 gl) {
        // Clear color and depth buffers using clear-values set earlier
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();                // Reset model-view matrix

        if (insideScene) { // fixed scene for inside
            gl.glTranslatef(0.0f, -5.5f, -12.5f);
            gl.glRotatef(15, 1.0f, 0.0f, 0.0f);
            gl.glRotatef(-116, 0.0f, 1.0f, 0.0f);
        } else {    // rotate and zoom out for outside
            gl.glTranslatef(0.0f, 0.0f, zoom);
            gl.glRotatef(degreeX, 1.0f, 0.0f, 0.0f);
            gl.glRotatef(degreeY, 0.0f, 1.0f, 0.0f);
        }

        if(insideScene && light) {  // we need to enable light
            gl.glEnable(GL10.GL_LIGHTING);
            gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_AMBIENT, lightAmbientBuffer);
            gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_DIFFUSE, lightDiffuseBuffer);
            gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_POSITION, lightPositionBuffer);
            gl.glEnable(GL10.GL_LIGHT1);    // use light1
        }
        else
            gl.glDisable(GL10.GL_LIGHTING);

        scene.draw(gl);
    }

    public void setDegree(boolean thresholdX, boolean left, boolean thresholdY, boolean up) {
        if (insideScene) return;    // disable for inside

        if (thresholdX) // increase/decrease y by 5 if it is over threshold
            if (left)
                this.degreeY -= 5;
            else
                this.degreeY += 5;

        if (thresholdY) // same for x
            if(up)
                degreeX -= 5;
            else
                degreeX += 5;
    }

    public void setZoom(boolean zoomIn) {
        if (insideScene)    // switch light for inside
            if (zoomIn)
                light = false;
            else
                light = true;
        else
            if (zoomIn) // set zoom for outside
                if (zoom <= -10.0f) zoom += 5; else;    // with bounds
            else
                if (zoom >= -75.0f) zoom -= 5; else;
    }

    public void changeScene() {
        insideScene = !insideScene;

        if (insideScene) {  // change scene and show toast
            loadInside();
            Toast.makeText(context, R.string.hi, Toast.LENGTH_SHORT).show();
        }
        else {
            loadOutside();
            Toast.makeText(context, R.string.bye, Toast.LENGTH_SHORT).show();
        }
    }
}
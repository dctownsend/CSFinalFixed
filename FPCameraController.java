/*
 * file: FinalProject.java
 * author: Sebastian, Diane, Nghi, Dakota
 * group: Synchronized Broncos
 * class: CS4450 - Computer Graphic
 *
 * assignment: Semester Project
 * date last modified: 3/28/2019
 *
 * purpose: this class draw out the 3d cube and control the camera according to
 * the input of the user.
 */

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.Sys;
import org.lwjgl.util.Point;

//This is our First Person Camera Controller class - handles motion, user input
public class FPCameraController {
    private Vector3Float position;
    private Vector3Float lPosition;
    private Vector3Float me;
    
    private float yaw;
    private float pitch;
    
    private Chunk chunkObject;
    private boolean firstTime;
    private boolean firstRender;
    private boolean spaceLimited;
    
    double[][] height = null;
    private final int UPPER_X_BOUND = 2;
    private final int LOWER_X_BOUND = -5;
    private final int UPPER_Y_BOUND = -3;
    private final int LOWER_Y_BOUND = 2;
    private final int UPPER_Z_BOUND = 2;
    private final int LOWER_Z_BOUND = -5;
    private int xGrid;
    private int zGrid;
    
    /**
     * Takes in x, y, z coordinates and creates new camera positions 
     * as well as settings the yaw and pitch of camera.
     * @param x
     * @param y
     * @param z 
     */
    public FPCameraController(float x, float y, float z) {
        position = new Vector3Float(25.91f, -35.7f, 28.53f);
        
        lPosition = new Vector3Float(x, y, z);
        lPosition.setX(0f);
        lPosition.setY(15f);
        lPosition.setZ(0f);
        
        yaw = 0.0f;
        pitch = 80.0f;
        firstRender = true;
        //chunkObject = new Chunk((int)x,(int)y,(int)z);
    }
    /**
     * Allows user to use mouse to move camera around horizontally
     * @param amount 
     */
    public void yaw(float amount) {
        yaw += amount;
    }
    
    /**
     * Allows user to use mouse to move camera vertically.
     * @param amount 
     */
    public void pitch(float amount) {
        pitch -= amount;
    }
    
    /**
     * Allows user to camera forward. Input distance is default movement speed for move
     * and strafe functions.
     * @param distance 
     * @param limited 
     */
    public void walkForward(float distance, boolean limited) {
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw));
        float zOffset = distance * (float)Math.cos(Math.toRadians(yaw));
        
        float newX = position.getX() - xOffset;
        float newZ = position.getZ() + zOffset;
        
        if (limited == true) {
            if (newX < 60 && newX > 0) {
                position.setX(newX);
            } else if (newX < 0) {
                position.setX(0);
            } else {
                position.setX(60);
            }
            if (newZ < 60 && newZ > 0) {
                position.setZ(newZ);
            } else if (newZ < 0) {
                position.setZ(0);
            } else {
                position.setZ(60);
            }
        } else {        
            position.setX(newX);
            position.setZ(newZ);
        }
        FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4);
        lightPosition.put(lPosition.x-=xOffset).put(
        lPosition.y).put(lPosition.z+=zOffset).put(1.0f).flip();
        glLight(GL_LIGHT0, GL_POSITION, lightPosition);
    }
    
    /**
     * Allows user to move camera backward. 
     * @param distance 
     * @param limited 
     */
    public void walkBackwards(float distance, boolean limited) {
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw));
        float zOffset = distance * (float)Math.cos(Math.toRadians(yaw));
        
        float newX = position.getX() + xOffset;
        float newZ = position.getZ() - zOffset;
        
        if (limited == true) {
            if (newX < 60 && newX > 0) {
                position.setX(newX);
            } else if (newX < 0) {
                position.setX(0);
            } else {
                position.setX(60);
            }
            if (newZ < 60 && newZ > 0) {
                position.setZ(newZ);
            } else if (newZ < 0) {
                position.setZ(0);
            } else {
                position.setZ(60);
            }
        } else {        
            position.setX(newX);
            position.setZ(newZ);
        }
         FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4);
        lightPosition.put(lPosition.x-=xOffset).put(
        lPosition.y).put(lPosition.z+=zOffset).put(1.0f).flip();
        glLight(GL_LIGHT0, GL_POSITION, lightPosition);
    }
    
    /**
     * Allows user to move camera to the leftward.
     * @param distance
     * @param limited
     */
    public void strafeLeft(float distance, boolean limited) {
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw-90));
        float zOffset = distance * (float)Math.cos(Math.toRadians(yaw-90));
        
        float newX = position.getX() - xOffset;
        float newZ = position.getZ() + zOffset;
        
        if (limited == true) {
            if (newX < 60 && newX > 0) {
                position.setX(newX);
            } else if (newX < 0) {
                position.setX(0);
            } else {
                position.setX(60);
            }
            if (newZ < 60 && newZ > 0) {
                position.setZ(newZ);
            } else if (newZ < 0) {
                position.setZ(0);
            } else {
                position.setZ(60);
            }
        } else {        
            position.setX(newX);
            position.setZ(newZ);
        }
         FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4);
        lightPosition.put(lPosition.x-=xOffset).put(
        lPosition.y).put(lPosition.z+=zOffset).put(1.0f).flip();
        glLight(GL_LIGHT0, GL_POSITION, lightPosition);
    }
    
    /**
     * Allows user to move camera rightward.
     * @param distance 
     * @param limited 
     */
    public void strafeRight(float distance, boolean limited) {
        //if(position.getX() < 60 && position.getX() > 0 && position.getY() < 60 && position.getY() < 0){
        float xOffset = distance * (float) Math.sin(Math.toRadians(yaw + 90));
        float zOffset = distance * (float) Math.cos(Math.toRadians(yaw + 90));
        
        float newX = position.getX() - xOffset;
        float newZ = position.getZ() + zOffset;
        
        if (limited == true) {
            if (newX < 60 && newX > 0) {
                position.setX(newX);
            } else if (newX < 0) {
                position.setX(0);
            } else {
                position.setX(60);
            }
            if (newZ < 60 && newZ > 0) {
                position.setZ(newZ);
            } else if (newZ < 0) {
                position.setZ(0);
            } else {
                position.setZ(60);
            }
        } else { 
            position.setX(newX);
            position.setZ(newZ);
        }
        FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4);
        lightPosition.put(lPosition.x-=xOffset).put(
        lPosition.y).put(lPosition.z+=zOffset).put(1.0f).flip();
        glLight(GL_LIGHT0, GL_POSITION, lightPosition);

    }
    
    /**
     * Allows user to move camera upwards.
     * @param distance 
     * @param limited 
     */
    public void moveUp(float distance, boolean limited) {
        
        if (limited == true) {
            if (position.getY() > -15) {
                position.setY(position.getY() - distance);
            }
        } else {
            position.setY(position.getY() - distance);

        }
    }
    
    /**
     * Allows user to move camera downwards.
     * @param distance 
     * @param limited 
     */
    public void moveDown(float distance, boolean limited) {
        if (limited == true) {
            if (position.getY() < 15) {
                position.setY(position.getY() + distance);
            }
        }else {
            position.setY(position.getY() + distance);
        }
    }
    
    /**
     * Reset the position when the space become limited.
     */
    public void resetPosition(){
        //reset x-position if out of bound
        if (position.getX() > 60) {
            position.setX(60);
        } else if (position.getX() < 0) {
            position.setX(0);
        }

        //reset z-position if out of bound
        if (position.getZ() > 60) {
            position.setZ(60);
        } else if (position.getZ() < 0) {
            position.setZ(0);
        }
        System.out.println("y: " + position.getY());
        if(position.getY() > 15){
            position.setY(15);
            System.out.println("y1: " + position.getY());
        }else if( position.getY() < -15){
            position.setY(-15);
            System.out.println("y2: " + position.getY());
        }
    }
    
    /**
     * Translates and rotates matrix so that it looks through the camera.
     */
    public void lookThrough() {
        glRotatef(pitch, 1.0f, 0.0f, 0.0f);
        glRotatef(yaw, 0.0f, 1.0f, 0.0f);
        glTranslatef(position.getX(), position.getY(), position.getZ());
        
        FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4);
        lightPosition.put(lPosition.getX()).put(
        lPosition.getY()).put(lPosition.getZ()).put(1.0f).flip();
        glLight(GL_LIGHT0, GL_POSITION, lightPosition);
    }
    
    /**
     * Main loop of the program, calls all movement and camera movement functions.
     * Sets and controls values such as distance of movement, mouse sensitivity, and movement speed.
     * Locks mouse into window and removes cursor. Calls render() to render objects. Loop will close
     * and program will close when user clicks Close or the X on window, or if user presses ESC.
     */
    public void gameLoop() {
        FPCameraController camera = new FPCameraController(0, 0, 0);
        
        float dx = 0.0f;
        float dy = 0.0f;
        float dt = 0.0f;
        float lastTime = 0.0f; 
        long time = 0;
        float mouseSensitivity = 0.09f;
        float movementSpeed = .35f;
        spaceLimited = false; //free to move everywhere

        
        Mouse.setGrabbed(true);
        
        while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            time = Sys.getTime();
            lastTime = time;
            
            dx = Mouse.getDX();
            dy = Mouse.getDY();
            
            camera.yaw(dx * mouseSensitivity);
            camera.pitch(dy * mouseSensitivity);
            
            if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
                camera.walkForward(movementSpeed, spaceLimited);
            }
            
            if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
                camera.walkBackwards(movementSpeed, spaceLimited);
            }
            
            if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
                camera.strafeLeft(movementSpeed, spaceLimited);
            }
        
            if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
                camera.strafeRight(movementSpeed, spaceLimited);
            }
    
            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
                camera.moveUp(movementSpeed, spaceLimited);
            }

            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                camera.moveDown(movementSpeed, spaceLimited);
            }
            
            glLoadIdentity();
            camera.lookThrough();
            glEnable(GL_DEPTH_TEST); //had to use this. If not enabled cube shows other side's colors and looks weird.
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            
            if(firstRender) {
                chunkObject = new Chunk(0,7,0);
                firstRender = false;
            }else if(Keyboard.isKeyDown(Keyboard.KEY_F1)){
                chunkObject = new Chunk(0,7,0);
            }  
            chunkObject.render();
            
            //set spacelimited
            if (Keyboard.isKeyDown(Keyboard.KEY_L)) {
                spaceLimited = true;
                resetPosition();
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_U)) {
                spaceLimited = false;

            }
            Display.update();
            Display.sync(60);
        }
        Display.destroy();
    }
    
     private boolean isInboundsY(float newY) {
        return !(newY > UPPER_Y_BOUND && newY < LOWER_Y_BOUND);
    }

    private boolean noCollisionYUp(float newY) {
        xGrid = (int) (position.x / .1);
        zGrid = (int) (position.z / .1);

        if (xGrid > -30 && xGrid < 1 && zGrid > -30 && zGrid < 1) {
            xGrid = Math.abs(xGrid);
            zGrid = Math.abs(zGrid);
            if (position.y > 0 && newY < 0) {
                return true;
            }
        }
        return false;
    }
    
    

    private boolean noCollisionYDown(float newY) {
        xGrid = (int) (position.x /.1);
        zGrid = (int) (position.z /.1 );

        if (xGrid > -30 && xGrid < 1 && zGrid > -30 && zGrid < 1) {
            xGrid = Math.abs(xGrid);
            zGrid = Math.abs(zGrid);
            if (position.y < height[xGrid][zGrid] && newY > height[xGrid][zGrid]) {
                return false;
            }
        }
        return true;
    }

    private boolean isInboundsXZ(float newX, float newZ) {
        return newX < UPPER_X_BOUND && newX > LOWER_X_BOUND
                && newZ < UPPER_Z_BOUND && newZ > LOWER_Z_BOUND;
    }

    private boolean noCollisionXZ(float newX, float newZ) {
        xGrid = (int) (newX / .1);
        zGrid = (int) (newZ / .1);

        if (xGrid > -20 && xGrid < 1 && zGrid > -20 && zGrid < 1) {
            xGrid = Math.abs(xGrid);
            zGrid = Math.abs(zGrid);
            if (position.y < 0 && position.y > height[xGrid][zGrid]) {
                return false;
            }
        }
        return true;
    }
    
    void tree(float x1,float y1, float x2,float y2,float angle, int n, int branchCount) {
    glBegin(GL_LINE_STRIP);
    glVertex2f(x1,y1); //root
    glVertex2f(x2,y2); //tree top 
    
     if(n<1)return ;
     int nn = n-1;
     float treeRatio = (float)0.5;
     
     float x3=(x2-x1)*treeRatio+x1-x2;
     float y3=(y2-y1)*treeRatio+y1-y2; 
     if(branchCount==2){
        tree(x2, y2, (float)(x3 * cos(angle) + y3 * sin(angle) + x2), (float)(x3 * sin(angle) + y3 * cos(angle) + y2), angle, nn, branchCount);
        tree(x2, y2, (float)(x3 * cos(-angle) + y3 * sin(-angle) + x2), (float)(x3 * sin(-angle) + y3 * cos(-angle) + y2),  angle, nn, branchCount);
        glEnd();
    }
}

    private void render() {
        try {
            glBegin(GL_QUADS);
            chunkObject.draw();
            float x=15, y=-20, x1=16, y1=-23;
            float a = 15;
            int num = 2, bc=2;
            tree(x, y, x1, y1, a, num, bc);
        } catch (Exception e) {
            e.printStackTrace();
            
      }
    }
}
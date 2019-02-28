import sys

try:
    from OpenGL.GLUT import *
    from OpenGL.GL import *
    from OpenGL.GLU import *
    from OpenGL.GL import glOrtho
    from OpenGL.GLU import gluPerspective
    from OpenGL.GL import glRotated
    from OpenGL.GL import glTranslated
    from OpenGL.GL import glLoadIdentity
    from OpenGL.GL import glMatrixMode
    from OpenGL.GL import GL_MODELVIEW
    from OpenGL.GL import GL_PROJECTION
    import math
except:
    print("ERROR: PyOpenGL not installed properly. ")

DISPLAY_WIDTH = 500.0
DISPLAY_HEIGHT = 500.0
mat=[0,-5,-35]
rot=[0,0,0]

animTrans=[0,0,0]
animRot=[0,0,0]

def init(): 
    glClearColor (0.0, 0.0, 0.0, 0.0)
    glShadeModel (GL_FLAT)
    glMatrixMode(GL_PROJECTION)
    glLoadIdentity()
    gluPerspective(50,DISPLAY_HEIGHT/DISPLAY_WIDTH,.1,100)

def drawHouse ():
    glLineWidth(2.5)
    glColor3f(1.0, 0.0, 0.0)
    #Floor
    glBegin(GL_LINES)
    glVertex3f(-5.0, 0.0, -5.0)
    glVertex3f(5, 0, -5)
    glVertex3f(5, 0, -5)
    glVertex3f(5, 0, 5)
    glVertex3f(5, 0, 5)
    glVertex3f(-5, 0, 5)
    glVertex3f(-5, 0, 5)
    glVertex3f(-5, 0, -5)
    #Ceiling
    glVertex3f(-5, 5, -5)
    glVertex3f(5, 5, -5)
    glVertex3f(5, 5, -5)
    glVertex3f(5, 5, 5)
    glVertex3f(5, 5, 5)
    glVertex3f(-5, 5, 5)
    glVertex3f(-5, 5, 5)
    glVertex3f(-5, 5, -5)
    #Walls
    glVertex3f(-5, 0, -5)
    glVertex3f(-5, 5, -5)
    glVertex3f(5, 0, -5)
    glVertex3f(5, 5, -5)
    glVertex3f(5, 0, 5)
    glVertex3f(5, 5, 5)
    glVertex3f(-5, 0, 5)
    glVertex3f(-5, 5, 5)
    #Door
    glVertex3f(-1, 0, 5)
    glVertex3f(-1, 3, 5)
    glVertex3f(-1, 3, 5)
    glVertex3f(1, 3, 5)
    glVertex3f(1, 3, 5)
    glVertex3f(1, 0, 5)
    #Roof
    glVertex3f(-5, 5, -5)
    glVertex3f(0, 8, -5)
    glVertex3f(0, 8, -5)
    glVertex3f(5, 5, -5)
    glVertex3f(-5, 5, 5)
    glVertex3f(0, 8, 5)
    glVertex3f(0, 8, 5)
    glVertex3f(5, 5, 5)
    glVertex3f(0, 8, 5)
    glVertex3f(0, 8, -5)
    glEnd()

 
def drawCar():
    glLineWidth(2.5)
    glColor3f(0.0, 1.0, 0.0)
    glBegin(GL_LINES)
    #Front Side
    glVertex3f(-3, 2, 2)
    glVertex3f(-2, 3, 2)
    glVertex3f(-2, 3, 2)
    glVertex3f(2, 3, 2)
    glVertex3f(2, 3, 2)
    glVertex3f(3, 2, 2)
    glVertex3f(3, 2, 2)
    glVertex3f(3, 1, 2)
    glVertex3f(3, 1, 2)
    glVertex3f(-3, 1, 2)
    glVertex3f(-3, 1, 2)
    glVertex3f(-3, 2, 2)
    #Back Side
    glVertex3f(-3, 2, -2)
    glVertex3f(-2, 3, -2)
    glVertex3f(-2, 3, -2)
    glVertex3f(2, 3, -2)
    glVertex3f(2, 3, -2)
    glVertex3f(3, 2, -2)
    glVertex3f(3, 2, -2)
    glVertex3f(3, 1, -2)
    glVertex3f(3, 1, -2)
    glVertex3f(-3, 1, -2)
    glVertex3f(-3, 1, -2)
    glVertex3f(-3, 2, -2)
    #Connectors
    glVertex3f(-3, 2, 2)
    glVertex3f(-3, 2, -2)
    glVertex3f(-2, 3, 2)
    glVertex3f(-2, 3, -2)
    glVertex3f(2, 3, 2)
    glVertex3f(2, 3, -2)
    glVertex3f(3, 2, 2)
    glVertex3f(3, 2, -2)
    glVertex3f(3, 1, 2)
    glVertex3f(3, 1, -2)
    glVertex3f(-3, 1, 2)
    glVertex3f(-3, 1, -2)
    glEnd()
    
def drawTire():
    glLineWidth(2.5)
    glColor3f(0.0, 0.0, 1.0)
    glBegin(GL_LINES)
    #Front Side
    glVertex3f(-1, .5, .5)
    glVertex3f(-.5, 1, .5)
    glVertex3f(-.5, 1, .5)
    glVertex3f(.5, 1, .5)
    glVertex3f(.5, 1, .5)
    glVertex3f(1, .5, .5)
    glVertex3f(1, .5, .5)
    glVertex3f(1, -.5, .5)
    glVertex3f(1, -.5, .5)
    glVertex3f(.5, -1, .5)
    glVertex3f(.5, -1, .5)
    glVertex3f(-.5, -1, .5)
    glVertex3f(-.5, -1, .5)
    glVertex3f(-1, -.5, .5)
    glVertex3f(-1, -.5, .5)
    glVertex3f(-1, .5, .5)
    #Back Side
    glVertex3f(-1, .5, -.5)
    glVertex3f(-.5, 1, -.5)
    glVertex3f(-.5, 1, -.5)
    glVertex3f(.5, 1, -.5)
    glVertex3f(.5, 1, -.5)
    glVertex3f(1, .5, -.5)
    glVertex3f(1, .5, -.5)
    glVertex3f(1, -.5, -.5)
    glVertex3f(1, -.5, -.5)
    glVertex3f(.5, -1, -.5)
    glVertex3f(.5, -1, -.5)
    glVertex3f(-.5, -1, -.5)
    glVertex3f(-.5, -1, -.5)
    glVertex3f(-1, -.5, -.5)
    glVertex3f(-1, -.5, -.5)
    glVertex3f(-1, .5, -.5)
    #Connectors
    glVertex3f(-1, .5, .5)
    glVertex3f(-1, .5, -.5)
    glVertex3f(-.5, 1, .5)
    glVertex3f(-.5, 1, -.5)
    glVertex3f(.5, 1, .5)
    glVertex3f(.5, 1, -.5)
    glVertex3f(1, .5, .5)
    glVertex3f(1, .5, -.5)
    glVertex3f(1, -.5, .5)
    glVertex3f(1, -.5, -.5)
    glVertex3f(.5, -1, .5)
    glVertex3f(.5, -1, -.5)
    glVertex3f(-.5, -1, .5)
    glVertex3f(-.5, -1, -.5)
    glVertex3f(-1, -.5, .5)
    glVertex3f(-1, -.5, -.5)
    glEnd()


def display():
    global mat, rot ,animTrans
    glClear (GL_COLOR_BUFFER_BIT)
    glColor3f (1.0, 1.0, 1.0)
    # viewing transformation 

    
    #Your Code Here
    
    
   # /* draw scene */
    glMatrixMode(GL_MODELVIEW)
    glLoadIdentity()
    glRotate(rot[0],0,1,0)
    glTranslated(mat[0],mat[1],mat[2])
    #glMatrixMode(GL_PROJECTION)
    drawHouse()

    glPushMatrix()
    glTranslated(15,0,0)
    drawHouse()

    glPushMatrix()
    glTranslated(15,0,0)
    drawHouse()


    glPushMatrix()
    glTranslated(15,0,0)
    drawHouse()



    glPopMatrix()
    glPopMatrix()
    glPopMatrix()

    glPushMatrix()
    glTranslated(-15,0,0)
    drawHouse()

    glPushMatrix()
    glTranslated(-15,0,0)
    drawHouse()


    glPushMatrix()
    glTranslated(-15,0,0)
    drawHouse()

    glPopMatrix()
    glPopMatrix()
    glPopMatrix()


#start drawing car 

    glTranslated(animTrans[0],.19,15)

    glPushMatrix()
    drawCar()
    
    glTranslated(0,-.19,2.5)
    glPushMatrix()
    


    glTranslated(2.5,0,0)
    glRotate(math.degrees(animRot[0]),0,0,1)
    drawTire()
    glPopMatrix()

    glPushMatrix()
    glTranslated(-2.5,0,0)
    glRotate(math.degrees(animRot[0]),0,0,1)
    drawTire()
    glPopMatrix()

    glPushMatrix()
    glTranslated(2.5,0,-5)
    glRotate(math.degrees(animRot[0]),0,0,1)
    drawTire()
    glPopMatrix()

    glPushMatrix()
    glTranslated(-2.5,0,-5)
    glRotate(math.degrees(animRot[0]),0,0,1)
    drawTire()
    glPopMatrix()

    glPopMatrix()




    
    glFlush()
    

def keyboard(key, x, y):
    global mat, rot,animTrans,animRot

    if key == chr(27):
        import sys
        sys.exit(0)

    #Your Code Here


    if key == b'a':
        mat[2]=mat[2]-math.cos(math.radians(rot[0]+90))
        mat[0]=mat[0]+math.sin(math.radians(rot[0]+90))

          
    if key == b'd':
        mat[2]=mat[2]+math.cos(math.radians(rot[0]+90))
        mat[0]=mat[0]-math.sin(math.radians(rot[0]+90))
            

    if key == b'w':
        mat[2]=mat[2]+math.cos(math.radians(rot[0]))
        mat[0]=mat[0]-math.sin(math.radians(rot[0]))
        
    if key == b's':
        mat[2]=mat[2]-math.cos(math.radians(rot[0]))
        mat[0]=mat[0]+math.sin(math.radians(rot[0]))
    
    if key == b'q':
       rot[0]=rot[0]-1
       
    if key == b'e':
       rot[0]=rot[0]+1
       
       
    if key == b'r':
        mat[1]=mat[1]-1
        
    
    if key == b'f':
        mat[1]=mat[1]+1
            
    if key == b'h':
       mat=[0,-5,-35]
       rot=[0,0,0]
       animTrans=[0,0,0]
       animRot=[0,0,0]

   
    
    if key == b'o':
       glMatrixMode(GL_PROJECTION)
       glLoadIdentity()
       glOrtho(-50,50,-50,50,.1,50)
        #glOrtho()
       
    
    if key == b'p':
        glMatrixMode(GL_PROJECTION)
        glLoadIdentity()
        gluPerspective(50,DISPLAY_HEIGHT/DISPLAY_WIDTH,.1,200)
   

    if key == b'z':
        import sys
        sys.exit(0)
  
    
    glutPostRedisplay()

def animate(val):
    global animTrans
    animTrans[0]=animTrans[0]+.07
    animRot[0]=animRot[0]-.07

    glutPostRedisplay()
    glutTimerFunc(int(1000.0/60),animate,1)
   
    



glutInit(sys.argv)
glutInitDisplayMode (GLUT_SINGLE | GLUT_RGB)
glutInitWindowSize (int(DISPLAY_WIDTH), int(DISPLAY_HEIGHT))
glutInitWindowPosition (100, 100)
glutCreateWindow (b'OpenGL Lab')
init ()
glutDisplayFunc(display)
glutKeyboardFunc(keyboard)
glutTimerFunc(int(1000.0/60),animate,1) 
glutMainLoop()

# Import a library of functions called 'pygame'
import pygame
from math import pi
import numpy as np
import math

class Point:
	def __init__(self,x,y):
		self.x = x
		self.y = y

class Point3D:
	def __init__(self,x,y,z):
		self.x = x
		self.y = y
		self.z = z
		
class Line3D():
	
	def __init__(self, start, end):
		self.start = start
		self.end = end

def loadOBJ(filename):
	
	vertices = []
	indices = []
	lines = []
	
	f = open(filename, "r")
	for line in f:
		t = str.split(line)
		if not t:
			continue
		if t[0] == "v":
			vertices.append(Point3D(float(t[1]),float(t[2]),float(t[3])))
			
		if t[0] == "f":
			for i in range(1,len(t) - 1):
				index1 = int(str.split(t[i],"/")[0])
				index2 = int(str.split(t[i+1],"/")[0])
				indices.append((index1,index2))
			
	f.close()
	
	#Add faces as lines
	for index_pair in indices:
		index1 = index_pair[0]
		index2 = index_pair[1]
		lines.append(Line3D(vertices[index1 - 1],vertices[index2 - 1]))
		
	#Find duplicates
	duplicates = []
	for i in range(len(lines)):
		for j in range(i+1, len(lines)):
			line1 = lines[i]
			line2 = lines[j]
			
			# Case 1 -> Starts match
			if line1.start.x == line2.start.x and line1.start.y == line2.start.y and line1.start.z == line2.start.z:
				if line1.end.x == line2.end.x and line1.end.y == line2.end.y and line1.end.z == line2.end.z:
					duplicates.append(j)
			# Case 2 -> Start matches end
			if line1.start.x == line2.end.x and line1.start.y == line2.end.y and line1.start.z == line2.end.z:
				if line1.end.x == line2.start.x and line1.end.y == line2.start.y and line1.end.z == line2.start.z:
					duplicates.append(j)
					
	duplicates = list(set(duplicates))
	duplicates.sort()
	duplicates = duplicates[::-1]
	
	#Remove duplicates
	for j in range(len(duplicates)):
		del lines[duplicates[j]]
	
	return lines

def loadHouse():
    house = []
    #Floor
    house.append(Line3D(Point3D(-5, 0, -5), Point3D(5, 0, -5)))
    house.append(Line3D(Point3D(5, 0, -5), Point3D(5, 0, 5)))
    house.append(Line3D(Point3D(5, 0, 5), Point3D(-5, 0, 5)))
    house.append(Line3D(Point3D(-5, 0, 5), Point3D(-5, 0, -5)))
    #Ceiling
    house.append(Line3D(Point3D(-5, 5, -5), Point3D(5, 5, -5)))
    house.append(Line3D(Point3D(5, 5, -5), Point3D(5, 5, 5)))
    house.append(Line3D(Point3D(5, 5, 5), Point3D(-5, 5, 5)))
    house.append(Line3D(Point3D(-5, 5, 5), Point3D(-5, 5, -5)))
    #Walls
    house.append(Line3D(Point3D(-5, 0, -5), Point3D(-5, 5, -5)))
    house.append(Line3D(Point3D(5, 0, -5), Point3D(5, 5, -5)))
    house.append(Line3D(Point3D(5, 0, 5), Point3D(5, 5, 5)))
    house.append(Line3D(Point3D(-5, 0, 5), Point3D(-5, 5, 5)))
    #Door
    house.append(Line3D(Point3D(-1, 0, 5), Point3D(-1, 3, 5)))
    house.append(Line3D(Point3D(-1, 3, 5), Point3D(1, 3, 5)))
    house.append(Line3D(Point3D(1, 3, 5), Point3D(1, 0, 5)))
    #Roof
    house.append(Line3D(Point3D(-5, 5, -5), Point3D(0, 8, -5)))
    house.append(Line3D(Point3D(0, 8, -5), Point3D(5, 5, -5)))
    house.append(Line3D(Point3D(-5, 5, 5), Point3D(0, 8, 5)))
    house.append(Line3D(Point3D(0, 8, 5), Point3D(5, 5, 5)))
    house.append(Line3D(Point3D(0, 8, 5), Point3D(0, 8, -5)))
	
    return house

def loadCar():
    car = []
    #Front Side
    car.append(Line3D(Point3D(-3, 2, 2), Point3D(-2, 3, 2)))
    car.append(Line3D(Point3D(-2, 3, 2), Point3D(2, 3, 2)))
    car.append(Line3D(Point3D(2, 3, 2), Point3D(3, 2, 2)))
    car.append(Line3D(Point3D(3, 2, 2), Point3D(3, 1, 2)))
    car.append(Line3D(Point3D(3, 1, 2), Point3D(-3, 1, 2)))
    car.append(Line3D(Point3D(-3, 1, 2), Point3D(-3, 2, 2)))

    #Back Side
    car.append(Line3D(Point3D(-3, 2, -2), Point3D(-2, 3, -2)))
    car.append(Line3D(Point3D(-2, 3, -2), Point3D(2, 3, -2)))
    car.append(Line3D(Point3D(2, 3, -2), Point3D(3, 2, -2)))
    car.append(Line3D(Point3D(3, 2, -2), Point3D(3, 1, -2)))
    car.append(Line3D(Point3D(3, 1, -2), Point3D(-3, 1, -2)))
    car.append(Line3D(Point3D(-3, 1, -2), Point3D(-3, 2, -2)))
    
    #Connectors
    car.append(Line3D(Point3D(-3, 2, 2), Point3D(-3, 2, -2)))
    car.append(Line3D(Point3D(-2, 3, 2), Point3D(-2, 3, -2)))
    car.append(Line3D(Point3D(2, 3, 2), Point3D(2, 3, -2)))
    car.append(Line3D(Point3D(3, 2, 2), Point3D(3, 2, -2)))
    car.append(Line3D(Point3D(3, 1, 2), Point3D(3, 1, -2)))
    car.append(Line3D(Point3D(-3, 1, 2), Point3D(-3, 1, -2)))

    return car

def loadTire():
    tire = []
    #Front Side
    tire.append(Line3D(Point3D(-1, .5, .5), Point3D(-.5, 1, .5)))
    tire.append(Line3D(Point3D(-.5, 1, .5), Point3D(.5, 1, .5)))
    tire.append(Line3D(Point3D(.5, 1, .5), Point3D(1, .5, .5)))
    tire.append(Line3D(Point3D(1, .5, .5), Point3D(1, -.5, .5)))
    tire.append(Line3D(Point3D(1, -.5, .5), Point3D(.5, -1, .5)))
    tire.append(Line3D(Point3D(.5, -1, .5), Point3D(-.5, -1, .5)))
    tire.append(Line3D(Point3D(-.5, -1, .5), Point3D(-1, -.5, .5)))
    tire.append(Line3D(Point3D(-1, -.5, .5), Point3D(-1, .5, .5)))

    #Back Side
    tire.append(Line3D(Point3D(-1, .5, -.5), Point3D(-.5, 1, -.5)))
    tire.append(Line3D(Point3D(-.5, 1, -.5), Point3D(.5, 1, -.5)))
    tire.append(Line3D(Point3D(.5, 1, -.5), Point3D(1, .5, -.5)))
    tire.append(Line3D(Point3D(1, .5, -.5), Point3D(1, -.5, -.5)))
    tire.append(Line3D(Point3D(1, -.5, -.5), Point3D(.5, -1, -.5)))
    tire.append(Line3D(Point3D(.5, -1, -.5), Point3D(-.5, -1, -.5)))
    tire.append(Line3D(Point3D(-.5, -1, -.5), Point3D(-1, -.5, -.5)))
    tire.append(Line3D(Point3D(-1, -.5, -.5), Point3D(-1, .5, -.5)))

    #Connectors
    tire.append(Line3D(Point3D(-1, .5, .5), Point3D(-1, .5, -.5)))
    tire.append(Line3D(Point3D(-.5, 1, .5), Point3D(-.5, 1, -.5)))
    tire.append(Line3D(Point3D(.5, 1, .5), Point3D(.5, 1, -.5)))
    tire.append(Line3D(Point3D(1, .5, .5), Point3D(1, .5, -.5)))
    tire.append(Line3D(Point3D(1, -.5, .5), Point3D(1, -.5, -.5)))
    tire.append(Line3D(Point3D(.5, -1, .5), Point3D(.5, -1, -.5)))
    tire.append(Line3D(Point3D(-.5, -1, .5), Point3D(-.5, -1, -.5)))
    tire.append(Line3D(Point3D(-1, -.5, .5), Point3D(-1, -.5, -.5)))
    
    return tire

	
# Initialize the game engine
pygame.init()
 
# Define the colors we will use in RGB format
BLACK = (  0,   0,   0)
WHITE = (255, 255, 255)
BLUE =  (  0,   0, 255)
GREEN = (  0, 255,   0)
RED =   (255,   0,   0)

# Set the height and width of the screen
size = [512, 512]

screen = pygame.display.set_mode(size)

pygame.display.set_caption("Shape Drawing")
 
#Set needed variables
done = False
clock = pygame.time.Clock()
start = Point(0.0,0.0)
end = Point(0.0,0.0)

linelist = loadHouse()


#higherTransform=
#matrixList=[np.zeros(4)]





#vars we need #
aspectR=size[0]/size[1]
fov=math.radians(60)
camPos=[0,1,0]
zoomY=1/math.tan(fov/2)
zoomX=zoomY/aspectR
near=.1
far=100
#init transformMatrix
transformMat = np.identity(4)


#init rotationMat
rotationMat = np.zeros((4,4))


#init clip Matrix
clipMat=np.zeros((4,4))

clipMat[0,0]=zoomY
clipMat[1,1]=zoomX
clipMat[2,2]=((far+near)/(far-near))
clipMat[2,3]=((-2*far*near)/(far-near))
clipMat[3,2]=1

#init projection matrix
projectMat=np.zeros((3,3))
projectMat[0,0]=size[0]/2
projectMat[0,2]=size[0]/2
projectMat[1,1]=((-size[0])/2)
projectMat[1,2]=(size[0]/2)
projectMat[2,2]=1



#init transform/rot 
transformCam=[0,0,0]
rotateCam=0

transform=np.identity(4)



def toPoint(toPoint):
	return Point3D(toPoint[0],toPoint[1],toPoint[2])


def buildTrans(x,y,z,degree):
	transform=np.identity(4)
	transform[0,3]+=x
	transform[1,3]+=y
	transform[2,3]+=z


	return transform




def objectToWorld(list,x,y,z,d):
	result=[]
	transform=buildTrans(x,y,z,d)

	for line in list:
		start=toArray(line.start)
		end=toArray(line.end)
		startR=np.matmul(transform,start)
		endR=np.matmul(transform,end)
		result.append(Line3D(toPoint(startR),toPoint(endR)))
	return result




def toArray(point):
 	return np.array([point.x,point.y,point.z,1])



def applyClip(line):
	start,end = line.start,line.end
	resultStart=np.matmul(clipMat,toArray(start))
	resultEnd=np.matmul(clipMat,toArray(end))
	start = Point3D(resultStart[0],resultStart[1],resultStart[2])
	end = Point3D(resultEnd[0],resultEnd[1],resultEnd[2])
	

	return Line3D(start,end)



def worldToCam(line):
	start,end = line.start,line.end
	transformMat[0,3]=-transformCam[0]
	transformMat[1,3]=-transformCam[1]
	transformMat[2,3]=-transformCam[2]


	rotationMat[0,0]=math.cos(math.radians(rotateCam))
	rotationMat[1,1]=1
	rotationMat[0,2]=-math.sin(math.radians(rotateCam))
	rotationMat[2,0]=math.sin(math.radians(rotateCam))
	rotationMat[2,2]=math.cos(math.radians(rotateCam))

	
	newTransForm=np.matmul(rotationMat,transformMat)
	resultStart=np.matmul(newTransForm,toArray(start))
	resultEnd=np.matmul(newTransForm,toArray(end))
	start = Point3D(resultStart[0],resultStart[1],resultStart[2])
	end = Point3D(resultEnd[0],resultEnd[1],resultEnd[2])

	return Line3D(start,end)

def toScreenSpace(line):
	startBool=True
	endBool=True
	
	endZ=True
	startz=True

	nearZ= True

	start,end = line.start,line.end


	wStart=line.start.z/fov
	wEnd=line.end.z/fov

	startArray=toArray(start)[:3]
	endArray=toArray(end)[:3]

	startN=startArray/wStart
	endN=endArray/wEnd


	if(abs(start.x)>abs(wStart)):
		startBool=False
		
	if(abs(start.y)>abs(wStart)):
		startBool=False

	if(abs(end.x)>abs(wEnd)):
		endBool=False
	
	if(abs(end.y)>abs(wEnd)):
		endBool=False
		None
	
	if(abs(start.z)>far):
		startz=False
		None

	if(abs(end.z)>far):
		endZ=False
		None


	if not endBool and not startBool and (start.x != end.x):
		return None

	if abs(start.z) > far and abs(end.z)>far:
		return None

	if start.z<near or end.z<near:
		return None

	resultStart=np.matmul(projectMat,startN)
	resultEnd=np.matmul(projectMat,endN)


	start = Point3D(resultStart[0],resultStart[1],resultStart[2])
	end = Point3D(resultEnd[0],resultEnd[1],resultEnd[2])

	#return line
	return Line3D(start,end)


def pipeLine(line):
	worldLine=worldToCam(line)
	projectionLine=applyClip(worldLine)
	screenLine=toScreenSpace(projectionLine)
	return screenLine



    

def pushMat():
    
    None
def popMat():
    matrixStack.pop()




#append new house
linelist+=objectToWorld(loadHouse(),15,0,0,0)
linelist+=objectToWorld(loadHouse(),30,0,0,0)
linelist+=objectToWorld(loadHouse(),-15,0,0,0)
linelist+=objectToWorld(loadHouse(),-30,0,0,0)

linelist+=objectToWorld(loadCar(),5,-.5,-10,0)
linelist+=objectToWorld(loadTire(),8,0,-12,0)
linelist+=objectToWorld(loadTire(),8,0,-8,0)
linelist+=objectToWorld(loadTire(),2,0,-12,0)
linelist+=objectToWorld(loadTire(),2,0,-8,0)




while not done:
 
	# This limits the while loop to a max of 100 times per second.
	# Leave this out and we will use all CPU we can.
	#clock.tick(4)
	# Clear the screen and set the screen background
	screen.fill(BLACK)
	#Controller Code#
	#####################################################################

	for event in pygame.event.get():
		if event.type == pygame.QUIT: # If user clicked close
			done=True
			
	pressed = pygame.key.get_pressed()
		
	if pressed[pygame.K_s]:
		transformCam[0]=transformCam[0]-math.sin(math.radians(rotateCam))
		transformCam[2]=transformCam[2]-math.cos(math.radians(rotateCam))

	if pressed[pygame.K_w]:
		transformCam[0]=transformCam[0]+math.sin(math.radians(rotateCam))
		transformCam[2]=transformCam[2]+math.cos(math.radians(rotateCam))
		

	if pressed[pygame.K_a]:
		transformCam[0]=transformCam[0]-math.cos(math.radians(rotateCam))
		transformCam[2]=transformCam[2]+math.sin(math.radians(rotateCam))

	if pressed[pygame.K_d]:
		transformCam[0]=transformCam[0]+math.cos(math.radians(rotateCam))
		transformCam[2]=transformCam[2]-math.sin(math.radians(rotateCam))
		
	if pressed[pygame.K_e]:
		rotateCam+=1
		
		
	if pressed[pygame.K_q]:
		rotateCam-=1
		
		
		
	if pressed[pygame.K_f]:
		transformCam[1]-=1
		
	if pressed[pygame.K_r]:
		transformCam[1]+=1
		
	if pressed[pygame.K_h]:
		transformCam=[0,0,0]
		rotateCam=0
		

	#Viewer Code#
	#####################################################################
	
	for s in linelist:
		#BOGUS DRAWING PARAMETERS SO YOU CAN SEE THE HOUSE WHEN YOU START UP
		#print(s.start.x,s.start.y,s.start.z,1)
		line = pipeLine(s)
		if line != None:
			#pygame.draw.line(screen, BLUE, (20*s.start.x+200, -20*s.start.y+200), (20*s.end.x+200, -20*s.end.y+200))
			pygame.draw.line(screen, BLUE, (line.start.x, line.start.y), (line.end.x, line.end.y))
		pygame.display.flip()

	# Go ahead and update the screen with what we've drawn.
	# This MUST happen after all the other drawing commands.
	#pygame.display.flip()
 
# Be IDLE friendly
pygame.quit()

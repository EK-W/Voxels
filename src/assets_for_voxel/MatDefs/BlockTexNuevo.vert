uniform mat4 g_WorldViewProjectionMatrix;

attribute vec3 inPosition;
attribute vec2 inTexCoord;
attribute vec3 inNormal;
attribute vec4 inColor;
attribute vec2 inTexCoord2; //TEX MAP OFFSET.

varying vec2 texCoord;
varying vec2 texMapOffset;
varying float light;

void main(){
	light = inColor.w; //max(inColor.z, inColor.w);
	//light = inNormal.x; //1.0;
	//light = 0.5;
    gl_Position = g_WorldViewProjectionMatrix * vec4(inPosition, 1.0);
    texCoord = inTexCoord;
    texMapOffset = inTexCoord2;
}
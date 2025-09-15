//attribute vec4 a_Position;
//attribute vec2 a_TextureCoordinate;
//
//uniform float u_TextureRotationAngle;
//uniform float u_AspectRatio;
//uniform float u_ScreenAspectRatio;
//
//varying vec2 v_TextureCoordinate;
//
//void main() {
//    float angle = radians(u_TextureRotationAngle);
//
//    float s = sin(angle);
//    float c = cos(angle);
//
//    vec2 pos = a_Position.xy;
//    vec2 rotatedPos;
//
//    rotatedPos.x = pos.x * c + pos.y * s;
//    rotatedPos.y = - pos.x * s + pos.y * c;
//
//    float scaleX = 1.0;
//    float scaleY = 1.0;
//
//    if (u_TextureRotationAngle == 90.0 || u_TextureRotationAngle == 270.0) {
//        if (u_AspectRatio > 1.0) {
//            scaleX = 1.0 / u_AspectRatio;
//        } else {
//            scaleY = u_AspectRatio;
//        }
//    } else {
//        if (u_AspectRatio > 1.0) {
//            scaleY = 1.0 / u_AspectRatio;
//        } else {
//            scaleX = u_AspectRatio;
//        }
//    }
//
//    rotatedPos.x = rotatedPos.x * scaleX / u_ScreenAspectRatio;
//    rotatedPos.y = rotatedPos.y * scaleY;
//
//    gl_Position = vec4(rotatedPos, 0.0, 1.0);
//    v_TextureCoordinate = a_TextureCoordinate;
//}

attribute vec4 a_Position;
attribute vec2 a_TextureCoordinate;

uniform float u_Cnt;
uniform float u_AspectRatio;
uniform float u_ScreenAspectRatio;

varying vec2 v_TextureCoordinate;

bool isEqual(float a, float b) {
    return abs(a - b) < 0.001;
}

void main() {

    vec4 pos = a_Position;

    float scaleX = 1.0;
    float scaleY = 1.0;

    if (u_AspectRatio > 1.0) {
        scaleY = 1.0 / u_AspectRatio;
    } else {
        scaleX = u_AspectRatio;
    }

    scaleX *= 0.5;
    scaleY *= 0.5;

    if (isEqual(pos.x, 0.0) && isEqual(pos.y, 1.0)) {
        pos.x = -1.0+ (scaleX / u_ScreenAspectRatio);
    }
    else if (isEqual(pos.x, -1.0) && isEqual(pos.y, 0.0)) {
        pos.y = 1.0 - scaleY;
    }
    else if (isEqual(pos.x, 0.0) && isEqual(pos.y, 0.0)) {
        pos.x = -1.0 + (scaleX / u_ScreenAspectRatio);
        pos.y = 1.0 - scaleY;
    }

    float cnt = mod(u_Cnt, (2.0 - scaleY));

    if (isEqual(pos.y, 1.0)) {
        if ((pos.y-scaleY-cnt) > -1.0) pos.y -= cnt;
    }
    else {
        if ((pos.y-cnt) > -1.0) pos.y -= cnt;
    }

    gl_Position = pos;
    v_TextureCoordinate = a_TextureCoordinate;
}
#version 150

uniform sampler2D DiffuseSampler;

uniform float C1;
uniform float C2;
uniform float zoomFactor;

in vec2 oneTexel;
in vec2 texCoord;

out vec4 fragColor;

void main() {
	float X = texCoord.x - 0.5;
	float Y = texCoord.y - 0.5;

    float R = sqrt(X * X + Y * Y) / zoomFactor;

	R = clamp(R, 0.0, 0.817);

	float T = tan(C1 * R) * C2;
	float C = T / R;

	X *= C;
	Y *= C;

	float A = 2 * abs(X);
	if(A > 1.0) {
		X = X / A;
		Y = Y / A;
	}

	A = 2 * abs(Y);
	if(A > 1.0) {
		X = X / A;
		Y = Y / A;
	}

	X += 0.5;
	Y += 0.5;

	vec2 texels = vec2(X, Y) / oneTexel;
	vec2 texelsFract = fract(texels);

	vec4 v11 = texture2D(DiffuseSampler, vec2(X, Y));
	vec4 v12 = texture2D(DiffuseSampler, vec2(X + oneTexel.x, Y));
	vec4 v21 = texture2D(DiffuseSampler, vec2(X, Y + oneTexel.y));
	vec4 v22 = texture2D(DiffuseSampler, vec2(X + oneTexel.x, Y + oneTexel.y));

	vec4 color = mix(mix(v11, v12, texelsFract.x), mix(v21, v22, texelsFract.x), texelsFract.y);
	fragColor = vec4(color.rgb, 1.0);
}
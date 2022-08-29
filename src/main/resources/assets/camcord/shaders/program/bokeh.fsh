#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D DepthSampler;

uniform vec2 OutSize;

uniform vec2 blurNormal;
uniform float firstPass;

uniform float nearPlane;
uniform float farPlane;

in vec2 texCoord;

out vec4 fragColor;

vec3 sqr(vec3 x) {
	return x * x;
}

float linearizedDepth(vec2 uv) {
	float n = nearPlane;
	float f = farPlane;
	float z = texture(DepthSampler, uv).x;
	return (2.0 * n) / (f + n - z * (f - n));
}

float random(vec2 uv) {
	uv = fract(uv * vec2(5.3987, 5.4421));
	uv += dot(uv.yx, uv.xy + vec2(21.5351, 14.3137));
	return fract(uv.x * uv.y * 95.4307);
}

void main() {
	float depth = linearizedDepth(texCoord) / 2.0;
	float blurDepth = depth * 64.0;

	vec2 uv1 = texCoord - 0.5 * blurDepth * (normalize(blurNormal) / OutSize);
	vec2 uv2 = texCoord + 0.5 * blurDepth * (normalize(blurNormal) / OutSize);
	vec2 uv3 = (uv2 - uv1) / 16.0;
	vec2 uv4 = uv1 + (random(texCoord) - 0.5) * uv3;

	vec3 color = vec3(0.0);
	for (int i = 0; i < 16; ++i) {
		if (firstPass >= 1.0) {
			vec3 smp = sqr(texture(DiffuseSampler, uv4).xyz);
			color += smp * smp;
		} else {
			color += sqr(texture(DiffuseSampler, uv4).xyz);
		}
		uv4 += uv3;
	}

	color /= 16.0;

	if (firstPass >= 1.0) {
		color = max(color, 0.0);
		fragColor = vec4(sqrt(color * 4.0), 1.0);
	} else {
		fragColor = vec4(sqrt(color), 1.0);
	}
}

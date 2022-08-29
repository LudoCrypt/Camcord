#version 150

uniform sampler2D DiffuseSampler;

in vec2 texCoord;
in vec2 OutSize;

out vec4 fragColor;

void main() {
	if (texCoord.x < (1.0 / 8.0) || texCoord.x > (7.0 / 8.0)) {
		fragColor = vec4(0.0, 0.0, 0.0, 1.0);
	} else {
		float blend = 0.0;
		if (texCoord.x < (17.0 / 128.0)) {
			blend = distance(texCoord.x, (17.0 / 128.0)) * 128.0;
		} else if (texCoord.x > (111.0 / 128.0)) {
			blend = distance(texCoord.x, (111.0 / 128.0)) * 128.0;
		}
		fragColor = mix(vec4(texture(DiffuseSampler, texCoord).xyz, 1.0), vec4(0.0, 0.0, 0.0, 1.0), blend);
	}
}

#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D Bokeh;

in vec2 texCoord;

out vec4 fragColor;

vec4 sqr(vec4 x) {
	return x * x;
}

void main() {
	vec4 bokeh1 = sqr(texture(DiffuseSampler, texCoord));
	vec4 bokeh2 = sqr(texture(Bokeh, texCoord));

	fragColor = vec4(sqrt(min(bokeh1, bokeh2)));
}

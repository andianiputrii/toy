$input v_color, v_position, v_dir

/*
* Copyright 2017 Stanislav Pidhorskyi. All rights reserved.
* License: https://github.com/bkaradzic/bgfx#license-bsd-2-clause
*/

uniform vec4 u_sky_p0;
#define u_sun_size u_sky_p0.x
#define u_sun_bloom u_sky_p0.y
uniform vec4 u_sun_direction;
uniform vec4 u_sun_luminance;

#include <common.sh>
#include <convert.sh>

void main()
{
	float size2 = u_sun_size * u_sun_size;

	vec3 lightDir = normalize(u_sun_direction.xyz);
	float dist = 2.0 * (1.0 - dot(normalize(v_dir), lightDir));
	float sun  = exp(-dist/ u_sun_bloom / size2) + step(dist, size2);
	float sun2 = min(sun * sun, 1.0);
	vec3 color = v_color.rgb + sun2;
	color = toGamma(color);

	gl_FragColor = vec4(color, 1.0);
}

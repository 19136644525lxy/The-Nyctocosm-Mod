#built using mc-build (https://github.com/mc-build/mc-build)

tp @s[tag=!upper,x_rotation=..80] ~ ~ ~ ~ ~3
execute at @s run tp @s[tag=upper,x_rotation=-90..-1] ~ ~ ~ ~ ~5
execute at @s run tp @s[tag=upper,x_rotation=0..15] ~ ~ ~ ~ ~8
execute at @s run tp @s[tag=upper,x_rotation=0..30] ~ ~ ~ ~ ~3
execute at @s run tp @s[tag=upper,x_rotation=0..60] ~ ~ ~ ~ ~1.5
execute at @s run tp @s[tag=upper,x_rotation=0..90] ~ ~ ~ ~ ~0.8
execute at @s run function emissary:attacks/phase4/apocalypse_rain/slowcast
execute at @s[tag=!end] run function emissary:attacks/phase4/apocalypse_rain/slowcast
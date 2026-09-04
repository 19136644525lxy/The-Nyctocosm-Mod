#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players reset @s s.transmittance_timer
effect clear @s levitation
execute at @s run particle flash ~ ~1 ~ 0.3 0.3 0.3 0 10 force
execute at @s run particle smoke ~ ~1 ~ 0.3 0.3 0.3 0.2 100
execute at @s run particle minecraft:wax_off ~ ~1 ~ 0.3 0.6 0.3 10 100
execute at @s rotated ~ 0 positioned ~ ~1 ~ run function effects:shockwave/transmittance_first
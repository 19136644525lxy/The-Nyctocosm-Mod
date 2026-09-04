#built using mc-build (https://github.com/mc-build/mc-build)

execute as @e[tag=plane,tag=origin,distance=..3] at @s run function spells:cancel_cast
execute at @s run particle crit ~ ~1 ~ 0.3 0.6 0.3 0.2 20
tag @s add cancelsnuck
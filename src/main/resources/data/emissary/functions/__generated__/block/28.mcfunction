#built using mc-build (https://github.com/mc-build/mc-build)

particle flash ~ ~2 ~ 0.4 0.4 0.4 0 5 force
execute rotated ~ 0 run function animated_java:ajemissary/summon/wingless
execute as @e[tag=emissary,tag=emissarymodel] at @s run function emissary:__generated__/block/29
#built using mc-build (https://github.com/mc-build/mc-build)

tp @s ^ ^ ^0.7 ~ ~
execute at @e[tag=emissary,tag=locator,tag=wing,tag=phase2] run particle white_ash ~ ~ ~ 0 0 0 0.05 2 force
execute if entity @a[distance=..5,gamemode=!spectator,tag=!emissarydebug] run function emissary:attacks/phase3/runclose/__generated__/block/479
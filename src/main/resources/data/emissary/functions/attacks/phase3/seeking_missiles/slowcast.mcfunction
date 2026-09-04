#built using mc-build (https://github.com/mc-build/mc-build)

execute if entity @e[tag=spell,tag=reflector,tag=!matchedid,distance=..3] run scoreboard players operation TEMP s.damage = seekingmissilesdamage em.value
execute as @e[tag=reflector,tag=spell,distance=..3] at @s run function emissary:attacks/phase3/seeking_missiles/__generated__/block/606
execute rotated as @e[tag=spell,tag=reflector,distance=..3] run tp @s ~ ~ ~ ~ ~
execute as @s[scores={em.timer=61..}] run function emissary:attacks/phase3/seeking_missiles/__generated__/block/607
execute at @s[scores={em.timer=..60}] anchored eyes facing entity @a[distance=..300,limit=1,gamemode=!spectator,tag=!emissarydebug,sort=nearest] eyes positioned ^ ^ ^0.5 rotated as @s positioned ^ ^ ^6 facing entity @s feet facing ^ ^ ^-1 positioned as @s run tp @s ~ ~ ~ ~ ~
execute at @s run tp @s ^ ^ ^0.7
particle cloud ~ ~ ~ 0.1 0.1 0.1 0.05 2 force
particle firework ~ ~ ~ 0.1 0.1 0.1 0 1 force
particle electric_spark ~ ~ ~ 0.1 0.1 0.1 0 5 force
particle flame ~ ~ ~ 0.1 0.1 0.1 0 5 force
execute at @s positioned ~-0.5 ~-0.5 ~-0.5 if entity @a[gamemode=!spectator,tag=!emissarydebug,dx=0] run function emissary:attacks/phase3/seeking_missiles/__generated__/block/608
execute if score @s em.timer matches ..0 run tag @s add end
execute at @s unless block ~ ~ ~ #nyctocosm:passthrough run function emissary:attacks/phase3/seeking_missiles/__generated__/block/609
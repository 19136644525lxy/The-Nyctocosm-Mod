#built using mc-build (https://github.com/mc-build/mc-build)

execute if entity @e[tag=spell,tag=reflector,tag=!matchedid,distance=..3] run scoreboard players operation TEMP s.damage = meteorshowerdamage em.value
execute as @e[tag=reflector,tag=spell,distance=..3] at @s run function emissary:attacks/phase1/meteor_shower/__generated__/block/63
execute rotated as @e[tag=spell,tag=reflector,distance=..3] run tp @s ~ ~ ~ ~ ~
execute at @s anchored eyes facing entity @a[distance=..300,limit=1,gamemode=!spectator,tag=!emissarydebug,sort=nearest] feet positioned ^ ^ ^0.5 rotated as @s positioned ^ ^ ^14 facing entity @s eyes facing ^ ^ ^-1 positioned as @s run tp @s ~ ~ ~ ~ ~
execute at @s run tp @s ^ ^ ^1
particle lava ~ ~ ~ 0.5 0.5 0.5 0 3 force
particle flame ~ ~ ~ 0.5 0.5 0.5 0.05 7 force
particle smoke ~ ~ ~ 0.5 0.5 0.5 0.05 5 force
particle campfire_cosy_smoke ~ ~ ~ 0.5 0.5 0.5 0 2 force
particle wax_on ~ ~ ~ 0.5 0.5 0.5 0.05 5 force
execute if score @s em.value matches 60.. run tag @s add end
execute at @s unless block ~ ~ ~ #nyctocosm:passthrough run function emissary:attacks/phase1/meteor_shower/__generated__/block/64
execute at @s positioned ~-0.5 ~-0.5 ~-0.5 if entity @a[dx=0,gamemode=!spectator,tag=!emissarydebug] run function emissary:attacks/phase1/meteor_shower/__generated__/block/65
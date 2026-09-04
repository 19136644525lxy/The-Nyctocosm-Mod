#built using mc-build (https://github.com/mc-build/mc-build)

execute if entity @e[tag=spell,tag=reflector,tag=!matchedid,distance=..3] run scoreboard players operation TEMP s.damage = pillarsdamage em.value
execute as @e[tag=reflector,tag=spell,distance=..3] at @s run function emissary:attacks/phase1/pillars/__generated__/block/315
execute rotated as @e[tag=spell,tag=reflector,distance=..3] run tp @s ~ ~ ~ ~ ~
execute at @s anchored eyes facing entity @a[distance=..300,limit=1,gamemode=!spectator,tag=!emissarydebug,sort=nearest] feet positioned ^ ^ ^0.5 rotated as @s positioned ^ ^ ^15 facing entity @s eyes facing ^ ^ ^-1 positioned as @s run tp @s ~ ~ ~ ~ ~
execute at @s run tp @s ^ ^ ^0.6
execute at @s positioned ~-2 ~-3 ~-2 as @a[gamemode=!spectator,tag=!emissarydebug,dy=20,dx=3,dz=3] run function emissary:attacks/phase1/pillars/__generated__/block/316
execute at @s run function emissary:attacks/phase1/pillars/__generated__/block/317
execute at @s positioned ~ ~-3 ~ run fill ~-2 ~ ~-2 ~2 ~20 ~2 fire replace air